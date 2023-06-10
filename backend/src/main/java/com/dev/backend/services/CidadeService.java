package com.dev.backend.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.dev.backend.model.Cidade;
import com.dev.backend.repositories.CidadeRepository;
import com.dev.backend.services.exceptions.DatabaseException;
import com.dev.backend.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityExistsException;

import org.springframework.dao.DataIntegrityViolationException;

@Service
public class CidadeService {
    
    @Autowired
    private CidadeRepository repo;


    
    public List<Cidade> findAll() {
        return repo.findAll();
    }
    
    public Cidade findById(Long id) {
        Optional<Cidade> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Cidade insert(Cidade obj) {
        obj.setDataCriacao(new Date());
        return repo.saveAndFlush(obj);
    }

    public Cidade update(Long id, Cidade obj) {
        try {
            obj.setDataAtualizacao(new Date());
            Cidade entity = repo.getReferenceById(id);
            updateData(entity, obj);
            return repo.saveAndFlush(entity);
        } catch (EntityExistsException e){
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Cidade entity, Cidade obj) {
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
