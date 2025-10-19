package com.lionani07.algamoney_api.repository;

import com.lionani07.algamoney_api.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
