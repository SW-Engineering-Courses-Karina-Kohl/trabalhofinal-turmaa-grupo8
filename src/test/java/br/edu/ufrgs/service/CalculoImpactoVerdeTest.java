package br.edu.ufrgs.service;

import br.edu.ufrgs.model.enums.ETipoCalculo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculoImpactoVerdeTest {

    @Test
    public void deveCalcularImpactoVerdeCorretamente() {

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
}