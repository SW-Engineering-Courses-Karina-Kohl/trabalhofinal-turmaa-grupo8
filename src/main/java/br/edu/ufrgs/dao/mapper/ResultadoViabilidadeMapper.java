package br.edu.ufrgs.dao.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.edu.ufrgs.model.enums.ETipoCalculo;
import br.edu.ufrgs.model.resultado.RelatorioProjeto;

public class ResultadoViabilidadeMapper {

    public List<String[]> mapear(

            List<RelatorioProjeto>
                    relatorios

    ) {

        List<String[]> linhas =
                new ArrayList<>();


        linhas.add(

                new String[]{

                        "projeto_id",

                        "economia_mensal_rs",

                        "payback_anos",

                        "co2_evitado_kg",

                        "status"

                }

        );


        for(

                RelatorioProjeto relatorio

                :

                relatorios

        ){

            linhas.add(

                    new String[]{

                            relatorio
                                    .getProjetoId(),

                            String.format(
                                    Locale.US,
                                    "%.2f",

                                    relatorio
                                            .getResultadoViabilidade()
                                            .getValorPeloTipo(

                                                    ETipoCalculo
                                                            .ECONOMIA_MENSAL

                                            )

                            ),

                            String.format(
                                    Locale.US,
                                    "%.2f",

                                    relatorio
                                            .getResultadoViabilidade()
                                            .getValorPeloTipo(

                                                    ETipoCalculo
                                                            .PAYBACK

                                            )

                            ),

                            String.format(
                                    Locale.US,
                                    "%.2f",

                                    relatorio
                                            .getResultadoViabilidade()
                                            .getValorPeloTipo(

                                                    ETipoCalculo
                                                            .IMPACTO_VERDE

                                            )

                            ),

                            relatorio
                                    .getResultadoViabilidade()
                                    .getStatus()
                                    .name()

                    }

            );

        }

        return linhas;

    }

}