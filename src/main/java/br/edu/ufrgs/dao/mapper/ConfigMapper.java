package br.edu.ufrgs.dao.mapper;

import br.edu.ufrgs.model.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Responsável por converter (mapear) linhas lidas do CSV
// em um objeto Config do domínio da aplicação
public class ConfigMapper {

    // método mapear
    // List<String[]> linhas -> Config
    // converte os dados do CSV em um objeto Config:
    // coluna 0 = nome do parâmetro
    // coluna 1 = valor do parâmetro
    public Config mapear(List<String[]> linhas) {

        // validações iniciais
        validarLinhas(linhas);
        validarEstrutura(linhas);
        validarCabecalho(linhas.get(0));

        // estrutura auxiliar:
        // armazena pares parametro (String) -> valor (Double)
        Map<String, Double> parametros = new HashMap<>();

        // percorre o CSV ignorando o cabeçalho
        // índice 0 = linha "parametro,valor"
        for (int i = 1; i < linhas.size(); i++) {

            String[] linha = linhas.get(i);

            // cada linha deve possuir:
            // [0] = nome do parâmetro
            // [1] = valor do parâmetro (lido como String do CSV)
            String nomeParametro = linha[0].trim(); // remove espaços em branco
            double valorParametro = converteStringDouble(linha[1]);

            // adiciona par parâmetro no mapa (dicionário)
            parametros.put(nomeParametro, valorParametro);
        }

        // valida parâmetros obrigatórios
        validarParametroObrigatorio(
                parametros,
                "tarifa_kwh"
        );

        validarParametroObrigatorio(
                parametros,
                "fator_co2_kwh"
        );

        validarParametroObrigatorio(
                parametros,
                "limite_excelente_anos"
        );

        validarParametroObrigatorio(
                parametros,
                "limite_viavel_anos"
        );

        // cria e retorna o objeto Config do domínio
        // usando os nomes dos parâmetros (agora normalizados) esperados para acessar os valores no mapa
        return new Config(
                parametros.get("tarifa_kwh"),
                parametros.get("fator_co2_kwh"),
                parametros.get("limite_excelente_anos"),
                parametros.get("limite_viavel_anos"));
    }

    // ---------------- métodos auxiliares internos ----------------

    // String valor -> double
    // converte texto String do CSV para valor numérico Double
    private double converteStringDouble(String valor) {

        try {

            return Double.parseDouble(valor);

        } catch (NumberFormatException e) {

            // encapsula falhas de conversão numérica
            throw new IllegalArgumentException("Valor numérico inválido no CSV: " + valor, e);
        }
    }

    // List<String[]> linhas -> void
    // valida a estrutura básica das linhas lidas do CSV
    private void validarLinhas(List<String[]> linhas) {

        if (linhas == null) {
            // pré-condição: 
            // linhas não podem ser null
            throw new IllegalArgumentException("Linhas do CSV não podem ser null.");
        }

        if (linhas.isEmpty()) {
            // pré-condição:
            // linhas não podem ser vazias
            throw new IllegalArgumentException("CSV vazio.");
        }
    }

    // List<String[]> linhas -> void
    // valida a estrutura de cada linha do CSV
    private void validarEstrutura(List<String[]> linhas) {

        // percorre todas as linhas do CSV
        for (String[] linha : linhas) {

            if (linha.length != 2) {

                throw new IllegalArgumentException(
                        "Todas as linhas do CSV devem possuir exatamente 2 colunas.");
            }
        }
    }

    // List<String[]> linhas -> void
    // valida o cabeçalho do CSV
    private void validarCabecalho(String[] cabecalho) {
        // valida nomes esperados no cabeçalho 
        // coluna 0 = "parametro"
        // coluna 1 = "valor"
        if (!cabecalho[0].trim().equals("parametro")
                || !cabecalho[1].trim().equals("valor")) {

            throw new IllegalArgumentException("Cabeçalho do CSV inválido.");
        }
    }

    // List<String[]> linhas -> void
    // segue a ideia de Objeto Config esperada para validar os parâmetros obrigatórios
    private void validarParametroObrigatorio(Map<String, Double> parametros,
                                                            String nomeParametro) {

        // verifica se o parâmetro obrigatório existe no mapa
        if (!parametros.containsKey(nomeParametro)) {

            throw new IllegalArgumentException("Parâmetro obrigatório ausente: " + nomeParametro);
        }
    }


}