package br.edu.ufrgs.model.resultado;

public class RelatorioProjeto {

    private final String projetoId;

    private final ResultadoViabilidade resultadoViabilidade;

    public RelatorioProjeto(
            String projetoId,
            ResultadoViabilidade resultadoViabilidade
    ) {

        this.projetoId = projetoId;
        this.resultadoViabilidade =
                resultadoViabilidade;

    }

    public String getProjetoId() {
        return projetoId;
    }

    public ResultadoViabilidade
    getResultadoViabilidade() {

        return resultadoViabilidade;

    }

}