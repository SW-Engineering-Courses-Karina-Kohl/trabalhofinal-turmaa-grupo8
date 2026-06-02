package br.edu.ufrgs.service;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import java.util.EnumMap;
import java.util.List;

import br.edu.ufrgs.model.enums.ETipoCalculo;
import br.edu.ufrgs.model.enums.EStatusViabilidade;
import br.edu.ufrgs.model.entradas.*;
import br.edu.ufrgs.service.*;



public class MotorCalculosTest {
    //Testes a serem implementados: 
    //      - criação adequada do Motor de Cálculos com a lista de cálculos passada no construtor, tanto a lista correta quanto incorreta (ex: lista vazia, lista com tipos de cálculo inválidos, etc.)
    //      - criação do objeto CalculoContexto com os valores corretos do projeto e da configuração e com valores inválidos (ex: valores negativos, nulos, etc.)
    //      - processamento dos cálculos e geração do ResultadoViabilidade, verificando se os resultados estão corretos para diferentes cenários de entrada (ex: projeto viável, projeto não viável, etc.) e se as exceções são lançadas corretamente para entradas inválidas.
    //      - teste para entradas negativas e nulas.
    //     

    //pré-requisitos para os testes: ter objetos Config e ProjetoSolar, e uma lista de cálculos (ex: Payback, EconomiaMensal, ImpactoVerde) para passar para o MotorCalculos.

    private List<ICalculo> calculos;
    ICalculo calculoPayback = new CalculoPayback();
    ICalculo calculoEconomiaMensal = new CalculoEconomiaMensal();
    ICalculo calculoImpactoVerde = new CalculoImpactoVerde();

    ProjetoSolar projeto = new ProjetoSolar();
    Config config = new Config(0.5, 0.2, 3.0, 5.0);

    @Test
    void testeCriacaoMotorCalculosComListaValida() {
        F
    }
}
