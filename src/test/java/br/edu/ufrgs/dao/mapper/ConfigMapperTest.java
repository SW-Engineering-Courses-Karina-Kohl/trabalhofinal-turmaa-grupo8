package br.edu.ufrgs.dao.mapper;
import br.edu.ufrgs.model.entradas.Config;
import org.junit.jupiter.api.Test
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ConfigMapperTest {

    @Test
    void mapearDeveRetornarConfigValida() {

        // ---------------- Arrange ----------------
        // simula linhas lidas de um CSV válido
        List<String[]> linhas = List.of(
                new String[] { "parametro", "valor" },
                new String[] { "tarifa_kwh", "0.85" },
                new String[] { "fator_co2_kwh", "0.092" },
                new String[] { "limite_excelente_anos", "4" },
                new String[] { "limite_viavel_anos", "8" });

        ConfigMapper mapper = new ConfigMapper();

        // ---------------- Act ----------------

        Config config = mapper.mapear(linhas);

        // ---------------- Assert ----------------
        assertEquals(0.85, config.getTarifaKWh());
        assertEquals(0.092, config.getFatorCO2KWh());
        assertEquals(4, config.getLimiteExcelenteAnos());
        assertEquals(8, config.getLimiteViavelAnos());

    }
    
    @Test
    void mapearDeveFalharSeCSVEstiverVazio() {

        // simula um CSV sem nenhuma linha
        List<String[]> linhas = List.of();

        ConfigMapper mapper = new ConfigMapper();

        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> mapper.mapear(linhas));

        assertEquals("CSV vazio: nenhuma linha encontrada.", erro.getMessage());
    }
    
    @Test
    void mapearDeveFalharSeCabecalhoForInvalidoNomes() {

        // simula um CSV com cabeçalho errado (coluna "parametro" ausente)
        List<String[]> linhas = new ArrayList<>();
        linhas.add(new String[] { "nome", "valor" }); 

        ConfigMapper mapper = new ConfigMapper();

        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> mapper.mapear(linhas));

        assertEquals("Cabeçalho do CSV inválido.", erro.getMessage());
    }
    
    @Test
    void mapearDeveFalharSeCabecalhoForInvalidoColunas() {

        // simula cabeçalho com quantidade incorreta de colunas
        List<String[]> linhas = new ArrayList<>();
        linhas.add(new String[] { "parametro", "valor", "extra" }); // cabeçalho com 3 colunas

        ConfigMapper mapper = new ConfigMapper();
        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> mapper.mapear(linhas));

        assertEquals("Todas as linhas do CSV devem possuir exatamente 2 colunas.", erro.getMessage());
    }
    
    @Test
    void mapearDeveFalharSeValorNumericoForInvalido() {

        List<String[]> linhas = List.of(
                new String[] { "parametro", "valor" },
                new String[] { "tarifa_kwh", "invalido" });

        ConfigMapper mapper = new ConfigMapper();

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> mapper.mapear(linhas));

        assertEquals("Valor numérico inválido no CSV: invalido", erro.getMessage());
    }

    
    @Test
    void mapearDeveFalharSeLinhaForNula() {

        List<String[]> linhas = new ArrayList<>();

        linhas.add(new String[] { "parametro", "valor" });
        linhas.add(null);

        ConfigMapper mapper = new ConfigMapper();

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> mapper.mapear(linhas));

        assertEquals("Linha do CSV não pode ser null.", erro.getMessage());
    }
    
    @Test 
    void mapearDeveFalharSeLinhaComQuantidadeIncorretaDeColunas() {
        List<String[]> linhas = List.of(
                new String[] { "parametro", "valor" },
                new String[] { "tarifa_kwh" } // linha com apenas 1 coluna
        );

        ConfigMapper mapper = new ConfigMapper();

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> mapper.mapear(linhas));

        assertEquals("Todas as linhas do CSV devem possuir exatamente 2 colunas.", erro.getMessage());
    }

    @Test 
    void mapearDeveFalharSeNomeParametroForVazio() {
        List<String[]> linhas = List.of(
                new String[] { "parametro", "valor" },
                new String[] { "", "0.85" } // nome do parâmetro vazio
        );

        ConfigMapper mapper = new ConfigMapper();

        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> mapper.mapear(linhas));

        assertEquals("Nome de parâmetro vazio.", erro.getMessage());
    }
    
    @Test 
    void mapearDeveFalharSeValorParametroForVazio() {
        List<String[]> linhas = List.of(
                new String[] { "parametro", "valor" },
                new String[] { "tarifa_kwh", "" } // valor do parâmetro vazio
        );

        ConfigMapper mapper = new ConfigMapper();

        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> mapper.mapear(linhas));

        assertEquals("Valor do parâmetro está vazio.", erro.getMessage());
    }
    
    @Test
    void mapearDeveFalharSeParametroEstiverDuplicado() {
        List<String[]> linhas = List.of(
                new String[] { "parametro", "valor" },
                new String[] { "tarifa_kwh", "0.85" },
                new String[] { "tarifa_kwh", "0.90" } // parâmetro duplicado
        );

        ConfigMapper mapper = new ConfigMapper();

        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> mapper.mapear(linhas));

        assertEquals("Parâmetro duplicado no CSV: tarifa_kwh", erro.getMessage());
    }

    @Test
    void mapearDeveFalharSeParametroObrigatorioAusente() {
        List<String[]> linhas = List.of(
                new String[] { "parametro", "valor" },
                new String[] { "fator_co2_kwh", "0.092" } // falta tarifa_kwh
        );

        ConfigMapper mapper = new ConfigMapper();

        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> mapper.mapear(linhas));

        assertEquals("Parâmetro obrigatório ausente: tarifa_kwh", erro.getMessage());
    }

    @Test 
    void mapearDeveFalharSeValorSemanticoInvalidoTarifaKwh() {
        List<String[]> linhas = List.of(
                new String[] { "parametro", "valor" },
                new String[] { "tarifa_kwh", "-0.85" }, // valor semântico inválido
                new String[] { "fator_co2_kwh", "0.092" },
                new String[] { "limite_excelente_anos", "4" },
                new String[] { "limite_viavel_anos", "8" });

        ConfigMapper mapper = new ConfigMapper();

        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> mapper.mapear(linhas));

        assertEquals("tarifa_kwh deve ser maior ou igual a zero.", erro.getMessage());
    }
    
    @Test
    void mapearDeveFalharSeValorSemanticoInvalidoFatorCO2Kwh() {
        List<String[]> linhas = List.of(
                new String[] { "parametro", "valor" },
                new String[] { "tarifa_kwh", "0.85" },
                new String[] { "fator_co2_kwh", "-0.092" }, // valor semântico inválido
                new String[] { "limite_excelente_anos", "4" },
                new String[] { "limite_viavel_anos", "8" });

        ConfigMapper mapper = new ConfigMapper();

        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> mapper.mapear(linhas));

        assertEquals("fator_co2_kwh não pode ser negativo.", erro.getMessage());
    }

    @Test
    void mapearDeveFalharSeLimiteExcelenteAnosForZero() {
        List<String[]> linhas = List.of(
                new String[] { "parametro", "valor" },
                new String[] { "tarifa_kwh", "0.85" },
                new String[] { "fator_co2_kwh", "0.092" },
                new String[] { "limite_excelente_anos", "0" }, // valor semântico inválido
                new String[] { "limite_viavel_anos", "8" });

        ConfigMapper mapper = new ConfigMapper();

        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> mapper.mapear(linhas));

        assertEquals("limite_excelente_anos deve ser maior que zero.", erro.getMessage());
    }

    @Test
    void mapearDeveFalharSeLimiteViavelAnosForZero() {
        List<String[]> linhas = List.of(
                new String[] { "parametro", "valor" },
                new String[] { "tarifa_kwh", "0.85" },
                new String[] { "fator_co2_kwh", "0.092" },
                new String[] { "limite_excelente_anos", "4" },
                new String[] { "limite_viavel_anos", "0" } // valor semântico inválido
        );

        ConfigMapper mapper = new ConfigMapper();

        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> mapper.mapear(linhas));

        assertEquals("limite_viavel_anos deve ser maior que zero.", erro.getMessage());
    }
    
    @Test
    void mapearDeveFalharSeLimiteExcelenteAnosForMaiorQueLimiteViavelAnos() {
        List<String[]> linhas = List.of(
                new String[] { "parametro", "valor" },
                new String[] { "tarifa_kwh", "0.85" },
                new String[] { "fator_co2_kwh", "0.092" },
                new String[] { "limite_excelente_anos", "10" }, // valor semântico inválido
                new String[] { "limite_viavel_anos", "8" }
        );

        ConfigMapper mapper = new ConfigMapper();

        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> mapper.mapear(linhas));

        assertEquals("limite_excelente_anos não pode ser maior que limite_viavel_anos.", erro.getMessage());
    }

}
