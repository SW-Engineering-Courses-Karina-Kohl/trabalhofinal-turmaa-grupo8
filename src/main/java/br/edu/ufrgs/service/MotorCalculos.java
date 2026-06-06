package br.edu.ufrgs.service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import br.edu.ufrgs.model.enums.ETipoCalculo;
import br.edu.ufrgs.model.entradas.ProjetoSolar;
import br.edu.ufrgs.model.entradas.Config;


/**
 * Orquestrador que executa uma lista de ICalculo sobre um
 * {ContextoCalculo -> Projeto Solar e Config} e coleta os resultados em um mapa.
 *
 * Regras principais: valida entradas (não-nulas e sem valores negativos)
 * e retorna um mapa com os valores por {ETipoCalculo}.
 */
public class MotorCalculos {
    private static MotorCalculos instance;
    private final List<ICalculo> calculos;

    private MotorCalculos(List<ICalculo> calculos) {
        this.calculos = calculos;
    }

    /**
     * Retorna a instância singleton do motor de cálculos.
     *
     * Importante: a lista de cálculos fornecida aqui é considerada somente na
     * primeira chamada; chamadas posteriores retornam a mesma instância.
     *
     * @param calculos lista de implementações de {ICalculo}; não pode
     *                 ser nula nem vazia
     * @return instância única de {MotorCalculos}
     * @throws IllegalArgumentException se a lista for nula ou vazia
     */
    public static MotorCalculos getInstance(List<ICalculo> calculos) {
        if (calculos == null || calculos.isEmpty()) {
            throw new IllegalArgumentException("A lista de cálculos não pode ser nula ou vazia.");
        }
        if (instance == null){
            instance = new MotorCalculos(calculos);
        }
        return instance;
    }
    
    /**
     * Executa os cálculos registrados.
     * Valida {Config} e {ProjetoSolar} (não-nulos, sem valores negativos)
     * e retorna um mapa com os resultados por tipo de cálculo.
     */
    public Map<ETipoCalculo, Double> processarCalculos(Config config, ProjetoSolar projeto) {
        Map<ETipoCalculo, Double> mapa = new EnumMap<>(ETipoCalculo.class);

        // Validação da configuração: não nula e sem valores negativos, tarifa por kWh não pode ser zero
        if (config == null) {
            throw new IllegalArgumentException("Configuração não pode ser nula.");
        }else{
            if(config.getTarifaKWh() < 0 || config.getFatorCO2KWh() < 0){
                throw new IllegalArgumentException("Valores de configuração não podem ser negativos.");
            }

            if (config.getTarifaKWh() == 0) {
                throw new IllegalArgumentException("Tarifa por kWh não pode ser zero.");
            }
            
        }

        // Validação do projeto: não nulo e sem valores negativos, produção mensal de kWh não pode ser zero
        if (projeto == null) {
            throw new IllegalArgumentException("Projeto Solar não pode ser nulo.");
        } else {
            if (projeto.getProducaoMesKWh() < 0 || projeto.getInvestimentoInicial() < 0) {
                throw new IllegalArgumentException("Valores do projeto não podem ser negativos.");
            }
            if (projeto.getProducaoMesKWh() == 0) {
                throw new IllegalArgumentException("Produção mensal de kWh não pode ser zero.");
            }
        }

        try{
            // Monta o contexto único usado por todos os cálculos
            ContextoCalculo contexto = new ContextoCalculo(
                    projeto.getProducaoMesKWh(),
                    projeto.getInvestimentoInicial(),
                    config.getTarifaKWh(),
                    config.getFatorCO2KWh()
            );
            for (ICalculo calculo : calculos) {
                try {
                    // Cada cálculo é responsável por atualizar seu próprio estado
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