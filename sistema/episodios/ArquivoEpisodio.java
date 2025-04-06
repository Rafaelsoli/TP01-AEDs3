//////////////////////////////////////////////////
// Pacote

package sistema.episodios;

//////////////////////////////////////////////////
// BIBLIOTECAS

import sistema.series.ArquivoSerie;
import java.util.ArrayList;
// import aed3.Arquivo;

//////////////////////////////////////////////////
// Implementação do ArquivoEpisodio em si

public class ArquivoEpisodio extends aed3.Arquivo <Episodio> 
{

  public ArquivoEpisodio () throws Exception
  {
    super("episodio", Episodio.class.getConstructor());
  }

  @Override
  public int create (Episodio e) throws Exception
  {
    if (ArquivoSerie.serieExiste (e.getIdSerie ()) == false)
    {
      throw new Exception ("Episodio não pode ser criado pois a serie vinculada não existe");
    }

    return super.create (e);
  }

  public Episodio [] readNomeEpisodio (String nome) throws Exception
  {
      if (nome.length () == 0)
      {
        return null;
      }

      ArrayList <Episodio> episodios = new ArrayList <> ();
      int ultimo_id = super.getUltimoId  ();
      int id = 0; 

      while (id <= ultimo_id)
      {
        Episodio ep = read (id);
        if (ep != null && ep.getNome().equalsIgnoreCase (nome))
        {
          episodios.add (ep);
        }

        id = id + 1;
      }

      if (episodios.size() > 0)
      {
        return episodios.toArray (new Episodio[episodios.size()]);
      }

      return null;
    }

  public Episodio [] readEpisodioSerie (String nome, int id_serie) throws Exception
  {
    if (nome.length() == 0)
    {
      return null;
    }

    ArrayList<Episodio> episodios = new ArrayList <> ();
    int ultimo_id = super.getUltimoId ();
    int id = 0;

    while (id <= ultimo_id)
    {
      Episodio ep = read (id);
      if (ep != null && ep.getNome().equalsIgnoreCase (nome) && ep.getIdSerie () == id_serie)
      {
        episodios.add (ep);
      }

      id = id + 1;
    }

    if (episodios.size() > 0)
    {
      return episodios.toArray (new Episodio[episodios.size()]);
    }

    return null;
  }


  public Episodio[] readEpisodiosSerie (int id_serie) throws Exception
  {
    ArrayList<Episodio> episodios = new ArrayList<>();
    int ultimo_id = super.getUltimoId ();
    int id = 0;

    while (id <= ultimo_id)
    {
      Episodio ep = read (id);
      if (ep != null && ep.getIdSerie () == id_serie)
      {
        episodios.add (ep);
      }

      id = id + 1;
    }

    if (episodios.size() > 0)
    {
      return episodios.toArray (new Episodio[episodios.size()]);
    }

    return null;
  }

  @Override
  public boolean delete (int id) throws Exception
  {
    return super.delete (id);
  }

  public boolean deleteEpisodioSerie (int id_serie) throws Exception
  {
    boolean algum_apagado = false;
    int ultimo_id = super.getUltimoId ();
    int id = 0;

    while (id <= ultimo_id)
    {
      Episodio ep = read (id);
      if (ep != null && ep.getIdSerie() == id_serie)
      {
        delete (id);
        algum_apagado = true;
      }

      id = id + 1;
    }

    return algum_apagado;
  }

  @Override
  public boolean update (Episodio novoEpisodio) throws Exception
  {
    Episodio antigo = read (novoEpisodio.getId());

    if (antigo != null)
    {
      return super.update (novoEpisodio);
    }

    return false;
  }
}
