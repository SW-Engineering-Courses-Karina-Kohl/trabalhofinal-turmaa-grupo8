<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="br.edu.ufrgs.model.entradas.ProjetoSolar" %>
<%@ page import="br.edu.ufrgs.model.resultado.ResultadoViabilidade" %>
<%@ page import="br.edu.ufrgs.model.enums.ETipoCalculo" %>
<%@ page import="br.edu.ufrgs.model.enums.EStatusViabilidade" %>
<%@ page import="br.edu.ufrgs.model.erros.ErroProjetoSolar" %>
<%@ page import="br.edu.ufrgs.model.entradas.ProjetoSolar" %>
<%@ page import="br.edu.ufrgs.model.entradas.Config" %>


<%
String economyTotal =
    request.getAttribute("economiaTotal") != null ?
    request.getAttribute("economiaTotal").toString() :
    "--";

String impactoVerde =
    request.getAttribute("impactoVerde") != null ?
    request.getAttribute("impactoVerde").toString() :
    "--";

String paybackMedio =
    request.getAttribute("paybackMedio") != null ?
    request.getAttribute("paybackMedio").toString() :
    "--";
%>

<!DOCTYPE html>

<html class="light" lang="pt-br"><head>
<meta charset="utf-8"/>
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<title>SolarEfficiency | Gestor de Viabilidade Fotovoltaica</title>
<script src="https://cdn.tailwindcss.com?plugins=forms,container-queries"></script>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&amp;family=Hanken+Grotesk:wght@600;700;800&amp;family=JetBrains+Mono:wght@500&amp;display=swap" rel="stylesheet"/>
<link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght,FILL@100..700,0..1&amp;display=swap" rel="stylesheet"/>
<script id="tailwind-config">
        tailwind.config = {
          darkMode: "class",
          theme: {
            extend: {
              "colors": {
                      "outline": "#76777d",
                      "on-surface": "#0b1c30",
                      "on-primary-fixed": "#131b2e",
                      "on-secondary-fixed": "#261a00",
                      "on-primary-container": "#7c839b",
                      "tertiary-fixed": "#6ffbbe",
                      "outline-variant": "#c6c6cd",
                      "on-tertiary-container": "#009668",
                      "surface-container-low": "#eff4ff",
                      "surface-dim": "#cbdbf5",
                      "error": "#ba1a1a",
                      "on-tertiary-fixed-variant": "#005236",
                      "secondary-container": "#ffc329",
                      "inverse-primary": "#bec6e0",
                      "on-primary-fixed-variant": "#3f465c",
                      "secondary": "#795900",
                      "on-secondary-fixed-variant": "#5c4300",
                      "on-secondary-container": "#6f5100",
                      "on-background": "#0b1c30",
                      "on-secondary": "#ffffff",
                      "background": "#f8f9ff",
                      "primary-fixed": "#dae2fd",
                      "error-container": "#ffdad6",
                      "on-surface-variant": "#45464d",
                      "surface-tint": "#565e74",
                      "on-tertiary": "#ffffff",
                      "inverse-on-surface": "#eaf1ff",
                      "tertiary-container": "#002113",
                      "primary": "#000000",
                      "primary-container": "#131b2e",
                      "tertiary-fixed-dim": "#4edea3",
                      "primary-fixed-dim": "#bec6e0",
                      "on-error-container": "#93000a",
                      "surface-bright": "#f8f9ff",
                      "tertiary": "#000000",
                      "on-primary": "#ffffff",
                      "on-error": "#ffffff",
                      "surface-container": "#e5eeff",
                      "surface": "#f8f9ff",
                      "inverse-surface": "#213145",
                      "secondary-fixed-dim": "#f9bd22",
                      "surface-container-highest": "#d3e4fe",
                      "surface-container-lowest": "#ffffff",
                      "surface-variant": "#d3e4fe",
                      "surface-container-high": "#dce9ff",
                      "secondary-fixed": "#ffdf9f",
                      "on-tertiary-fixed": "#002113"
              },
              "borderRadius": {
                      "DEFAULT": "0.125rem",
                      "lg": "0.25rem",
                      "xl": "0.5rem",
                      "full": "0.75rem"
              },
              "spacing": {
                      "lg": "48px",
                      "md": "24px",
                      "gutter": "24px",
                      "xs": "4px",
                      "xl": "80px",
                      "margin-mobile": "16px",
                      "sm": "12px",
                      "base": "8px",
                      "margin-desktop": "64px",
                      "grid-columns": "12"
              },
              "fontFamily": {
                      "body-lg": ["Inter"],
                      "label-sm": ["JetBrains Mono"],
                      "body-md": ["Inter"],
                      "headline-md": ["Hanken Grotesk"],
                      "display-lg-mobile": ["Hanken Grotesk"],
                      "data-ui": ["Inter"],
                      "display-lg": ["Hanken Grotesk"]
              },
              "fontSize": {
                      "body-lg": ["18px", {"lineHeight": "28px", "fontWeight": "400"}],
                      "label-sm": ["12px", {"lineHeight": "16px", "letterSpacing": "0.05em", "fontWeight": "500"}],
                      "body-md": ["16px", {"lineHeight": "24px", "fontWeight": "400"}],
                      "headline-md": ["24px", {"lineHeight": "32px", "fontWeight": "600"}],
                      "display-lg-mobile": ["32px", {"lineHeight": "40px", "fontWeight": "700"}],
                      "data-ui": ["14px", {"lineHeight": "20px", "fontWeight": "500"}],
                      "display-lg": ["48px", {"lineHeight": "56px", "letterSpacing": "-0.02em", "fontWeight": "700"}]
              }
            },
          },
        }
    </script>
<style>
        .material-symbols-outlined {
            font-variation-settings: 'FILL' 0, 'wght' 400, 'GRAD' 0, 'opsz' 24;
            vertical-align: middle;
        }
        .bento-card {
            transition: transform 0.2s ease, box-shadow 0.2s ease;
        }
        .bento-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 24px -12px rgba(11, 28, 48, 0.08);
        }
    </style>
</head>
<body class="bg-surface text-on-surface font-body-md min-h-screen">
<main class="max-w-6xl mx-auto p-margin-mobile md:p-margin-desktop flex flex-col gap-lg">
    
<header class="text-center md:text-left">
    <div class="flex items-center gap-sm mb-xs justify-center md:justify-start">
        <div class="w-10 h-10 bg-primary-container rounded-lg flex items-center justify-center">
            <span class="material-symbols-outlined text-on-primary" data-icon="bolt">bolt</span>
        </div>
        <h1 class="font-display-lg text-display-lg-mobile md:text-display-lg text-on-surface font-bold">SolarEfficiency</h1>
    </div>
    <h2 class="font-headline-md text-headline-md text-on-surface-variant mb-sm">Gestor de Viabilidade Fotovoltaica</h2>
    <p class="font-body-lg text-body-lg text-on-surface-variant max-w-3xl mx-auto md:mx-0">
        Faça upload de um arquivo CSV contendo projetos solares para calcular economia financeira, impacto verde e tempo de retorno estimado.
    </p>
</header>

<%
String mensagemErro = (String) request.getAttribute("mensagemErro");
if (mensagemErro != null) {
%>
<div class="bg-red-100 border-2 border-red-400 text-red-800 p-md rounded-xl flex items-center gap-sm shadow-sm animate-pulse">
    <span class="material-symbols-outlined text-error text-2xl">error</span>
    <div class="font-medium">
        <span class="font-bold">Erro no processamento:</span> <%= mensagemErro %>
    </div>
</div>
<%
}
%>

<section class="grid grid-cols-1 lg:grid-cols-3 gap-lg items-stretch">
    
    <div class="lg:col-span-1 bg-white border border-outline-variant rounded-2xl p-md shadow-sm flex flex-col justify-between">
        <div class="flex items-center gap-sm mb-md">
            <span class="material-symbols-outlined text-primary text-2xl">tune</span>
            <h3 class="font-headline-md text-body-lg font-bold text-on-surface">Parâmetros de Configuração</h3>
        </div>

        <div class="flex flex-col gap-sm">
            <div class="flex justify-between items-center bg-surface-container-low p-sm rounded-xl border border-outline-variant/30">
                <span class="text-[11px] font-bold text-on-surface-variant uppercase tracking-wider">Tarifa de Energia</span>
                <span class="font-display-lg text-base text-primary font-bold">${tarifaKWh}</span>
            </div>

            <div class="flex justify-between items-center bg-surface-container-low p-sm rounded-xl border border-outline-variant/30">
                <span class="text-[11px] font-bold text-on-surface-variant uppercase tracking-wider">Fator de Carbono</span>
                <span class="font-display-lg text-base text-primary font-bold">${fatorCO2}</span>
            </div>

            <div class="flex justify-between items-center bg-surface-container-low p-sm rounded-xl border border-outline-variant/30">
                <span class="text-[11px] font-bold text-on-surface-variant uppercase tracking-wider">Limite Excelente</span>
                <span class="font-display-lg text-base text-primary font-bold">${limiteExcelente}</span>
            </div>

            <div class="flex justify-between items-center bg-surface-container-low p-sm rounded-xl border border-outline-variant/30">
                <span class="text-[11px] font-bold text-on-surface-variant uppercase tracking-wider">Limite Viável</span>
                <span class="font-display-lg text-base text-primary font-bold">${limiteViavel}</span>
            </div>
        </div>
    </div>

    <div class="lg:col-span-2 bg-white border border-outline-variant rounded-2xl p-lg shadow-sm flex flex-col justify-center items-center">
        <div class="w-14 h-14 bg-surface-container-high rounded-full flex items-center justify-center mb-sm text-primary">
            <span class="material-symbols-outlined text-3xl">upload_file</span>
        </div>
        <h3 class="font-headline-md text-xl font-bold text-on-surface mb-xs">Entrada de Projetos</h3>
        <p class="text-label-sm text-on-surface-variant mb-md text-center max-w-sm">
            Clique para selecionar os arquivos de entrada.
        </p>
        
        <form action="upload" method="POST" enctype="multipart/form-data" class="w-full max-w-md">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-sm mb-md">
                <div class="flex flex-col gap-xs">
                    <input accept=".csv" class="hidden" id="config-input" name="arquivoConfig" type="file"/>
                    <label for="config-input" class="w-full py-sm bg-primary text-on-primary rounded-xl font-bold hover:opacity-90 transition-all cursor-pointer text-center text-label-sm">
                        Configuração (CSV)
                    </label>
                    <span id="config-name" class="text-[10px] text-center truncate italic text-outline">Nenhum selecionado</span>
                </div>
                <div class="flex flex-col gap-xs">
                    <input accept=".csv" class="hidden" id="projetos-input" name="arquivoProjetos" type="file"/>
                    <label for="projetos-input" class="w-full py-sm bg-primary text-on-primary rounded-xl font-bold hover:opacity-90 transition-all cursor-pointer text-center text-label-sm">
                        Base de Projetos (CSV)
                    </label>
                    <span id="projetos-name" class="text-[10px] text-center truncate italic text-outline">Nenhum selecionado</span>
                </div>
            </div>
            
            <button type="submit" class="w-full py-sm bg-primary text-on-primary rounded-xl font-bold hover:opacity-90 transition-all shadow-md flex items-center justify-center gap-sm text-body-md">
                <span class="material-symbols-outlined">analytics</span>
                Processar Arquivos
            </button>
        </form>
    </div>
</section>

<section class="grid grid-cols-1 md:grid-cols-3 gap-gutter">
    
    <div class="bento-card bg-white border border-outline-variant rounded-xl p-md flex flex-col justify-between min-h-[140px] border-l-4 border-l-on-tertiary-container shadow-md">
        <div class="flex justify-between items-start">
            <div>
                <span class="text-label-sm font-label-sm text-on-surface-variant uppercase tracking-wider block font-bold">Economia Mensal Total</span>
                <span class="text-[11px] text-outline italic block mt-xs">Produção (kWh) × Tarifa</span>
            </div>
            <div class="p-xs bg-tertiary-fixed-dim text-on-tertiary-fixed rounded-lg">
                <span class="material-symbols-outlined" data-icon="payments">payments</span>
            </div>
        </div>
        <div class="mt-md">
            <h4 class="font-display-lg text-[32px] text-primary font-bold" id="res-economia">
                <%= request.getAttribute("economiaTotal") != null ? request.getAttribute("economiaTotal") : "R$ --" %>
            </h4>
            <p class="text-label-sm font-label-sm text-on-tertiary-container mt-xs font-medium">
            Consolidado de <%= request.getAttribute("projetos") != null ? ((List) request.getAttribute("projetos")).size() : 0 %> projetos ativos
            </p>
        </div>
    </div>

    <div class="bento-card bg-white border border-outline-variant rounded-xl p-md flex flex-col justify-between min-h-[140px] border-l-4 border-l-tertiary-fixed-dim shadow-md">
        <div class="flex justify-between items-start">
            <div>
                <span class="text-label-sm font-label-sm text-on-surface-variant uppercase tracking-wider block font-bold">Impacto Verde</span>
                <span class="text-[11px] text-outline italic block mt-xs">Produção (kWh) × Fator CO₂</span>
            </div>
            <div class="p-xs bg-surface-container-high text-on-tertiary-container rounded-lg">
                <span class="material-symbols-outlined" data-icon="forest">forest</span>
            </div>
        </div>
        <div class="mt-md">
            <h4 class="font-display-lg text-[32px] text-primary font-bold" id="res-impacto">
                <%= request.getAttribute("impactoVerde") != null ? request.getAttribute("impactoVerde") + " kg CO₂" : "-- kg CO₂" %>
            </h4>
            <p class="text-label-sm font-label-sm text-on-surface-variant mt-xs font-medium">Evitados este mês</p>
        </div>
    </div>

    <div class="bento-card bg-white border border-outline-variant rounded-xl p-md flex flex-col justify-between min-h-[140px] border-l-4 border-l-secondary-container shadow-md">
        <div class="flex justify-between items-start">
            <div>
                <span class="text-label-sm font-label-sm text-on-surface-variant uppercase tracking-wider block font-bold">Payback Estimado</span>
                <span class="text-[11px] text-outline italic block mt-xs">Investimento / Economia Mensal</span>
            </div>
            <div class="p-xs bg-surface-container-high text-on-surface rounded-lg">
                <span class="material-symbols-outlined" data-icon="schedule">schedule</span>
            </div>
        </div>
        <div class="mt-md">
            <h4 class="font-display-lg text-[32px] text-primary font-bold" id="res-payback">
                <%= request.getAttribute("paybackMedio") != null ? request.getAttribute("paybackMedio") + " anos" : "-- anos" %>
            </h4>
            <p class="text-label-sm font-label-sm text-on-surface-variant mt-xs font-medium">
                Classificação Média: <span class="text-secondary font-bold"><%= request.getAttribute("statusViabilidade") != null ? request.getAttribute("statusViabilidade") : "--" %></span>
            </p>
        </div>
    </div>

</section>

<section class="bg-white border border-outline-variant rounded-xl overflow-hidden shadow-sm">
<div class="px-md py-sm border-b border-outline-variant bg-surface-container-lowest">
    <div class="flex justify-between items-center">
        <h3 class="font-headline-md text-headline-md text-on-surface">Projetos Processados</h3>
        <a href="upload?acao=exportar" class="flex items-center gap-xs px-md py-xs bg-primary text-on-primary rounded-lg font-bold text-label-sm hover:opacity-90 transition-all">
            <span class="material-symbols-outlined text-body-md">download</span>Exportar CSV dos resultados
        </a>
    </div>
</div>
<div class="overflow-x-auto">
<table class="w-full text-left border-collapse">
<thead>
<tr class="bg-surface-container-low">
<th class="px-md py-sm text-label-sm font-label-sm text-on-surface-variant uppercase">Cliente</th>
<th class="px-md py-sm text-label-sm font-label-sm text-on-surface-variant uppercase text-right">Investimento</th>
<th class="px-md py-sm text-label-sm font-label-sm text-on-surface-variant uppercase text-right">Produção (KWh)</th>
<th class="px-md py-sm text-label-sm font-label-sm text-on-surface-variant uppercase text-right">Economia Mensal</th>
<th class="px-md py-sm text-label-sm font-label-sm text-on-surface-variant uppercase text-center">Payback</th>
<th class="px-md py-sm text-label-sm font-label-sm text-on-surface-variant uppercase text-center">STATUS</th>
</tr>
</thead>
<%
List<ProjetoSolar> projetos = (List<ProjetoSolar>) request.getAttribute("projetos");
List<ResultadoViabilidade> resultados = (List<ResultadoViabilidade>) request.getAttribute("resultados");

if (projetos != null && !projetos.isEmpty()) {
    for (int i = 0; i < projetos.size(); i++) {
        ProjetoSolar proj = projetos.get(i);
        ResultadoViabilidade res = resultados.get(i);
        EStatusViabilidade status = res.getStatus();

        // define a cor do badge de status
        String badgeClasse = "";
        String statusTexto = "";
        if (status.name().equals("EXCELENTE")) {
            badgeClasse = "bg-tertiary-fixed text-on-tertiary-fixed";
            statusTexto = "Excelente";
        } else if (status.name().equals("VIAVEL")) {
            badgeClasse = "bg-secondary-container text-on-secondary-container";
            statusTexto = "Viável";
        } else {
            badgeClasse = "bg-error-container text-on-error-container";
            statusTexto = "Baixa Prioridade";
        }
%>
<tr class="hover:bg-surface-container-low transition-colors">
    <td class="px-md py-sm font-label-sm text-label-sm font-bold"><%= proj.getCliente() %></td>
    <td class="px-md py-sm font-label-sm text-label-sm text-right">R$ <%= String.format("%,.2f", proj.getInvestimentoInicial()) %></td>
    <td class="px-md py-sm font-label-sm text-label-sm text-right"><%= String.format("%,.0f", proj.getProducaoMesKWh()) %></td>
    <td class="px-md py-sm font-label-sm text-label-sm text-right">R$ <%= String.format("%,.2f", res.getValorPeloTipo(ETipoCalculo.ECONOMIA_MENSAL)) %></td>
    <td class="px-md py-sm font-label-sm text-label-sm text-center"><%= String.format("%.2f", res.getValorPeloTipo(ETipoCalculo.PAYBACK)) %></td>
    <td class="px-md py-sm text-center">
        <span class="px-sm py-xs <%= badgeClasse %> rounded-full text-[10px] font-bold uppercase"><%= statusTexto %></span>
    </td>
</tr>
<% } } else { %>
<tr>
    <td colspan="6" class="px-md py-lg text-center text-on-surface-variant text-label-sm italic">
        Nenhum projeto processado ainda.
    </td>
</tr>
<% } %>
</table>
</div>
<div class="px-md py-sm border-t border-outline-variant bg-surface-container-low flex flex-col md:flex-row justify-between items-center gap-sm">
    <span class="text-label-sm text-on-surface-variant" id="proj-info"></span>
    <div class="flex items-center gap-xs">
        <button onclick="projPaginar(projPaginaAtual - 1)" class="px-sm py-xs border border-outline rounded text-label-sm hover:bg-surface-container-high transition-colors">Anterior</button>
        <div class="flex gap-xs" id="proj-paginas"></div>
        <button onclick="projPaginar(projPaginaAtual + 1)" class="px-sm py-xs border border-outline rounded text-label-sm hover:bg-surface-container-high transition-colors">Próximo</button>
    </div>
</div>
</section>
<section class="bg-white border border-outline-variant rounded-xl overflow-hidden shadow-sm">
<div class="px-md py-sm border-b border-outline-variant bg-error-container">
    <div class="flex justify-between items-center">
        <h3 class="font-headline-md text-headline-md text-on-error-container flex items-center gap-sm">
            <span class="material-symbols-outlined">error</span>Log de Erros (Dados Inválidos)
        </h3>
    </div>
</div>
<div class="overflow-x-auto">
<table class="w-full text-left border-collapse">
<thead>
<tr class="bg-surface-container-low">
<th class="px-md py-sm text-label-sm font-label-sm text-on-surface-variant uppercase">ID</th>
<th class="px-md py-sm text-label-sm font-label-sm text-on-surface-variant uppercase">Cliente</th>
<th class="px-md py-sm text-label-sm font-label-sm text-on-surface-variant uppercase">Motivo do Erro</th>
</tr>
</thead>
<%
List<ErroProjetoSolar> erros = (List<ErroProjetoSolar>) request.getAttribute("erros");

if (erros != null && !erros.isEmpty()) {
    for (ErroProjetoSolar erro : erros) {
%>
<tr id="erro-row-<%= erro.getLinha() %>" class="hover:bg-surface-container-low transition-colors">
    <td class="px-md py-sm font-label-sm text-label-sm">Linha <%= erro.getLinha() %></td>
    <td class="px-md py-sm font-label-sm text-label-sm font-bold"><%= erro.getConteudoLinha() %></td>
    <td class="px-md py-sm font-label-sm text-label-sm text-error"><%= erro.getMotivo() %></td>
</tr>
<% } } else { %>
<tr>
    <td colspan="3" class="px-md py-lg text-center text-on-surface-variant text-label-sm italic">
        Nenhum erro encontrado.
    </td>
</tr>
<% } %>
</table>
</div>
<div class="px-md py-sm border-t border-outline-variant bg-surface-container-low flex flex-col md:flex-row justify-between items-center gap-sm">
    <span class="text-label-sm text-on-surface-variant" id="erro-info"></span>
    <div class="flex items-center gap-xs">
        <button onclick="erroPaginar(erroPaginaAtual - 1)" class="px-sm py-xs border border-outline rounded text-label-sm hover:bg-surface-container-high transition-colors">Anterior</button>
        <div class="flex gap-xs" id="erro-paginas"></div>
        <button onclick="erroPaginar(erroPaginaAtual + 1)" class="px-sm py-xs border border-outline rounded text-label-sm hover:bg-surface-container-high transition-colors">Próximo</button>
    </div>
</div>
<footer class="mt-auto pt-lg border-t border-outline-variant flex justify-center items-center pb-md">
<p class="font-label-sm text-label-sm text-on-surface-variant">© 2026 SolarEfficiency - Sistema de Gestão de Projetos</p>
</footer>
</main>

<script>
    document.getElementById('config-input').addEventListener('change', function(e) {
        const spanText = document.getElementById('config-name');
        if(this.files && this.files.length > 0) {
            spanText.textContent = "✓ " + this.files[0].name;
        } else {
            spanText.textContent = "Nenhum selecionado";
        }
    });

    document.getElementById('projetos-input').addEventListener('change', function(e) {
        const spanText = document.getElementById('projetos-name');
        if(this.files && this.files.length > 0) {
            spanText.textContent = "✓ " + this.files[0].name;
        } else {
            spanText.textContent = "Nenhum selecionado";
        }
    });

    const PROJ_POR_PAGINA = 5;
    let projPaginaAtual = 1;
    function projPaginar(pagina) {
        const linhas = document.querySelectorAll('[id^="proj-row-"]');
        const total = linhas.length;
        const totalPaginas = Math.max(1, Math.ceil(total / PROJ_POR_PAGINA));
        if (pagina < 1 || pagina > totalPaginas) return;
        projPaginaAtual = pagina;
        const inicio = (pagina - 1) * PROJ_POR_PAGINA;
        const fim = inicio + PROJ_POR_PAGINA;
        linhas.forEach((linha, i) => {
            linha.style.display = (i >= inicio && i < fim) ? '' : 'none';
        });
        const infoEl = document.getElementById('proj-info');
        if (infoEl) {
            infoEl.textContent = 'Exibindo ' + (inicio + 1) + '-' + Math.min(fim, total) + ' de ' + total + ' projetos';
        }
        const paginasEl = document.getElementById('proj-paginas');
        if (paginasEl) {
            paginasEl.innerHTML = '';
            for (let p = 1; p <= totalPaginas; p++) {
                const btn = document.createElement('button');
                btn.textContent = p;
                btn.onclick = () => projPaginar(p);
                btn.className = p === pagina
                    ? 'w-8 h-8 flex items-center justify-center bg-primary text-on-primary rounded text-label-sm font-bold'
                    : 'w-8 h-8 flex items-center justify-center border border-outline rounded text-label-sm hover:bg-surface-container-high';
                paginasEl.appendChild(btn);
            }
        }
    }
    projPaginar(1);

    const ERRO_POR_PAGINA = 5;
    let erroPaginaAtual = 1;
    function erroPaginar(pagina) {
        const linhas = document.querySelectorAll('[id^="erro-row-"]');
        const total = linhas.length;
        const totalPaginas = Math.max(1, Math.ceil(total / ERRO_POR_PAGINA));
        if (pagina < 1 || pagina > totalPaginas) return;
        erroPaginaAtual = pagina;
        const inicio = (pagina - 1) * ERRO_POR_PAGINA;
        const fim = inicio + ERRO_POR_PAGINA;
        linhas.forEach((linha, i) => {
            linha.style.display = (i >= inicio && i < fim) ? '' : 'none';
        });
        const infoEl = document.getElementById('erro-info');
        if (infoEl) {
            infoEl.textContent = 'Exibindo ' + (inicio + 1) + '-' + Math.min(fim, total) + ' de ' + total + ' erros';
        }
        const paginasEl = document.getElementById('erro-paginas');
        if (paginasEl) {
            paginasEl.innerHTML = '';
            for (let p = 1; p <= totalPaginas; p++) {
                const btn = document.createElement('button');
                btn.textContent = p;
                btn.onclick = () => erroPaginar(p);
                btn.className = p === pagina
                    ? 'w-8 h-8 flex items-center justify-center bg-primary text-on-primary rounded text-label-sm font-bold'
                    : 'w-8 h-8 flex items-center justify-center border border-outline rounded text-label-sm hover:bg-surface-container-high';
                paginasEl.appendChild(btn);
            }
        }
    }
    erroPaginar(1);
</script>

</body></html>