package br.edu.ufrgs.model.entradas;

public class ProjetoSolar {

    private String projeto_id; // ID do projeto
    private String cliente;
    private double investimento_inicial; // investimento inicial em reais
    private double producao_mes_kWh;
    private String modelo_painel;


    public ProjetoSolar(
            String projeto_id,
            String cliente,
            double investimento_inicial,
            double producao_mes_kWh,
            String modelo_painel
    ) {
        this.projeto_id = projeto_id;
        this.cliente = cliente;
        this.investimento_inicial = investimento_inicial;
        this.producao_mes_kWh = producao_mes_kWh;
        this.modelo_painel = modelo_painel;
    }

    // ---------------- getters ----------------
    public String getProjeto_id() {
        return projeto_id;
    }
    public String getCliente() {
        return cliente;
    }
    public double getInvestimento_inicial() {
        return investimento_inicial;
    }
    public double getProducao_mes_kWh() {
        return producao_mes_kWh;
    }
    public String getModelo_painel() {
        return modelo_painel;
    }
}


    