package br.edu.ufrgs.dao.mapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import br.edu.ufrgs.model.entradas.ProjetoSolar;


public class ProjetoSolarMapperTest {


        @Test
        void deveCriarProjetoValido(){

                List<String[]> linhas =
                        new ArrayList<>();


                linhas.add(
                        new String[]{
                                "id",
                                "cliente",
                                "investimento",
                                "producao",
                                "modelo"
                        }
                );


                linhas.add(
                        new String[]{
                                "1",
                                "Juraci",
                                "20000",
                                "500",
                                "ABC"
                        }
                );


                ProjetoSolarMapper mapper =
                        new ProjetoSolarMapper();


                List<ProjetoSolar> projetos =
                        mapper.mapear(
                                linhas
                        );


                assertEquals(
                        1,
                        projetos.size()
                );


                assertEquals(
                        "Juraci",
                        projetos.get(0)
                                .getCliente()
                );

        }



        @Test
        void deveRegistrarErroQuandoInvestimentoNegativo(){

                List<String[]> linhas =
                        new ArrayList<>();

                linhas.add(
                        new String[]{
                                "id",
                                "cliente",
                                "investimento",
                                "producao",
                                "modelo"
                        }
                );

                linhas.add(
                        new String[]{
                                "1",
                                "Juraci",
                                "-5000",
                                "400",
                                "ABC"
                        }
                );

                ProjetoSolarMapper mapper =
                        new ProjetoSolarMapper();

                mapper.mapear(
                        linhas
                );

                assertEquals(
                        1,
                        mapper.getErros().size()
                );

                assertEquals(
                        "Investimento negativo",
                        mapper.getErros()
                                .get(0)
                                .getMotivo()
                );
        }



        @Test
        void deveRegistrarErroQuandoValorNaoForNumero(){

                List<String[]> linhas =
                        new ArrayList<>();

                linhas.add(
                        new String[]{
                                "id",
                                "cliente",
                                "investimento",
                                "producao",
                                "modelo"
                        }
                );

                linhas.add(
                        new String[]{
                                "1",
                                "Juraci",
                                "abc",
                                "500",
                                "ABC"
                        }
                );

                ProjetoSolarMapper mapper =
                        new ProjetoSolarMapper();

                mapper.mapear(
                        linhas
                );

                assertEquals(
                        1,
                        mapper.getErros().size()
                );

                assertEquals(
                        "Dados inválidos, números esperados para investimento ou produção",
                        mapper.getErros()
                                .get(0)
                                .getMotivo()
                );
        }


        @Test
        void deveRegistrarErroQuandoNumeroColunasInvalido(){

                List<String[]> linhas =
                        new ArrayList<>();

                linhas.add(
                        new String[]{
                                "id",
                                "cliente"
                        }
                );

                linhas.add(
                        new String[]{
                                "1",
                                "Juraci",
                                "20000"
                        }
                );

                ProjetoSolarMapper mapper =
                        new ProjetoSolarMapper();

                mapper.mapear(
                        linhas
                );

                assertEquals(
                        1,
                        mapper.getErros().size()
                );

                assertEquals(
                        "Número inválido de colunas",
                        mapper.getErros()
                                .get(0)
                                .getMotivo()
                );
        }



        @Test
        void deveRegistrarErroQuandoProducaoNegativa(){

                List<String[]> linhas =
                        new ArrayList<>();

                linhas.add(
                        new String[]{
                                "id",
                                "cliente",
                                "investimento",
                                "producao",
                                "modelo"
                        }
                );

                linhas.add(
                        new String[]{
                                "1",
                                "Juraci",
                                "20000",
                                "-50",
                                "ABC"
                        }
                );

                ProjetoSolarMapper mapper =
                        new ProjetoSolarMapper();

                mapper.mapear(
                        linhas
                );

                assertEquals(
                        1,
                        mapper.getErros().size()
                );

                assertEquals(
                        "Produção negativa",
                        mapper.getErros()
                                .get(0)
                                .getMotivo()
                );
        }

}