package br.edu.ufrgs.dao;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import br.edu.ufrgs.model.erros.ErroProjetoSolar;

/*
Classe reponsável por registrar em um arquivo texto os erros encontrados durante o processamento dos projetos solares.
Ela recebe uma lista de objetos ErroProjetoSolar e grava, para cada erro, informações como o número da linha do CSV, o motivo da falha e o conteúdo da linha que causou o problema.
Dessa forma, é possível gerar um relatório de erros para facilitar a identificação e correção de inconsistências nos dados de entrada.
Caso ocorra algum problema durante a escrita do arquivo, a classe lança uma exceção do tipo RuntimeException, indicando que houve uma falha na geração do relatório de erros.
 */


public class ErroProjetoSolarService {

        public void salvarErros(
                        List<ErroProjetoSolar> erros,
                        String caminhoArquivo
        ) {

                try(
                        BufferedWriter writer =
                                new BufferedWriter( new FileWriter(caminhoArquivo))
                ) {

                for (ErroProjetoSolar erro : erros) {
                        writer.write( "Linha: " + erro.getLinha() );
                        writer.newLine();

                        writer.write("Motivo: " + erro.getMotivo() );
                        writer.newLine(); 

                        writer.write( "Conteúdo: " + erro.getConteudoLinha() );
                        writer.newLine();

                        writer.newLine();

                        }

                }

                catch (IOException e) {
                        throw new RuntimeException(
                                "Erro ao salvar TXT",
                                        e
                        );

                }

        }

}