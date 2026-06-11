package br.edu.ufrgs.dao.mapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import br.edu.ufrgs.model.entradas.Config;

// Responsável por converter (mapear) linhas lidas do CSV
// em um objeto Config do domínio da aplicação
public class ConfigMapper {

    // parâmetros obrigatórios esperados no CSV
    private static final String[] PARAMETROS_OBRIGATORIOS = {
            "tarifa_kwh",
            "fator_co2_kwh",
            "limite_excelente_anos",
            "limite_viavel_anos"
    };

    // List<String[]> linhas -> Config
    // converte os dados do CSV em um objeto Config
    public Config mapear(List<String[]> linhas) {

        // validações estruturais do texto em CSV
        validarArquivoNaoVazio(linhas);

        // valida estrutura do cabeçalho antes de acessá-lo
        validarCabecalho(linhas.get(0));

        // estrutura de dicionário auxiliar:
        // armazena pares (parametro (String) -> valor (Double))
        // constrói o mapa já validando cada linha
        Map<String, Double> parametros = construirMapaParametros(linhas);

        // garantias
        validarParametrosObrigatorios(parametros);
        validarValoresSemanticos(parametros); // de acordo com domínio da aplicacao

        return new Config(
                parametros.get("tarifa_kwh"),
                parametros.get("fator_co2_kwh"),
                parametros.get("limite_excelente_anos"),
                parametros.get("limite_viavel_anos"));
    }

    // ---------------- métodos auxiliares internos ----------------

    // percorre o CSV ignorando o cabeçalho, valida cada linha
    // e constrói o mapa de parâmetros
    private Map<String, Double> construirMapaParametros(List<String[]> linhas) {

        Map<String, Double> parametros = new HashMap<>();

        // ignora cabeçalho (índice 0)
        // índice 0 = linha "parametro,valor"
        for (int i = 1; i < linhas.size(); i++) {

            String[] linha = linhas.get(i);

            validarLinhaNaoNula(linha);
            validarQuantidadeColunas(linha);

            // cada linha deve possuir:
            // [Col:0] = nome do parâmetro
            // [Col:1] = valor do parâmetro (lido como String do CSV)
            String nomeParametro = linha[0].trim(); // remove espaços em branco
            String valorTexto = linha[1].trim();

            validarNomeParametro(nomeParametro);
            validarValorNaoVazio(valorTexto);
            validarParametroDuplicado(parametros, nomeParametro);

            double valorParametro = converteStringDouble(valorTexto);

            // adiciona par parâmetro no mapa (dicionário)
            parametros.put(nomeParametro, valorParametro);
        }
        return parametros;
    }

    // String valor -> double
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
    private void validarArquivoNaoVazio(List<String[]> linhas) {

        if (linhas.isEmpty()) {
            // pré-condição:
            // linhas não podem ser vazias
            throw new IllegalArgumentException("CSV vazio: nenhuma linha encontrada.");
        }

    }

    // String[] linha -> void
    // valida se a linha do CSV não é nula
    private void validarLinhaNaoNula(String[] linha) {

        if (linha == null) {
            // pré-condição:
            // linha do CSV não pode ser null
            throw new IllegalArgumentException("Linha do CSV não pode ser null.");
        }
    }

    // List<String[]> linhas -> void
    // valida a estrutura de cada linha do CSV
    private void validarQuantidadeColunas(String[] linha) {

        if (linha.length != 2) {
            // pre-condição:
            // cada linha deve possuir exatamente 2 colunas
            throw new IllegalArgumentException(
                    "Todas as linhas do CSV devem possuir exatamente 2 colunas.");
        }
    }

    // List<String[]> linhas -> void
    // valida o cabeçalho do CSV
    private void validarCabecalho(String[] cabecalho) {

        validarQuantidadeColunas(cabecalho); // garante que o cabeçalho tenha 2 colunas
        // valida nomes esperados no cabeçalho
        // coluna 0 = "parametro"
        // coluna 1 = "valor"
        if (!cabecalho[0].trim().equals("parametro")
                || !cabecalho[1].trim().equals("valor")) {

            throw new IllegalArgumentException("Cabeçalho do CSV inválido.");
        }
    }

    // String nomeParametro -> void
    // valida se o nome do parâmetro está vazio
    private void validarNomeParametro(String nomeParametro) {

        if (nomeParametro.isEmpty()) {
            // precondição:
            // nome do parâmetro não pode ser vazio
            throw new IllegalArgumentException("Nome de parâmetro vazio.");
        }
    }

    // String valor -> void
    private void validarValorNaoVazio(String valor) {

        if (valor.isEmpty()) {
            // pre-condição:
            // valor do parâmetro não pode ser vazio
            throw new IllegalArgumentException("Valor do parâmetro está vazio.");
        }
    }

    // Map<String, Double> parametros, String nomeParametro -> void
    private void validarParametroDuplicado(Map<String, Double> parametros,
            String nomeParametro) {

        if (parametros.containsKey(nomeParametro)) {
            // pre-condição:
            // não pode existir mais de um parâmetro com o mesmo nome
            throw new IllegalArgumentException(
                    "Parâmetro duplicado no CSV: " + nomeParametro);
        }
    }

    // List<String[]> linhas -> void
    // garante que todos os parâmetros obrigatórios existam
    private void validarParametrosObrigatorios(Map<String, Double> parametros) {

        for (String nomeParametro : PARAMETROS_OBRIGATORIOS) {

            if (!parametros.containsKey(nomeParametro)) {

                throw new IllegalArgumentException("Parâmetro obrigatório ausente: "
                        + nomeParametro);
            }
        }
    }

    // Map<String, Double> parametros -> void
    // valida regras semânticas dos valores do domínio
    private void validarValoresSemanticos(Map<String, Double> parametros) {

        double tarifaKwh = parametros.get("tarifa_kwh");
        double fatorCo2Kwh = parametros.get("fator_co2_kwh");
        double limiteExcelente = parametros.get("limite_excelente_anos");
        double limiteViavel = parametros.get("limite_viavel_anos");

        if (tarifaKwh < 0) {

            throw new IllegalArgumentException("tarifa_kwh deve ser maior ou igual a zero.");
        }

        if (fatorCo2Kwh < 0) {

            throw new IllegalArgumentException("fator_co2_kwh não pode ser negativo.");
        }

        if (limiteExcelente <= 0) {

            throw new IllegalArgumentException("limite_excelente_anos deve ser maior que zero.");
        }

        if (limiteViavel <= 0) {

            throw new IllegalArgumentException("limite_viavel_anos deve ser maior que zero.");
        }

        // excelente deve ser menor ou igual ao viável
        if (limiteExcelente > limiteViavel) {

            throw new IllegalArgumentException("limite_excelente_anos não pode ser maior que limite_viavel_anos.");
        }
    }

}
