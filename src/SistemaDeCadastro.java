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
public class SistemaDeCadastro {

    /**
     * Método principal
     */
    public static void main(String[] args) {
        // Matrizes criadas localmente (não são globais)
        String[][] matrizClientes = carregarClientesCSV(); // TEMPORÁRIO: só para teste
        String[][] matrizContatos = carregarContatosCSV(); // TEMPORÁRIO: só para teste

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

    // ========== FUNÇÕES DE CLIENTES ==========

    /**
     * Inclui um novo cliente
     * O código é gerado automaticamente: maior código da matriz + 1
     * Devolve a matriz com o cliente novo
     */
    private static String[][] incluirCliente(Scanner scanner, String[][] matrizClientes) {
        System.out.println("\n========== INCLUIR CLIENTE ==========");

        // 1) Acha o maior código que já existe na coluna 0
        int maior = 0;
        for (int i = 0; i < matrizClientes.length; i++) {
            int codigoAtual = Integer.parseInt(matrizClientes[i][0]);
            if (codigoAtual > maior) {
                maior = codigoAtual;
            }
        }

        // 2) O código do novo cliente é o maior + 1
        int novoCodigo = maior + 1;

        // 3) Pede os dados do cliente
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

        // 4) Ganha uma linha nova na matriz e preenche a última linha
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

        // 5) Confirma e devolve a matriz nova
        System.out.println("\nCliente incluído com sucesso! Código: " + novoCodigo);

        return matrizClientes;
    }

    /**
     * Lista todos os clientes em formato de tabela
     * Um cliente por linha, colunas separadas por " | "
     */
    private static void listarClientesTabela(String[][] matrizClientes) {
        System.out.println("\nCódigo | Nome | CPF/CNPJ | Nascimento | Sexo | Cidade | Estado | Status");
        System.out.println("--------------------------------------------------------------------------------------------");

        // Se não há clientes, avisa e sai da função
        if (matrizClientes.length == 0) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        // for de fora: uma volta para cada cliente (linha da matriz)
        for (int i = 0; i < matrizClientes.length; i++) {

            // for de dentro: uma volta para cada dado do cliente (coluna 0 a 7)
            for (int j = 0; j < 8; j++) {
                System.out.print(matrizClientes[i][j]);

                // Coloca o separador entre as colunas, mas não depois da última
                if (j < 7) {
                    System.out.print(" | ");
                }
            }

            // Terminou as 8 colunas: pula para a linha de baixo
            System.out.println();
        }
    }

    /**
     * Procura um cliente pelo código
     * Devolve a posição (linha) do cliente na matriz, ou -1 se não achar
     */
    private static int buscarClientePorCodigo(String[][] matrizClientes, String codigo) {
        int codigoBuscado;

        // Converte o texto digitado para número; se não for número, não achou
        try {
            codigoBuscado = Integer.parseInt(codigo.trim());
        } catch (NumberFormatException e) {
            return -1;
        }

        // Percorre as linhas comparando o código de cada cliente (coluna 0)
        for (int i = 0; i < matrizClientes.length; i++) {
            if (Integer.parseInt(matrizClientes[i][0]) == codigoBuscado) {
                return i; // achou: devolve a posição e encerra a função
            }
        }

        // Terminou o for sem achar
        return -1;
    }

    /**
     * Consulta um cliente pelo código e mostra a linha dele em formato de tabela
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
     * Altera um cliente
     * Lista os clientes, pergunta qual alterar, mostra o cliente encontrado,
     * pede a confirmação (S/N) e grava os novos dados na linha dele.
     * Não devolve matriz: só muda valores de uma linha, não o tamanho.
     */
    private static void alterarCliente(Scanner scanner, String[][] matrizClientes) {
        System.out.println("\n========== ALTERAR CLIENTE ==========");

        // 1) Mostra todos os clientes para a pessoa escolher
        listarClientesTabela(matrizClientes);

        // Se não há clientes, não tem o que alterar
        if (matrizClientes.length == 0) {
            return;
        }

        // 2) Pergunta o código e procura o cliente
        System.out.print("\nQual cliente deseja alterar => ");
        String codigo = scanner.nextLine();

        int posicao = buscarClientePorCodigo(matrizClientes, codigo);

        if (posicao == -1) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        // 3) Mostra só o cliente encontrado, em tabela
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

        // 4) Pede a confirmação: só continua se a resposta começar com S
        System.out.print("\nDeseja alterar este cliente? (S/N) ");
        String resposta = scanner.nextLine().toUpperCase();

        if (resposta.length() == 0 || resposta.charAt(0) != 'S') {
            System.out.println("Alteração cancelada.");
            return;
        }

        // 5) Pede os novos dados (o código do cliente não muda)
        System.out.println("\nDigite os novos dados:");

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

        // 6) Grava os novos dados na linha do cliente (colunas 1 a 7)
        matrizClientes[posicao][1] = nome;
        matrizClientes[posicao][2] = cpfCnpj;
        matrizClientes[posicao][3] = nascimento;
        matrizClientes[posicao][4] = sexo;
        matrizClientes[posicao][5] = cidade;
        matrizClientes[posicao][6] = estado;
        matrizClientes[posicao][7] = status;

        System.out.println("\nCliente alterado com sucesso!");
    }

    private static String[][] apagarCliente(Scanner scanner, String[][] matrizClientes) {
        System.out.println("(apagarCliente - em construção)");
        return matrizClientes;
    }

    private static void ordenarClientesPorNome(String[][] matrizClientes) {
        System.out.println("(ordenarClientesPorNome - em construção)");
    }

    // ========== FUNÇÕES AUXILIARES ==========

    private static int compararNomeCharPorChar(String nome1, String nome2) {
        return 0;
    }

    private static void copiarLinha(String[][] origem, int linhaOrigem, String[][] destino, int linhaDestino) {
    }

    private static void limparLinha(String[][] matriz, int linha) {
    }

    private static void trocarLinhas(String[][] matriz, int linha1, int linha2) {
    }

}