//////////////////////////////////////////////////
// Pacote

package sistema.episodios;

//////////////////////////////////////////////////
// BIBLIOTECAS

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.Scanner; // Para Scanner

import sistema.series.ArquivoSerie; // para ArquivoSerie
import sistema.series.Serie;

//////////////////////////////////////////////////
// Implementação do MenuEpisodio em si

public class MenuEpisodio
{
    // VARIÁVEIS
    private static Scanner console = new Scanner (System.in);
    ArquivoEpisodio arquivoEpisodio;
    ArquivoSerie       arquivoSerie;

    // CONSTRUTORES
    public MenuEpisodio () throws Exception 
    {
        arquivoEpisodio = new ArquivoEpisodio ();
        arquivoSerie = new ArquivoSerie ();
    }

    
    public void menu (String servico) 
    {
        int opcao = -1;
        
        while (opcao != 0)
        {
            System.out.println ("\n\n" + servico);
            System.out.println ("-----------");
            System.out.println ("> Início > Episódios");
            System.out.println ("\n1 - Buscar");
            System.out.println ("2 - Incluir");
            System.out.println ("3 - Alterar");
            System.out.println ("4 - Excluir");
            System.out.println ("0 - Voltar");
            
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

            if (opcao == 1)
            {
                buscarEpisodio ();
            }
            else if (opcao == 2)
            {
                incluirEpisodio ();
            }
            else if (opcao == 3)
            {
                alterarEpisodio ();
            }
            else if (opcao == 4)
            {
                excluirEpisodio ();
            }
            else if (opcao != 0)
            {
                System.out.println ("Opção inválida!");
            }
        }
    }

    public void buscarEpisodio () 
    {
        System.out.println ("\nBusca de episódio \n");
        System.out.print ("\nNome da série: ");
        boolean correto = false;

        String nomeSerieVinculada = console.nextLine ();
        System.out.println ();

        do
        {
            try 
            {
                Serie serie = arquivoSerie.read (nomeSerieVinculada);

                if (serie == null)
                {
                    System.out.println ("Série não encontrada!");
                    correto = true;
                } 
                else 
                {
                    System.out.println (serie);

                    System.out.print ("\nDigite o nome do episódio (N para sair): ");
                    String nomeEpisodio = console.nextLine ();

                    if (nomeEpisodio.equals("N")) correto = true;
                    {
                        Episodio [] episodios = arquivoEpisodio.readEpisodioSerie (nomeEpisodio, serie.getId ());
    
                        if(episodios != null && episodios.length > 0)
                        {
                            for (Episodio ep : episodios)   
                            {
                                System.out.println (ep);
        
                                correto = true;
                            }
                        }
                        else System.out.println ("Episódio não encontrado.");
                    }
                }
            } 
            catch (Exception e) 
            {
                System.out.println ("Erro ao buscar a série: " + e.getMessage ());
            }

        }
        while (!correto);
    }

    // Inclusão de episódio no sistema
    public void incluirEpisodio ()
    {
        System.out.println ("\nInclusão de Episódio:\n");

        boolean correto = false;
        DateTimeFormatter formato_data = DateTimeFormatter.ofPattern ("dd/MM/yyyy");

        String nome = "";
        int numero = 0;
        short duracao = 0;
        int id_serie = 0;
        String sinopse = "";
        short temporada = 0;
        LocalDate lancamento = LocalDate.now ();

        System.out.print ("Nome da série: ");
        String nomeSerieVinculada = console.nextLine ();

        try
        {
            Serie serie = arquivoSerie.read (nomeSerieVinculada);

            if (serie == null)
            {
                System.out.println ("Série não encontrada.");
                return;
            }

            System.out.println (serie);

            id_serie = serie.getId ();
        }
        catch (Exception e)
        {
            System.out.println ("Erro ao buscar série: " + e.getMessage ());
            return;
        }

        correto = false;
        while (!correto)
        {
            System.out.print ("Nome do episódio (N para sair): ");
            nome = console.nextLine ();
            if (nome.length () >= 4)
            {
                correto = true;
            }
            else
            {
                System.out.println ("Nome deve ter no mínimo 4 caracteres.");
            }
        }

        correto = false;
        while (!correto)
        {
            System.out.print ("Número do episódio: ");
            String numero_str = console.nextLine ();
            try
            {
                numero = Integer.valueOf (numero_str);
                correto = true;
            }
            catch (Exception e)
            {
                System.out.println ("Número inválido.");
            }
        }

        correto = false;
        while (!correto)
        {
            System.out.print ("Temporada: ");
            String temp_str = console.nextLine ();
            try
            {
                temporada = Short.valueOf (temp_str);
                correto = true;
            }
            catch (Exception e)
            {
                System.out.println ("Temporada inválida.");
            }
        }

        correto = false;
        while (!correto)
        {
            System.out.print ("Duração em minutos (0-999): ");
            String duracao_str = console.nextLine ();
            try
            {
                duracao = Short.valueOf (duracao_str);
                if (duracao >= 0 && duracao <= 999)
                {
                    correto = true;
                }
                else
                {
                    System.out.println ("Duração fora do intervalo permitido.");
                }
            }
            catch (Exception e)
            {
                System.out.println ("Valor inválido para duração.");
            }
        }

        correto = false;
        while (!correto)
        {
            System.out.print ("Data de lançamento (DD/MM/AAAA): ");
            String data_str = console.nextLine ();
            try
            {
                lancamento = LocalDate.parse (data_str, formato_data);
                correto = true;
            }
            catch (Exception e)
            {
                System.out.println ("Formato de data inválido.");
            }
        }

        correto = false;
        while (!correto)
        {
            System.out.print ("Sinopse (mínimo 10 caracteres): ");
            sinopse = console.nextLine ();
            if (sinopse.length () >= 10)
            {
                correto = true;
            }
            else
            {
                System.out.println ("Sinopse muito curta.");
            }
        }

        System.out.print ("\nConfirmar inclusão do episódio? (S/N): ");
        String confirmacao = console.nextLine ();
        if (confirmacao.equalsIgnoreCase ("S"))
        {
            try
            {
                Episodio novo = new Episodio (nome, numero, duracao, id_serie, sinopse, temporada, lancamento);
                arquivoEpisodio.create (novo);
                System.out.println ("Episódio incluído com sucesso.");
            }
            catch (Exception e)
            {
                System.out.println ("Erro ao incluir episódio: " + e.getMessage ());
            }
        }
    }

    public void excluirEpisodio () 
    {
        System.out.println ("\nExclusão de episódio \n");
        System.out.print ("\nNome da série: ");
        String nomeSerieVinculada = console.nextLine ();
        System.out.println ();

        try
        {
            Serie serie = arquivoSerie.read (nomeSerieVinculada);
            
            if(serie == null) 
            {
                System.out.println ("Série não encontrada");
                return;
            }
            else
            {
                System.out.println (serie);
                
                Episodio [] episodio = arquivoEpisodio.readEpisodiosSerie (serie.getId ());

                for (int i = 0; i < episodio.length; i++) 
                {
                    System.out.println ("Id: " + i);
                    System.out.println (episodio[i]);
                }

                System.out.print ("\nDigite o número do episódio a ser excluído: ");

                int num2 = console.nextInt ();

                if (num2 < 0 || num2 > episodio.length) System.err.println ("Número inválido!");
                else
                {
                    System.out.print ("\nConfirma a exclusão do episódio? (S/N) ");

                    char resp = console.next ().charAt (0);
                    console.nextLine (); // Limpar buffer

                    if (resp == 'S' || resp == 's') 
                    {
                        boolean excluido = arquivoEpisodio.delete (episodio [num2].getId ());

                        if (excluido) System.out.println ("Episódio excluído com sucesso.");
                        else System.out.println ("Erro ao excluir o episódio.");
    
                    } 
                    else 
                    {
                        System.out.println ("Exclusão cancelada.");
                    }
                }
            }

        } catch (Exception e) 
        {
            System.out.println ("Erro do sistema. Não foi possível excluir o episódio! " + e.getMessage ());
        }
    }

    public void alterarEpisodio () 
    {
        System.out.println ("\nAlteração de episódio");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern ("dd/MM/yyyy");
    
        String nome_serie_vinculada;
    
        do
        {
            System.out.print ("Digite o nome da série em que deseja alterar o episódio: ");
            nome_serie_vinculada = console.nextLine ();
        } while (nome_serie_vinculada.trim ().isEmpty ());
    
        try
        {
            Serie serie = arquivoSerie.read (nome_serie_vinculada);
    
            if (serie == null)
            {
                System.err.println ("Nenhuma série encontrada com esse nome.");
                return;
            }
    
            System.out.println (serie);

            Episodio[] episodios = arquivoEpisodio.readEpisodiosSerie (serie.getId ());
    
            if (episodios == null || episodios.length == 0)
            {
                System.err.println ("Nenhum episódio encontrado para esta série.");
                return;
            }
    
            System.out.println ("\nEpisódios da série " + serie.getNome () + ": ");
    
            for (int i = 0; i < episodios.length; i++)
            {
                System.out.println ("Id: " + i);
                System.out.println (episodios[i]);
            }
    
            int num_episodio;
    
            do
            {
                System.out.print ("\nDigite o número do episódio a ser alterado: ");
    
                while (!console.hasNextInt ())
                {
                    System.err.println ("Número inválido!");
                    console.next ();
                }
    
                num_episodio = console.nextInt ();
                console.nextLine ();
            } while (num_episodio < 0 || num_episodio >= episodios.length);
    
            Episodio episodio_encontrado = episodios[num_episodio];
    
            System.out.println (episodio_encontrado);
    
            do
            {
                System.out.print ("Novo nome do episodio (min. de 4 letras) (ou Enter para manter): ");
                String novo_nome = console.nextLine ();
    
                if (novo_nome.isEmpty() || novo_nome.length() >= 4)
                {
                    if (!novo_nome.isEmpty())
                    {
                        episodio_encontrado.setNome (novo_nome);
                    }
    
                    break;
                }
                else
                {
                    System.err.println ("O Nome do episódio deve ter no mínimo 4 caracteres.");
                }
    
            } while (true);
    
            do
            {
                System.out.print ("Novo número do episódio (ou Enter para manter): ");
                String entrada = console.nextLine ();
    
                if (entrada.isEmpty())
                {
                    break;
                }
    
                try
                {
                    int novo_numero = Integer.parseInt (entrada);
                    episodio_encontrado.setNumero (novo_numero);
                    break;
                }
                catch (NumberFormatException e)
                {
                    System.err.println ("Número inválido!");
                }
    
            } while (true);
    
            do
            {
                System.out.print ("Nova temporada (ou Enter para manter): ");
                String entrada = console.nextLine ();
    
                if (entrada.isEmpty())
                {
                    break;
                }
    
                try
                {
                    short nova_temporada = Short.parseShort (entrada);
                    episodio_encontrado.setTemporada (nova_temporada);
                    break;
                }
                catch (NumberFormatException e)
                {
                    System.err.println ("Número inválido!");
                }
    
            } while (true);
    
            do
            {
                System.out.print ("Nova data de lançamento (DD/MM/AAAA) (ou Enter para manter): ");
                String nova_data = console.nextLine ();
    
                if (nova_data.isEmpty())
                {
                    break;
                }
    
                try
                {
                    episodio_encontrado.setLancamento (LocalDate.parse (nova_data, formatter));
                    break;
                }
                catch (Exception e)
                {
                    System.err.println ("Data inválida! Use o formato DD/MM/AAAA.");
                }
    
            } while (true);
    
            do
            {
                System.out.print ("Nova duração em minutos (0-999) (ou Enter para manter): ");
                String entrada = console.nextLine ();
    
                if (entrada.isEmpty())
                {
                    break;
                }
    
                try
                {
                    short nova_duracao = Short.parseShort (entrada);
                    episodio_encontrado.setDuracao (nova_duracao);
                    break;
                }
                catch (NumberFormatException e)
                {
                    System.err.println ("Número inválido!");
                }
    
            } while (true);
    
            do
            {
                System.out.print ("Nova sinopse (min. 10 letras) (ou Enter para manter): ");
                String nova_sinopse = console.nextLine ();
    
                if (nova_sinopse.isEmpty() || nova_sinopse.length() >= 10)
                {
                    if (!nova_sinopse.isEmpty())
                    {
                        episodio_encontrado.setSinopse (nova_sinopse);
                    }
    
                    break;
                }
                else
                {
                    System.err.println ("A sinopse deve ter no mínimo 10 caracteres.");
                }
    
            } while (true);
    
            System.out.print ("\nConfirma as alterações? (S/N) ");
            char resp = console.next ().charAt (0);
            console.nextLine ();
    
            if (resp == 'S' || resp == 's')
            {
                boolean alterado = arquivoEpisodio.update (episodio_encontrado);
    
                if (alterado)
                {
                    System.out.println ("Episódio alterado com sucesso.");
                }
                else
                {
                    System.out.println ("Erro ao alterar o episódio.");
                }
            }
            else
            {
                System.out.println ("Alterações canceladas.");
            }
        }
        catch (Exception e)
        {
            System.out.println ("Erro do sistema. Não foi possível alterar o episódio! " + e.getMessage ());
            e.printStackTrace ();
        }
    }    
}