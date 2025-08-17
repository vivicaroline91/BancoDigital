public class ContaPoupanca extends ContaBancaria {
    private double taxaRendimento; // percentual

    public ContaPoupanca(Cliente titular, double saldoInicial, double taxaRendimento) {
        super(titular, saldoInicial);
        this.taxaRendimento = taxaRendimento >= 0 ? taxaRendimento : 0;
        registrarHistorico("Conta Poupança criada com taxa: " + this.taxaRendimento + "%");
    }

    public void aplicarRendimento() {
        double rendimento = saldo * (taxaRendimento / 100);
        saldo += rendimento;
        registrarHistorico(ANSI_VERDE + "Rendimento aplicado: R$" + rendimento + ANSI_RESET);
        System.out.println(ANSI_VERDE + "Rendimento aplicado com sucesso!" + ANSI_RESET);
    }
}
