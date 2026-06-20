package br.edu.ufrgs.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContextoCalculoTest {

    // o delta 0.0001 é usado para comparação de números do tipo double (ponto flutuante)
    // permite uma pequena margem de erro devido à representação interna desses números em Java
    // p/ evitar falhas nos testes por diferenças mto pequenas que podem ocorrer nos cálculos

    @Test
    // verifica se o construtor armazena corretamente os valores fornecidos e se os getters 
    // retornam exatamente os mesmos valores

    public void testContextoValoresCorretos() {
        double producaoEsperada = 1200;
        double investimentoEsperado = 50000;
        double tarifaEsperada = 0.85;
        double fatorCO2Esperado = 0.092;


        ContextoCalculo contexto = new ContextoCalculo(
            producaoEsperada, 
            investimentoEsperado, 
            tarifaEsperada, 
            fatorCO2Esperado
        );

        assertEquals(producaoEsperada, contexto.getProducaoKWh(), 0.0001);
        assertEquals(investimentoEsperado, contexto.getInvestimentoInicial(), 0.0001);
        assertEquals(tarifaEsperada, contexto.getTarifa(), 0.0001);
        assertEquals(fatorCO2Esperado, contexto.getFatorCO2(), 0.0001);
    }

// teste com todos os valores zerados
    @Test
    public void testContextoValoresZerados() {
        double producaoEsperada = 0;
        double investimentoEsperado = 0;
        double tarifaEsperada = 0;
        double fatorCO2Esperado = 0;

        ContextoCalculo contexto = new ContextoCalculo(
            producaoEsperada, 
            investimentoEsperado, 
            tarifaEsperada, 
            fatorCO2Esperado
        );

        assertEquals(0, contexto.getProducaoKWh(), 0.0001);
        assertEquals(0, contexto.getInvestimentoInicial(), 0.0001);
        assertEquals(0, contexto.getTarifa(), 0.0001);
        assertEquals(0, contexto.getFatorCO2(), 0.0001);
    }

// teste com valores decimais
    @Test
    public void testContextoValoresDecimais() {
        double producaoEsperada = 1234.56;
        double investimentoEsperado = 78901.23;
        double tarifaEsperada = 0.75;
        double fatorCO2Esperado = 0.045;

        ContextoCalculo contexto = new ContextoCalculo(
            producaoEsperada, 
            investimentoEsperado, 
            tarifaEsperada, 
            fatorCO2Esperado
        );

        assertEquals(producaoEsperada, contexto.getProducaoKWh(), 0.0001);
        assertEquals(investimentoEsperado, contexto.getInvestimentoInicial(), 0.0001);
        assertEquals(tarifaEsperada, contexto.getTarifa(), 0.0001);
        assertEquals(fatorCO2Esperado, contexto.getFatorCO2(), 0.0001);
    }

}