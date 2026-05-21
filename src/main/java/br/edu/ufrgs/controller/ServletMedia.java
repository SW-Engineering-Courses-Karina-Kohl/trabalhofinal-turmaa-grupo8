package br.edu.ufrgs.controller;

import br.edu.ufrgs.model.Aluno; // Importação do Model
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/processa")
public class ServletMedia extends HttpServlet {
    protected void doPost(HttptreeServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String nome = request.getParameter("nome");
            double nota = Double.parseDouble(request.getParameter("nota"));

            // Uso da classe Model
            Aluno aluno = new Aluno(nome, nota);
            String mensagem = aluno.getMensagemFinal();

            request.setAttribute("resultado", mensagem);
            
        } catch (NumberFormatException e) {
            request.setAttribute("resultado", "Erro: Informe uma nota válida.");
        }

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}

@WebServlet("/csv-teste")
public class CSVTesteServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        CSVService csvService =
                new CSVService();

        List<String[]> linhas =
                csvService.ler("dados.csv");

        req.setAttribute(
                "quantidadeLinhas",
                linhas.size()
        );

        req.setAttribute(
                "dadosCsv",
                linhas
        );

        req.getRequestDispatcher(
                "/resultado.jsp"
        ).forward(req, resp);
    }
}