package br.edu.ufrgs.service;


//classe abstrata para cálculos de economia
// eh aplicada para os calculos de economia mensal e payback estimado

//calcula a economia mensal gerada pelo projeto solar
// economia = produção em kWh * tarifa de energia

public abstract class CalculoEconomia implements ICalculo {
    protected double calcularEconomiaMensal(ContextoCalculo contexto) {
        return contexto.getProducaoKWh() * contexto.getTarifa();
    }
}