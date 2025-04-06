//////////////////////////////////////////////////
// Pacote

package sistema.series;

//////////////////////////////////////////////////
// BIBLIOTECAS

import aed3.HashExtensivel; // Para HashExtensivel
// import aed3.Arquivo; // Para arquivo

//////////////////////////////////////////////////
// Implementação do ArquivoSerie em si

public class ArquivoSerie extends aed3.Arquivo <Serie>
{
    // VARIÁVEIS
    private HashExtensivel <ParIDNome> indiceIndiretoNome;
    // private Arquivo        <Serie    >          arqSeries;

    // CONSTRUTORES
    public ArquivoSerie () throws Exception
    {
        super("series", Serie.class.getConstructor ());

        indiceIndiretoNome = new HashExtensivel <>
        (
            ParIDNome.class.getConstructor(), 
            4,
            ".\\dados\\series\\indiceNome.d.db",
            ".\\dados\\series\\indiceNome.c.db"
        );
    }

    // CRUD
    @Override
    public int create (Serie c) throws Exception 
    {
        int id = super.create(c);
        indiceIndiretoNome.create (new ParIDNome (id, c.getNome ()));
        return id;
    }

    public Serie read (String nome) throws Exception 
    {
        ParIDNome pin = indiceIndiretoNome.read(ParIDNome.hash (nome));
        if(pin == null) return null;
        return read (pin.getId ());
    }

    public static boolean serieExiste (int id) throws Exception
    {
       ArquivoSerie arqSeries = new ArquivoSerie ();
       Serie s = arqSeries.read (id);   // na superclasse
       if(s != null) 
       {
           return true;
       }
       return false;
    }
    
    public boolean delete (String  nome) throws Exception 
    {
        ParIDNome pin = indiceIndiretoNome.read (ParIDNome.hash (nome));
        if (pin != null) 
            if (delete (pin.getId ())) 
                return indiceIndiretoNome.delete (ParIDNome.hash (nome));
        return false;
    }

    @Override public boolean delete (int id) throws Exception 
    {
        Serie c = super.read (id);
        if (c != null) 
        {
            if (super.delete(id))
                return indiceIndiretoNome.delete (ParIDNome.hash (c.getNome ()));
        }
        return false;
    }

    @Override public boolean update (Serie novoSerie) throws Exception 
    {
        Serie SerieVelho = read (novoSerie.getNome ());
        if (super.update (novoSerie)) 
        {
            if (novoSerie.getNome ().compareTo (SerieVelho.getNome ()) != 0) 
            {
                indiceIndiretoNome.delete (ParIDNome.hash (SerieVelho.getNome ()));
                indiceIndiretoNome.create (new ParIDNome (novoSerie.getId (), novoSerie.getNome ()));
            }
            return true;
        }
        return false;
    }
}

//////////////////////////////////////////////////
