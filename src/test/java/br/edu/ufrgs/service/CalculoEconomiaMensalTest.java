package br.edu.ufrgs.service;

import br.edu.ufrgs.model.enums.ETipoCalculo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculoEconomiaMensalTest {

    // teste P_001 — 1200 kWh × R$0,85 = R$1020,00
    @Test
    public void deveCalcularEconomiaMensalP001() {
        ContextoCalculo contexto = new ContextoCalculo(
                1200, 50000, 0.85, 0.092
        );
        CalculoEconomiaMensal calculo = new CalculoEconomiaMensal();

        calculo.calcular(contexto);

        assertEquals(1020.00, calculo.getValor(), 0.01);
    }

    // teste P_002 — 250 kWh × R$0,85 = R$212,50
    @Test
    public void deveCalcularEconomiaMensalP002() {
        ContextoCalculo contexto = new ContextoCalculo(
                250, 15000, 0.85, 0.092
        );
        CalculoEconomiaMensal calculo = new CalculoEconomiaMensal();

        calculo.calcular(contexto);

        assertEquals(212.50, calculo.getValor(), 0.01);
    }

    // teste P_003 — 8500 kWh × R$0,85 = R$7225,00
    @Test
    public void deveCalcularEconomiaMensalP003() {
        ContextoCalculo contexto = new ContextoCalculo(
                8500, 250000, 0.85, 0.092
        );
        CalculoEconomiaMensal calculo = new CalculoEconomiaMensal();

        calculo.calcular(contexto);

        assertEquals(7225.00, calculo.getValor(), 0.01);
    }

    //verifica se o tipo retornado é ECONOMIA_MENSAL
    @Test
    public void deveRetornarTipoEconomiaMensal() {
        CalculoEconomiaMensal calculo = new CalculoEconomiaMensal();

        assertEquals(ETipoCalculo.ECONOMIA_MENSAL, calculo.getTipo());
    }

    //producao zero, economia deve ser zero
    @Test
    public void deveRetornarZeroParaProducaoZero() {
        ContextoCalculo contexto = new ContextoCalculo(
                0, 50000, 0.85, 0.092
        );
        CalculoEconomiaMensal calculo = new CalculoEconomiaMensal();

        calculo.calcular(contexto);

        assertEquals(0.0, calculo.getValor(), 0.01);
    }
}