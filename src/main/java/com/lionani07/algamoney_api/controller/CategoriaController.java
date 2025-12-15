package com.lionani07.algamoney_api.controller;

import com.lionani07.algamoney_api.model.Categoria;
import com.lionani07.algamoney_api.repository.CategoriaRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    @PostMapping
    public ResponseEntity<Categoria> create(@RequestBody Categoria categoria) {
        val categoriaSaved = this.categoriaRepository.save(categoria);

        val location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(categoriaSaved.getCodigo())
                .toUri();

        return ResponseEntity.created(location).body(categoriaSaved);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Categoria> findByCodigo(@PathVariable Long codigo) {

        return this.categoriaRepository.findById(codigo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }
}
