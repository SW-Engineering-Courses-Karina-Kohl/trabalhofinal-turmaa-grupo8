package br.edu.ufrgs.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ufrgs.dao.CSVService;
import br.edu.ufrgs.dao.mapper.ConfigMapper;
import br.edu.ufrgs.dao.mapper.ProjetoSolarMapper;
import br.edu.ufrgs.model.entradas.Config;
import br.edu.ufrgs.model.entradas.ProjetoSolar;
import br.edu.ufrgs.model.enums.ETipoCalculo;
import br.edu.ufrgs.model.resultado.ResultadoViabilidade; 
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

@MultipartConfig
public class UploadServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        String acao = req.getParameter("acao");
        
        // Se o usuário clicou no link de exportar
        if ("exportar".equals(acao)) {
            // Recupera a lista que foi salva na sessão do usuário durante o POST
            @SuppressWarnings("unchecked")
            List<ResultadoViabilidade> resultados = (List<ResultadoViabilidade>) req.getSession().getAttribute("listaSessao");
            @SuppressWarnings("unchecked")
            List<ProjetoSolar> projetos = (List<ProjetoSolar>) req.getSession().getAttribute("listaProjetosSessao");
            
            if (resultados != null && !resultados.isEmpty()) {
                // Configura os cabeçalhos HTTP para download de arquivo CSV
                resp.setContentType("text/csv");
                resp.setCharacterEncoding("UTF-8");
                resp.setHeader("Content-Disposition", "attachment; filename=\"relatorio_viabilidade.csv\"");
                
                try (PrintWriter writer = resp.getWriter()) {
                    // Cabeçalho conforme o enunciado (image.png)
                    writer.println("projeto_id,economia_mensal_rs,payback_anos,co2_evitado_kg,status");
                    
                    // Percorre as duas listas sincronizadas para montar o CSV de saída
                    for (int i = 0; i < resultados.size(); i++) {
                        ResultadoViabilidade res = resultados.get(i);
                        ProjetoSolar proj = projetos.get(i);
                        
                        writer.println(String.format("%s,%.2f,%.2f,%.2f,%s",
                            proj.getProjetoId(), // Presumindo que o método se chama getProjetoId na sua classe
                            res.getValorPeloTipo(ETipoCalculo.ECONOMIA_MENSAL),
                            res.getValorPeloTipo(ETipoCalculo.PAYBACK),
                            res.getValorPeloTipo(ETipoCalculo.IMPACTO_VERDE),
                            res.getStatus().name()
                        ));
                    }
                }
                return; // Encerra aqui para não renderizar o JSP no download
            }
        }
        
        // Se for só o acesso normal à página, encaminha para o JSP, sem dados
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            // --------------------------------------------------
            // PASSO 1: receber os dois arquivos do formulário
            // --------------------------------------------------
            Part partConfig   = req.getPart("arquivoConfig");
            Part partProjetos = req.getPart("arquivoProjetos");

            // --------------------------------------------------
            // PASSO 2: salvar temporariamente para o CSVService ler
            // --------------------------------------------------
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

            List<ResultadoViabilidade> todosResultados = new ArrayList<>();
            double somaEconomia = 0;
            double somaPayback  = 0;
            double somaImpacto  = 0;

            for (ProjetoSolar projeto : projetos) {
                Map<ETipoCalculo, Double> mapaCalculosBrutos = motor.processarCalculos(config, projeto);
                
                ResultadoViabilidade resultado = new ResultadoViabilidade(
                    mapaCalculosBrutos, 
                    config.getLimiteExcelenteAnos(), 
                    config.getLimiteViavelAnos()
                );
                
                todosResultados.add(resultado);

                somaEconomia += resultado.getValorPeloTipo(ETipoCalculo.ECONOMIA_MENSAL);
                somaPayback  += resultado.getValorPeloTipo(ETipoCalculo.PAYBACK);
                somaImpacto  += resultado.getValorPeloTipo(ETipoCalculo.IMPACTO_VERDE);
            }

            double paybackMedio = projetos.isEmpty() ? 0 : somaPayback / projetos.size();

            Map<ETipoCalculo, Double> mapaMedias = new HashMap<>();
            mapaMedias.put(ETipoCalculo.PAYBACK, paybackMedio);
            
            ResultadoViabilidade resultadoGeralDaBase = new ResultadoViabilidade(
                mapaMedias, 
                config.getLimiteExcelenteAnos(), 
                config.getLimiteViavelAnos()
            );

            // --------------------------------------------------
            // PASSO 6: empacotar os dados formatados para o JSP
            // --------------------------------------------------
            String economiaStr = String.format("R$ %,.2f", somaEconomia);
            String impactoStr  = String.format("%,.2f", somaImpacto);
            String paybackStr  = String.format("%.2f", paybackMedio);
            String statusGeral = resultadoGeralDaBase.getStatus().name();


            // Salva na SESSION para o link do botão de exportação (doGet) conseguir ler depois
            req.getSession().setAttribute("listaSessao", todosResultados);
            req.getSession().setAttribute("listaProjetosSessao", projetos);

            // Passando os atributos estruturados para a tela (Request)
            req.setAttribute("resultados",         todosResultados); 
            req.setAttribute("projetos",           projetos);        
            req.setAttribute("economiaTotal",      economiaStr);
            req.setAttribute("impactoVerde",       impactoStr);
            req.setAttribute("paybackMedio",       paybackStr);
            req.setAttribute("statusViabilidade",  statusGeral);

        } catch (Exception e) {
            req.setAttribute("mensagemErro", e.getMessage());
        }

        // --------------------------------------------------
        // PASSO 7: encaminhar para o JSP (com ou sem erro)
        // --------------------------------------------------
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    private Path salvarTemp(Part part, String prefixo) throws IOException {
        Path temp = Files.createTempFile(prefixo, ".csv");
        try (InputStream in = part.getInputStream()) {
            Files.copy(in, temp, StandardCopyOption.REPLACE_EXISTING);
        }
        return temp;
    }
}