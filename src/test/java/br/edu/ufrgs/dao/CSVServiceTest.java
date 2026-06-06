package br.edu.ufrgs.dao;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CSVServiceTest {

        // RF-CSV-001
        // caminho vazio deve falhar
        @Test
        void lerDeveFalharSeCaminhoForVazio() {

                CSVService csvService = new CSVService();

                assertThrows(IllegalArgumentException.class,
                                () -> csvService.ler(""));
        }

        // RF-CSV-002
        // leitura de CSV válido
        @Test
        void lerDeveRetornarLinhasDoCSV() {

                // ---------- Arrange ----------
                CSVService csvService = new CSVService();

                String caminhoArquivo = "src/test/resources/CSV/arquivo_valido.csv";

                // ---------- Act ----------
                List<String[]> linhas = csvService.ler(caminhoArquivo);

                // ---------- Assert ----------
                assertEquals(3, linhas.size());

                assertEquals(
                                "parametro",
                                linhas.get(0)[0]);

                assertEquals(
                                "tarifa_kwh",
                                linhas.get(1)[0]);

                assertEquals(
                                "0.85",
                                linhas.get(1)[1]);
        }

        // RF-CSV-002
        // caminho null deve falhar

        @Test
        void lerDeveFalharSeCaminhoForNull() {

                // ---------- Arrange ----------
                CSVService csvService = new CSVService();

                // ---------- Act + Assert ----------
                IllegalArgumentException erro = assertThrows(
                                IllegalArgumentException.class,
                                () -> csvService.ler(null));

                assertEquals(
                                "Caminho do arquivo inválido.",
                                erro.getMessage());
        }

        // RF-CSV-003
        // arquivo inexistente deve lançar exceção

        @Test
        void lerDeveFalharSeArquivoNaoExistir() {

                // ---------- Arrange ----------
                CSVService csvService = new CSVService();

                String caminhoArquivo = "arquivo_inexistente.csv";

                // ---------- Act + Assert ----------
                RuntimeException erro = assertThrows(
                                RuntimeException.class,
                                () -> csvService.ler(caminhoArquivo));

                assertTrue(
                                erro.getMessage()
                                                .contains("Erro ao ler arquivo CSV"));
        }

        @Test
        void escreverDeveFalharSeCaminhoForNull() {

                CSVService csvService = new CSVService();

                List<String[]> dados = new ArrayList<>();

                assertThrows(IllegalArgumentException.class,
                                () -> csvService.escrever(null, dados));
        }

        // RF-CSV-004
        // escrita de CSV válido

        @Test
        void escreverDeveCriarArquivoCSV() {

                // ---------- Arrange ----------
                CSVService csvService = new CSVService();

                String caminhoArquivo = "src/test/resources/CSV/saida.csv";

                List<String[]> dados = new ArrayList<>();

                dados.add(
                                new String[] {
                                                "nome",
                                                "valor"
                                });

                dados.add(
                                new String[] {
                                                "tarifa_kwh",
                                                "0.85"
                                });

                // ---------- Act ----------
                csvService.escrever(
                                caminhoArquivo,
                                dados);

                List<String[]> linhasLidas = csvService.ler(caminhoArquivo);

                // ---------- Assert ----------
                assertEquals(
                                2,
                                linhasLidas.size());

                assertEquals(
                                "tarifa_kwh",
                                linhasLidas.get(1)[0]);
        }

        // RF-CSV-005
        // escrita deve falhar com dados null

        @Test
        void escreverDeveFalharSeDadosForemNull() {

                // ---------- Arrange ----------
                CSVService csvService = new CSVService();

                // ---------- Act + Assert ----------
                IllegalArgumentException erro = assertThrows(
                                IllegalArgumentException.class,
                                () -> csvService.escrever(
                                                "teste.csv",
                                                null));

                assertEquals(
                                "Dados inválidos.",
                                erro.getMessage());
        }

        @Test
        void escreverDeveFalharQuandoNaoConseguirCriarArquivo() {

                CSVService csvService = new CSVService();

                List<String[]> dados = new ArrayList<>();

                dados.add(
                                new String[] { "a", "b" });

                RuntimeException erro = assertThrows(
                                RuntimeException.class,
                                () -> csvService.escrever(
                                                "?:/caminho/invalido.csv",
                                                dados));

                assertTrue(
                                erro.getMessage()
                                                .contains("Erro ao escrever arquivo CSV"));
        }

}