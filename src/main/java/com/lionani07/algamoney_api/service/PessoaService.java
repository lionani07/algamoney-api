package com.lionani07.algamoney_api.service;

import com.lionani07.algamoney_api.exception.AlgamoneyResourceNotFoundException;
import com.lionani07.algamoney_api.model.Pessoa;
import com.lionani07.algamoney_api.repository.PessoaRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public Pessoa save(final Pessoa pessoa) {
        return this.pessoaRepository.save(pessoa);
    }

    public List<Pessoa> findAll() {
        return this.pessoaRepository.findAll();
    }

    public Pessoa findById(final Long codigo) {
        return this.pessoaRepository.findById(codigo)
                .orElseThrow(() -> new AlgamoneyResourceNotFoundException("Pessoa not found"));
    }

    public void deleteById(final Long codigo) {
        this.pessoaRepository.deleteById(codigo);
    }

    public void updateFieldAtivo(final Long codigo, final Boolean ativo) {
        val pessoa = this.findById(codigo);
        pessoa.setAtivo(ativo);
        this.save(pessoa);
    }
}
