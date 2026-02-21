package com.lionani07.algamoney_api.controller;

import com.lionani07.algamoney_api.event.ResourceCriadoEvent;
import com.lionani07.algamoney_api.model.Pessoa;
import com.lionani07.algamoney_api.service.PessoaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pessoas")
@AllArgsConstructor
public class PessoaController {

    private final PessoaService pessoaService;

    private final ApplicationEventPublisher eventPublisher;

    @PostMapping
    public ResponseEntity<Pessoa> create(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
        val pessoaCriada = this.pessoaService.save(pessoa);

        this.eventPublisher.publishEvent(new ResourceCriadoEvent(this, response, pessoaCriada.getCodigo()));

        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaCriada);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        val pessoas = this.pessoaService.findAll();
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> findByCodigo(@PathVariable Long codigo) {
        val pessoa = this.pessoaService.findById(codigo);

        return ResponseEntity.ok(pessoa);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> delete(@PathVariable Long codigo) {
        this.pessoaService.deleteById(codigo);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Pessoa> update(@PathVariable Long codigo, @RequestBody @Valid Pessoa pessoa) {
        val pessoaDb = this.pessoaService.findById(codigo);

        BeanUtils.copyProperties(pessoa, pessoaDb, "codigo");

        return ResponseEntity.ok(this.pessoaService.save(pessoaDb));
    }

    @PutMapping("/{codigo}/ativo")
    public ResponseEntity<Void> updateFieldAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
        this.pessoaService.updateFieldAtivo(codigo, ativo);
        return ResponseEntity.noContent().build();
    }
}
