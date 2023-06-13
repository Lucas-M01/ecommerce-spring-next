package com.dev.backend.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.dev.backend.model.Pessoa;
import com.dev.backend.repositories.PessoaRepository;
import com.dev.backend.services.exceptions.DatabaseException;
import com.dev.backend.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityExistsException;

import org.springframework.dao.DataIntegrityViolationException;

@Service
public class PessoaService {
    
    @Autowired
    private PessoaRepository repo;
    
    public List<Pessoa> findAll() {
        return repo.findAll();
    }
    
    public Pessoa findById(Long id) {
        Optional<Pessoa> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Pessoa insert(Pessoa obj) {
        if(validar("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", obj.getCpf()) && validar("\\d{5}-\\d{3}", obj.getCep()) && validar("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", obj.getEmail())){
            obj.setDataCriacao(new Date());
            return repo.saveAndFlush(obj);
        }
        return null;
    }

    public Pessoa update(Long id, Pessoa obj) {
        try {
            Pessoa entity = repo.getReferenceById(id);
            updateData(entity, obj);
            return repo.saveAndFlush(entity);
        } catch (EntityExistsException e){
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Pessoa entity, Pessoa obj) {
        if(validar("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", obj.getCpf()) && validar("\\d{5}-\\d{3}", obj.getCep()) && validar("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", obj.getEmail())){
            entity.setName(obj.getName());
            entity.setCpf(obj.getCpf());
            entity.setEmail(obj.getEmail());
            entity.setTelefone(obj.getTelefone());
            entity.setEndereco(obj.getEndereco());
            entity.setSenha(obj.getSenha());
            entity.setCidade(obj.getCidade());
            entity.setCep(obj.getCep());
            entity.setDataAtualizacao(new Date());
        }
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

    public Boolean validar(String regex, String atr){
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(atr).matches();
    }
    

}
