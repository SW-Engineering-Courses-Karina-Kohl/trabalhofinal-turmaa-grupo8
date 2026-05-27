package br.edu.ufrgs.service;

import br.edu.ufrgs.model.enums.ETipoCalculo;

// interface ICalculo: define o contrato que as classes de cálculo devem seguir
// polimorfismo: diferentes classes tratadas de maneira uniforme

public interface ICalculo {

    // faz o cálculo usando os dados do contexto
    // void pq o resultado é armazenado internamente e acessado dps com getValor()
    void calcular(ContextoCalculo contexto);

    // retorna o valor do cálculo
    double getValor();

    // retorna o tipo do cálculo
    // ETipoCalculo = enum (IMPACTO_VERDE, PAYBACK, ECONOMIA_MENSAL)
    ETipoCalculo getTipo();
}