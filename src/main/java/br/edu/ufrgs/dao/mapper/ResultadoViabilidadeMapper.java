package br.edu.ufrgs.dao.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.edu.ufrgs.model.enums.ETipoCalculo;
import br.edu.ufrgs.model.resultado.RelatorioProjeto;


/*
Classe responsável por converter os resultados e analises de viabilidade em um formato adequado para exportação em CSV. 
Ela recebe uma lista de objetos do tipo RelatorioProjeto e gera uma lista de linhas CSV (List<String[]>) contendo os dados que serao escritos no arquivo de saída. O mapper cria automaticamente o cabeçalho do relatório e, para cada projeto, extrai informações como economia mensal, payback, impacto ambiental (CO2 evitado) e status de viabilidade.
Os valores numéricos são formatados com duas casas decimais utilizando o padrão americano (Locale.US) para garantir compatibilidade com o formato CSV especificado.
O resultado final pode ser utilizado pelo CSVService para gerar o arquivo relatorio_viabilidade.csv.
*/


public class ResultadoViabilidadeMapper {

        public List<String[]> mapear(
                        List<RelatorioProjeto> relatorios

        ) {
                List<String[]> linhas = 
                        new ArrayList<>();

                linhas.add(new String[] {
                                        "projeto_id",
                                        "economia_mensal_rs",
                                        "payback_anos",
                                        "co2_evitado_kg",
                                        "status"

                                }

                );


                for (RelatorioProjeto relatorio : relatorios) {
                        linhas.add(
                                new String[] {
                                        relatorio.getProjetoId(),
                                        String.format(Locale.US, "%.2f",

                                        relatorio.getResultadoViabilidade().getValorPeloTipo(ETipoCalculo.ECONOMIA_MENSAL)),
                                        String.format(Locale.US, "%.2f",

                                        relatorio.getResultadoViabilidade().getValorPeloTipo(ETipoCalculo.PAYBACK)),
                                        String.format(Locale.US, "%.2f",

                                        relatorio.getResultadoViabilidade().getValorPeloTipo(ETipoCalculo.IMPACTO_VERDE)),

                                        relatorio.getResultadoViabilidade().getStatus().name()

                                        }

                        );

                }

                return linhas;

        }

}