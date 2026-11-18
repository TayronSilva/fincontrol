package com.fincontrol.api.fincontrol.categoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public Categoria salvar(Categoria categoria) {
        return repository.save(categoria);
    }
}
