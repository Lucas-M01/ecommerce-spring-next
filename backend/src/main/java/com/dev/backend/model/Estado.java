package com.dev.backend.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import lombok.Data;

import java.util.Date;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "tb_estado")
@Data
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    private String Sigla;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;
    

}
