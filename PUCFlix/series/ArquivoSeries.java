//////////////////////////////////////////////////
// PACOTE

package series;

//////////////////////////////////////////////////
// BIBLIOTECAS DO SISTEMA

import java.util.ArrayList;

//////////////////////////////////////////////////
// BIBLIOTECAS PRÓPRIAS

import episodios.*;
import aeds3.*;

//////////////////////////////////////////////////
// CLASSE ARQUIVOSERIES EM SI

public class ArquivoSeries extends Arquivo <Serie> 
{
    // VARIÁVEIS
    ArvoreBMais <ParNomeSerieId> indiceNomeSerie;
    Arquivo     <Serie>            arquivoSeries;
    ArvoreBMais <ParIdId>                indices;

    public ArquivoSeries () throws Exception 
    {
        super ("series", Serie.class.getConstructor ());
        
        indiceNomeSerie = new ArvoreBMais <> 
        (
            ParNomeSerieId.class.getConstructor (), 
            5, "./dados/"+nomeEntidade+"/indiceNomeSerie.d.db"
        );
    }

    @Override public int create (Serie s) throws Exception 
    {
        int id = super.create (s);
        indiceNomeSerie.create (new ParNomeSerieId (s.getNome (), id));
        return id;
    }
 
    public Serie [] readNome (String nome) throws Exception 
    {
        if (nome.length ()==0)
            return null;
            
        ArrayList<ParNomeSerieId> ptis = indiceNomeSerie.read (new ParNomeSerieId (nome, -1));
        if (ptis.size ()>0) 
        {
            Serie[] series = new Serie[ptis.size ()];
            int i=0;
            
            for (ParNomeSerieId pti: ptis) 
                series[i++] = read (pti.getId ());
            return series;
        }
        else 
            return null;
    }

    @Override
    public boolean delete (int id) throws Exception 
    {
        Serie s = read (id);   
        if (s!=null) 
        {
            if (super.delete (id))
                return indiceNomeSerie.delete (new ParNomeSerieId (s.getNome (), id));
        }
        return false;
    }

    public boolean delete (String nome, int id) throws Exception 
    {
        ArquivoEpisodios arqEpisodios = new ArquivoEpisodios ();
        
        Serie s = read (id); 
        
        if (s != null && s.getID () == id && s.getNome ().equals (nome)){

            System.out.println (s);
           
           
           Episodio[] ids = arqEpisodios.readEpisodiosSerie (id); 
            
            if (ids!= null)
            {
                boolean deletados = arqEpisodios.deleteEpisodioSerie (id);
                if (deletados)
                {
                    return delete (id);
                }
            } 

                return delete (id);
            
        }
        return false;
    }

    @Override public boolean update (Serie novaSerie) throws Exception 
    {
        Serie s = read (novaSerie.getID ());   
        if (s!=null) 
        {
            if (super.update (novaSerie)) 
            {
                if (!s.getNome ().equals (novaSerie.getNome ())) 
                {
                    indiceNomeSerie.delete (new ParNomeSerieId (s.getNome (), s.getID ()));
                    indiceNomeSerie.create (new ParNomeSerieId (novaSerie.getNome (), s.getID ()));
                }
                return true;
            }
        }
        return false;
    }


    public static boolean serieExiste (int id) throws Exception
    {
        ArquivoSeries arquivoSeries = new ArquivoSeries ();
        Serie s = arquivoSeries.read (id); 
        if (s != null) 
        {
            return true;
        }
        return false;
    }
}

//////////////////////////////////////////////////