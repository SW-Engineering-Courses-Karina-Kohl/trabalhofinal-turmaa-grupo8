<%@ page contentType="text/html;charset=UTF-8" %>

<html>

<head>
    <title>Resultado CSV</title>
</head>

<body>

<h1>CSV carregado com sucesso</h1>

<p>
Quantidade de linhas:
${quantidadeLinhas}
</p>

<h2>Conteúdo:</h2>

<%
    java.util.List<String[]> dados =
        (java.util.List<String[]>)
            request.getAttribute("dadosCsv");

    if (dados != null) {
        for (String[] linha : dados) {
%>

    <p>

    <% for (String coluna : linha) { %>

        <%= coluna %> |

    <% } %>

    </p>

<%
        }
    } else {
%>

    <p>
        Nenhum dado carregado. Acesse o servlet pelo caminho:
        <a href="csv-teste">/csv-teste</a>
    </p>

<%
    }
%>

</body>
</html>