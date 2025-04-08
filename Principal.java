///////////////////O-Pear-ation///////////////////
// Bibliotecas do sistema

import java.util.Scanner;

//////////////////////////////////////////////////
// Bibliotecas próprias

import episodios.*;
import series.*;
import aeds3.*;

//////////////////////////////////////////////////
// Classe Principal

public class Principal 
{

    public static void main (String args []) 
    {
        // VARIÁVEIS
        String servico  = "PUCFLIX";
        Scanner console =      null;
        
        try 
        {

            console = new Scanner (System.in);
            int opcao;
            do {

                System.out.println (servico);
                System.out.println ( "-----------");
                System.out.println ("> Início");
                System.out.println ("1 - Series");
                System.out.println ("2 - Episodios");
                System.out.println ("3 - Povoar");
                System.out.println ("0 - Sair");
                
                System.out.print("\nOpção: ");
                try 
                {
                    opcao = Integer.valueOf(console.nextLine());
                } 
                catch(NumberFormatException e) 
                {
                    opcao = -1;
                }

                switch (opcao) 
                {
                    case 1:
                        (new MenuSeries ()).menu (servico);
                    break;

                    case 2:
                        (new MenuEpisodios ()).menu (servico);
                    break;

                    case 3:
                        (new MenuSeries ()).povoar ();
                        (new MenuEpisodios ()).povoar ();
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

//////////////////////////////////////////////////z