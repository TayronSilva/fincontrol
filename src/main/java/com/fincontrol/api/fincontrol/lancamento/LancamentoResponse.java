package com.fincontrol.api.fincontrol.lancamento;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fincontrol.api.fincontrol.categoria.CategoriaResponse;

public record LancamentoResponse(
    Long id,
    String descricao,
    BigDecimal valor,
    LocalDate data,
    TipoLancamento tipo,
    CategoriaResponse categoria
) {

    public LancamentoResponse(Lancamento lancamento) {
        this(
            lancamento.getId(),
            lancamento.getDescricao(),
            lancamento.getValor(),
            lancamento.getData(),
            lancamento.getTipo(),
            lancamento.getCategoria() != null ? new CategoriaResponse(lancamento.getCategoria()) : null
        );
    }
}
