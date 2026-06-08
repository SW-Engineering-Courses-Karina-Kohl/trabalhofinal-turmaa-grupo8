package br.edu.ufrgs.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.edu.ufrgs.dao.CSVService;
import br.edu.ufrgs.dao.mapper.ConfigMapper;
import br.edu.ufrgs.dao.mapper.ProjetoSolarMapper;
import br.edu.ufrgs.model.entradas.Config;
import br.edu.ufrgs.model.entradas.ProjetoSolar;
import br.edu.ufrgs.model.enums.ETipoCalculo;
import br.edu.ufrgs.service.CalculoEconomiaMensal;
import br.edu.ufrgs.service.CalculoImpactoVerde;
import br.edu.ufrgs.service.CalculoPaybackEstimado;
import br.edu.ufrgs.service.ICalculo;
import br.edu.ufrgs.service.MotorCalculos;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

// @MultipartConfig é obrigatório para receber arquivos via formulário
@MultipartConfig
public class UploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            // --------------------------------------------------
            // PASSO 1: receber os dois arquivos do formulário
            // --------------------------------------------------
            // "arquivoConfig" e "arquivoProjetos" são os names
            // do <input type="file"> lá no index.jsp da Lauren
            Part partConfig   = req.getPart("arquivoConfig");
            Part partProjetos = req.getPart("arquivoProjetos");

            // --------------------------------------------------
            // PASSO 2: salvar temporariamente para o CSVService ler
            // --------------------------------------------------
            // O CSVService lê por caminho de arquivo (String)
            // então precisamos salvar o stream em um arquivo temp
            Path arquivoConfigTemp   = salvarTemp(partConfig,   "config");
            Path arquivoProjetosTemp = salvarTemp(partProjetos, "projetos");

            // --------------------------------------------------
            // PASSO 3: ler os CSVs com o CSVService
            // --------------------------------------------------
            CSVService csvService = new CSVService();

            List<String[]> linhasConfig   = csvService.ler(arquivoConfigTemp.toString());
            List<String[]> linhasProjetos = csvService.ler(arquivoProjetosTemp.toString());

            // --------------------------------------------------
            // PASSO 4: converter em objetos com os Mappers
            // --------------------------------------------------
            Config config = new ConfigMapper().mapear(linhasConfig);

            ProjetoSolarMapper projetoMapper = new ProjetoSolarMapper();
            List<ProjetoSolar> projetos = projetoMapper.mapear(linhasProjetos);

            // --------------------------------------------------
            // PASSO 5: calcular com o MotorCalculos
            // --------------------------------------------------
            List<ICalculo> listaCalculos = new ArrayList<>();
            listaCalculos.add(new CalculoEconomiaMensal());
            listaCalculos.add(new CalculoImpactoVerde());
            listaCalculos.add(new CalculoPaybackEstimado());

            MotorCalculos motor = MotorCalculos.getInstance(listaCalculos);

            // processa cada projeto e acumula resultados
            List<Map<ETipoCalculo, Double>> todosResultados = new ArrayList<>();
            double somaEconomia = 0;
            double somaPayback  = 0;
            double somaImpacto  = 0;

            for (ProjetoSolar projeto : projetos) {
                Map<ETipoCalculo, Double> resultado = motor.processarCalculos(config, projeto);
                todosResultados.add(resultado);

                // acumula para os cards do topo
                somaEconomia += resultado.getOrDefault(ETipoCalculo.ECONOMIA_MENSAL, 0.0);
somaPayback  += resultado.getOrDefault(ETipoCalculo.PAYBACK,         0.0);
somaImpacto  += resultado.getOrDefault(ETipoCalculo.IMPACTO_VERDE,   0.0);
            }

            double paybackMedio = projetos.isEmpty() ? 0 : somaPayback / projetos.size();

            // --------------------------------------------------
            // PASSO 6: empacotar os dados para o JSP da Lauren
            // --------------------------------------------------
            req.setAttribute("resultados",    todosResultados); // lista com mapa por projeto
            req.setAttribute("projetos",      projetos);        // lista de ProjetoSolar
            req.setAttribute("economiaTotal", somaEconomia);
            req.setAttribute("impactoVerde",  somaImpacto);
            req.setAttribute("paybackMedio",  paybackMedio);

        } catch (Exception e) {
            // qualquer erro vai para o JSP como mensagem de erro
            req.setAttribute("mensagemErro", e.getMessage());
        }

        // --------------------------------------------------
        // PASSO 7: encaminhar para o JSP (com ou sem erro)
        // --------------------------------------------------
        req.getRequestDispatcher("/WEB-INF/view/index.jsp")
           .forward(req, resp);
    }

    // --------------------------------------------------
    // método auxiliar: salva o stream do Part em arquivo temp
    // --------------------------------------------------
    private Path salvarTemp(Part part, String prefixo) throws IOException {
        Path temp = Files.createTempFile(prefixo, ".csv");
        try (InputStream in = part.getInputStream()) {
            Files.copy(in, temp, StandardCopyOption.REPLACE_EXISTING);
        }
        return temp;
    }
}