package br.edu.ufrgs.dao;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

// Responsável por ler e escrever arquivos CSV usando OpenCSV
// Encapsula a lógica de acesso a .csv
// essa classe não precisa conhecer (é independente) os objetos do domínio da aplicação
public class CSVService {

    // String caminhoArquivo -> List<String[]> linhasCsv
    // (cada array String[] representa uma linha do CSV)
    public List<String[]> ler(String caminhoArquivo) {

        validarCaminho(caminhoArquivo);

        try (CSVReader reader = new CSVReader(new FileReader(caminhoArquivo))) {
            // Se arquivo aberto com suceso para leitura:
            List<String[]> linhasCsv = reader.readAll(); // lê todas as linhas do CSV
            return linhasCsv;

        } catch (IOException e) { 
            // encapsula o erro de leitura do arquivo em uma RuntimeException
            throw new RuntimeException("Erro ao ler arquivo CSV: " + caminhoArquivo, e);

        } catch (CsvException e) { // SE REMOVER ESSA EXCEÇÃO, NAO COMPILA
            // encapsula falhas sobre formato ou estrutura do CSV (ex: número de colunas
            // diferente entre linhas) 
            throw new RuntimeException("Erro no formato/conteúdo do CSV: " + caminhoArquivo, e);
        }
    }

    // String caminhoArquivo, List<String[]> dados -> void
// escreve os dados em um arquivo CSV físico
// no sistema de arquivos onde a aplicação está executando
// (servidor local ou container Docker)
    public void escrever(String caminhoArquivo, List<String[]> dados) {

        validarCaminho(caminhoArquivo);
        validarDados(dados);

        try (CSVWriter writer = new CSVWriter(new FileWriter(caminhoArquivo))) {
            // se o arquivo for aberto/criado com sucesso para escrita:
            writer.writeAll(dados);

        } catch (IOException e) {
            // encapsula o erro de escrita do arquivo (Ex: permissão negada, espaço insuficiente) 
            throw new RuntimeException("Erro ao escrever arquivo CSV: " + caminhoArquivo, e);
        }
    }
    
    // ------------- metodos auxiliares de validação de parâmetros --------------
    // String caminhoArquivo -> void
    private void validarCaminho(String caminhoArquivo) {

        if (caminhoArquivo == null || caminhoArquivo.isBlank()) {
            // pre-condição: o caminho do arquivo não pode ser nulo ou vazio
            throw new IllegalArgumentException("Caminho do arquivo inválido.");
        }
    }

    // List<String[]> dados -> void
    private void validarDados(List<String[]> dados) {

        if (dados == null) {
            // pre-condição: os dados a serem escritos não podem ser nulos
            throw new IllegalArgumentException("Dados inválidos.");
        }
    }

}
