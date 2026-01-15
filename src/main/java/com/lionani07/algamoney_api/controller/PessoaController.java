package com.lionani07.algamoney_api.controller;

import com.lionani07.algamoney_api.event.ResourceCriadoEvent;
import com.lionani07.algamoney_api.model.Pessoa;
import com.lionani07.algamoney_api.repository.PessoaRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pessoas")
@AllArgsConstructor
public class PessoaController {

    private final PessoaRepository pessoaRepository;

    private final ApplicationEventPublisher eventPublisher;

    @PostMapping
    public ResponseEntity<Pessoa> create(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
        val pessoaCriada = this.pessoaRepository.save(pessoa);

        this.eventPublisher.publishEvent(new ResourceCriadoEvent(this, response, pessoaCriada.getCodigo()));

        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaCriada);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        val pessoas = this.pessoaRepository.findAll();
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> findByCodigo(@PathVariable Long codigo) {
        return this.pessoaRepository.findById(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
