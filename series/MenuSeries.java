package series;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MenuSeries {
    
    ArquivoSerie arqSeries;
    private static Scanner console = new Scanner(System.in);

    public MenuSeries() throws Exception {
        arqSeries = new ArquivoSerie();
    }

    public void menu() {

        int opcao;
        do {

            System.out.println("\n\nAEDsIII");
            System.out.println("-------");
            System.out.println("> Início > Series");
            System.out.println("\n1 - Buscar");
            System.out.println("2 - Incluir");
            System.out.println("3 - Alterar");
            System.out.println("4 - Excluir");
            System.out.println("0 - Voltar");

            System.out.print("\nOpção: ");
            try {
                opcao = Integer.valueOf(console.nextLine());
            } catch(NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    buscarSerie();
                    break;
                case 2:
                    incluirSerie();
                    break;
                case 3:
                    alterarSerie();
                    break;
                case 4:
                    excluirSerie();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 0);
    }


    public void buscarSerie() {
        System.out.println("\nBusca de Serie");
        String cpf;
        boolean cpfValido = false;

        do {
            System.out.print("\nCPF (11 dígitos): ");
            cpf = console.nextLine();  // Lê o CPF digitado pelo usuário

            if(cpf.isEmpty())
                return; 

            // Validação do CPF (11 dígitos e composto apenas por números)
            if (cpf.length() == 11 && cpf.matches("\\d{11}")) {
                cpfValido = true;  // CPF válido
            } else {
                System.out.println("CPF inválido. O CPF deve conter exatamente 11 dígitos numéricos, sem pontos e traços.");
            }
        } while (!cpfValido);

        try {
            Serie Serie = arqSeries.read(cpf);  // Chama o método de leitura da classe Arquivo
            if (Serie != null) {
                mostraSerie(Serie);  // Exibe os detalhes do Serie encontrado
            } else {
                System.out.println("Serie não encontrado.");
            }
        } catch(Exception e) {
            System.out.println("Erro do sistema. Não foi possível buscar o Serie!");
            e.printStackTrace();
        }
    }   


    public void incluirSerie() {
        System.out.println("\nInclusão de Serie");
        String nome = "";
        String cpf = "";
        float salario = 0;
        LocalDate dataNascimento = null;
        boolean dadosCorretos = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        do {
            System.out.print("\nNome (min. de 4 letras ou vazio para cancelar): ");
            nome = console.nextLine();
            if(nome.length()==0)
                return;
            if(nome.length()<4)
                System.err.println("O nome do Serie deve ter no mínimo 4 caracteres.");
        } while(nome.length()<4);

        do {
            System.out.print("CPF (11 dígitos sem pontos ou traço): ");
            cpf = console.nextLine();
            if(cpf.length()!=11 && cpf.length()!=0)
                System.err.println("O CPF deve ter exatamente 11 dígitos.");
        } while(cpf.length()!=11 && cpf.length()!=0);

        do {
            dadosCorretos = false;
            System.out.print("Salário: ");
            if (console.hasNextFloat()) {
                salario = console.nextFloat();
                dadosCorretos = true;
            } else {
                System.err.println("Salário inválido! Por favor, insira um número válido.");
            }
            console.nextLine(); // Limpar o buffer 
        } while(!dadosCorretos);

        do {
            System.out.print("Data de nascimento (DD/MM/AAAA): ");
            String dataStr = console.nextLine();
            dadosCorretos = false;
            try {
                dataNascimento = LocalDate.parse(dataStr, formatter);
                dadosCorretos = true;
            } catch (Exception e) {
                System.err.println("Data inválida! Use o formato DD/MM/AAAA.");
            }
        } while(!dadosCorretos);

        System.out.print("\nConfirma a inclusão da Serie? (S/N) ");
        char resp = console.nextLine().charAt(0);
        if(resp=='S' || resp=='s') {
            try {
                Serie c = new Serie(nome, cpf, salario, dataNascimento);
                arqSeries.create(c);
                System.out.println("Serie incluído com sucesso.");
            } catch(Exception e) {
                System.out.println("Erro do sistema. Não foi possível incluir o Serie!");
            }
        }
    }

    public void alterarSerie() {
        System.out.println("\nAlteração de Serie");
        String cpf;
        boolean cpfValido = false;

        do {
            System.out.print("\nCPF (11 dígitos): ");
            cpf = console.nextLine();  // Lê o CPF digitado pelo usuário

            if(cpf.isEmpty())
                return; 

            // Validação do CPF (11 dígitos e composto apenas por números)
            if (cpf.length() == 11 && cpf.matches("\\d{11}")) {
                cpfValido = true;  // CPF válido
            } else {
                System.out.println("CPF inválido. O CPF deve conter exatamente 11 dígitos numéricos, sem pontos e traços.");
            }
        } while (!cpfValido);


        try {
            // Tenta ler o Serie com o ID fornecido
            Serie Serie = arqSeries.read(cpf);
            if (Serie != null) {
                System.out.println("Serie encontrado:");
                mostraSerie(Serie);  // Exibe os dados do Serie para confirmação

                // Alteração de nome
                System.out.print("\nNovo nome (deixe em branco para manter o anterior): ");
                String novoNome = console.nextLine();
                if (!novoNome.isEmpty()) {
                    Serie.nome = novoNome;  // Atualiza o nome se fornecido
                }

                // Alteração de CPF
                System.out.print("Novo CPF (deixe em branco para manter o anterior): ");
                String novoCpf = console.nextLine();
                if (!novoCpf.isEmpty()) {
                    Serie.cpf = novoCpf;  // Atualiza o CPF se fornecido
                }

                // Alteração de salário
                System.out.print("Novo salário (deixe em branco para manter o anterior): ");
                String novoSalarioStr = console.nextLine();
                if (!novoSalarioStr.isEmpty()) {
                    try {
                        Serie.salario = Float.parseFloat(novoSalarioStr);  // Atualiza o salário se fornecido
                    } catch (NumberFormatException e) {
                        System.err.println("Salário inválido. Valor mantido.");
                    }
                }

                // Alteração de data de nascimento
                System.out.print("Nova data de nascimento (DD/MM/AAAA) (deixe em branco para manter a anterior): ");
                String novaDataStr = console.nextLine();
                if (!novaDataStr.isEmpty()) {
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        Serie.nascimento = LocalDate.parse(novaDataStr, formatter);  // Atualiza a data de nascimento se fornecida
                    } catch (Exception e) {
                        System.err.println("Data inválida. Valor mantido.");
                    }
                }

                // Confirmação da alteração
                System.out.print("\nConfirma as alterações? (S/N) ");
                char resp = console.next().charAt(0);
                if (resp == 'S' || resp == 's') {
                    // Salva as alterações no arquivo
                    boolean alterado = arqSeries.update(Serie);
                    if (alterado) {
                        System.out.println("Serie alterado com sucesso.");
                    } else {
                        System.out.println("Erro ao alterar o Serie.");
                    }
                } else {
                    System.out.println("Alterações canceladas.");
                }
            } else {
                System.out.println("Serie não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível alterar o Serie!");
            e.printStackTrace();
        }
        
    }


    public void excluirSerie() {
        System.out.println("\nExclusão de Serie");
        String cpf;
        boolean cpfValido = false;

        do {
            System.out.print("\nCPF (11 dígitos): ");
            cpf = console.nextLine();  // Lê o CPF digitado pelo usuário

            if(cpf.isEmpty())
                return; 

            // Validação do CPF (11 dígitos e composto apenas por números)
            if (cpf.length() == 11 && cpf.matches("\\d{11}")) {
                cpfValido = true;  // CPF válido
            } else {
                System.out.println("CPF inválido. O CPF deve conter exatamente 11 dígitos numéricos, sem pontos e traços.");
            }
        } while (!cpfValido);

        try {
            // Tenta ler o Serie com o ID fornecido
            Serie Serie = arqSeries.read(cpf);
            if (Serie != null) {
                System.out.println("Serie encontrado:");
                mostraSerie(Serie);  // Exibe os dados do Serie para confirmação

                System.out.print("\nConfirma a exclusão do Serie? (S/N) ");
                char resp = console.next().charAt(0);  // Lê a resposta do usuário

                if (resp == 'S' || resp == 's') {
                    boolean excluido = arqSeries.delete(cpf);  // Chama o método de exclusão no arquivo
                    if (excluido) {
                        System.out.println("Serie excluído com sucesso.");
                    } else {
                        System.out.println("Erro ao excluir o Serie.");
                    }
                } else {
                    System.out.println("Exclusão cancelada.");
                }
            } else {
                System.out.println("Serie não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível excluir o Serie!");
            e.printStackTrace();
        }
    }


    public void mostraSerie(Serie Serie) {
    if (Serie != null) {
        System.out.println("\nDetalhes do Serie:");
        System.out.println("----------------------");
        System.out.printf("Nome......: %s%n", Serie.nome);
        System.out.printf("CPF.......: %s%n", Serie.cpf);
        System.out.printf("Salário...: R$ %.2f%n", Serie.salario);
        System.out.printf("Nascimento: %s%n", Serie.nascimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.println("----------------------");
    }
}
}
