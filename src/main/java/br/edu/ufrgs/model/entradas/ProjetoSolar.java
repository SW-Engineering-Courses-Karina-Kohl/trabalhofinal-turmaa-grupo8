package br.edu.ufrgs.model.entradas;

public class ProjetoSolar {

    private String projetoId; // ID do projeto
    private String cliente;
    private double investimentoInicial; // investimento inicial em reais
    private double producaoMesKWh;
    private String modeloPainel;


    public ProjetoSolar(
            String projetoId,
            String cliente,
            double investimentoInicial,
            double producaoMesKWh,
            String modeloPainel
    ) {
        this.projetoId = projetoId;
        this.cliente = cliente;
        this.investimentoInicial = investimentoInicial;
        this.producaoMesKWh = producaoMesKWh;
        this.modeloPainel = modeloPainel;
    }

    // ---------------- getters ----------------
    public String getProjetoId() {
        return projetoId;
    }
    public String getCliente() {
        return cliente;
    }
    public double getInvestimentoInicial() {
        return investimentoInicial;
    }
    public double getProducaoMesKWh() {
        return producaoMesKWh;
    }
    public String getModeloPainel() {
        return modeloPainel;
    }
}


    