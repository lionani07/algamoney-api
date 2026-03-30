package com.lionani07.algamoney_api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoLancamento {

    DESPESA("Despesa"),
    RECEITA("Receita");

    private final String descricao;
}
