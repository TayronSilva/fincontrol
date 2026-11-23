package com.fincontrol.api.fincontrol.categoria;

public record CategoriaResponse(
    Long id,
    String nome,
    String tipo,
    String cor
) {
    public CategoriaResponse(Categoria categoria) {
        this(
            categoria != null ? categoria.getId() : null,
            categoria != null ? categoria.getNome() : null,
            categoria != null ? categoria.getTipo() : null,
            categoria != null ? categoria.getCor() : null
        );
    }
}
