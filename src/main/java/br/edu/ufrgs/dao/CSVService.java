package br.edu.ufrgs.dao;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

// Responsável por ler e escrever arquivos CSV usando OpenCSV
// Encapsula a lógica de acesso a .csv
// essa classe não precisa conhecer os objetos do nosso domínio
public class CSVService {

    // método ler
    // String caminhoArquivo -> Lista de arrays de strings (cada array representa
    // uma linha do CSV)
    public List<String[]> ler(String caminhoArquivo) {

        // valida o caminho do arquivo antes de tentar ler
        if (caminhoArquivo == null || caminhoArquivo.isBlank()) {

            throw new IllegalArgumentException("Caminho do arquivo inválido.");
        }

        try (CSVReader reader = new CSVReader(new FileReader(caminhoArquivo))) {
            // tenta abrir o arquivo CSV para leitura
            // Se arquivo aberto com suceso:
            List<String[]> linhasCsv = reader.readAll(); // lê todas as linhas do CSV
            return linhasCsv;

        } catch (IOException e) {

            throw new RuntimeException("Erro ao ler arquivo CSV: " + caminhoArquivo, e);
            // RuntimeException é uma exceção não verificada (não precisa ser declarada na
            // assinatura do método)
            // Lança uma exceção por qualquer erro de leitura do arquivo CSV, incluindo o
            // caminho do arquivo e a causa original (e)
        }
    }

    // método escrever
    // String caminhoArquivo, List<String[]> dados -> void
    // escreve os dados no arquivo CSV especificado
    public void escrever(String caminhoArquivo, List<String[]> dados) {

        // fail-fast:
        // valida os parâmetros antes de tentar escrever o arquivo
        if (caminhoArquivo == null || caminhoArquivo.isBlank()) {

            throw new IllegalArgumentException("Caminho do arquivo inválido.");
        }

        if (dados == null) {

            throw new IllegalArgumentException("Os dados para escrita não podem ser null.");
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter(caminhoArquivo))) {
            // tenta abrir/criar o arquivo CSV para escrita
            // se o arquivo for aberto/criado com sucesso:

            writer.writeAll(dados);
            // escreve todas as linhas da lista no arquivo CSV
            // cada String[] representa uma linha da tabela

        } catch (IOException e) {
            // lança uma exceção caso ocorra erro de escrita do arquivo
            // inclui o caminho do arquivo e preserva a causa original (e)
            throw new RuntimeException("Erro ao escrever arquivo CSV: " + caminhoArquivo, e);
        }
    }
}
