package br.edu.ufrgs.model.resultado;

import java.util.Map;

import br.edu.ufrgs.model.enums.*;

public class ResultadoViabilidade {
    private final Map<ETipoCalculo, Double> valoresCalculo;
    private final StatusViabilidade status;
    public ResultadoViabilidade(Map<ETipoCalculo, Double> valoresCalculo, double limiteExcelenteAnos, double limiteViavelAnos) {
        this.valoresCalculo = valoresCalculo;
        try{
            double payback = valoresCalculo.get(ETipoCalculo.PAYBACK);
            this.status = new StatusViabilidade(payback, limiteExcelenteAnos, limiteViavelAnos);
        }catch(NullPointerException e){
            throw new IllegalArgumentException("O cálculo de PAYBACK é obrigatório para determinar a viabilidade.");
        }
    }

    public EStatusViabilidade getStatus() {
        return status.getStatus();
    }

    public double getValorPeloTipo(ETipoCalculo tipo) {
        return valoresCalculo.getOrDefault(tipo, 0.0);
    }

}
