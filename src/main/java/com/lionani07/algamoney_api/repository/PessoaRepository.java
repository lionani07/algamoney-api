package com.lionani07.algamoney_api.repository;

import com.lionani07.algamoney_api.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
