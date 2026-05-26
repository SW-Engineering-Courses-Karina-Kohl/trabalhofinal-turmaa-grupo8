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
// essa classe não precisa conhecer os objetos do domínio da aplicação
public class CSVService {

    // método ler
    // String caminhoArquivo -> List<String[]> linhasCsv
    // (cada array String[] representa uma linha do CSV)
    public List<String[]> ler(String caminhoArquivo) {

        // antes de tentar ler:
        validarCaminho(caminhoArquivo);

        try (CSVReader reader = new CSVReader(new FileReader(caminhoArquivo))) {
            // tenta abrir o arquivo CSV para leitura
            // Se arquivo aberto com suceso:
            List<String[]> linhasCsv = reader.readAll(); // lê todas as linhas do CSV
            return linhasCsv;

        } catch (IOException e) {
            // encapsula o erro de leitura do arquivo em uma RuntimeException
            // (sem necessidade de declarar throws na assinatura do método significa )
            throw new RuntimeException("Erro ao ler arquivo CSV: " + caminhoArquivo, e);

        } catch (CsvException e) {
            // encapsula falhas sobre formato ou estrutura do CSV (ex: número de colunas
            // diferente entre linhas) 
            throw new RuntimeException("Erro no formato/conteúdo do CSV: " + caminhoArquivo, e);
        }
    }

    // método escrever
    // String caminhoArquivo, List<String[]> dados -> void
    // escreve os dados no arquivo CSV especificado
    public void escrever(String caminhoArquivo, List<String[]> dados) {

        // valida os parâmetros antes de tentar escrever o arquivo
        validarCaminho(caminhoArquivo);
        validarDados(dados);

        try (CSVWriter writer = new CSVWriter(new FileWriter(caminhoArquivo))) {
            // tenta abrir/criar o arquivo CSV para escrita
            // se o arquivo for aberto/criado com sucesso:
            writer.writeAll(dados);
            // escreve todas as linhas da lista no arquivo CSV

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
