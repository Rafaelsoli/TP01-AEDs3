import java.util.Scanner;

import sistema.episodios.MenuEpisodio;
import sistema.series.*;

public class Principal 
{

public static void main (String [] args) 
{
    String servico = "PUCFLIX";
    Scanner console;

    try {
        console = new Scanner (System.in);
        int opcao = -1;
        do 
        {
            System.out.println ("\n\n" + servico);
            System.out.println ("-------");
            System.out.println ("> Início");
            System.out.println ("\n1 - Séries");
            System.out.println (  "2 - Episódios");
            System.out.println (  "0 - Sair");

            System.out.print ("\nOpção: ");
            try
            {
                if (console.hasNextLine ())
                {
                    String a = console.nextLine ();
                    opcao = Integer.valueOf (a);
                }
                else 
                {
                    System.out.print ("\n\nPrograma finalizado forçadamente.\n\n");
                    System.exit (0);
                }
            }
            catch (NumberFormatException e)
            {
                opcao = -1;
            }

            switch (opcao) 
            {
                case 2:
                    (new MenuEpisodio ()).menu (servico);
                break;

                case 1:
                    (new MenuSerie ()).menu (servico);
                break;
                
                case 0:
                break;

                default:
                    System.out.println ("Opção inválida!");
                break;
            }

        } 
        while (opcao != 0);

    } 
    catch(Exception e) 
    {
        e.printStackTrace ();
    }

}

}