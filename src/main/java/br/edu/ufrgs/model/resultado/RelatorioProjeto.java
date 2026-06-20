package br.edu.ufrgs.model.resultado;

/*
Clsse que representa consolidado da analise de viabilidade de um projeto solar, contendo o ID do projeto e os resultados calculados para economia mensal, payback e impacto ambiental (CO2 evitado).
Ela é utilizada para armazenar os resultados de cada projeto após a análise de viabilidade, permitindo que esses dados sejam facilmente acessados e exportados para relatórios ou arquivos CSV. O RelatorioProjeto é uma estrutura de dados fundamental para organizar as informações de saída do sistema, facilitando a geração de relatórios de viabilidade para cada projeto solar analisado.
*/

public class RelatorioProjeto {

    private final String projetoId;

    private final ResultadoViabilidade resultadoViabilidade;

    public RelatorioProjeto(String projetoId,ResultadoViabilidade resultadoViabilidade) {

        this.projetoId = projetoId;
        this.resultadoViabilidade = resultadoViabilidade;

    }


    public String getProjetoId() {
        return projetoId;
    }


    public ResultadoViabilidade getResultadoViabilidade() {
        return resultadoViabilidade;
    }


}
