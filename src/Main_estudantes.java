import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Sistema de Cadastro de Clientes e Contatos 
 * Funcionalidade: Carregar e exibir dados de arquivos CSV
 *
 *
 * Estrutura de dados:
 * - Matriz de Clientes: String[linhas][8 colunas]
 * - Matriz de Contatos: String[linhas][5 colunas]
 */
public class Main_estudantes {

    /**
     * Método principal
     */
    public static void main(String[] args) {
        // Matrizes criadas localmente (não são globais)
        String[][] matrizClientes = new String[0][8];
        String[][] matrizContatos = new String[0][5];

        System.out.println("================================================");
        System.out.println(" SISTEMA DE CADASTRO DE CLIENTES e CONTATOS ");
        System.out.println("========================================");

        // Passa as matrizes para o menu principal
        menuPrincipal(matrizClientes, matrizContatos);

        System.out.println("\nSistema encerrado. Até logo!");
    }

    // ========== MENU ==========

    /**
     * Menu Principal
     * Permite acessar os módulos de clientes, contatos e relatórios
     */
    private static void menuPrincipal(String[][] matrizClientes, String[][] matrizContatos) {
        int opcao = -1;
        Scanner scanner = new Scanner(System.in);

        while (opcao != 0) {
            System.out.println("\n========== MENU PRINCIPAL ==========");
            System.out.println("1 - Gerenciar clientes");
            System.out.println("2 - Gerenciar contatos");
            System.out.println("3 - Relatórios");
            System.out.println("0 - Sair");
            System.out.println("====================================");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1: {
                        int opcaoCliente = -1;

                        while (opcaoCliente != 0) {
                            opcaoCliente = menuClientes(scanner);

                            switch (opcaoCliente) {
                                case 1:
                                    System.out.println("\n(Incluir cliente - em construção)");
                                    break;

                                case 2:
                                    System.out.println("\n(Listar clientes - em construção)");
                                    break;

                                case 3:
                                    System.out.println("\n(Consultar cliente - em construção)");
                                    break;

                                case 4:
                                    System.out.println("\n(Alterar cliente - em construção)");
                                    break;

                                case 5:
                                    System.out.println("\n(Apagar cliente - em construção)");
                                    break;

                                case 6:
                                    System.out.println("\n(Ordenar por nome - em construção)");
                                    break;

                                case 0:
                                    break; // volta ao menu principal

                                case -1:
                                    break; // entrada inválida, a mensagem já foi mostrada

                                default:
                                    System.out.println("\nOpção inválida! Tente novamente.");
                            }
                        }
                        break;
                    }

                    case 2:
                        System.out.println("\n(Gerenciar contatos - em construção)");
                        break;

                    case 3:
                        System.out.println("\n(Relatórios - em construção)");
                        break;

                    case 0:
                        System.out.println("\nEncerrando sistema...");
                        break;

                    default:
                        System.out.println("\nOpção inválida! Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nEntrada inválida! Digite apenas números.");
                opcao = -1;
            }
        }

        scanner.close();

    }

    /**
     * Menu de Clientes
     * Mostra as opções e devolve o número digitado (-1 se for inválido)
     */
    private static int menuClientes(Scanner scanner) {
        System.out.println("\n========== GERENCIAR CLIENTES ==========");
        System.out.println("1 - Incluir cliente");
        System.out.println("2 - Listar clientes (todos os clientes)");
        System.out.println("3 - Consultar cliente por código");
        System.out.println("4 - Alterar cliente");
        System.out.println("5 - Apagar cliente");
        System.out.println("6 - Ordenar por nome");
        System.out.println("0 - Voltar");
        System.out.println("========================================");
        System.out.print("Escolha uma opção: ");

        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("\nEntrada inválida! Digite apenas números.");
            return -1;
        }
    }

    // ========== FUNÇÕES DE CARREGAMENTO DE DADOS ==========

    /**
     * Carrega clientes do arquivo clientes.csv
     * Formato: codigo,nome,cpf_cnpj,data_nascimento,sexo,cidade,estado,status
     */
    private static String[][] carregarClientesCSV() {
        String arquivo = "clientes.csv";
        String[][] matrizClientes = new String[0][8];
        int contador = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                // Pula o cabeçalho
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                // Divide a linha por vírgula
                String[] dados = linha.split(",");

                if (dados.length >= 8) {
                    // Aumenta a matriz em 1 linha
                    matrizClientes = aumentarMatrizClientes(matrizClientes);
                    int indice = matrizClientes.length - 1;

                    // Preenche a nova linha com os dados do CSV
                    matrizClientes[indice][0] = dados[0].trim(); // codigo
                    matrizClientes[indice][1] = dados[1].trim(); // nome
                    matrizClientes[indice][2] = dados[2].trim(); // cpf_cnpj
                    matrizClientes[indice][3] = dados[3].trim(); // data_nascimento
                    matrizClientes[indice][4] = dados[4].trim(); // sexo
                    matrizClientes[indice][5] = dados[5].trim(); // cidade
                    matrizClientes[indice][6] = dados[6].trim(); // estado
                    matrizClientes[indice][7] = dados[7].trim().toUpperCase(); // status

                    contador++;
                }
            }

            System.out.println("✓ " + contador + " clientes foram carregados do arquivo.");

        } catch (IOException e) {
            System.out.println("\n✗ Erro ao ler arquivo " + arquivo + ": " + e.getMessage());
            System.out.println("Certifique-se de que o arquivo existe no diretório do projeto.");
        }

        return matrizClientes;
    }

    /**
     * Carrega contatos do arquivo contatos.csv
     * Formato: codigo_contato,codigo_cliente,tipo,valor,status
     */
    private static String[][] carregarContatosCSV() {
        String arquivo = "contatos.csv";
        String[][] matrizContatos = new String[0][5];
        int contador = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                // Pula o cabeçalho
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                // Divide a linha por vírgula
                String[] dados = linha.split(",");

                if (dados.length >= 5) {
                    // Aumenta a matriz em 1 linha
                    matrizContatos = aumentarMatrizContatos(matrizContatos);
                    int indice = matrizContatos.length - 1;

                    // Preenche a nova linha com os dados do CSV
                    matrizContatos[indice][0] = dados[0].trim(); // codigo_contato
                    matrizContatos[indice][1] = dados[1].trim(); // codigo_cliente
                    matrizContatos[indice][2] = dados[2].trim(); // tipo
                    matrizContatos[indice][3] = dados[3].trim(); // valor
                    matrizContatos[indice][4] = dados[4].trim().toUpperCase(); // status

                    contador++;
                }
            }

            System.out.println("✓ " + contador + " contatos foram carregados do arquivo.");

        } catch (IOException e) {
            System.out.println("\n✗ Erro ao ler arquivo " + arquivo + ": " + e.getMessage());
            System.out.println("Certifique-se de que o arquivo existe no diretório do projeto.");
        }

        return matrizContatos;
    }

    // ========== FUNÇÕES DE MANIPULAÇÃO DE MATRIZES ==========

    /**
     * Aumenta a matriz de clientes em 1 linha
     * Cria uma nova matriz com uma linha a mais e copia os dados existentes
     */
    private static String[][] aumentarMatrizClientes(String[][] matrizClientes) {

        String[][] novaMatriz = new String[matrizClientes.length + 1][8];

        for (int i = 0; i < matrizClientes.length; i++) {
            for (int j = 0; j < 8; j++) {
                novaMatriz[i][j] = matrizClientes[i][j];
            }
        }

        return novaMatriz;
    }

    /**
     * Aumenta a matriz de contatos em 1 linha
     * Cria uma nova matriz com uma linha a mais e copia os dados existentes
     */
    private static String[][] aumentarMatrizContatos(String[][] matrizContatos) {

        String[][] novaMatriz = new String[matrizContatos.length + 1][5];

        for (int i = 0; i < matrizContatos.length; i++) {
            for (int j = 0; j < 5; j++) {
                novaMatriz[i][j] = matrizContatos[i][j];
            }
        }

        return novaMatriz;
    }

}