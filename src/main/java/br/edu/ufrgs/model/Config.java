package br.edu.ufrgs.model;

// Representa as configurações do sistema
// carregadas a partir do arquivo CSV
public class Config {

    // tarifa de energia elétrica por kWh
    private double tarifaKWh;

    // fator de emissão de CO2 por kWh
    private double fatorCO2KWh;

    // limite máximo (anos)
    // para classificação excelente
    private double limiteExcelenteAnos;

    // limite máximo (anos)
    // para classificação viável
    private double limiteViavelAnos;

    // construtor:
    // recebe todos os parâmetros da configuração
    public Config(
            double tarifaKWh,
            double fatorCO2KWh,
            double limiteExcelenteAnos,
            double limiteViavelAnos
    ) {

        this.tarifaKWh =
                tarifaKWh;

        this.fatorCO2KWh =
                fatorCO2KWh;

        this.limiteExcelenteAnos =
                limiteExcelenteAnos;

        this.limiteViavelAnos =
                limiteViavelAnos;
    }

    // ---------------- getters ----------------

    public double getTarifaKWh() {
        return tarifaKWh;
    }

    public double getFatorCO2KWh() {
        return fatorCO2KWh;
    }

    public double getLimiteExcelenteAnos() {
        return limiteExcelenteAnos;
    }

    public double getLimiteViavelAnos() {
        return limiteViavelAnos;
    }
}