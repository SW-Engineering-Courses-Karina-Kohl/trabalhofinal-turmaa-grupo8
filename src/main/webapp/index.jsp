<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html><html class="light" lang="pt-br" style=><head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<title>SolarEfficiency | Gestor de Viabilidade Fotovoltaica</title>
<script src="https://cdn.tailwindcss.com?plugins=forms,container-queries"></script>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&amp;family=Hanken+Grotesk:wght@600;700;800&amp;family=JetBrains+Mono:wght@500&amp;display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght,FILL@100..700,0..1&amp;display=swap" rel="stylesheet">
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
        .drag-active {
            border-color: #000000;
            background-color: #eff4ff;
        }
    </style>
</head>
<body class="bg-surface text-on-surface font-body-md min-h-screen">
<main class="max-w-6xl mx-auto p-margin-mobile md:p-margin-desktop flex flex-col gap-lg">
<!-- Simplified Header -->
<header class="text-center md:text-left"><div class="flex items-center gap-sm mb-xs justify-center md:justify-start"><div class="w-10 h-10 bg-primary-container rounded-lg flex items-center justify-center"><span class="material-symbols-outlined text-on-primary" data-icon="bolt">bolt</span></div><h1 class="font-display-lg text-display-lg-mobile md:text-display-lg text-on-surface font-bold">SolarEfficiency</h1></div><h2 class="font-headline-md text-headline-md text-on-surface-variant mb-sm">Gestor de Viabilidade Fotovoltaica</h2><p class="font-body-lg text-body-lg text-on-surface-variant max-w-3xl mx-auto md:mx-0">Faça upload de um arquivo CSV contendo projetos solares para calcular&nbsp;<div>economia financeira, impacto verde e tempo de retorno estimado.</div></p></header>
<!-- Central Upload Area -->
<section class="w-full">
<form action="seu-endpoint-jsp" enctype="multipart/form-data" method="POST">
<div class="relative group cursor-pointer border-2 border-dashed border-outline-variant rounded-xl bg-surface-container-lowest p-lg lg:p-xl transition-all duration-300 flex flex-col items-center justify-center text-center" id="drop-zone">
<input accept=".csv" class="absolute inset-0 w-full h-full opacity-0 cursor-pointer" id="file-input" name="arquivoCsv" type="file">
<div class="w-16 h-16 bg-surface-container-high rounded-full flex items-center justify-center mb-md group-hover:bg-primary-container group-hover:text-on-primary transition-colors">
<span class="material-symbols-outlined text-4xl" data-icon="upload_file">upload_file</span>
</div>
<h3 class="font-headline-md text-headline-md text-on-surface mb-xs">Entrada de Projetos</h3>
<p class="text-body-md font-body-md text-on-surface-variant mb-lg max-w-md">
                        Arraste seu arquivo CSV ou use os botões abaixo para iniciar o processamento automatizado.
                    </p>
<div class="flex flex-col sm:flex-row items-center gap-md">
<label class="px-lg py-sm border border-outline text-on-surface rounded-lg font-bold hover:bg-surface-container-high transition-all cursor-pointer" for="file-input">
                            Selecionar Arquivo
                        </label>
<button class="px-lg py-sm bg-primary text-on-primary rounded-lg font-bold hover:opacity-90 transition-all flex items-center gap-sm shadow-md" type="submit">
<span class="material-symbols-outlined" data-icon="analytics">analytics</span>
                            Processar Arquivo
                        </button>
</div>
<p class="mt-md text-label-sm font-label-sm text-primary font-bold hidden" id="file-name"></p>
</div>
</form>
</section>
<!-- Placeholder Result Cards -->
<section class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-gutter"><div class="bento-card bg-white border border-outline-variant rounded-xl p-md flex flex-col justify-between min-h-[140px] border-l-4 border-l-on-tertiary-container shadow-md"><div class="flex justify-between items-start"><span class="text-label-sm font-label-sm text-on-surface-variant uppercase tracking-wider">Economia Mensal</span><div class="p-xs bg-tertiary-fixed-dim text-on-tertiary-fixed rounded-lg"><span class="material-symbols-outlined" data-icon="payments">payments</span></div></div><div><h4 class="font-display-lg text-[32px] text-primary" id="res-economia">R$ --</h4><p class="text-label-sm font-label-sm text-on-tertiary-container mt-xs">Aguardando dados</p></div></div><div class="bento-card bg-white border border-outline-variant rounded-xl p-md flex flex-col justify-between min-h-[140px] border-l-4 border-l-tertiary-fixed-dim shadow-md"><div class="flex justify-between items-start"><span class="text-label-sm font-label-sm text-on-surface-variant uppercase tracking-wider">Impacto Verde</span><div class="p-xs bg-surface-container-high text-on-tertiary-container rounded-lg"><span class="material-symbols-outlined" data-icon="forest">forest</span></div></div><div><h4 class="font-display-lg text-[32px] text-primary" id="res-impacto">-- kg CO2</h4><p class="text-label-sm font-label-sm text-on-surface-variant mt-xs">Aguardando dados</p></div></div><div class="bento-card bg-white border border-outline-variant rounded-xl p-md flex flex-col justify-between min-h-[140px] border-l-4 border-l-secondary-container shadow-md"><div class="flex justify-between items-start"><span class="text-label-sm font-label-sm text-on-surface-variant uppercase tracking-wider">Payback</span><div class="p-xs bg-surface-container-high text-on-surface rounded-lg"><span class="material-symbols-outlined" data-icon="schedule">schedule</span></div></div><div><h4 class="font-display-lg text-[32px] text-primary" id="res-payback">-- anos</h4><p class="text-label-sm font-label-sm text-on-surface-variant mt-xs">Aguardando dados</p></div></div><div class="bento-card bg-surface-container-low border border-outline-variant rounded-xl p-md flex flex-col justify-between min-h-[140px] shadow-sm"><div class="flex justify-between items-start"><span class="text-label-sm font-label-sm text-on-surface-variant uppercase tracking-wider">Status VIABILIDADE</span><div class="p-xs bg-surface-container-highest text-on-surface-variant rounded-lg"><span class="material-symbols-outlined" data-icon="fact_check">fact_check</span></div></div><div><h4 class="font-display-lg text-[32px] text-primary" id="res-status">--</h4><p class="text-label-sm font-label-sm text-on-surface-variant mt-xs">Aguardando análise</p></div></div></section>
<!-- Results Table -->
<section class="bg-white border border-outline-variant rounded-xl overflow-hidden shadow-sm">
<div class="px-md py-sm border-b border-outline-variant bg-surface-container-lowest">
<h3 class="font-headline-md text-headline-md text-on-surface">Projetos Processados</h3>
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
<tbody class="divide-y divide-outline-variant">
<!-- Example Row 1 -->
<tr class="hover:bg-surface-container-low transition-colors">
<td class="px-md py-sm font-label-sm text-label-sm font-bold">Industria Metal</td>
<td class="px-md py-sm font-label-sm text-label-sm text-right">R$ 250.000,00</td>
<td class="px-md py-sm font-label-sm text-label-sm text-right">8.500</td>
<td class="px-md py-sm font-label-sm text-label-sm text-right">R$ 7.225,00</td>
<td class="px-md py-sm font-label-sm text-label-sm text-center">2,88</td>
<td class="px-md py-sm text-center">
<span class="px-sm py-xs bg-tertiary-fixed text-on-tertiary-fixed rounded-full text-[10px] font-bold uppercase">Excelente</span>
</td>
</tr>
<!-- Example Row 2 -->
<tr class="hover:bg-surface-container-low transition-colors">
<td class="px-md py-sm font-label-sm text-label-sm font-bold">Fazenda Sol</td>
<td class="px-md py-sm font-label-sm text-label-sm text-right">R$ 50.000,00</td>
<td class="px-md py-sm font-label-sm text-label-sm text-right">1.200</td>
<td class="px-md py-sm font-label-sm text-label-sm text-right">R$ 1.020,00</td>
<td class="px-md py-sm font-label-sm text-label-sm text-center">4,08</td>
<td class="px-md py-sm text-center">
<span class="px-sm py-xs bg-secondary-container text-on-secondary-container rounded-full text-[10px] font-bold uppercase">Viável</span>
</td>
</tr>
<!-- Example Row 3 -->
<tr class="hover:bg-surface-container-low transition-colors">
<td class="px-md py-sm font-label-sm text-label-sm font-bold">Residencial Silva</td>
<td class="px-md py-sm font-label-sm text-label-sm text-right">R$ 15.000,00</td>
<td class="px-md py-sm font-label-sm text-label-sm text-right">250</td>
<td class="px-md py-sm font-label-sm text-label-sm text-right">R$ 212,50</td>
<td class="px-md py-sm font-label-sm text-label-sm text-center">5,88</td>
<td class="px-md py-sm text-center">
<span class="px-sm py-xs bg-secondary-container text-on-secondary-container rounded-full text-[10px] font-bold uppercase">Viável</span>
</td>
</tr>
</tbody>
</table>
</div>
</section>
<!-- Simplified Footer -->
<footer class="mt-auto pt-lg border-t border-outline-variant flex justify-center items-center pb-md">
<p class="font-label-sm text-label-sm text-on-surface-variant">© 2026 SolarEfficiency - Sistema de Gestão de Projetos</p>
</footer>
</main>
<script>
        const dropZone = document.getElementById('drop-zone');
        const fileInput = document.getElementById('file-input');
        const fileNameDisplay = document.getElementById('file-name');
        
        ['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
            dropZone.addEventListener(eventName, e => {
                e.preventDefault();
                e.stopPropagation();
            }, false);
        });

        ['dragenter', 'dragover'].forEach(eventName => {
            dropZone.addEventListener(eventName, () => {
                dropZone.classList.add('drag-active');
            }, false);
        });

        ['dragleave', 'drop'].forEach(eventName => {
            dropZone.addEventListener(eventName, () => {
                dropZone.classList.remove('drag-active');
            }, false);
        });

        const updateFileName = (name) => {
            fileNameDisplay.textContent = 'Arquivo selecionado: ' + name;
            fileNameDisplay.classList.remove('hidden');
        };

        fileInput.addEventListener('change', (e) => {
            if(fileInput.files.length > 0) {
                updateFileName(fileInput.files[0].name);
            }
        });

        dropZone.addEventListener('drop', e => {
            const dt = e.dataTransfer;
            const files = dt.files;
            if (files.length > 0 && files[0].name.endsWith('.csv')) {
                fileInput.files = files;
                updateFileName(files[0].name);
            } else if (files.length > 0) {
                alert('Por favor, envie apenas arquivos CSV.');
            }
        }, false);
    </script>




</body></html>