import sistema.episodios.Episodio;
import sistema.series.ParIDNome;
import       sistema.series.Serie;

import java.time.LocalDate; // Para o <lancamento>

public class Principal 
{
    public static void main (String args []) 
    {
        try
        {
            Episodio tmp = new Episodio
            (
                "Nome do ep", 
                33, 
                (short) 240, 
                44, 
                "Aqui vai a Sinopse de seu ep", 
                (short) 5, 
                LocalDate.now ()
            );
            
            Serie tmp0 = new Serie 
            (
                "Nome da serie",
                "Aqui vai a grandiosa sinopse de sua serie",
                16,
                "PUCSTREAMING",
                LocalDate.now ()
            );

            ParIDNome tmp1 = new ParIDNome
            (
                -1, "Nome da serie"
            );
                
            System.out.println (new Serie     (tmp0.toByteArray ()));           
            System.out.println (new Episodio  (tmp .toByteArray ()));
            System.out.println (new ParIDNome (tmp1.toByteArray ()));
        }
        catch (Exception e)
        {
            System.out.println ("ERRO: " + e);
        }
    }
}
