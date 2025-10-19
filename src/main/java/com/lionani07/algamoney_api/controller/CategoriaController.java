package com.lionani07.algamoney_api.controller;

import com.lionani07.algamoney_api.model.Categoria;
import com.lionani07.algamoney_api.repository.CategoriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@AllArgsConstructor
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    @GetMapping
    public List<Categoria> findAll() {
        return this.categoriaRepository.findAll();
    }
}
