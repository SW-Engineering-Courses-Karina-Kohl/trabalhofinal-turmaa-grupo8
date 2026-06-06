package br.edu.ufrgs.model.resultado;

import java.util.Map;
import java.util.EnumMap;
import br.edu.ufrgs.model.enums.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;



/**
 * Testes para `ResultadoViabilidade`.
 * Resumo: verifica criação de resultados para cada status (EXCELENTE/VIAVEL/BAIXA_PRIORIDADE),
 * erro quando PAYBACK está ausente e leitura de valores por tipo.
 * Testes:
 * Teste1: testa criação de ResultadoViabilidade com status EXCELENTE (payback <= limiteExcelenteAnos).
 * Teste2: testa criação de ResultadoViabilidade com status VIAVEL (payback <= limiteViavelAnos).
 * Teste3: testa criação de ResultadoViabilidade com status BAIXA_PRIORIDADE
 *        (payback > limiteViavelAnos).
 * Teste4: testa que criar ResultadoViabilidade sem PAYBACK lança IllegalArgumentException.
 * Teste5: testa que getValorPeloTipo retorna os valores corretos para cada tipo de cálculo.
 * Testes6: testa que criar ResultadoViabilidade com limites inválidos lança IllegalArgumentException
 *       (limiteExcelenteAnos negativo, limiteViavelAnos negativo, limites iguais, limiteExcelenteAnos maior que limiteViavelAnos).
 */
public class ResultadoViabilidadeTest {
    
    @Test
    void deveCriarResultadoViabilidadeExelenteCorretamente(){
        double limiteExcelenteAnos = 3.0;
        double limiteViavelAnos = 5.0;

        Map<ETipoCalculo, Double> valoresCalculo = new EnumMap<>(ETipoCalculo.class);
        valoresCalculo.put(ETipoCalculo.PAYBACK, 2.0);
        valoresCalculo.put(ETipoCalculo.ECONOMIA_MENSAL, 500.0);
        valoresCalculo.put(ETipoCalculo.IMPACTO_VERDE, 1000.0);
        ResultadoViabilidade resultado = new ResultadoViabilidade(valoresCalculo, limiteExcelenteAnos, limiteViavelAnos);
        assertEquals(EStatusViabilidade.EXCELENTE, resultado.getStatus());
    }

     
    @Test
    void deveCriarResultadoViabilidadeViavelCorretamente(){
        double limiteExcelenteAnos = 3.0;
        double limiteViavelAnos = 5.0;

        Map<ETipoCalculo, Double> valoresCalculo = new EnumMap<>(ETipoCalculo.class);
        valoresCalculo.put(ETipoCalculo.PAYBACK, 4.0);
        valoresCalculo.put(ETipoCalculo.ECONOMIA_MENSAL, 500.0);
        valoresCalculo.put(ETipoCalculo.IMPACTO_VERDE, 1000.0);
        ResultadoViabilidade resultado = new ResultadoViabilidade(valoresCalculo, limiteExcelenteAnos, limiteViavelAnos);
        assertEquals(EStatusViabilidade.VIAVEL, resultado.getStatus());
    }

    @Test
    void deveCriarResultadoViabilidadeBaixaPrioridadeCorretamente(){
        double limiteExcelenteAnos = 3.0;
        double limiteViavelAnos = 5.0;

        Map<ETipoCalculo, Double> valoresCalculo = new EnumMap<>(ETipoCalculo.class);
        valoresCalculo.put(ETipoCalculo.PAYBACK, 6.0);
        valoresCalculo.put(ETipoCalculo.ECONOMIA_MENSAL, 500.0);
        valoresCalculo.put(ETipoCalculo.IMPACTO_VERDE, 1000.0);
        ResultadoViabilidade resultado = new ResultadoViabilidade(valoresCalculo, limiteExcelenteAnos, limiteViavelAnos);
        assertEquals(EStatusViabilidade.BAIXA_PRIORIDADE, resultado.getStatus());
    }

    @Test
    void deveLancarExcecaoParaPaybackFaltando() {
        double limiteExcelenteAnos = 3.0;
        double limiteViavelAnos = 5.0;

        Map<ETipoCalculo, Double> valoresCalculo = new EnumMap<>(ETipoCalculo.class);
        valoresCalculo.put(ETipoCalculo.ECONOMIA_MENSAL, 500.0);
        valoresCalculo.put(ETipoCalculo.IMPACTO_VERDE, 1000.0);

        try {
            new ResultadoViabilidade(valoresCalculo, limiteExcelenteAnos, limiteViavelAnos);
        } catch (IllegalArgumentException e) {
            assertEquals("O cálculo de PAYBACK é obrigatório para determinar a viabilidade.", e.getMessage());
        }
    }

    @Test
    void deveRetornarValorPeloTipoCorretamente() {
        double limiteExcelenteAnos = 3.0;
        double limiteViavelAnos = 5.0;

        Map<ETipoCalculo, Double> valoresCalculo = new EnumMap<>(ETipoCalculo.class);
        valoresCalculo.put(ETipoCalculo.PAYBACK, 2.0);
        valoresCalculo.put(ETipoCalculo.ECONOMIA_MENSAL, 500.0);
        valoresCalculo.put(ETipoCalculo.IMPACTO_VERDE, 1000.0);
        ResultadoViabilidade resultado = new ResultadoViabilidade(valoresCalculo, limiteExcelenteAnos, limiteViavelAnos);

        assertEquals(2.0, resultado.getValorPeloTipo(ETipoCalculo.PAYBACK));
        assertEquals(500.0, resultado.getValorPeloTipo(ETipoCalculo.ECONOMIA_MENSAL));
        assertEquals(1000.0, resultado.getValorPeloTipo(ETipoCalculo.IMPACTO_VERDE));
    }

    @Test
    void deveLancarExcecaoParaLimitesInvalidos() {
        double limiteExcelenteAnos = 3.0;
        double limiteViavelAnos = 5.0;

        try {
            new StatusViabilidade(2.0, -1.0, limiteViavelAnos);
        } catch (IllegalArgumentException e) {
            assertEquals("Os limites de anos devem ser positivos.", e.getMessage());
        }

        try {
            new StatusViabilidade(2.0, limiteExcelenteAnos, -1.0);
        } catch (IllegalArgumentException e) {
            assertEquals("Os limites de anos devem ser positivos.", e.getMessage());
        }
    }

    @Test
    void deveLancarExcecaoParaLimitesIguais() {
        double limiteExcelenteAnos = 3.0;
        double limiteViavelAnos = 3.0;

        try {
            new StatusViabilidade(2.0, limiteExcelenteAnos, limiteViavelAnos);
        } catch (IllegalArgumentException e) {
            assertEquals("O limite de anos para excelente deve ser menor que o limite para viável.", e.getMessage());
        }
    }

    @Test
    void deveLancarExcecaoParaLimiteExcelenteMaiorQueViavel() {
        double limiteExcelenteAnos = 4.0;
        double limiteViavelAnos = 3.0;

        try {
            new StatusViabilidade(2.0, limiteExcelenteAnos, limiteViavelAnos);
        } catch (IllegalArgumentException e) {
            assertEquals("O limite de anos para excelente deve ser menor que o limite para viável.", e.getMessage());
        }
    }
}
