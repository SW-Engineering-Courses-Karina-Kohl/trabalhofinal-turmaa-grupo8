package br.edu.ufrgs.service;

import br.edu.ufrgs.model.enums.ETipoCalculo;

//calcula a economia mensal gerada pelo projeto solar
// economia mensal = produção em kWh * tarifa de energia

public class CalculoEconomiaMensal extends CalculoEconomia {
    private double valor;

    @Override
    public void calcular(ContextoCalculo contexto) {
        this.valor = calcularEconomiaMensal(contexto);
    }

    @Override
    public double getValor() {
        return valor; 
    }

    //retorna o tipo ECONOMIA_MENSAL para ser usada pelo MotorDeCalculo
    @Override
    public ETipoCalculo getTipo() {
        return ETipoCalculo.ECONOMIA_MENSAL;
    }
}