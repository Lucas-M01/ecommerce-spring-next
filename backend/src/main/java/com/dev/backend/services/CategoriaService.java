package com.dev.backend.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.dev.backend.model.Categoria;
import com.dev.backend.repositories.CategoriaRepository;
import com.dev.backend.services.exceptions.DatabaseException;
import com.dev.backend.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityExistsException;

import org.springframework.dao.DataIntegrityViolationException;

@Service
public class CategoriaService {
    
    @Autowired
    private CategoriaRepository repo;
    
    public List<Categoria> findAll() {
        return repo.findAll();
    }
    
    public Categoria findById(Long id) {
        Optional<Categoria> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Categoria insert(Categoria obj) {
        obj.setDataCriacao(new Date());
        return repo.saveAndFlush(obj);

    }

    public Categoria update(Long id, Categoria obj) {
        try {
            Categoria entity = repo.getReferenceById(id);
            updateData(entity, obj);
            return repo.saveAndFlush(entity);
        } catch (EntityExistsException e){
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Categoria entity, Categoria obj) {
        entity.setName(obj.getName());
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
