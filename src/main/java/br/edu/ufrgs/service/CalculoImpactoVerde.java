package br.edu.ufrgs.service;

import br.edu.ufrgs.model.enums.ETipoCalculo;

// implements ICalculo - segue o contrato da interface
// tem os métodos calcular(), getValor() e getTipo()

// a classe só recebe o contexto e calcula

public class CalculoImpactoVerde implements ICalculo {

    // guarda o resultado do cálculo
    private double valor;

    // final pq IMPACTO_VERDE não muda
    private final ETipoCalculo tipo;

    // construtor: define o tipo do cálculo
    public CalculoImpactoVerde() {
        this.tipo = ETipoCalculo.IMPACTO_VERDE;
    }

    // faz o cálculo do impacto verde usando os dados do contexto
    // fórmula:
    // (produção de energia × fator CO2 evitado)
    
    @Override
    public void calcular(ContextoCalculo contexto) {

        this.valor =
                contexto.getProducaoKWh()
                * contexto.getFatorCO2();
    }

    // retorna o valor calculado

    @Override
    public double getValor() {
        return valor;
    }

    // retorna o tipo do cálculo
    
    @Override
    public ETipoCalculo getTipo() {
        return tipo;
    }
}