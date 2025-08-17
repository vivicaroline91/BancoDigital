import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_VERDE = "\u001B[32m";
    public static final String ANSI_VERMELHO = "\u001B[31m";
    public static final String ANSI_AMARELO = "\u001B[33m";
    public static final String ANSI_AZUL = "\u001B[34m";
    public static final String ANSI_CIANO = "\u001B[36m";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<ContaBancaria> contas = new ArrayList<>();
        boolean sair = false;

        while (!sair) {
            System.out.println(ANSI_AZUL + "\n=== Banco Digital Dinâmico ===" + ANSI_RESET);
            System.out.println(ANSI_CIANO + "1. Criar Conta Corrente" + ANSI_RESET);
            System.out.println(ANSI_CIANO + "2. Criar Conta Poupança" + ANSI_RESET);
            System.out.println(ANSI_CIANO + "3. Listar Contas" + ANSI_RESET);
            System.out.println(ANSI_CIANO + "4. Operar Conta" + ANSI_RESET);
            System.out.println(ANSI_AMARELO + "0. Sair" + ANSI_RESET);
            System.out.print(ANSI_VERDE + "Escolha uma opção: " + ANSI_RESET);

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Nome do cliente: ");
                    String nomeCC = scanner.nextLine();
                    System.out.print("Saldo inicial: R$");
                    double saldoCC = scanner.nextDouble();
                    System.out.print("Limite da Conta Corrente: R$");
                    double limite = scanner.nextDouble();
                    contas.add(new ContaCorrente(new Cliente(nomeCC), saldoCC, limite));
                    System.out.println(ANSI_VERDE + "Conta Corrente criada com sucesso!" + ANSI_RESET);
                    break;

                case 2:
                    System.out.print("Nome do cliente: ");
                    String nomeCP = scanner.nextLine();
                    System.out.print("Saldo inicial: R$");
                    double saldoCP = scanner.nextDouble();
                    System.out.print("Taxa de rendimento (%): ");
                    double taxa = scanner.nextDouble();
                    contas.add(new ContaPoupanca(new Cliente(nomeCP), saldoCP, taxa));
                    System.out.println(ANSI_VERDE + "Conta Poupança criada com sucesso!" + ANSI_RESET);
                    break;

                case 3:
                    System.out.println(ANSI_AMARELO + "\n=== Lista de Contas ===" + ANSI_RESET);
                    for (int i = 0; i < contas.size(); i++) {
                        ContaBancaria c = contas.get(i);
                        System.out.println(ANSI_CIANO + i + " - " + c.getClass().getSimpleName() +
                                " | Titular: " + c.getTitular().getNome() +
                                " | Saldo: R$" + c.getSaldo() + ANSI_RESET);
                    }
                    break;

                case 4:
                    if (contas.isEmpty()) {
                        System.out.println(ANSI_VERMELHO + "Nenhuma conta cadastrada!" + ANSI_RESET);
                        break;
                    }

                    System.out.print("Escolha o índice da conta: ");
                    int indice = scanner.nextInt();
                    if (indice < 0 || indice >= contas.size()) {
                        System.out.println(ANSI_VERMELHO + "Índice inválido!" + ANSI_RESET);
                        break;
                    }

                    ContaBancaria conta = contas.get(indice);
                    boolean voltar = false;
                    while (!voltar) {
                        System.out.println(ANSI_AZUL + "\n=== Operações para " + conta.getTitular().getNome() + " ===" + ANSI_RESET);
                        System.out.println(ANSI_CIANO + "1. Depositar" + ANSI_RESET);
                        System.out.println(ANSI_CIANO + "2. Sacar" + ANSI_RESET);
                        System.out.println(ANSI_CIANO + "3. Transferir" + ANSI_RESET);
                        System.out.println(ANSI_CIANO + "4. Aplicar rendimento (Poupança)" + ANSI_RESET);
                        System.out.println(ANSI_CIANO + "5. Mostrar histórico" + ANSI_RESET);
                        System.out.println(ANSI_CIANO + "6. Consultar saldo" + ANSI_RESET);
                        System.out.println(ANSI_AMARELO + "0. Voltar" + ANSI_RESET);
                        System.out.print(ANSI_VERDE + "Escolha uma operação: " + ANSI_RESET);

                        int op = scanner.nextInt();
                        scanner.nextLine();
                        switch (op) {
                            case 1:
                                System.out.print("Valor para depositar: R$");
                                double dep = scanner.nextDouble();
                                conta.depositar(dep);
                                break;
                            case 2:
                                System.out.print("Valor para sacar: R$");
                                double saque = scanner.nextDouble();
                                conta.sacar(saque);
                                break;
                            case 3:
                                System.out.println("Escolha a conta destino:");
                                for (int i = 0; i < contas.size(); i++) {
                                    if (i != indice) {
                                        ContaBancaria c = contas.get(i);
                                        System.out.println(i + " - " + c.getTitular().getNome() + " (" + c.getClass().getSimpleName() + ")");
                                    }
                                }
                                int dest = scanner.nextInt();
                                if (dest < 0 || dest >= contas.size() || dest == indice) {
                                    System.out.println(ANSI_VERMELHO + "Índice inválido!" + ANSI_RESET);
                                } else {
                                    System.out.print("Valor da transferência: R$");
                                    double valTrans = scanner.nextDouble();
                                    conta.transferir(contas.get(dest), valTrans);
                                }
                                break;
                            case 4:
                                if (conta instanceof ContaPoupanca) {
                                    ((ContaPoupanca) conta).aplicarRendimento();
                                } else {
                                    System.out.println(ANSI_VERMELHO + "Apenas Conta Poupança pode aplicar rendimento." + ANSI_RESET);
                                }
                                break;
                            case 5:
                                conta.mostrarHistorico();
                                break;
                            case 6:
                                System.out.println("Saldo atual: R$" + conta.getSaldo());
                                break;
                            case 0:
                                voltar = true;
                                break;
                            default:
                                System.out.println(ANSI_VERMELHO + "Opção inválida!" + ANSI_RESET);
                        }
                    }
                    break;

                case 0:
                    sair = true;
                    System.out.println(ANSI_AMARELO + "Saindo do Banco Digital. Até logo!" + ANSI_RESET);
                    break;

                default:
                    System.out.println(ANSI_VERMELHO + "Opção inválida! Tente novamente." + ANSI_RESET);
            }
        }

        scanner.close();
    }
}
