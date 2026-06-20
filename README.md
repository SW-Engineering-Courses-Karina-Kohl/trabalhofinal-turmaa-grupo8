# ☀️ SolarEfficiency

Sistema de Gestão de Viabilidade Fotovoltaica desenvolvido em Java Web para análise econômica e ambiental de projetos de energia solar.

O sistema processa projetos cadastrados em arquivos CSV, calcula indicadores financeiros e ambientais e classifica automaticamente a viabilidade de cada projeto com base em parâmetros configuráveis.

---

## 📋 Objetivo

O SolarEfficiency foi desenvolvido para automatizar a análise de projetos fotovoltaicos através do cálculo de:

- Economia Mensal
- Impacto Verde (CO₂ evitado)
- Payback Estimado

Com base nesses indicadores, especialmente no payback, o sistema classifica a viabilidade de cada projeto.

Além disso, a aplicação oferece:

- Importação de projetos via CSV;
- Importação de parâmetros de configuração externos;
- Validação dos dados recebidos;
- Registro de erros encontrados;
- Exportação dos resultados processados.

---

## 🏗️ Arquitetura


### Controller

Responsável por:

- Receber requisições HTTP;
- Processar uploads dos arquivos CSV;
- Acionar os serviços necessários;
- Encaminhar os resultados para a interface JSP.

### Service

Responsável exclusivamente pela lógica de cálculo dos indicadores.

Os serviços implementados realizam:

- Cálculo da Economia Mensal;
- Cálculo do Impacto Verde;
- Cálculo do Payback Estimado.

Todos os cálculos seguem uma interface comum (`ICalculo`) e são executados pelo motor de cálculos.

### Model

Contém as estruturas de dados utilizadas pela aplicação.

#### Entradas

- `ProjetoSolar`
- `Config`

#### Resultados

- `ResultadoViabilidade`
- `StatusViabilidade`
- `RelatorioProjeto`

#### Enumerações

- `ETipoCalculo`
- `EStatusViabilidade`

#### Tratamento de Erros

- `ErroProjetoSolar`

---

## 📐 Regras de Negócio

### Economia Mensal

Representa a economia financeira gerada mensalmente pelo projeto.

```text
Economia Mensal = Produção (kWh) × Tarifa de Energia
```

### Impacto Verde

Representa a quantidade estimada de CO₂ evitada pela geração de energia solar.

```text
Impacto Verde = Produção (kWh) × Fator de Carbono
```

### Payback Estimado

Representa o tempo necessário para recuperar o investimento realizado (em anos).

```text
Payback = Investimento / Economia Mensal / 12
```

---

## 📊 Classificação da Viabilidade

A classificação não utiliza valores fixos no código.

Os limites são carregados dinamicamente a partir do arquivo de configuração fornecido pelo usuário.

### Exemplo de Configuração

```csv
parametro,valor
tarifa_kwh,0.85
fator_co2_kwh,0.092
limite_excelente_anos,4
limite_viavel_anos,8
```

### Regras de Classificação

| Status | Condição |
|----------|----------|
| EXCELENTE | Payback < Limite Excelente |
| VIÁVEL | Limite Excelente ≤ Payback < Limite Viável |
| BAIXA PRIORIDADE | Payback ≥ Limite Viável |

Dessa forma, a classificação pode ser alterada apenas modificando o arquivo de configuração, sem necessidade de recompilar a aplicação.

---

## 🏛️ Diagrama de Classes

O diagrama abaixo apresenta a arquitetura e os relacionamentos entre as principais classes do sistema.

<img width="14246" height="7271" alt="Diagrama de classe - viabilidade fotovoltaica (4)" src="https://github.com/user-attachments/assets/a5d7f2c2-11ec-464c-81af-a20afdc1da8f" />

Você pode acessar o diagrama no LucidChart [clicando aqui](https://lucid.app/lucidchart/d39a84ae-59fb-4756-8208-d214189c0f58/edit?viewport_loc=-2931%2C-2006%2C12266%2C6388%2C0_0&invitationId=inv_bdbbefc4-43cb-4665-bb70-132fcf2a2bd6).


---

## 📂 Estrutura do Projeto

```text
├───src
│   ├───main
│   │   ├───java
│   │   │   └───br
│   │   │       └───edu
│   │   │           └───ufrgs
│   │   │               ├───controller
│   │   │               ├───dao
│   │   │               │   └───mapper
│   │   │               ├───model
│   │   │               │   ├───entradas
│   │   │               │   ├───enums
│   │   │               │   ├───erros
│   │   │               │   └───resultado
│   │   │               └───service
│   │   └───webapp
│   │       └───WEB-INF
│   └───test
│       ├───java
│       │   └───br
│       │       └───edu
│       │           └───ufrgs
│       │               ├───dao
│       │               │   └───mapper
│       │               ├───model
│       │               │   └───resultado
│       │               └───service
│       └───resources
│           └───CSV
└───target
    ├───classes
    │   └───br
    │       └───edu
    │           └───ufrgs
    │               ├───controller
    │               ├───dao
    │               │   └───mapper
    │               ├───model
    │               │   ├───entradas
    │               │   ├───enums
    │               │   ├───erros
    │               │   └───resultado
    │               └───service
    ├───generated-sources
    │   └───annotations
    ├───generated-test-sources
    │   └───test-annotations
    ├───maven-status
    │   └───maven-compiler-plugin
    │       ├───compile
    │       │   └───default-compile
    │       └───testCompile
    │           └───default-testCompile
    ├───site
    │   └───jacoco
    │       ├───br.edu.ufrgs.controller
    │       ├───br.edu.ufrgs.dao
    │       ├───br.edu.ufrgs.dao.mapper
    │       ├───br.edu.ufrgs.model.entradas
    │       ├───br.edu.ufrgs.model.enums
    │       ├───br.edu.ufrgs.model.erros
    │       ├───br.edu.ufrgs.model.resultado
    │       ├───br.edu.ufrgs.service
    │       └───jacoco-resources
    ├───surefire-reports
    └───test-classes
        ├───br
        │   └───edu
        │       └───ufrgs
        │           ├───dao
        │           │   └───mapper
        │           ├───model
        │           │   └───resultado
        │           └───service
        └───CSV
```

---

## 📥 Arquivos de Entrada

### Arquivo de Configuração

Contém os parâmetros utilizados pelos cálculos e pela classificação.

```csv
parametro,valor
tarifa_kwh,0.85
fator_co2_kwh,0.092
limite_excelente_anos,4
limite_viavel_anos,8
```

### Base de Projetos

Contém os projetos fotovoltaicos a serem processados.

```csv
projeto_id,cliente,investimento_inicial,producao_mes_kwh,modelo_painel
P_001,Fazenda Sol,50000.00,1200,Monocristalino
P_002,Residencial Silva,15000.00,250,Policristalino
P_003,Industria Metal, 250000.00,8500,Bifacial
```

---

## ✅ Validações Implementadas

Durante a leitura dos arquivos, o sistema verifica:

- Quantidade correta de colunas;
- Campos obrigatórios;
- Conversão de valores numéricos;
- Investimento maior que zero;
- Produção maior que zero;
- e outros;

Projetos inválidos são registrados no log de erros e não interrompem o processamento dos demais registros.

---

## 🚀 Tecnologias Utilizadas

### Backend

- Java 17
- Jakarta Servlet
- JSP
- OpenCSV

### Build

- Maven

### Servidor de Aplicação

- Apache Tomcat 10

### Containerização

- Docker

### Frontend

- HTML5
- CSS3
- JavaScript

---

## 🐳 Execução do Projeto

### Construir e iniciar os containers

```bash
docker-compose up --build -d
```

### Acessar a aplicação

```text
http://localhost:8080
```

### Encerrar os containers

```bash
docker-compose down
```

---

## 📈 Funcionalidades

- Upload de arquivo de configuração;
- Upload da base de projetos;
- Processamento automático dos indicadores;
- Classificação da viabilidade dos projetos;
- Dashboard consolidado;
- Tabela paginada de resultados;
- Registro de erros de validação;
- Exportação dos resultados para CSV.

---

## 🖥️ Interface

### Dashboard Principal

- Upload dos arquivos CSV;
- Visualização dos parâmetros carregados;
- Indicadores consolidados de economia, impacto verde e payback.

### Tabela de Resultados

- Projetos processados;
- Investimento inicial;
- Economia mensal calculada;
- Impacto ambiental;
- Payback estimado;
- Status de viabilidade.

### Log de Erros

- Exibição dos registros de projetos inválidos encontrados durante o processamento;
- Descrição detalhada do motivo de cada erro.

---

## 📷 Telas do Sistema

### Upload de Dados

<img width="1359" height="776" alt="Screenshot 2026-06-11 223502" src="https://github.com/user-attachments/assets/6a0b5b90-93fe-43a1-90e6-0cb11b03645c" />


### Resultados

<img width="1342" height="732" alt="image" src="https://github.com/user-attachments/assets/d706ac82-e4fe-4ea2-a80a-ea4810fec110" />

### Log de Erros

<img width="1361" height="716" alt="image" src="https://github.com/user-attachments/assets/2a0de9fe-88a8-4661-8de5-48c56838a59d" />


## 👥 Equipe

Projeto desenvolvido para a disciplina de Desenvolvimento de Software – UFRGS.

**Grupo 08**

- [Brenda Melo Soares](https://github.com/BrendaMeso)
- [Eduarda Post Michels](https://github.com/eduardapmichels)
- [Júlia Feltraco Lemos](https://github.com/julialemos18)
- [Lauren Santos Lázaro](https://github.com/laurlzr)
- [Manoella Florisbal de Oliveira](https://github.com/florisbalmanoella)


