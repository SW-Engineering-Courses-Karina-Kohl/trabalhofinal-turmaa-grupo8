package br.edu.ufrgs.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContextoCalculoTest {

    @Test
    public void contextoValoresCorretos() {
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

        assertEquals(producaoEsperada, contexto.getProducaoKWh());
        assertEquals(investimentoEsperado, contexto.getInvestimentoInicial());
        assertEquals(tarifaEsperada, contexto.getTarifa());
        assertEquals(fatorCO2Esperado, contexto.getFatorCO2());
    }
}