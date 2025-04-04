import sistema.episodios.Episodio;
import       sistema.series.Serie;

import java.time.LocalDate; // Para o <lancamento>

public class Principal 
{
    public static void main (String args []) 
    {
        Episodio tmp = new Episodio
        (
            "Nome do ep", 
            33, 
            (short) 42, 
            44, 
            "Aqui vai a Sinopse de seu ep", 
            (short) 5, 
            LocalDate.now ()
        );

        System.out.println (tmp);

        Serie tmp0 = new Serie 
        (
            "Nome da serie",
            "Aqui vai a grandiosa sinopse de sua serie",
            16,
            "PUCSTREAMING",
            LocalDate.now ()
        );

        System.out.println (tmp0);
    }
}
