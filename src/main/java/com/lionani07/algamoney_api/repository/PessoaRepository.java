package com.lionani07.algamoney_api.repository;

import com.lionani07.algamoney_api.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    @Modifying
    @Query("UPDATE Pessoa p SET p.ativo = :ativo WHERE p.codigo = :codigo")
    void updateAtivoById(Boolean ativo, Long codigo);
}
