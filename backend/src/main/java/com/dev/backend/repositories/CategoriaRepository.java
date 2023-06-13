package com.dev.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.backend.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
}
