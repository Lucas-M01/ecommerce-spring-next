package com.dev.backend.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.dev.backend.model.Produto;
import com.dev.backend.repositories.ProdutoRepository;
import com.dev.backend.services.exceptions.DatabaseException;
import com.dev.backend.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityExistsException;

import org.springframework.dao.DataIntegrityViolationException;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository repo;
    
    public List<Produto> findAll() {
        return repo.findAll();
    }
    
    public Produto findById(Long id) {
        Optional<Produto> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Produto insert(Produto obj) {
        obj.setDataCriacao(new Date());
        return repo.saveAndFlush(obj);

    }

    public Produto update(Long id, Produto obj) {
        try {
            Produto entity = repo.getReferenceById(id);
            updateData(entity, obj);
            return repo.saveAndFlush(entity);
        } catch (EntityExistsException e){
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Produto entity, Produto obj) {
        entity.setName(obj.getName());
        entity.setPrice(obj.getPrice());
        entity.setDescription(obj.getDescription());
        entity.setImgUrl(obj.getImgUrl());
        entity.setCategorias(obj.getCategorias());
        entity.setDataAtualizacao(new Date());
    }

    public void delete(Long id) {
        try {
            repo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
