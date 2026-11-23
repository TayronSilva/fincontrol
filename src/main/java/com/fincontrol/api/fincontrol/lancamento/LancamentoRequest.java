package com.fincontrol.api.fincontrol.lancamento;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LancamentoRequest(
    @NotBlank(message = "A descrição é obrigatória")
    String descricao,

    @NotNull(message = "O valor é obrigatório")
    @Positive(message = "O valor deve ser positivo")
    BigDecimal valor,

    @NotNull(message = "A data é obrigatória")
    LocalDate data,

    @NotNull(message = "O tipo é obrigatório")
    TipoLancamento tipo,

    @NotNull(message = "O ID da categoria é obrigatório")
    Long categoriaId
) {
}
