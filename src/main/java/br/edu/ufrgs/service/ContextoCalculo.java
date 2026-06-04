package br.edu.ufrgs.service;

public class ContextoCalculo {

    private final double producaoKWh;
    private final double investimentoInicial;
    private final double tarifa;
    private final double fatorCO2;

    // construtor

    public ContextoCalculo(
            double producaoKWh,
            double investimentoInicial,
            double tarifa,
            double fatorCO2
    ) {

        if (producaoKWh<0 || investimentoInicial < 0 || tarifa < 0 || fatorCO2 < 0) {
            throw new IllegalArgumentException("Valores de contexto de cálculo não podem ser negativos.");
        }

        if(producaoKWh == 0 || tarifa == 0) {
            throw new IllegalArgumentException("ProducaoKWh e Tarifa não podem ser zero.");
        }

        this.producaoKWh = producaoKWh;
        this.investimentoInicial = investimentoInicial;
        this.tarifa = tarifa;
        this.fatorCO2 = fatorCO2;
    }

    // getters

    public double getProducaoKWh() {
        return producaoKWh;
    }

    public double getInvestimentoInicial() {
        return investimentoInicial;
    }

    public double getTarifa() {
        return tarifa;
    }

    public double getFatorCO2() {
        return fatorCO2;
    }
}


