package br.edu.ufrgs.model.resultado;

import java.util.Map;

import br.edu.ufrgs.model.enums.*;

/**
 * Resultado da análise de viabilidade.
 *
 * Regras de negócio (resumo):
 * - O cálculo de PAYBACK é obrigatório; caso ausente, lança
 *   {@link IllegalArgumentException}.
 * - O status é determinado a partir do payback e dos limites
 *   fornecidos ao construir a instância.
 * - Valores de outros tipos podem ser consultados via
 *   {@link #getValorPeloTipo(br.edu.ufrgs.model.enums.ETipoCalculo)}
 *   (retorna 0.0 se não existir).
 * - O status é acessível via {@link #getStatus()}.
 * - A classe é imutável e encapsula a lógica de determinação do status
 *  a partir dos resultados dos cálculos.
 * - A relação entre ResultadoViabilidade e StatusViabilidade é de composição: 
 * o status é parte integrante do resultado e é determinado no momento da 
 * criação do ResultadoViabilidade, não podendo existir sem o resultado.
 */
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
