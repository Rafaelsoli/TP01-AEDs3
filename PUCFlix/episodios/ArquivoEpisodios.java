package episodios;
import aeds3.*;
import episodios.*;
import series.*;
import java.util.ArrayList;

//Ideia printar a avaliação da serie, que será a media das avaliações dos seus episodios

public class ArquivoEpisodios extends Arquivo<Episodio> 
{

  Arquivo<Episodio> arquivoEpisodio;
  ArvoreBMais <ParIdId> indiceIdEpisodio_IdSerie;
  ArvoreBMais <ParTituloId> indiceNomeEpisodio;

  public ArquivoEpisodios() throws Exception 
  {
    super("episodio", Episodio.class.getConstructor());

    indiceIdEpisodio_IdSerie = new ArvoreBMais<>(
      ParIdId.class.getConstructor(),
      5,
      "./dados/"+nomeEntidade+"/indiceIdEpisodios_IdSerie.db"
    );

    indiceNomeEpisodio = new ArvoreBMais<>(
      ParTituloId.class.getConstructor(),
      5,
      "./dados/"+nomeEntidade+"/indiceNomeEpisodios.db"
    );
  }

  @Override
  public int create(Episodio e) throws Exception
  {
    if(ArquivoSeries.serieExiste(e.getID_serie()) == false)
    {
      throw new Exception("Episodio não pode ser criado pois a serie vinculada não existe");
    }

    int id = super.create(e);
    
    indiceIdEpisodio_IdSerie.create(new ParIdId(e.getID_serie() , id));
    indiceNomeEpisodio.create(new ParTituloId(e.getNome(), id));

    return id;
  }

  public Episodio[] readNomeEpisodio(String nome) throws Exception
  {
    if(nome.length() == 0)
      return null;

    ArrayList<ParTituloId> ptis = indiceNomeEpisodio.read(new ParTituloId(nome, -1));
    if(ptis.size() > 0)
    {
      Episodio[] episodios = new Episodio[ptis.size()];
      int i = 0;
      for(ParTituloId pti: ptis)
        episodios[i++] = read(pti.getId());
      return episodios;

    }else
      return null;
  }

  public Episodio[] readNomeEpisodioPorSerie(String nome, int id_serie) throws Exception
  {
    if(nome.length() == 0)
      return null;

    ArrayList<ParTituloId> ptis = indiceNomeEpisodio.read(new ParTituloId(nome, -1));
    if(ptis.size() > 0)
    {
      Episodio[] episodios = new Episodio[ptis.size()];
      int i = 0;
      for(ParTituloId pti: ptis)
        episodios[i++] = read(pti.getId());

      ArrayList<Episodio> episodiosSerie = new ArrayList<>();
      for(Episodio e : episodios)
      {
        if(e.getID_serie() == id_serie)
          episodiosSerie.add(e);
      }

      return episodiosSerie.toArray(new Episodio[episodiosSerie.size()]);
    }else
      return null;
  }
  
  public Episodio[] readEpisodiosSerie(int id_serie) throws Exception
  {
    ArrayList<ParIdId> pIds = indiceIdEpisodio_IdSerie.read(new ParIdId(id_serie, -1));
  
    if(pIds.size() > 0)
    {
      Episodio[] episodios = new Episodio[pIds.size()];
      int i = 0;
      for(ParIdId pID : pIds)
        episodios[i++] = read(pID.getId_agregado());
      return episodios;
    }else
      return null;
  }

  @Override
  public boolean delete(int id) throws Exception
  {
    Episodio e = read(id);
    if(e != null)
    {
      if(super.delete(id))
        return indiceIdEpisodio_IdSerie.delete(new ParIdId(e.getID_serie(), id)) 
            && indiceNomeEpisodio.delete(new ParTituloId(e.getNome(), id));
    }
    return false;
  }

  public boolean deleteEpisodioSerie(int id_serie) throws Exception
  {
    ArrayList<ParIdId> pIds = indiceIdEpisodio_IdSerie.read(new ParIdId(id_serie, -1));

    System.out.println("Quantidade de episódios da serie deletados: " + pIds.size()); // < --- aquiii ó

    if(pIds.size() > 0)
    {
      for(ParIdId pID : pIds)
        delete(pID.getId_agregado());
      return true;
 
   } 
    return false;
  }

  @Override
  public boolean update(Episodio novoEpisodio) throws Exception
  {
    Episodio e = read(novoEpisodio.getID());
    if(e != null)
    {
      if(super.update(novoEpisodio))
      {
        if(!e.getNome().equals(novoEpisodio.getNome()))
        {
          indiceNomeEpisodio.delete(new ParTituloId(e.getNome(), e.getID()));
          indiceNomeEpisodio.create(new ParTituloId(novoEpisodio.getNome(), e.getID()));
        }

        if(e.getID_serie() != novoEpisodio.getID_serie())
        {
          indiceIdEpisodio_IdSerie.delete(new ParIdId(e.getID_serie(), e.getID()));
          indiceIdEpisodio_IdSerie.create(new ParIdId(novoEpisodio.getID_serie(), e.getID()));
        }

        return true;
      }
    }
    return false;
  }
}

