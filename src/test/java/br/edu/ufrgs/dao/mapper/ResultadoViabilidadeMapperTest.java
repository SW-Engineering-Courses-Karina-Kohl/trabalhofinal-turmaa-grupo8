package br.edu.ufrgs.dao.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import br.edu.ufrgs.model.enums.ETipoCalculo;
import br.edu.ufrgs.model.resultado.RelatorioProjeto;
import br.edu.ufrgs.model.resultado.ResultadoViabilidade;

public class ResultadoViabilidadeMapperTest {

        @Test
        void deveGerarLinhaCsvCorretamente() {

                Map<ETipoCalculo, Double> valores = 
                        new HashMap<>();

                valores.put(
                                ETipoCalculo.ECONOMIA_MENSAL,
                                1020.0
                        );

                valores.put(
                                ETipoCalculo.PAYBACK,
                                4.08
                        );

                valores.put(
                                ETipoCalculo.IMPACTO_VERDE,
                                110.40
                        );

                ResultadoViabilidade resultado = 
                        new ResultadoViabilidade(
                                valores,
                                3,
                                6
                        );

                RelatorioProjeto relatorio = 
                        new RelatorioProjeto(
                                "P_001",
                                resultado
                        );

                ResultadoViabilidadeMapper mapper = 
                        new ResultadoViabilidadeMapper();


                List<String[]> linhas = 
                        mapper.mapear(List.of(relatorio));

                assertEquals(
                                2, 
                                linhas.size()
                        );

                assertEquals(
                                "projeto_id",
                                linhas.get(0)[0]
                        );

                assertEquals(
                                "P_001",
                                linhas.get(1)[0]
                        );

                assertEquals(
                                "1020.00",
                                linhas.get(1)[1]
                        );

                assertEquals(
                                "4.08",
                                linhas.get(1)[2]
                        );

                assertEquals(
                                "110.40",
                                linhas.get(1)[3]
                        );

                assertEquals(
                                "VIAVEL",
                                linhas.get(1)[4]
                        );
        }
}