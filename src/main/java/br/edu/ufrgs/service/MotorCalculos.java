package br.edu.ufrgs.service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import br.edu.ufrgs.model.enums.ETipoCalculo;
import br.edu.ufrgs.model.resultado.ResultadoViabilidade;
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
    
    public ResultadoViabilidade processarCalculos(Config config, ProjetoSolar projeto) {

        Map<ETipoCalculo, Double> mapa = new EnumMap<>(ETipoCalculo.class);
        ContextoCalculo contexto = new ContextoCalculo(
                projeto.getProducaoKWh(),
                projeto.getInvestimentoInicial(),
                config.getTarifaKWh(),
                config.getFatorCO2KWh()
        );

        for (ICalculo calculo : calculos) {
            try {
                calculo.calcular(contexto);
                mapa.put(calculo.getTipo(), calculo.getValor());

            } catch (Exception e) {
                throw new RuntimeException(
                        "Erro ao executar cálculo " + calculo.getTipo(),
                        e
                );
            }
        }

        try {
            return new ResultadoViabilidade(
                    mapa,
                    config.getLimiteExcelenteAnos(),
                    config.getLimiteViavelAnos()
            );

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao gerar ResultadoViabilidade",
                    e
            );
        }
    }