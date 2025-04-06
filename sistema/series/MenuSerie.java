//////////////////////////////////////////////////
// Pacote

package sistema.series;

//////////////////////////////////////////////////
// BIBLIOTECAS

import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Scanner;

import sistema.episodios.ArquivoEpisodio;
import sistema.episodios.Episodio;

//////////////////////////////////////////////////
// Implementação do MenuSerie em si

public class MenuSerie
{
    // VARIÁVEIS
    private static Scanner console = new Scanner (System.in);
    ArquivoSerie arquivoSerie;

    // CONSTRUTORES
    public MenuSerie () throws Exception
    {
        arquivoSerie = new ArquivoSerie ();
    }

    // FUNÇÕES
    public void menu (String servico)
    {
        int opcao = -1;

        while (opcao != 0)
        {
            System.out.println ("\n\n" + servico);
            System.out.println ("-------");
            System.out.println ("> Início > Séries");
            System.out.println ("\n1 - Buscar");
            System.out.println ("2 - Incluir");
            System.out.println ("3 - Alterar");
            System.out.println ("4 - Excluir");
            System.out.println ("5 - Povoar");
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
                    System.out.print("\n\nPrograma finalizado forçadamente.\n\n");
                    System.exit (0);
                }
            }
            catch (NumberFormatException e)
            {
                opcao = -1;
            }

            if (opcao == 1)
            {
                buscarSerie ();
            }
            else if (opcao == 2)
            {
                incluirSerie ();
            }
            else if (opcao == 3)
            {
                alterarSerie ();
            }
            else if (opcao == 4)
            {
                excluirSerie ();
            }
            else if (opcao == 5)
            {
                try{povoar ();} catch (Exception e) {System.out.println ("Erro no povoar");};
            }
            else if (opcao != 0)
            {
                System.out.println ("Opção inválida!");
            }
        }
    }

    // CRUD
    public void buscarSerie ()
    {
        System.out.println ("\nBusca de Série");
        System.out.print ("\nNome: ");
        String nome = console.nextLine ();

        if (nome.isEmpty ())
            return;

        try
        {
            Serie serie = arquivoSerie.read (nome);
            if (serie != null)
            {
                System.out.println (serie);
            }
            else
            {
                System.out.println ("Série não encontrada.");
            }
        }
        catch (Exception e)
        {
            System.out.println ("Erro do sistema. Não foi possível buscar a série!");
            e.printStackTrace ();
        }
    }

    public void incluirSerie ()
    {
        System.out.println ("\nInclusão de Série");
        String nome = "";
        String sinopse = "";
        short classificacao_indicativa = -1;
        String plataforma_streaming = "";
        LocalDate data_lancamento;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern ("dd/MM/yyyy");

        do
        {
            System.out.print ("\nNome: ");
            nome = console.nextLine ();
            if (nome.length () == 0)
                return;
            if (nome.length () < 4)
                System.err.println ("O nome da série deve ter no mínimo 4 caracteres.");
        }
        while (nome.length () < 4);

        System.out.print ("Sinopse: ");
        sinopse = console.nextLine ();

        boolean dados_corretos = false;

        while (!dados_corretos)
        {
            System.out.print ("Classificação indicativa: ");
            try
            {
                classificacao_indicativa = Short.parseShort (console.nextLine ());
                dados_corretos = true;
            }
            catch (Exception e)
            {
                System.err.println ("Valor inválido.");
            }
        }

        System.out.print ("Plataforma de Streaming: ");
        plataforma_streaming = console.nextLine ();

        dados_corretos = false;
        while (!dados_corretos)
        {
            System.out.print ("Data de lançamento (DD/MM/AAAA): ");
            String data_str = console.nextLine ();
            try
            {
                data_lancamento = LocalDate.parse (data_str, formatter);
                dados_corretos = true;
            }
            catch (Exception e)
            {
                System.err.println ("Data inválida!");
                continue;
            }

            System.out.print ("\nConfirma a inclusão da série? (S/N) ");
            char resp = console.nextLine ().charAt (0);
            if (resp == 'S' || resp == 's')
            {
                try
                {
                    Serie nova = new Serie (nome, sinopse, classificacao_indicativa, plataforma_streaming, data_lancamento);
                    arquivoSerie.create (nova);
                    System.out.println ("Série incluída com sucesso.");
                }
                catch (Exception e)
                {
                    System.out.println ("Erro ao incluir série.");
                }
            }
        }
    }

    public void alterarSerie ()
    {
        System.out.println ("\nAlteração de Série");
        System.out.print ("\nNome da série a alterar: ");
        String nome = console.nextLine ();

        if (nome.isEmpty ())
            return;

        try
        {
            Serie serie = arquivoSerie.read (nome);
            if (serie != null)
            {
                System.out.println ("Série encontrada:");
                System.out.println (serie);

                System.out.print ("\nNovo nome (enter para manter): ");
                String novo_nome = console.nextLine ();
                if (!novo_nome.isEmpty ())
                    serie.setNome (novo_nome);

                System.out.print ("Nova sinopse (enter para manter): ");
                String nova_sinopse = console.nextLine ();
                if (!nova_sinopse.isEmpty ())
                    serie.setSinopse(nova_sinopse);

                System.out.print ("Nova classificação indicativa (enter para manter): ");
                String nova_class_str = console.nextLine ();
                if (!nova_class_str.isEmpty ())
                {
                    try
                    {
                        serie.setAgeRating(Short.parseShort (nova_class_str));
                    }
                    catch (NumberFormatException e)
                    {
                        System.out.println ("Valor inválido. Mantido anterior.");
                    }
                }

                System.out.print ("Nova plataforma de streaming (enter para manter): ");
                String nova_stream = console.nextLine ();
                if (!nova_stream.isEmpty ())
                    serie.setStreaming (nova_stream);

                System.out.print ("Nova data de lançamento (DD/MM/AAAA, enter para manter): ");
                String nova_data = console.nextLine ();
                if (!nova_data.isEmpty ())
                {
                    try
                    {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern ("dd/MM/yyyy");
                        serie.setLancamento (LocalDate.parse (nova_data, formatter));
                    }
                    catch (Exception e)
                    {
                        System.out.println ("Data inválida. Mantido valor anterior.");
                    }
                }

                System.out.print ("\nConfirma as alterações? (S/N) ");
                char resp = console.next ().charAt (0);
                console.nextLine ();

                if (resp == 'S' || resp == 's')
                {
                    boolean ok = arquivoSerie.update (serie);
                    if (ok)
                        System.out.println ("Série alterada com sucesso.");
                    else
                        System.out.println ("Erro ao alterar a série.");
                }
            }
            else
            {
                System.out.println ("Série não encontrada.");
            }
        }
        catch (Exception e)
        {
            System.out.println ("Erro ao tentar alterar a série.");
            e.printStackTrace ();
        }
    }

    public void excluirSerie ()
    {
        System.out.println ("\nExclusão de Série");
        System.out.print ("Nome: ");
        String nome = console.nextLine ();

        if (nome.isEmpty ())
            return;

        try
        {
            Serie serie = arquivoSerie.read (nome);
            if (serie != null)
            {
                System.out.println (serie);
                System.out.print ("\nConfirma a exclusão? (S/N) ");
                char resp = console.next ().charAt (0);
                console.nextLine ();

                if (resp == 'S' || resp == 's')
                {
                    boolean ok = arquivoSerie.delete (nome);
                    if (ok)
                        System.out.println ("Série excluída.");
                    else
                        System.out.println ("Erro ao excluir.");
                }
            }
            else
            {
                System.out.println ("Série não encontrada.");
            }
        }
        catch (Exception e)
        {
            System.out.println ("Erro do sistema.");
            e.printStackTrace ();
        }
    }

    public void povoar () throws Exception
    {
        System.out.print ("Atenção, povoar só deve ser executado se não houver os arquivos do banco de dados. Você os apagou de antemão (S/N)? ");
        String resp = console.nextLine ();
        
        if (! (resp.equals("S") || resp.equals ("s"))) return;

        System.out.println ("Povoando...");

        ArquivoSerie arqSerie = new ArquivoSerie ();
        ArquivoEpisodio arqEp = new ArquivoEpisodio ();
    
        arqSerie.create 
        (
            new Serie (
                "Neon Genesis Evangelion", 
                "Evangelion se passa quinze anos após um cataclismo mundial, particularmente na futurística cidade fortificada de Tokyo-3. O protagonista é Shinji, um adolescente que foi recrutado por seu pai, Gendo, para uma misteriosa organização chamada Nerv para pilotar em combate uma biomáquina gigante conhecida como \"Evangelion\" contra seres chamados \"Anjos\".", 
                14,
                "Disney+, Prime Video, Netflix", 
                LocalDate.of (1995, 10, 4)
            )
        );
        arqEp.create (
            new Episodio (
                "Angel Attack", 
                1, 
                (short) 25, 
                arqSerie.getUltimoId (), 
                "Shinji Ikari é chamado para pilotar o EVA pela primeira vez, enfrentando o terceiro anjo.", 
                (short) 1, 
                LocalDate.of (1995, 10, 4)
            )
        );
    
        arqSerie.create 
        (
            new Serie (
                "Breaking Bad", 
                "Um professor de química do ensino médio diagnosticado com câncer terminal começa a fabricar e vender metanfetamina para garantir o futuro de sua família.", 
                16, 
                "Netflix", 
                LocalDate.of (2008, 1, 20)
            )
        );
        arqEp.create (
            new Episodio (
                "Pilot", 
                1, 
                (short) 58, 
                arqSerie.getUltimoId (), 
                "Walter White, um professor de química, começa a cozinhar metanfetamina com seu ex-aluno Jesse Pinkman.", 
                (short) 1, 
                LocalDate.of (2008, 1, 20)
            )
        );
    
        arqSerie.create 
        (
            new Serie (
                "Stranger Things", 
                "Quando um garoto desaparece, sua mãe, um chefe de polícia e seus amigos enfrentam forças sobrenaturais para trazê-lo de volta.", 
                18, 
                "Netflix", 
                LocalDate.of (2016, 7, 15)
            )
        );
        arqEp.create (
            new Episodio (
                "The Vanishing of Will Byers", 
                1, 
                (short) 47, 
                arqSerie.getUltimoId (), 
                "Will Byers desaparece misteriosamente, dando início a uma série de eventos paranormais em Hawkins.", 
                (short) 1, 
                LocalDate.of (2016, 7, 15)
            )
        );
    
        arqSerie.create 
        (
            new Serie (
                "The Mandalorian", 
                "Um caçador de recompensas solitário percorre a galáxia nos confins da Nova República, enfrentando inimigos e protegendo uma misteriosa criança.", 
                8, 
                "Disney+", 
                LocalDate.of (2019, 11, 12)
            )
        );
        arqEp.create (
            new Episodio (
                "Chapter 1: The Mandalorian", 
                1, 
                (short) 39, 
                arqSerie.getUltimoId (), 
                "O Mandaloriano aceita uma missão peculiar que o leva a um encontro inesperado.", 
                (short) 1, 
                LocalDate.of (2019, 11, 12)
            )
        );
    }
}

//////////////////////////////////////////////////