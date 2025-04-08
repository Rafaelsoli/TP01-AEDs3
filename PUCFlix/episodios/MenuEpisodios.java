//////////////////////////////////////////////////
// PACOTE

package episodios;

//////////////////////////////////////////////////
// BIBLIOTECAS DO SISTEMA

import java.time.format.DateTimeFormatter;
import                java.time.LocalDate;
import                  java.util.Scanner;

//////////////////////////////////////////////////
// BIBLIOTECAS PRÓPRIAS

import series.*;
import aeds3.*;

//////////////////////////////////////////////////
// IMPLMENTAÇÃO DE MENUEPISODIOS EM SI

public class MenuEpisodios 
{
    ArquivoEpisodios arquivoEpsodio;
    ArquivoSeries arquivoSeries;
    private static Scanner console = new Scanner (System.in);

    public MenuEpisodios () throws Exception 
    {
        arquivoEpsodio = new ArquivoEpisodios ();
        arquivoSeries = new ArquivoSeries ();
    }

    public void menu (String servico) 
    {
        int opcao = - 1;
        do 
        {
            System.out.println ("\n\n" + servico);
            System.out.println ("-----------");
            System.out.println ("> Início > Episódios");
            System.out.println ("\n1) Incluir");
            System.out.println ("2) Buscar");
            System.out.println ("3) Alterar");
            System.out.println ("4) Excluir");
            System.out.println ("0) Retornar ao menu anterior");
            
            System.out.print ("\nOpção: ");
            try 
            {
                opcao = Integer.valueOf (console.nextLine ());
            } 
            catch (NumberFormatException e) 
            {
                opcao = -1;
            }

            switch (opcao) 
            {
                case 1:
                    incluirEpisodio ();
                    break;
                case 2:
                    buscarEpisodio ();
                    break;
                case 3:
                    alterarEpisodio ();
                    break;
                case 4:
                    excluirEpisodio ();
                    break;
                case 0:
                    break;
                default:
                    System.out.println ("Opção inválida!");
                    break;
            }
        } while (opcao != 0);
    }

    public void incluirEpisodio () 
    {
        System.out.println ("\nInclusão de Episódio: \n");
        String nome = "";
        int temporada = 0;
        LocalDate dataLancamento = LocalDate.now ();
        int Minutos = 0;
        float avaliacao = 0F;
        String descricao = "";
        int id_serie = 0;

        boolean dadosCorretos = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern ("dd/MM/yyyy");



        dadosCorretos = false;
        do 
        {
            System.out.print ("Em que serie deseja incluir o episodio? (Nome da serie): ");
            String nomeSerieVinculada = console.nextLine ();
            System.out.println ();

                try
                {
                    Serie[] series = arquivoSeries.readNome (nomeSerieVinculada);
                    if (series != null && series.length > 0) 
                    {
                        System.out.println ("Séries encontradas: ");
                        for (int i = 0; i < series.length; i++) 
                        {
                            System.out.println ("\t[" + i + "]");
                            MenuSeries.mostrarSerie (series[i]);
                        }

                        System.out.print ("\nDigite o número da série a ser vinculada: ");
                        if (console.hasNextInt ()) 
                        {
                            int num = console.nextInt ();
                            System.out.println ();
                            if (num < 0 || num > series.length) 
                            {
                                System.err.println ("Número inválido!");
                            } else 
                            {
                                id_serie = series[num].getID();
                                dadosCorretos = true;
                            }
                        } else 
                        {
                            System.err.println ("Número inválido!");
                        }
                    }

                    
                }catch (Exception e)
                {
                    System.err.println ("Erro ao buscar a série: " + e.getMessage ());
                }

            console.nextLine ();
        } while (!dadosCorretos);

        dadosCorretos = false;
        do 
        {
            System.out.print ("Nome do episodio (min. de 4 letras): ");
            nome = console.nextLine ();
            if (nome.length ()>=4)
                dadosCorretos = true;
            else
                System.err.println ("O Nome do episódio deve ter no mínimo 4 caracteres.");
        } while (!dadosCorretos);

        dadosCorretos = false;
        do 
        {
            System.out.print ("Temporada: ");
            if (console.hasNextInt ()) 
            {
                temporada = console.nextInt ();
                dadosCorretos = true;
            }
            console.nextLine ();
        } while (!dadosCorretos);

        dadosCorretos = false;
        do 
        {
            System.out.print ("Data de lançamento (DD/MM/AAAA): ");
            String dataStr = console.nextLine ();
            try 
            {
                dataLancamento = LocalDate.parse (dataStr, formatter);
                dadosCorretos = true;
            } catch (Exception e) {
                System.err.println ("Data inválida! Use o formato DD/MM/AAAA.");
            }
        } while (!dadosCorretos);

        
        dadosCorretos = false;
        do 
        {
            System.out.print ("Duração em Minutos (0-999): ");
            if (console.hasNextInt ()) 
            {
                Minutos = console.nextInt ();
                dadosCorretos = true;
            }
            console.nextLine ();
        } while (!dadosCorretos);

        dadosCorretos = false;
        do 
        {
            System.out.print ("Avaliação do episodio (0,0-10,0): ");
            if (console.hasNextFloat () || console.hasNextInt ())
            {
                avaliacao = console.nextFloat ();
                dadosCorretos = true;
            }
            console.nextLine ();
        } while (!dadosCorretos);

        dadosCorretos = false;
        do 
        {
            System.out.print ("Descrição do espisodio (min. de 10 letras): ");
            descricao = console.nextLine ();
            if (descricao.length ()>=10)
                dadosCorretos = true;
            else
                System.err.println ("A descricao do episódio deve ter no mínimo 10 caracteres.");
        } while (!dadosCorretos);



        System.out.print ("\nConfirma a inclusão do episódio? (S/N): ");
        char resp = console.nextLine ().charAt (0);
        if (resp=='S' || resp=='s') 
        {
            try 
            {
                Episodio e = new Episodio(nome, temporada, dataLancamento, Minutos, avaliacao, descricao, id_serie);
                arquivoEpsodio.create (e);
                System.out.println ("Episódio incluído com sucesso.");
            } catch (Exception e) {
                System.out.println ("Erro do sistema. Não foi possível incluir o episódio! " + e.getMessage ());
            }
        }
    }

    public void buscarEpisodio() 
    {
        System.out.println ("\nBusca de episódio: \n");
        System.out.print ("\nDe qual serie deseja buscar o episodio? (Nome da serie): ");
        boolean dadosCorretos = false;

        String nomeSerieVinculada = console.nextLine ();
        System.out.println ();

        do
        {
            try 
            {
                Serie[] series = arquivoSeries.readNome (nomeSerieVinculada);
                if (series != null && series.length > 0) {
                    System.out.println ("Series encontradas: ");
                    for(int i = 0; i < series.length; i++)
                    {
                        System.out.println ("\t[" + i + "]");
                        MenuSeries.mostrarSerie (series[i]);
                    }
                        
                    System.out.print ("\nDigite o número da série escolhida: ");
                    if (console.hasNextInt ()) 
                    {
                        int num = console.nextInt ();
                        if (num < 0 || num >= series.length) 
                        {
                            System.err.println ("Número inválido!");
                        }else
                        {
                            console.nextLine (); // Limpar buffer
                            System.out.print ("\nDigite o nome do episódio: ");
                            String nomeEpisodio = console.nextLine ();
                            Episodio[] episodios = arquivoEpsodio.readNomeEpisodioPorSerie (nomeEpisodio, series[num].getID());
                            if (episodios != null && episodios.length > 0)
                            {
                                for (Episodio ep : episodios) 
                                {
                                    mostraEpisodio(ep);
                                    dadosCorretos = true;
                                }
                            }else
                            {
                                System.out.println ("Nenhum episódio encontrado para esta série.");
                                dadosCorretos = true;
                            }
                        }
                    } else 
                    {
                        System.err.println ("Número inválido!");
                        dadosCorretos = true;
                    }
                }else
                {
                    System.out.println ("Nenhuma série encontrada com esse nome.");
                    dadosCorretos = true;
                }
            } catch (Exception e) 
            {
                System.out.println ("Erro ao buscar a série: " + e.getMessage ());
            }

        }while (!dadosCorretos);
    }

    public void excluirEpisodio() 
    {
        System.out.println ("\nExclusão de episódio: \n");
        System.out.print ("\nDe qual serie deseja excluir o episodio? (Nome da serie): ");
        String nomeSerieVinculada = console.nextLine ();
        System.out.println ();

        try 
        {
            Serie[] series = arquivoSeries.readNome (nomeSerieVinculada);
            
            if (series != null && series.length > 0) 
            {
                System.out.println ("Séries encontradas: ");
                for(int i = 0; i < series.length; i++)
                {
                    System.out.println ("\t[" + i + "]");
                    MenuSeries.mostrarSerie (series[i]);
                }

                System.out.print ("\nDigite o número da série escolhida: ");
                if (console.hasNextInt ())
                {
                    int num = console.nextInt ();
                    if (num < 0 || num > series.length) 
                    {
                        System.err.println ("Número inválido!");
                    }else
                    {
                        System.out.println ("\nEpisódios da série " + series[num].getNome () + ": ");
                        Episodio[] episodio = arquivoEpsodio.readEpisodiosSerie (series[num].getID());

                        for (int i = 0; i < episodio.length; i++) 
                        {
                            System.out.println ("\t[" + i + "]");
                            mostraEpisodio(episodio[i]);
                        }
                        System.out.print ("\nDigite o número do episódio a ser excluído: ");
                        int num2 = console.nextInt ();
                        if (num2 < 0 || num2 > episodio.length)
                        {
                            System.err.println ("Número inválido!");
                        }else
                        {
                            System.out.print ("\nConfirma a exclusão do episódio? (S/N) ");
                            char resp = console.next ().charAt (0);
                            console.nextLine (); // Limpar buffer
                            if (resp == 'S' || resp == 's') 
                            {
                                boolean excluido = arquivoEpsodio.delete (episodio[num2].getID());
                                if (excluido) 
                                {
                                    System.out.println ("Episódio excluído com sucesso.");
                                } else 
                                {
                                    System.out.println ("Erro ao excluir o episódio.");
                                }
                            } else 
                            {
                                System.out.println ("Exclusão cancelada.");
                            }
                        }
                    }

                }else
                {
                    System.err.println ("Número inválido!");
                }
            }

        } catch (Exception e) 
        {
            System.out.println ("Erro do sistema. Não foi possível excluir o episódio! " + e.getMessage ());
        }
    }

    public void alterarEpisodio() 
    {
        System.out.println ("\nAlteração de episódio");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern ("dd/MM/yyyy");
        
        String nomeSerieVinculada;
        do 
        {
            System.out.print ("Digite o nome da série em que deseja alterar o episódio: ");
            nomeSerieVinculada = console.nextLine ();
        } while (nomeSerieVinculada.trim().isEmpty());
    
        try 
        {
            Serie[] series = arquivoSeries.readNome (nomeSerieVinculada);
            if (series == null || series.length == 0) 
            {
                System.err.println ("Nenhuma série encontrada com esse nome.");
                return;
            }
            
            System.out.println ("Séries encontradas: ");
            for (int i = 0; i < series.length; i++) 
            {
                System.out.println ("\t[" + i + "]");
                MenuSeries.mostrarSerie (series[i]);
            }
            
            int numSerie;
            do 
            {
                System.out.print ("\nDigite o número da série escolhida: ");
                while (!console.hasNextInt ()) 
                {
                    System.err.println ("Número inválido!");
                    console.next ();
                }
                numSerie = console.nextInt ();
                console.nextLine ();
            } while (numSerie < 0 || numSerie >= series.length);
            
            Episodio[] episodios = arquivoEpsodio.readEpisodiosSerie (series[numSerie].getID());
            if (episodios == null || episodios.length == 0) 
            {
                System.err.println ("Nenhum episódio encontrado para esta série.");
                return;
            }
            
            System.out.println ("\nEpisódios da série " + series[numSerie].getNome () + ": ");
            for (int i = 0; i < episodios.length; i++) 
            {
                System.out.println ("\t[" + i + "]");
                mostraEpisodio(episodios[i]);
            }
            
            int numEpisodio;
            do 
            {
                System.out.print ("\nDigite o número do episódio a ser alterado: ");
                while (!console.hasNextInt ()) 
                {
                    System.err.println ("Número inválido!");
                    console.next ();
                }
                numEpisodio = console.nextInt ();
                console.nextLine ();
            } while (numEpisodio < 0 || numEpisodio >= episodios.length);
            
            Episodio episodioEncontrado = episodios[numEpisodio];
            System.out.println ("Episódio encontrado: ");
            mostraEpisodio(episodioEncontrado);
            
            do {
                System.out.print ("Novo nome do episodio (min. de 4 letras) (ou Enter para manter): ");
                String novoNome = console.nextLine ();
                if (novoNome.isEmpty() || novoNome.length () >= 4) 
                {
                    if (!novoNome.isEmpty()) 
                    {
                        episodioEncontrado.setNome (novoNome);
                    }
                    break;
                } else 
                {
                    System.err.println ("O Nome do episódio deve ter no mínimo 4 caracteres.");
                }
            } while (true);
            
            int novaTemporada = episodioEncontrado.getTemporada();
            do 
            {
                System.out.print ("Nova temporada (ou Enter para manter): ");
                String entrada = console.nextLine ();
                if (entrada.isEmpty()) 
                {
                    break;
                } else 
                {
                    try 
                    {
                        novaTemporada = Integer.parseInt (entrada);
                        break;
                    } catch (NumberFormatException e) 
                    {
                        System.err.println ("Número inválido!");
                    }
                }
            } while (true);
            
            do 
            {
                System.out.print ("Nova data de lançamento (DD/MM/AAAA) (ou Enter para manter): ");
                String novaData = console.nextLine ();
                if (novaData.isEmpty()) 
                {
                    break;
                }
                try 
                {
                    episodioEncontrado.setLancamento(LocalDate.parse (novaData, formatter));
                    break;
                } catch (Exception e) {
                    System.err.println ("Data inválida! Use o formato DD/MM/AAAA.");
                }
            } while (true);
            
            do 
            {
                System.out.print ("Nova duração em Minutos (0-999) (ou Enter para manter): ");
                String entrada = console.nextLine ();
                if (entrada.isEmpty()) 
                {
                    break;
                }
                try 
                {
                    int novaMinutos = Integer.parseInt (entrada);
                    episodioEncontrado.setMinutos (novaMinutos);
                    break;
                } catch (NumberFormatException e) {
                    System.err.println ("Número inválido!");
                }
            } while (true);            
            
            do 
            {
                System.out.print ("Descrição do episódio (min. de 10 letras) (ou Enter para manter): ");
                String novaDescricao = console.nextLine ();
                if (novaDescricao.isEmpty() || novaDescricao.length () >= 10) 
                {
                    if (!novaDescricao.isEmpty()) 
                    {
                        episodioEncontrado.setDescricao(novaDescricao);
                    }
                    break;
                } else 
                {
                    System.err.println ("A descrição do episódio deve ter no mínimo 10 caracteres.");
                }
            } while (true);
            
            System.out.print ("\nConfirma as alterações? (S/N) ");
            char resp = console.next ().charAt (0);
            console.nextLine ();
            if (resp == 'S' || resp == 's') 
            {
                boolean alterado = arquivoEpsodio.update (episodioEncontrado);
                if (alterado) 
                {
                    System.out.println ("Episódio alterado com sucesso.");
                } else 
                {
                    System.out.println ("Erro ao alterar o episódio.");
                }
            } else 
            {
                System.out.println ("Alterações canceladas.");
            }
        } catch (Exception e) 
        {
            System.out.println ("Erro do sistema. Não foi possível alterar o episódio! " + e.getMessage ());
            e.printStackTrace ();
        }
    }
    
    
    public void mostraEpisodio (Episodio episodio) 
    {
        System.out.println ( "| > \""         + episodio.getNome ()   + "\" Duração: "  + episodio.getMinutos ()/60 + ":" + ((episodio.getMinutos ()%60 < 10)?"0":"") + episodio.getMinutos ()%60 + ":00\n" +
        "| Episódio da " + episodio.getTemporada ()  +   " temporada\n" +
        "| "             + episodio.getDescricao ()     +                                 "\n" +
        "| Lançado em: "                   + episodio.getLancamento ().toString ()        + " - ID: "     + episodio.getID () + "@" + episodio.getID_serie ());
    }

    public void povoar() throws Exception 
    {

        // Serie de id 1 - Fullmetal Alchemist: Brotherhood
        arquivoEpsodio.create (new Episodio("A Lei da Troca Equivalente", 1, LocalDate.of (2009, 4, 5), 24, 9.5f, "Os irmãos Elric aprendem uma dura lição ao tentar ressuscitar sua mãe.", 1));
        arquivoEpsodio.create (new Episodio("O Alquimista de Aço", 2, LocalDate.of (2009, 4, 12), 23, 8.7f, "Edward recebe o título de alquimista federal e parte em jornada com Alphonse.", 1));

        // Serie de id 2 - Stranger Things
        arquivoEpsodio.create (new Episodio("O Desaparecimento", 1, LocalDate.of (2017, 1, 10), 25, 8.9f, "Na cidade de Hawkins, um garoto some misteriosamente após um blecaute.", 2));
        arquivoEpsodio.create (new Episodio("A Garota com Poderes", 2, LocalDate.of (2017, 1, 17), 24, 9.1f, "Mike encontra uma garota com habilidades sobrenaturais chamada Eleven.", 2));

        // Serie de id 3 - Shingeki no Kyojin (Attack on Titan)
        arquivoEpsodio.create (new Episodio("Para Você, 2 Mil Anos no Futuro", 1, LocalDate.of (2013, 4, 6), 24, 9.0f, "Titãs rompem a Muralha Maria, destruindo a cidade de Eren.", 3));

        // Serie de id 4 - Kimetsu no Yaiba (Demon Slayer)
        arquivoEpsodio.create (new Episodio("Crueldade", 1, LocalDate.of (2019, 4, 6), 24, 8.5f, "Tanjiro encontra sua família assassinada e decide proteger sua irmã Nezuko.", 4));
        arquivoEpsodio.create (new Episodio("Treinamento de Sabito", 2, LocalDate.of (2019, 4, 13), 24, 8.7f, "Tanjiro enfrenta um duro treinamento para se tornar caçador de demônios.", 4));

        // Serie de id 5 - Boku no Hero Academia (My Hero Academia)
        arquivoEpsodio.create (new Episodio("Izuku Midoriya: O Nascimento de um Herói", 1, LocalDate.of (2016, 4, 3), 24, 9.0f, "Mesmo sem poderes, Izuku sonha em se tornar um herói.", 5));

        // Serie de id 6 - Steins;Gate
        arquivoEpsodio.create (new Episodio("O Começo e o Fim do Ciclo", 1, LocalDate.of (2011, 4, 6), 24, 8.9f, "Rintarou descobre que pode enviar mensagens para o passado.", 6));

        // Serie de id 7 - Tokyo Revengers
        arquivoEpsodio.create (new Episodio("Reviver", 1, LocalDate.of (2021, 4, 11), 24, 9.2f, "Takemichi volta ao passado para salvar sua ex-namorada de uma gangue violenta.", 7));

        // Serie de id 8 - Jujutsu Kaisen
        arquivoEpsodio.create (new Episodio("Ryomen Sukuna", 1, LocalDate.of (2020, 10, 3), 24, 8.7f, "Yuji come um dedo amaldiçoado e abriga uma entidade poderosa dentro de si.", 8));

        // Serie de id 9 - Psycho-Pass
        arquivoEpsodio.create (new Episodio("Crime Coletado", 1, LocalDate.of (2012, 10, 12), 24, 9.0f, "No futuro, a justiça é determinada por scanners psicológicos.", 9));

        // Serie de id 10 - Black Mirror
        arquivoEpsodio.create (new Episodio("Códigos da Saudade", 1, LocalDate.of (2015, 10, 25), 24, 8.6f, "Uma jovem revive digitalmente seu namorado falecido usando IA experimental.", 10));

    }
}

//////////////////////////////////////////////////