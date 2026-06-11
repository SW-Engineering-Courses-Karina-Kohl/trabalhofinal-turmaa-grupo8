package br.edu.ufrgs.dao.mapper;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufrgs.model.entradas.ProjetoSolar;
import br.edu.ufrgs.model.erros.ErroProjetoSolar;


/*
Classe responsável por converter dados lidos do arquivo CSV (List<String[]>) em objetos do tipo ProjetoSolar. 
Durante processo, ela valida cada linha do arquivo, verificando se possui numero de colunas valido, se os valores numéricos sao validos e se nao existem valores negativos para investimento ou produção.
Linhas invalidas não interrompem o processo, ela registra os erros encontrados como objetos da classe ErroProjetoSolar em uma lista de erros. 
No final, ela retorna a lista de projetos válidos e a lista de erros encontrados. A lista de erros pode ser acessada depois através do método getErros(). 
*/


public class ProjetoSolarMapper {

        private List<ErroProjetoSolar> erros = 
                new ArrayList<>();

        public List<ProjetoSolar> mapear(List<String[]> linhasCsv) {
                List<ProjetoSolar> projetos = new ArrayList<>();

                for (int i = 1; i < linhasCsv.size(); i++) {
                        String[] linha = linhasCsv.get(i);
                        int numeroLinha = i + 1;

                        try {
                                // valida número colunas
                                if (linha.length != 5) {
                                        erros.add( new ErroProjetoSolar(
                                                        numeroLinha,
                                                        "Número inválido de colunas", 
                                                        String.join(",", linha)
                                                        )
                                                );

                                        continue;
                                }

                                double investimento = 
                                        Double.parseDouble(linha[2]);

                                double producao = 
                                        Double.parseDouble(linha[3]);
                                
                                boolean linhaValida = true;
                                
                                // investimento negativo
                                if (investimento < 0) {
                                        erros.add(new ErroProjetoSolar(
                                                        numeroLinha, 
                                                        "Investimento negativo", 
                                                        String.join(",", linha)
                                                        )
                                                );

                                        linhaValida = false;
                                }

                                // produção negativa
                                if (producao < 0) {
                                        erros.add( new ErroProjetoSolar(
                                                        numeroLinha, 
                                                        "Produção negativa", 
                                                        String.join(",", linha)
                                                        )
                                                );

                                        linhaValida = false;
                                }

                                // produção zero (não é válida para cálculo de payback)
                                if (producao == 0) {
                                        erros.add( new ErroProjetoSolar(
                                                        numeroLinha, 
                                                        "Produção 0 não é válida para cálculo de payback", 
                                                        String.join(",", linha)
                                                        )
                                                );

                                        linhaValida = false;
                                }

                                if (linhaValida) {
                                
                                        ProjetoSolar projeto =
                                                new ProjetoSolar(
                                                        linha[0],
                                                        linha[1],
                                                        investimento,
                                                        producao,
                                                        linha[4]

                                                        );

                                        projetos.add(projeto);

                                }

                        }

                        catch (Exception e) {
                                erros.add( new ErroProjetoSolar(
                                                numeroLinha,
                                                "Dados inválidos, números esperados para investimento ou produção",
                                                String.join( ",",linha)
                                                )
                                        );

                        }

                }

                return projetos;

        }

        public List<ErroProjetoSolar> getErros() {
                return erros;

        }

}