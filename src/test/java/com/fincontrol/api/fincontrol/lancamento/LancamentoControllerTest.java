package com.fincontrol.api.fincontrol.lancamento;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fincontrol.api.fincontrol.categoria.CategoriaResponse;

@ExtendWith(MockitoExtension.class)
class LancamentoControllerTest {

    @Mock
    private LancamentoService lancamentoService;

    @InjectMocks
    private LancamentoController lancamentoController;

    @Test
    @DisplayName("Deve criar lançamento e retornar HTTP 201 Created")
    void deveCriarLancamentoRetornando201() {
        LancamentoRequest request = new LancamentoRequest(
            "Aluguel",
            new BigDecimal("1200.00"),
            LocalDate.of(2026, 11, 23),
            TipoLancamento.DESPESA,
            1L
        );
        CategoriaResponse categoriaResp = new CategoriaResponse(1L, "Moradia", "DESPESA", "#0000FF");
        LancamentoResponse response = new LancamentoResponse(
            1L,
            request.descricao(),
            request.valor(),
            request.data(),
            request.tipo(),
            categoriaResp
        );

        when(lancamentoService.criar(request)).thenReturn(response);

        ResponseEntity<LancamentoResponse> result = lancamentoController.criar(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    @DisplayName("Deve listar lançamentos e retornar HTTP 200 OK")
    void deveListarLancamentosRetornando200() {
        CategoriaResponse cat = new CategoriaResponse(1L, "Lazer", "DESPESA", "#FFFF00");
        LancamentoResponse l1 = new LancamentoResponse(1L, "Cinema", new BigDecimal("50.00"), LocalDate.now(), TipoLancamento.DESPESA, cat);

        when(lancamentoService.listarTodos()).thenReturn(List.of(l1));

        ResponseEntity<List<LancamentoResponse>> result = lancamentoController.listarTodos();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody().size());
        assertEquals("Cinema", result.getBody().get(0).descricao());
    }
}
