package br.edu.ufrgs.model.entradas;

/*
Classe que representa um projeto solar, com informações como ID do projeto, nome do cliente, investimento inicial, produção mensal em kWh e modelo do painel solar.
Esses dados são essenciais para realizar análises de viabilidade econômica e ambiental dos projetos solares, permitindo calcular métricas como economia mensal, payback e impacto ambiental (CO2 evitado).
A classe possui um construtor para inicializar os atributos e getters para acessar os valores de cada campo. Ela é utilizada como parte do processo de leitura e mapeamento dos dados de entrada, onde cada linha do arquivo CSV é convertida em um objeto ProjetoSolar para posterior análise.
*/

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


    