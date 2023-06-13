package com.dev.backend.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dev.backend.model.Pessoa;
import com.dev.backend.services.PessoaService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/pessoa")
public class PessoaController {
    
    @Autowired
    private PessoaService PessoaService;

    @GetMapping()
    public ResponseEntity<List<Pessoa>> findAll(){
        List<Pessoa> pessoas = PessoaService.findAll();
        return ResponseEntity.ok().body(pessoas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> findById(@PathParam("id") Long id){
        Pessoa pessoa = PessoaService.findById(id);
        return ResponseEntity.ok().body(pessoa);
    }

    @PostMapping()
    public ResponseEntity<Pessoa> insert(@RequestBody Pessoa pessoa){
        pessoa = PessoaService.insert(pessoa);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pessoa.getId()).toUri();
        return ResponseEntity.created(uri).body(pessoa);
    }
    

    @PutMapping(value = "/{id}")
    public ResponseEntity<Pessoa> update(@PathVariable("id") Long id, @RequestBody Pessoa pessoa){
        pessoa = PessoaService.update(id, pessoa);
        return ResponseEntity.ok().body(pessoa);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        PessoaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
