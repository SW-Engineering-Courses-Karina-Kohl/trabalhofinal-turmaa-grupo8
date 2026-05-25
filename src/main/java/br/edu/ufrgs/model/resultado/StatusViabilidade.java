package br.edu.ufrgs.model.resultado;

import br.edu.ufrgs.model.enums.EStatusViabilidade;

public class StatusViabilidade {
    private final EStatusViabilidade statusViabilidade;

    public StatusViabilidade(double payback, double limiteExcelenteAnos, double limiteViavelAnos) {
        this.statusViabilidade = determinarStatus(payback, limiteExcelenteAnos, limiteViavelAnos);
    }

    private EStatusViabilidade determinarStatus(double payback, double limiteExcelenteAnos, double limiteViavelAnos) {
        if (payback <= limiteExcelenteAnos) {
            return EStatusViabilidade.EXCELENTE;
        } else if (payback <= limiteViavelAnos) {
            return EStatusViabilidade.VIAVEL;
        } else {
            return EStatusViabilidade.BAIXA_PRIORIDADE;
        }
    }

    protected EStatusViabilidade getStatus() {
        return statusViabilidade;
    }
}
