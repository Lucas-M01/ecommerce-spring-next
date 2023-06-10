package com.dev.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.backend.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {
    
}
