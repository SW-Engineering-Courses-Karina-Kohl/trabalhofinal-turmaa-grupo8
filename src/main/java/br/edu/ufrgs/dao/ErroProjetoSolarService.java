package br.edu.ufrgs.dao;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import br.edu.ufrgs.model.erros.ErroProjetoSolar;


public class ErroProjetoSolarService {


    public void salvarErros(

            List<ErroProjetoSolar>
                    erros,

            String caminhoArquivo

    ){


        try(

            BufferedWriter writer =

                new BufferedWriter(

                    new FileWriter(
                            caminhoArquivo
                    )

                )

        ){


            for(

                ErroProjetoSolar erro

                :

                erros

            ){


                writer.write(

                        "Linha: "

                        +

                        erro.getLinha()

                );


                writer.newLine();


                writer.write(

                        "Motivo: "

                        +

                        erro.getMotivo()

                );


                writer.newLine();


                writer.write(

                        "Conteúdo: "

                        +

                        erro.getConteudoLinha()

                );


                writer.newLine();

                writer.newLine();


            }


        }

        catch(
                IOException e
        ){

            throw new RuntimeException(

                    "Erro ao salvar TXT",

                    e

            );

        }

    }

}