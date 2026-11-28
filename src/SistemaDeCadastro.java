import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Sistema de Cadastro de Clientes e Contatos.
 * Matriz de clientes: String[linhas][8 colunas].
 * Matriz de contatos: String[linhas][5 colunas].
 */
public class SistemaDeCadastro {

    /**
     * main
     * Cria as matrizes (por enquanto carregadas dos CSVs, só para teste) e chama o menu principal.
     */
    public static void main(String[] args) {
        String[][] matrizClientes = carregarClientesCSV();
        String[][] matrizContatos = carregarContatosCSV();

        System.out.println("================================================");
        System.out.println(" SISTEMA DE CADASTRO DE CLIENTES e CONTATOS ");
        System.out.println("========================================");

        menuPrincipal(matrizClientes, matrizContatos);

        System.out.println("\nSistema encerrado. Até logo!");
    }

    /**
     * menuPrincipal
     * Mostra o menu principal e executa as opções de clientes, contatos e relatórios.
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
                                    matrizClientes = incluirCliente(scanner, matrizClientes);
                                    break;

                                case 2:
                                    listarClientesTabela(matrizClientes);
                                    break;

                                case 3:
                                    consultarCliente(scanner, matrizClientes);
                                    break;

                                case 4:
                                    alterarCliente(scanner, matrizClientes);
                                    break;

                                case 5:
                                    matrizClientes = apagarCliente(scanner, matrizClientes);
                                    break;

                                case 6:
                                    ordenarClientesPorNome(matrizClientes);
                                    break;

                                case 0:
                                    break;

                                case -1:
                                    break;

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
     * menuClientes
     * Mostra o menu de clientes e devolve a opção digitada (-1 se for inválida).
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

    /**
     * carregarClientesCSV
     * Lê o arquivo clientes.csv e devolve a matriz de clientes preenchida.
     */
    private static String[][] carregarClientesCSV() {
        String arquivo = "clientes.csv";
        String[][] matrizClientes = new String[0][8];
        int contador = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                String[] dados = linha.split(",");

                if (dados.length >= 8) {
                    matrizClientes = aumentarMatrizClientes(matrizClientes);
                    int indice = matrizClientes.length - 1;

                    matrizClientes[indice][0] = dados[0].trim();
                    matrizClientes[indice][1] = dados[1].trim();
                    matrizClientes[indice][2] = dados[2].trim();
                    matrizClientes[indice][3] = dados[3].trim();
                    matrizClientes[indice][4] = dados[4].trim();
                    matrizClientes[indice][5] = dados[5].trim();
                    matrizClientes[indice][6] = dados[6].trim();
                    matrizClientes[indice][7] = dados[7].trim().toUpperCase();

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
     * carregarContatosCSV
     * Lê o arquivo contatos.csv e devolve a matriz de contatos preenchida.
     */
    private static String[][] carregarContatosCSV() {
        String arquivo = "contatos.csv";
        String[][] matrizContatos = new String[0][5];
        int contador = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                String[] dados = linha.split(",");

                if (dados.length >= 5) {
                    matrizContatos = aumentarMatrizContatos(matrizContatos);
                    int indice = matrizContatos.length - 1;

                    matrizContatos[indice][0] = dados[0].trim();
                    matrizContatos[indice][1] = dados[1].trim();
                    matrizContatos[indice][2] = dados[2].trim();
                    matrizContatos[indice][3] = dados[3].trim();
                    matrizContatos[indice][4] = dados[4].trim().toUpperCase();

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

    /**
     * aumentarMatrizClientes
     * Cria uma matriz de clientes com uma linha a mais, copia os dados e devolve a nova matriz.
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
     * aumentarMatrizContatos
     * Cria uma matriz de contatos com uma linha a mais, copia os dados e devolve a nova matriz.
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

    /**
     * incluirCliente
     * Pede os dados de um novo cliente, gera o código automático e devolve a matriz com o cliente incluído.
     */
    private static String[][] incluirCliente(Scanner scanner, String[][] matrizClientes) {
        System.out.println("\n========== INCLUIR CLIENTE ==========");

        int maior = 0;
        for (int i = 0; i < matrizClientes.length; i++) {
            int codigoAtual = Integer.parseInt(matrizClientes[i][0]);
            if (codigoAtual > maior) {
                maior = codigoAtual;
            }
        }

        int novoCodigo = maior + 1;

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("CPF ou CNPJ: ");
        String cpfCnpj = scanner.nextLine();

        System.out.print("Data de nascimento (dd/mm/aaaa): ");
        String nascimento = scanner.nextLine();

        System.out.print("Sexo (M/F): ");
        String sexo = scanner.nextLine();

        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();

        System.out.print("Estado (ex.: SP): ");
        String estado = scanner.nextLine();

        System.out.print("Status (ATIVO/INATIVO): ");
        String status = scanner.nextLine().toUpperCase();

        matrizClientes = aumentarMatrizClientes(matrizClientes);
        int indice = matrizClientes.length - 1;

        matrizClientes[indice][0] = String.valueOf(novoCodigo);
        matrizClientes[indice][1] = nome;
        matrizClientes[indice][2] = cpfCnpj;
        matrizClientes[indice][3] = nascimento;
        matrizClientes[indice][4] = sexo;
        matrizClientes[indice][5] = cidade;
        matrizClientes[indice][6] = estado;
        matrizClientes[indice][7] = status;

        System.out.println("\nCliente incluído com sucesso! Código: " + novoCodigo);

        return matrizClientes;
    }

    /**
     * listarClientesTabela
     * Mostra todos os clientes em formato de tabela, um cliente por linha.
     */
    private static void listarClientesTabela(String[][] matrizClientes) {
        System.out.println("\nCódigo | Nome | CPF/CNPJ | Nascimento | Sexo | Cidade | Estado | Status");
        System.out.println("--------------------------------------------------------------------------------------------");

        if (matrizClientes.length == 0) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        for (int i = 0; i < matrizClientes.length; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(matrizClientes[i][j]);

                if (j < 7) {
                    System.out.print(" | ");
                }
            }

            System.out.println();
        }
    }

    /**
     * buscarClientePorCodigo
     * Procura um cliente pelo código e devolve a posição dele na matriz, ou -1 se não achar.
     */
    private static int buscarClientePorCodigo(String[][] matrizClientes, String codigo) {
        int codigoBuscado;

        try {
            codigoBuscado = Integer.parseInt(codigo.trim());
        } catch (NumberFormatException e) {
            return -1;
        }

        for (int i = 0; i < matrizClientes.length; i++) {
            if (Integer.parseInt(matrizClientes[i][0]) == codigoBuscado) {
                return i;
            }
        }

        return -1;
    }

    /**
     * consultarCliente
     * Pede o código de um cliente e mostra os dados dele em formato de tabela.
     */
    private static void consultarCliente(Scanner scanner, String[][] matrizClientes) {
        System.out.print("\nDigite o código do cliente: ");
        String codigo = scanner.nextLine();

        int posicao = buscarClientePorCodigo(matrizClientes, codigo);

        if (posicao == -1) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        System.out.println("\nCliente encontrado:");
        System.out.println("Código | Nome | CPF/CNPJ | Nascimento | Sexo | Cidade | Estado | Status");
        System.out.println("--------------------------------------------------------------------------------------------");

        for (int j = 0; j < 8; j++) {
            System.out.print(matrizClientes[posicao][j]);

            if (j < 7) {
                System.out.print(" | ");
            }
        }

        System.out.println();
    }

    /**
     * alterarCliente
     * Lista os clientes, pede o código, mostra o cliente encontrado e pede confirmação (S/N) para alterar.
     */
    private static void alterarCliente(Scanner scanner, String[][] matrizClientes) {
        System.out.println("\n========== ALTERAR CLIENTE ==========");

        listarClientesTabela(matrizClientes);

        if (matrizClientes.length == 0) {
            return;
        }

        System.out.print("\nQual cliente deseja alterar => ");
        String codigo = scanner.nextLine();

        int posicao = buscarClientePorCodigo(matrizClientes, codigo);

        if (posicao == -1) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        System.out.println("\nCliente encontrado:");
        System.out.println("Código | Nome | CPF/CNPJ | Nascimento | Sexo | Cidade | Estado | Status");
        System.out.println("--------------------------------------------------------------------------------------------");

        for (int j = 0; j < 8; j++) {
            System.out.print(matrizClientes[posicao][j]);

            if (j < 7) {
                System.out.print(" | ");
            }
        }

        System.out.println();

        System.out.print("\nDeseja alterar este cliente? (S/N) ");
        String resposta = scanner.nextLine().toUpperCase();

        if (resposta.length() == 0 || resposta.charAt(0) != 'S') {
            System.out.println("Alteração cancelada.");
            return;
        }

        System.out.println("(alterarCliente - em construção)");
    }

    /**
     * apagarCliente
     * Apaga um cliente (em construção).
     */
    private static String[][] apagarCliente(Scanner scanner, String[][] matrizClientes) {
        System.out.println("(apagarCliente - em construção)");
        return matrizClientes;
    }

    /**
     * ordenarClientesPorNome
     * Ordena os clientes pelo nome (em construção).
     */
    private static void ordenarClientesPorNome(String[][] matrizClientes) {
        System.out.println("(ordenarClientesPorNome - em construção)");
    }

    /**
     * compararNomeCharPorChar
     * Compara dois nomes letra por letra (em construção).
     */
    private static int compararNomeCharPorChar(String nome1, String nome2) {
        return 0;
    }

    /**
     * copiarLinha
     * Copia uma linha de uma matriz para outra (em construção).
     */
    private static void copiarLinha(String[][] origem, int linhaOrigem, String[][] destino, int linhaDestino) {
    }

    /**
     * limparLinha
     * Apaga os dados de uma linha da matriz (em construção).
     */
    private static void limparLinha(String[][] matriz, int linha) {
    }

    /**
     * trocarLinhas
     * Troca duas linhas de lugar na matriz (em construção).
     */
    private static void trocarLinhas(String[][] matriz, int linha1, int linha2) {
    }

}