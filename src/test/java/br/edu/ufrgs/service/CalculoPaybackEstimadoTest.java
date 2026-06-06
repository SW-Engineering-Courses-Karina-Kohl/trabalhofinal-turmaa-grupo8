package br.edu.ufrgs.service;

import br.edu.ufrgs.model.enums.ETipoCalculo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculoPaybackEstimadoTest {

    //Teste para verificar se o tipo retornado é PAYBACK
    @Test
    public void deveRetornarTipoPayback() {
        CalculoPaybackEstimado calculo = new CalculoPaybackEstimado();

        assertEquals(ETipoCalculo.PAYBACK, calculo.getTipo());
    }

    //exemplo P_003 — payback EXCELENTE (< 4 anos)
    // 250000 / 7225 / 12 = 2.88 anos
    @Test
    public void deveCalcularPaybackExcelente() {
        ContextoCalculo contexto = new ContextoCalculo(
                8500, 250000, 0.85, 0.092
        );
        CalculoPaybackEstimado calculo = new CalculoPaybackEstimado();

        calculo.calcular(contexto);

        assertEquals(2.88, calculo.getValor(), 0.01);
    }

    // exemplo P_001 — payback VIAVEL (entre 4 e 8 anos)
    // 50000 / 1020 / 12 = 4.08 anos
    @Test
    public void deveCalcularPaybackViavelP001() {
        ContextoCalculo contexto = new ContextoCalculo(
                1200, 50000, 0.85, 0.092
        );
        CalculoPaybackEstimado calculo = new CalculoPaybackEstimado();

        calculo.calcular(contexto);

        assertEquals(4.08, calculo.getValor(), 0.01);
    }

    // exemplo P_002 — payback VIAVEL (entre 4 e 8 anos)
    // 15000 / 212.5 / 12 = 5.88 anos
    @Test
    public void deveCalcularPaybackViavelP002() {
        ContextoCalculo contexto = new ContextoCalculo(
                250, 15000, 0.85, 0.092
        );
        CalculoPaybackEstimado calculo = new CalculoPaybackEstimado();

        calculo.calcular(contexto);

        assertEquals(5.88, calculo.getValor(), 0.01);
    }

    // teste payback BAIXA_PRIORIDADE (> 8 anos)
    // 100000 / 85 / 12 = 98.04 anos
    @Test
    public void deveCalcularPaybackBaixaPrioridade() {
        ContextoCalculo contexto = new ContextoCalculo(
                100, 100000, 0.85, 0.092
        );
        CalculoPaybackEstimado calculo = new CalculoPaybackEstimado();

        calculo.calcular(contexto);

        assertTrue(calculo.getValor() > 8);
    }

    // teste limite exato de 4 anos (fronteira entre EXCELENTE eVIAVEL)
    // 40800 / 850 / 12 = 4.00 anos
    @Test
    public void deveCalcularPaybackExatamenteQuatroAnos() {
        ContextoCalculo contexto = new ContextoCalculo(
                1000, 40800, 0.85, 0.092
        );
        CalculoPaybackEstimado calculo = new CalculoPaybackEstimado();

        calculo.calcular(contexto);

        assertEquals(4.00, calculo.getValor(), 0.01);
    }

    // teste limite exato de 8 anos (fronteira entre VIAVEL e BAIXA_PRIORIDADE)
    // 81600 / 850 / 12 = 8.00 anos
    @Test
    public void deveCalcularPaybackExatamenteOitoAnos() {
        ContextoCalculo contexto = new ContextoCalculo(
                1000, 81600, 0.85, 0.092
        );
        CalculoPaybackEstimado calculo = new CalculoPaybackEstimado();

        calculo.calcular(contexto);

        assertEquals(8.00, calculo.getValor(), 0.01);
    }

    // teste producao zero faz economiaMensal = 0
    // divisão de double por zero retorna Infinity
    @Test
    public void deveRetornarInfinityParaProducaoZero() {
        ContextoCalculo contexto = new ContextoCalculo(
                0, 50000, 0.85, 0.092
        );
        CalculoPaybackEstimado calculo = new CalculoPaybackEstimado();

        calculo.calcular(contexto);

        assertTrue(Double.isInfinite(calculo.getValor()));
    }
}