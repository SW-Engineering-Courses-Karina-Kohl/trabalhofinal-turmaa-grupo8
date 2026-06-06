package br.edu.ufrgs.dao;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import br.edu.ufrgs.model.erros.ErroProjetoSolar;

public class ErroProjetoSolarServiceTest {

        @Test
        void deveSalvarErrosEmArquivo() throws Exception {

                ErroProjetoSolar erro =
                        new ErroProjetoSolar(
                                2,
                                "Investimento negativo",
                                "1,Juraci,-5000,400,ABC"
                        );

                List<ErroProjetoSolar> erros =
                        List.of(erro);

                Path arquivoTemporario =
                        Files.createTempFile(
                                "erros",
                                ".txt"
                        );

                ErroProjetoSolarService service =
                        new ErroProjetoSolarService();

                service.salvarErros(
                        erros,
                        arquivoTemporario.toString()
                );

                String conteudo =
                        Files.readString(
                                arquivoTemporario
                        );

                assertTrue(
                        conteudo.contains("Linha: 2")
                );

                assertTrue(
                        conteudo.contains("Motivo: Investimento negativo")
                );

                assertTrue(
                        conteudo.contains("Conteúdo: 1,Juraci,-5000,400,ABC")
                );
        }

        @Test
        void deveLancarExcecaoQuandoNaoConseguirSalvarArquivo() {

                ErroProjetoSolar erro =
                        new ErroProjetoSolar(
                                1,
                                "Erro teste",
                                "linha teste"
                        );

                List<ErroProjetoSolar> erros =
                        List.of(erro);

                ErroProjetoSolarService service =
                        new ErroProjetoSolarService();

                RuntimeException excecao =
                        assertThrows(
                                RuntimeException.class,
                                () -> service.salvarErros(
                                        erros,
                                        ""
                                )
                        );

                assertTrue(
                        excecao.getMessage()
                                .contains("Erro ao salvar TXT")
                );
        }
}