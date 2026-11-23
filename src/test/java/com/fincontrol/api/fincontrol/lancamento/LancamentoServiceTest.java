package com.fincontrol.api.fincontrol.lancamento;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fincontrol.api.fincontrol.categoria.Categoria;
import com.fincontrol.api.fincontrol.categoria.CategoriaRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class LancamentoServiceTest {

    @Mock
    private LancamentoRepository lancamentoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private LancamentoService lancamentoService;

    private Categoria categoria;
    private LancamentoRequest request;

    @BeforeEach
    void setUp() {
        categoria = new Categoria(1L, "Alimentação", "DESPESA", "#FF5733");
        request = new LancamentoRequest(
            "Almoço",
            new BigDecimal("45.50"),
            LocalDate.of(2026, 11, 23),
            TipoLancamento.DESPESA,
            1L
        );
    }

    @Test
    @DisplayName("Deve criar lançamento com sucesso quando a categoria existir")
    void deveCriarLancamentoComSucesso() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        
        Lancamento lancamentoSalvo = new Lancamento(
            10L,
            request.descricao(),
            request.valor(),
            request.data(),
            request.tipo(),
            categoria
        );
        when(lancamentoRepository.save(any(Lancamento.class))).thenReturn(lancamentoSalvo);

        LancamentoResponse response = lancamentoService.criar(request);

        assertNotNull(response);
        assertEquals(10L, response.id());
        assertEquals("Almoço", response.descricao());
        assertEquals(new BigDecimal("45.50"), response.valor());
        assertEquals(TipoLancamento.DESPESA, response.tipo());
        assertNotNull(response.categoria());
        assertEquals("Alimentação", response.categoria().nome());

        verify(categoriaRepository).findById(1L);
        verify(lancamentoRepository).save(any(Lancamento.class));
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException quando a categoria não for encontrada")
    void deveLancarExcecaoQuandoCategoriaNaoExiste() {
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        LancamentoRequest requestInvalido = new LancamentoRequest(
            "Teste",
            new BigDecimal("10.00"),
            LocalDate.now(),
            TipoLancamento.DESPESA,
            99L
        );

        assertThrows(EntityNotFoundException.class, () -> lancamentoService.criar(requestInvalido));

        verify(categoriaRepository).findById(99L);
        verify(lancamentoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve listar todos os lançamentos cadastrados")
    void deveListarTodosOsLancamentos() {
        Lancamento l1 = new Lancamento(1L, "Salário", new BigDecimal("5000.00"), LocalDate.now(), TipoLancamento.RECEITA, categoria);
        Lancamento l2 = new Lancamento(2L, "Mercado", new BigDecimal("350.00"), LocalDate.now(), TipoLancamento.DESPESA, categoria);

        when(lancamentoRepository.findAll()).thenReturn(List.of(l1, l2));

        List<LancamentoResponse> lista = lancamentoService.listarTodos();

        assertEquals(2, lista.size());
        assertEquals("Salário", lista.get(0).descricao());
        assertEquals("Mercado", lista.get(1).descricao());
        verify(lancamentoRepository).findAll();
    }
}
