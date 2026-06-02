package br.edu.ufrgs.service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import br.edu.ufrgs.model.enums.ETipoCalculo;
import br.edu.ufrgs.model.entradas.ProjetoSolar;
import br.edu.ufrgs.model.entradas.Config;


public class MotorCalculos {
    private static MotorCalculos instance;
    private final List<ICalculo> calculos;

    private MotorCalculos(List<ICalculo> calculos) {
        this.calculos = calculos;
    }

    public static MotorCalculos getInstance(List<ICalculo> calculos) {
        if (calculos == null || calculos.isEmpty()) {
            throw new IllegalArgumentException("A lista de cálculos não pode ser nula ou vazia.");
        }
        if (instance == null){
            instance = new MotorCalculos(calculos);
        }
        return instance;
    }
    
    public Map<ETipoCalculo, Double> processarCalculos(Config config, ProjetoSolar projeto) {
        Map<ETipoCalculo, Double> mapa = new EnumMap<>(ETipoCalculo.class);

        if (config == null) {
            throw new IllegalArgumentException("Configuração não pode ser nula.");
        }else{
            if(config.getTarifaKWh() < 0 || config.getFatorCO2KWh() < 0){
                throw new IllegalArgumentException("Valores de configuração não podem ser negativos.");
            }
        }

        if (projeto == null) {
            throw new IllegalArgumentException("Projeto Solar não pode ser nulo.");
        } else {
            if (projeto.getProducaoMesKWh() < 0 || projeto.getInvestimentoInicial() < 0) {
                throw new IllegalArgumentException("Valores do projeto não podem ser negativos.");
            }
        }

       

        try{
            ContextoCalculo contexto = new ContextoCalculo(
                    projeto.getProducaoMesKWh(),
                    projeto.getInvestimentoInicial(),
                    config.getTarifaKWh(),
                    config.getFatorCO2KWh()
            );
            for (ICalculo calculo : calculos) {
                try {
                    calculo.calcular(contexto);
                    mapa.put(calculo.getTipo(), calculo.getValor());

                } catch (Exception e) {
                    throw new RuntimeException("Erro ao executar cálculo " + calculo.getTipo(), e);
                }
            }
            
        }catch (Exception e){
            throw new RuntimeException("Erro ao criar o contexto de cálculo: " + e.getMessage(), e);
        }

        return mapa;

        
    }
}