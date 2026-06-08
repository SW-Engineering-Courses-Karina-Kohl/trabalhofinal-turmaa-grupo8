package br.edu.ufrgs.model.erros;

/*
Classe que representa um erro encontrado durante o processamento dos projetos solares.
Ela armazena informações sobre a linha do arquivo CSV onde o erro ocorreu, o motivo da falha e o conteúdo da linha que causou o problema.
Esses dados são essenciais para gerar um relatório de erros detalhado, facilitando a identificação e correção de inconsistências nos dados de entrada. A classe possui um construtor para inicializar os atributos e getters para acessar os valores de cada campo.
*/

public class ErroProjetoSolar {

    private int linha;

    private String motivo;

    private String conteudoLinha;


    public ErroProjetoSolar(
            int linha,
            String motivo,
            String conteudoLinha
    ){

        this.linha = linha;
        this.motivo = motivo;
        this.conteudoLinha =
                conteudoLinha;
    }


    public int getLinha() {
        return linha;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getConteudoLinha() {
        return conteudoLinha;
    }

}