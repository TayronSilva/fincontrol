package com.fincontrol.api.fincontrol.lancamento;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fincontrol.api.fincontrol.categoria.Categoria;
import com.fincontrol.api.fincontrol.categoria.CategoriaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class LancamentoService {

    private final LancamentoRepository lancamentoRepository;
    private final CategoriaRepository categoriaRepository;

    public LancamentoService(LancamentoRepository lancamentoRepository, CategoriaRepository categoriaRepository) {
        this.lancamentoRepository = lancamentoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public LancamentoResponse criar(LancamentoRequest request) {
        Categoria categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com o ID: " + request.categoriaId()));

        Lancamento lancamento = new Lancamento();
        lancamento.setDescricao(request.descricao());
        lancamento.setValor(request.valor());
        lancamento.setData(request.data());
        lancamento.setTipo(request.tipo());
        lancamento.setCategoria(categoria);

        Lancamento salvo = lancamentoRepository.save(lancamento);
        return new LancamentoResponse(salvo);
    }

    public List<LancamentoResponse> listarTodos() {
        return lancamentoRepository.findAll()
                .stream()
                .map(LancamentoResponse::new)
                .toList();
    }
}
