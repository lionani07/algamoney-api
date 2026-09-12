package com.lionani07.algamoney_api.controller;

import com.lionani07.algamoney_api.event.ResourceCriadoEvent;
import com.lionani07.algamoney_api.exception.AlgamoneyResourceNotFoundException;
import com.lionani07.algamoney_api.model.Lancamento;
import com.lionani07.algamoney_api.repository.LancamentoRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lancamentos")
@AllArgsConstructor
public class LancamentoController {

    private final LancamentoRepository lancamentoRepository;
    private final ApplicationEventPublisher eventPublisher;

    @PostMapping
    public ResponseEntity<Lancamento> create(@Valid @RequestBody final Lancamento lancamento, HttpServletResponse response) {
        val lancamentoCriado = this.lancamentoRepository.save(lancamento);

        eventPublisher.publishEvent(new ResourceCriadoEvent(this, response, lancamentoCriado.getCodigo()));

        return ResponseEntity.ok(lancamentoCriado);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        val lancamentos = this.lancamentoRepository.findAll();
        return ResponseEntity.ok(lancamentos);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> findByCodigo(@PathVariable Long codigo) {
        val lancamento = this.lancamentoRepository.findById(codigo)
                .orElseThrow(() -> new AlgamoneyResourceNotFoundException("Lançamento", codigo));

        return ResponseEntity.ok(lancamento);
    }
}
