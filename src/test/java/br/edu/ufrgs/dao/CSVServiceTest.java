package br.edu.ufrgs.dao;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CSVServiceTest {

        @Test
        void lerDeveFalharSeCaminhoForVazio() {

                CSVService csvService = new CSVService();

                assertThrows(IllegalArgumentException.class, () -> csvService.ler(""));
        }

        @Test
        void lerDeveRetornarLinhasDoCSV() {

                // ---------- Arrange ----------
                CSVService csvService = new CSVService();

                String caminhoArquivo = "src/test/resources/CSV/arquivo_valido.csv";

                // ---------- Act ----------
                List<String[]> linhas = csvService.ler(caminhoArquivo);

                // ---------- Assert ----------
                assertEquals(3, linhas.size());
                assertEquals("parametro",linhas.get(0)[0]);
                assertEquals("tarifa_kwh",linhas.get(1)[0]);
                assertEquals("0.85",linhas.get(1)[1]);
        }
        
        @Test
        void lerDeveFalharSeCaminhoForNull() {

                CSVService csvService = new CSVService();

                IllegalArgumentException erro = assertThrows(
                                IllegalArgumentException.class,
                                () -> csvService.ler(null));

                assertEquals("Caminho do arquivo inválido.", erro.getMessage());
        }


        @Test
        void lerDeveFalharSeArquivoNaoExistir() {

                CSVService csvService = new CSVService();

                String caminhoArquivo = "arquivo_inexistente.csv";

                RuntimeException erro = assertThrows(
                                RuntimeException.class,
                                () -> csvService.ler(caminhoArquivo));

                assertTrue(erro.getMessage().contains("Erro ao ler arquivo CSV"));
        }

        @Test
        void escreverDeveFalharSeCaminhoForNull() {

                CSVService csvService = new CSVService();

                List<String[]> dados = new ArrayList<>();

                assertThrows(IllegalArgumentException.class,
                                () -> csvService.escrever(null, dados));
        }

        @Test
        void escreverDeveCriarArquivoCSV() {

                CSVService csvService = new CSVService();

                String caminhoArquivo = "src/test/resources/CSV/saida.csv";

                List<String[]> dados = new ArrayList<>();

                dados.add(new String[] {"nome","valor"});
                dados.add(new String[] {"tarifa_kwh","0.85"});

                csvService.escrever(caminhoArquivo, dados);

                List<String[]> linhasLidas = csvService.ler(caminhoArquivo);

                assertEquals(2, linhasLidas.size());
                assertEquals("tarifa_kwh", linhasLidas.get(1)[0]);
        }

        @Test
        void escreverDeveFalharSeDadosForemNull() {

                CSVService csvService = new CSVService();

                IllegalArgumentException erro = assertThrows(
                                IllegalArgumentException.class,
                                () -> csvService.escrever(
                                                "teste.csv",
                                                null));

                assertEquals("Dados inválidos.", erro.getMessage());
        }

        @Test
        void escreverDeveFalharQuandoNaoConseguirCriarArquivo() {

                CSVService csvService = new CSVService();

                List<String[]> dados = new ArrayList<>();

                dados.add(new String[] { "a", "b" });

                RuntimeException erro = assertThrows(
                                RuntimeException.class,
                                () -> csvService.escrever(
                                                "?:/caminho/invalido.csv",
                                                dados));

                assertTrue(erro.getMessage().contains("Erro ao escrever arquivo CSV"));
        }

}
