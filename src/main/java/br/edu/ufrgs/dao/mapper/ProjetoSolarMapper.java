package br.edu.ufrgs.dao.mapper;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufrgs.model.entradas.ProjetoSolar;
import br.edu.ufrgs.model.erros.ErroProjetoSolar;

public class ProjetoSolarMapper {

    private List<ErroProjetoSolar>
            erros =
            new ArrayList<>();



    public List<ProjetoSolar>
    mapear(
            List<String[]>
                    linhasCsv
    ){

        List<ProjetoSolar>
                projetos =
                new ArrayList<>();


        for(
                int i = 1;
                i < linhasCsv.size();
                i++
        ){

            String[] linha =
                    linhasCsv.get(i);

            int numeroLinha =
                    i + 1;


            try{

                // valida número colunas
                if(
                        linha.length != 5
                ){

                    erros.add(
                            new ErroProjetoSolar(

                                    numeroLinha,

                                    "Número inválido de colunas",

                                    String.join(
                                            ",",
                                            linha
                                    )
                            )
                    );

                    continue;
                }


                double investimento =
                        Double.parseDouble(
                                linha[2]
                        );

                double producao =
                        Double.parseDouble(
                                linha[3]
                        );


                // investimento negativo

                if(
                        investimento < 0
                ){

                    erros.add(
                            new ErroProjetoSolar(

                                    numeroLinha,

                                    "Investimento negativo",

                                    String.join(
                                            ",",
                                            linha
                                    )
                            )
                    );

                    continue;
                }


                // produção negativa

                if(
                        producao < 0
                ){

                    erros.add(
                            new ErroProjetoSolar(

                                    numeroLinha,

                                    "Produção negativa",

                                    String.join(
                                            ",",
                                            linha
                                    )
                            )
                    );

                    continue;
                }


                ProjetoSolar projeto =

                        new ProjetoSolar(

                                linha[0],

                                linha[1],

                                investimento,

                                producao,

                                linha[4]

                        );


                projetos.add(
                        projeto
                );


            }

            catch(
                    Exception e
            ){

                erros.add(

                        new ErroProjetoSolar(

                                numeroLinha,

                                "Dados inválidos",

                                String.join(
                                        ",",
                                        linha
                                )

                        )
                );

            }

        }


        return projetos;

    }



    public List<ErroProjetoSolar>
    getErros(){

        return erros;

    }


}