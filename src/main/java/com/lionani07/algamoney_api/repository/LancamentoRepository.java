package com.lionani07.algamoney_api.repository;

import com.lionani07.algamoney_api.model.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
}
