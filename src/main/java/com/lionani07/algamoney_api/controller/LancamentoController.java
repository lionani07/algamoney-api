package com.lionani07.algamoney_api.controller;

import com.lionani07.algamoney_api.exception.AlgamoneyResourceNotFoundException;
import com.lionani07.algamoney_api.repository.LancamentoRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lancamentos")
@AllArgsConstructor
public class LancamentoController {

    private final LancamentoRepository lancamentoRepository;

    @GetMapping
    public ResponseEntity<?> findAll() {
        val lancamentos = this.lancamentoRepository.findAll();
        return ResponseEntity.ok(lancamentos);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> findByCodigo(@PathVariable Long codigo) {
        val lancamento = this.lancamentoRepository.findById(codigo)
                .orElseThrow(() -> new AlgamoneyResourceNotFoundException("Lançamento não encontrado com o código: " + codigo));

        return ResponseEntity.ok(lancamento);
    }
}
