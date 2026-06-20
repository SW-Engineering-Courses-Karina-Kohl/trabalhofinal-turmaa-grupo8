package br.edu.ufrgs.service;

import br.edu.ufrgs.model.enums.ETipoCalculo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculoImpactoVerdeTest {

    @Test
    public void testCalcularImpactoVerdePadrao() {

        ContextoCalculo contexto =
                new ContextoCalculo(
                        1200,
                        50000,
                        0.85,
                        0.092
                );

        CalculoImpactoVerde calculo =
                new CalculoImpactoVerde();

        calculo.calcular(contexto);

        double valorEsperado = 110.4;

        assertEquals(
                valorEsperado,
                calculo.getValor(),
                0.0001
        );

        assertEquals(
                ETipoCalculo.IMPACTO_VERDE,
                calculo.getTipo()
        );
    }
    

// teste produção = 0 -> impacto verde deve ser 0

        @Test
        public void testCalcularImpactoVerdeProducaoZero() {

            ContextoCalculo contexto =
                    new ContextoCalculo(
                            0,
                            50000,
                            0.85,
                            0.092
                    );

            CalculoImpactoVerde calculo =
                    new CalculoImpactoVerde();

            calculo.calcular(contexto);

            double valorEsperado = 0;

            assertEquals(
                    valorEsperado,
                    calculo.getValor(),
                    0.0001
            );

            assertEquals(
                    ETipoCalculo.IMPACTO_VERDE,
                    calculo.getTipo()
            );
        }

//  teste fator CO2 = 0 -> impacto verde deve ser 0

        @Test
        public void testCalcularImpactoVerdeFatorCO2Zero() {

            ContextoCalculo contexto =
                    new ContextoCalculo(
                            1200,
                            50000,
                            0.85,
                            0
                    );

            CalculoImpactoVerde calculo =
                    new CalculoImpactoVerde();

            calculo.calcular(contexto);

            double valorEsperado = 0;

            assertEquals(
                    valorEsperado,
                    calculo.getValor(),
                    0.0001
            );

            assertEquals(
                    ETipoCalculo.IMPACTO_VERDE,
                    calculo.getTipo()
            );
        }

// teste produção e fator CO2 = 0 -> impacto verde deve ser 0

        @Test
        public void testCalcularImpactoVerdeProducaoFatorCO2Zero() {

            ContextoCalculo contexto =
                    new ContextoCalculo(
                            0,
                            50000,
                            0.85,
                            0
                    );

            CalculoImpactoVerde calculo =
                    new CalculoImpactoVerde();

            calculo.calcular(contexto);

            double valorEsperado = 0;

            assertEquals(
                    valorEsperado,
                    calculo.getValor(),
                    0.0001
            );

            assertEquals(
                    ETipoCalculo.IMPACTO_VERDE,
                    calculo.getTipo()
            );
        }

//  teste verificar o tipo independente do cálculo

        @Test
        public void testCalcularImpactoVerdeTipo() {

            CalculoImpactoVerde calculo =
            new CalculoImpactoVerde();

            assertEquals(
                    ETipoCalculo.IMPACTO_VERDE,
                    calculo.getTipo()
            );
        }

// teste de precisão com valores decimais

        @Test
        public void testCalcularImpactoVerdePrecisao() {

            ContextoCalculo contexto =
                    new ContextoCalculo(
                            1234.567,
                            50000,
                            0.85,
                            0.092
                    );

            CalculoImpactoVerde calculo =
                    new CalculoImpactoVerde();

            calculo.calcular(contexto);

            double valorEsperado = 113.580164;

            assertEquals(
                    valorEsperado,
                    calculo.getValor(),
                    0.000001
            );

            assertEquals(
                    ETipoCalculo.IMPACTO_VERDE,
                    calculo.getTipo()
            );
        }
}