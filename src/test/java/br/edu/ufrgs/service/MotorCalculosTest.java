package br.edu.ufrgs.service;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import br.edu.ufrgs.model.enums.ETipoCalculo;
import br.edu.ufrgs.model.entradas.*;



public class MotorCalculosTest {
    //pré-requisitos para os testes: ter objetos Config e ProjetoSolar, e uma lista de cálculos (ex: Payback, EconomiaMensal, ImpactoVerde) para passar para o MotorCalculos.
    private List<ICalculo> calculos = new ArrayList<>();;
    ICalculo calculoPayback = new CalculoPaybackEstimado();
    ICalculo calculoEconomiaMensal = new CalculoEconomiaMensal();
    ICalculo calculoImpactoVerde = new CalculoImpactoVerde();
 
    Map<ETipoCalculo, Double> mapa = new EnumMap<>(ETipoCalculo.class);
    


    @Test
    void testeProcessamentoMotorCalculosComListaValida() {
        ProjetoSolar projeto = new ProjetoSolar("P_001", "Cliente A", 10000.0, 500.0, "Modelo X");
        Config config = new Config(0.5, 0.2, 3.0, 5.0);

        calculos.add(calculoEconomiaMensal);
        calculos.add(calculoPayback);
        calculos.add(calculoImpactoVerde);

        MotorCalculos motor = MotorCalculos.getInstance(calculos);

        mapa.put(ETipoCalculo.ECONOMIA_MENSAL, projeto.getProducaoMesKWh() * config.getTarifaKWh());
        mapa.put(ETipoCalculo.PAYBACK, projeto.getInvestimentoInicial() / (projeto.getProducaoMesKWh() * config.getTarifaKWh() * 12));
        mapa.put(ETipoCalculo.IMPACTO_VERDE, projeto.getProducaoMesKWh() * config.getFatorCO2KWh());

        assertEquals(mapa, motor.processarCalculos(config, projeto));

    }

    @Test
    void testeCriacaoMotorCalculosComListaNula() {
        try {
            MotorCalculos.getInstance(null);
        } catch (IllegalArgumentException e) {
            assertEquals("A lista de cálculos não pode ser nula ou vazia.", e.getMessage());
        }
    }

    @Test
    void testeProcessamentoMotorCalculosComConfiguracaoNula() {
        ProjetoSolar projeto = new ProjetoSolar("P_001", "Cliente A", 10000.0, 500.0, "Modelo X");
       
        calculos.add(calculoEconomiaMensal);
        calculos.add(calculoPayback);
        calculos.add(calculoImpactoVerde);

        MotorCalculos motor = MotorCalculos.getInstance(calculos);
        
        try {
            motor.processarCalculos(null, projeto);
        } catch (IllegalArgumentException e) {
            assertEquals("Configuração não pode ser nula.", e.getMessage());
        }
    }

    @Test
    void testeProcessamentoMotorCalculosComProjetoNulo() {
        Config config = new Config(0.5, 0.2, 3.0, 5.0);

        calculos.add(calculoEconomiaMensal);
        calculos.add(calculoPayback);
        calculos.add(calculoImpactoVerde);

        MotorCalculos motor = MotorCalculos.getInstance(calculos);    
    
        try {
            motor.processarCalculos(config, null);
        } catch (IllegalArgumentException e) {
            assertEquals("Projeto Solar não pode ser nulo.", e.getMessage());
        }
    }

    @Test
    void testeProcessamentoMotorCalculosComValoresNegativosNaConfiguracao() {
        ProjetoSolar projeto = new ProjetoSolar("P_001", "Cliente A", 10000.0, 500.0, "Modelo X");
        Config config = new Config(0.5, -0.2, 3.0, 5.0);
        
        calculos.add(calculoEconomiaMensal);
        calculos.add(calculoPayback);
        calculos.add(calculoImpactoVerde);

        MotorCalculos motor = MotorCalculos.getInstance(calculos);  

        try {
            motor.processarCalculos(config, projeto);
        } catch (IllegalArgumentException e) {
            assertEquals("Valores de configuração não podem ser negativos.", e.getMessage());
        }

        Config config2 = new Config(-0.5, 0.2, 3.0, 5.0);
        try {            
            motor.processarCalculos(config2, projeto);
        } catch (IllegalArgumentException e) {
            assertEquals("Valores de configuração não podem ser negativos.", e.getMessage());
        }
    }


    
    @Test
    void testeProcessamentoMotorCalculosComValoresNegativosNoProjetoSolar() {
        ProjetoSolar projeto = new ProjetoSolar("P_001", "Cliente A", -10000.0, 500.0, "Modelo X");
        Config config = new Config(0.5, 0.2, 3.0, 5.0);
        
        calculos.add(calculoEconomiaMensal);
        calculos.add(calculoPayback);
        calculos.add(calculoImpactoVerde);

        MotorCalculos motor = MotorCalculos.getInstance(calculos);  

        try {
            motor.processarCalculos(config, projeto);
        } catch (IllegalArgumentException e) {
            assertEquals("Valores do projeto não podem ser negativos.", e.getMessage());
        }

        ProjetoSolar projeto2 = new ProjetoSolar("P_002", "Cliente B", 10000.0, -500.0, "Modelo Y");
        try {
            motor.processarCalculos(config, projeto2);
        } catch (IllegalArgumentException e) {
            assertEquals("Valores do projeto não podem ser negativos.", e.getMessage());
        }
    }

    
    @Test
    void testeProcessamentoMotorCalculosComValoresZeradosNaConfiguracao() {
        // Cenário de tarifa zero força o erro de Payback (divisão por zero)
        ProjetoSolar projeto = new ProjetoSolar("P_001", "Cliente A", 10000.0, 500.0, "Modelo X");
        Config config = new Config(0.0, 0.0, 3.0, 5.0); 

        calculos.add(calculoPayback);
        MotorCalculos motor = MotorCalculos.getInstance(calculos);

        // Captura a RuntimeException gerada pela falha interna do cálculo
        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> motor.processarCalculos(config, projeto)
        );
        
        assertEquals("Erro ao criar o contexto de cálculo: ProducaoKWh e Tarifa não podem ser zero.", exception.getMessage());
    }    

    @Test
    void testeProcessamentoMotorCalculosComValoresZeradosNoProjetoSolar() {
        // Cenário de tarifa zero força o erro de Payback (divisão por zero)
        ProjetoSolar projeto = new ProjetoSolar("P_001", "Cliente A", 10000.0, 0.0, "Modelo X");
        Config config = new Config(0.5, 0.2, 3.0, 5.0); 

        calculos.add(calculoPayback);
        MotorCalculos motor = MotorCalculos.getInstance(calculos);

        // Captura a RuntimeException gerada pela falha interna do cálculo
        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> motor.processarCalculos(config, projeto)
        );
        
        assertEquals("Erro ao criar o contexto de cálculo: ProducaoKWh e Tarifa não podem ser zero.", exception.getMessage());
    }    

}
