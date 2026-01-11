package com.lionani07.algamoney_api.controller;

import com.lionani07.algamoney_api.model.Pessoa;
import com.lionani07.algamoney_api.repository.PessoaRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/pessoas")
@AllArgsConstructor
public class PessoaController {

    private final PessoaRepository pessoaRepository;

    @PostMapping
    public ResponseEntity<Pessoa> create(@Valid @RequestBody Pessoa pessoa) {
        val pessoaCriada = this.pessoaRepository.save(pessoa);

        val location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(pessoa.getCodigo())
                .toUri();

        return ResponseEntity.created(location).body(pessoaCriada);
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
