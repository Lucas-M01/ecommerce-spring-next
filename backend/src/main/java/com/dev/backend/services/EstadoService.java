package com.dev.backend.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.dev.backend.model.Estado;
import com.dev.backend.repositories.EstadoRepository;
import com.dev.backend.services.exceptions.DatabaseException;
import com.dev.backend.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityExistsException;

import org.springframework.dao.DataIntegrityViolationException;

@Service
public class EstadoService {
    
    @Autowired
    private EstadoRepository repo;


    
    public List<Estado> findAll() {
        return repo.findAll();
    }
    
    public Estado findById(Long id) {
        Optional<Estado> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Estado insert(Estado obj) {
        obj.setDataCriacao(new Date());
        return repo.saveAndFlush(obj);
    }

    public Estado update(Long id, Estado obj) {
        try {
            Estado entity = repo.getReferenceById(id);
            updateData(entity, obj);
            return repo.saveAndFlush(entity);
        } catch (EntityExistsException e){
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Estado entity, Estado obj) {
        entity.setName(obj.getName());
        entity.setSigla(obj.getSigla());
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
