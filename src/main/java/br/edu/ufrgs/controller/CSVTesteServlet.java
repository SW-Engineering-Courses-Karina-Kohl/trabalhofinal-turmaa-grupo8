package br.edu.ufrgs.controller;

import br.edu.ufrgs.dao.CSVService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.List;

@WebServlet("/csv-teste")

// Java Servlet = tecnologia para criar aplicações web em Java 
// java + protocolo HTTP (GET, POST, etc) + geração de respostas HTTP (HTML, JSON, etc)
// HttpServlet = responde requisições HTTP (GET, POST, etc) e gera respostas HTTP
public class CSVTesteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
// --
        CSVService csvService = new CSVService();
        // camninho "/dados.csv" é relativo à raiz do projeto 
        // é fixo somente para fins de teste 
        //o caminho do arquivo deveria ser configurável
        String caminhoArquivo = getServletContext().getRealPath("/dados.csv"); // obtém o caminho físico do arquivo CSV no servidor

        List<String[]> linhas = csvService.ler(caminhoArquivo); // chama backend
// -- Após processsamento dos dados:
// -- envia respostas ao navegador (index.jsp) por meio de atributos de uma requisição req (parametro) --
// -- envia dados para as views (JSP) por meio de atributos da requisição  
        req.setAttribute("quantidadeLinhas", linhas.size());

        req.setAttribute("dadosCsv", linhas);

        req.getRequestDispatcher("/index.jsp").forward(req, resp);
        // encaminha a requisição e resposta para o JSP que irá gerar a página HTML final
        // "/index.jsp" --> ira se tornar "/views/index.jsp" quando for processada pelo servlet container 
    }
}