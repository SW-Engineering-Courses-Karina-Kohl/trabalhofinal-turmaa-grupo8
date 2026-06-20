package br.edu.ufrgs.model.resultado;

import br.edu.ufrgs.model.enums.EStatusViabilidade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


/**
 * Testes para `StatusViabilidade`.
 * Resumo: verifica classificação por payback (EXCELENTE/VIAVEL/BAIXA_PRIORIDADE)
 * e validações de entrada (payback negativo, limites inválidos/iguais).
 */
public class StatusViabilidadeTest {
    
    @Test
    void deveClassificarViabilidadeCorretamente(){
        double limiteExcelenteAnos = 3.0;
        double limiteViavelAnos = 5.0;

        StatusViabilidade status1 = new StatusViabilidade(2.0, limiteExcelenteAnos, limiteViavelAnos);
        assertEquals(EStatusViabilidade.EXCELENTE, status1.getStatus());

        StatusViabilidade status2 = new StatusViabilidade(4.0, limiteExcelenteAnos, limiteViavelAnos);
        assertEquals(EStatusViabilidade.VIAVEL, status2.getStatus());

        StatusViabilidade status3 = new StatusViabilidade(6.0, limiteExcelenteAnos, limiteViavelAnos);
        assertEquals(EStatusViabilidade.BAIXA_PRIORIDADE, status3.getStatus());
    }

    @Test
    void deveLancarExcecaoParaPaybackNegativo() {
        double limiteExcelenteAnos = 3.0;
        double limiteViavelAnos = 5.0;

        try {
            new StatusViabilidade(-1.0, limiteExcelenteAnos, limiteViavelAnos);
        } catch (IllegalArgumentException e) {
            assertEquals("O payback deve ser um valor não negativo.", e.getMessage());
        }
    }

    @Test
    void deveLancarExcecaoParaLimitesInvalidos(){
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
    void deveLancarExcecaoParaLimitesIguais(){
        double limiteExcelenteAnos = 3.0;
        double limiteViavelAnos = 3.0;

        try {
            new StatusViabilidade(2.0, limiteExcelenteAnos, limiteViavelAnos);
        } catch (IllegalArgumentException e) {
            assertEquals("O limite de anos para excelente deve ser menor que o limite para viável.", e.getMessage());
        }
    }

    @Test
    void deveLancarExcecaoParaLimiteExcelenteMaiorQueViavel(){
        double limiteExcelenteAnos = 4.0;
        double limiteViavelAnos = 3.0;

        try {
            new StatusViabilidade(2.0, limiteExcelenteAnos, limiteViavelAnos);
        } catch (IllegalArgumentException e) {
            assertEquals("O limite de anos para excelente deve ser menor que o limite para viável.", e.getMessage());
        }
    }

}
