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

import com.dev.backend.model.Estado;
import com.dev.backend.services.EstadoService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/estado")
public class EstadoController {
    
    @Autowired
    private EstadoService estadoService;

    @GetMapping()
    public ResponseEntity<List<Estado>> findAll(){
        List<Estado> estados = estadoService.findAll();
        return ResponseEntity.ok().body(estados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estado> findById(@PathParam("id") Long id){
        Estado estado = estadoService.findById(id);
        return ResponseEntity.ok().body(estado);
    }

    @PostMapping()
    public ResponseEntity<Estado> insert(@RequestBody Estado estado){
        estado = estadoService.insert(estado);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(estado.getId()).toUri();
        return ResponseEntity.created(uri).body(estado);
    }
    

    @PutMapping(value = "/{id}")
    public ResponseEntity<Estado> update(@PathVariable("id") Long id, @RequestBody Estado estado){
        estado = estadoService.update(id, estado);
        return ResponseEntity.ok().body(estado);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        estadoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
