package com.lionani07.algamoney_api.controller;

import com.lionani07.algamoney_api.event.ResourceCriadoEvent;
import com.lionani07.algamoney_api.model.Categoria;
import com.lionani07.algamoney_api.repository.CategoriaRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@AllArgsConstructor
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    @GetMapping
    public List<Categoria> findAll() {
        return this.categoriaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Categoria> create(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
        val categoriaSaved = this.categoriaRepository.save(categoria);

        val resourceCreatedEvent = new ResourceCriadoEvent(this, response, categoriaSaved.getCodigo());
        applicationEventPublisher.publishEvent(resourceCreatedEvent);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Categoria> findByCodigo(@PathVariable Long codigo) {

        return this.categoriaRepository.findById(codigo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }
}
