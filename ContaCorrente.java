public class ContaCorrente extends ContaBancaria {
    private double limite;

    public ContaCorrente(Cliente titular, double saldoInicial, double limite) {
        super(titular, saldoInicial);
        this.limite = limite >= 0 ? limite : 0;
        registrarHistorico("Conta Corrente criada com limite: R$" + this.limite);
    }

    @Override
    public void sacar(double valor) {
        if (valor > 0 && saldo + limite >= valor) {
            saldo -= valor;
            registrarHistorico(ANSI_VERMELHO + "Saque: R$" + valor + ANSI_RESET);
            System.out.println(ANSI_VERMELHO + "Saque realizado com sucesso!" + ANSI_RESET);
        } else {
            System.out.println(ANSI_VERMELHO + "Erro: saldo insuficiente ou valor inválido." + ANSI_RESET);
        }
    }
}
