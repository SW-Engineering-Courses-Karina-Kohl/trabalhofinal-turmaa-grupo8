package br.edu.ufrgs.service;

import br.edu.ufrgs.model.enums.ETipoCalculo;

//calcula o payback estimado do projeto solar
// payback estimado = investimento inicial / economia mensal / 12

public class CalculoPaybackEstimado extends CalculoEconomia {
    private double valor;

    @Override
    public void calcular(ContextoCalculo contexto) {
        double economiaMensal = calcularEconomiaMensal(contexto);
        this.valor = contexto.getInvestimentoInicial() / economiaMensal / 12;
    }

    @Override
    public double getValor() { 
        return valor;
    }

    //retorna o tipo PAYBACK para ser usado pelo MotorDeCalculo
    @Override
    public ETipoCalculo getTipo() {
        return ETipoCalculo.PAYBACK;
    }
}