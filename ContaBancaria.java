import java.util.ArrayList;
import java.util.List;

public abstract class ContaBancaria {
    private static int contador = 1;
    private int numeroConta;
    private Cliente titular;
    protected double saldo;
    protected List<String> historico;

    // Códigos ANSI para cores
    protected final String ANSI_RESET = "\u001B[0m";
    protected final String ANSI_VERDE = "\u001B[32m";
    protected final String ANSI_VERMELHO = "\u001B[31m";
    protected final String ANSI_AZUL = "\u001B[34m";
    protected final String ANSI_AMARELO = "\u001B[33m";

    public ContaBancaria(Cliente titular, double saldoInicial) {
        this.numeroConta = contador++;
        this.titular = titular;
        this.saldo = saldoInicial >= 0 ? saldoInicial : 0;
        this.historico = new ArrayList<>();
        registrarHistorico("Conta criada com saldo inicial: R$" + this.saldo);
    }

    public int getNumeroConta() {
        return numeroConta;
    }

    public Cliente getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
            registrarHistorico(ANSI_VERDE + "Depósito: R$" + valor + ANSI_RESET);
            System.out.println(ANSI_VERDE + "Depósito realizado com sucesso!" + ANSI_RESET);
        } else {
            System.out.println(ANSI_VERMELHO + "Erro: valor inválido para depósito." + ANSI_RESET);
        }
    }

    public void sacar(double valor) {
        if (valor > 0 && saldo >= valor) {
            saldo -= valor;
            registrarHistorico(ANSI_VERMELHO + "Saque: R$" + valor + ANSI_RESET);
            System.out.println(ANSI_VERMELHO + "Saque realizado com sucesso!" + ANSI_RESET);
        } else {
            System.out.println(ANSI_VERMELHO + "Erro: saldo insuficiente ou valor inválido." + ANSI_RESET);
        }
    }

    public void transferir(ContaBancaria destino, double valor) {
        if (valor > 0 && saldo >= valor) {
            saldo -= valor;
            destino.saldo += valor;
            registrarHistorico(ANSI_AZUL + "Transferência de R$" + valor + " para " + destino.getTitular().getNome() + ANSI_RESET);
            destino.registrarHistorico(ANSI_AZUL + "Recebido transferência de R$" + valor + " de " + titular.getNome() + ANSI_RESET);
            System.out.println(ANSI_AZUL + "Transferência realizada com sucesso!" + ANSI_RESET);
        } else {
            System.out.println(ANSI_VERMELHO + "Erro: saldo insuficiente ou valor inválido." + ANSI_RESET);
        }
    }

    protected void registrarHistorico(String acao) {
        historico.add(acao);
    }

    public void mostrarHistorico() {
        System.out.println(ANSI_AMARELO + "\n=== Histórico da Conta " + numeroConta + " - " + titular.getNome() + " ===" + ANSI_RESET);
        for (String acao : historico) {
            System.out.println(acao);
        }
    }
}
