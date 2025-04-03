import sistema.episodios.Episodio;

import java.time.LocalDate; // Para o <lancamento>

public class Principal 
{
    public static void main (String args []) 
    {
        Episodio tmp = new Episodio
        (
            "Nome da Série", 
            33, 
            (short) 42, 
            44, 
            "Aqui vai a Sinopse de sua série", 
            (short) 5, 
            LocalDate.now ()
        );

        System.out.println (tmp);
    }
}
