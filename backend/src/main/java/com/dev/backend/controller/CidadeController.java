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

import com.dev.backend.model.Cidade;
import com.dev.backend.services.CidadeService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/cidade")
public class CidadeController {
    
    @Autowired
    private CidadeService cidadeService;

    @GetMapping()
    public ResponseEntity<List<Cidade>> findAll(){
        List<Cidade> cidades = cidadeService.findAll();
        return ResponseEntity.ok().body(cidades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cidade> findById(@PathParam("id") Long id){
        Cidade cidade = cidadeService.findById(id);
        return ResponseEntity.ok().body(cidade);
    }

    @PostMapping()
    public ResponseEntity<Cidade> insert(@RequestBody Cidade obj){
        obj = cidadeService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }
    

    @PutMapping(value = "/{id}")
    public ResponseEntity<Cidade> update(@PathVariable("id") Long id, @RequestBody Cidade obj){
        obj = cidadeService.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        cidadeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
