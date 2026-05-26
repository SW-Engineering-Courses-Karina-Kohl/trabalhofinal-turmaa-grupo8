package br.edu.ufrgs.model.erros;

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