package com.indra.p8.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "copias")
@Data
public class Copia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private EstadoCopia estado;
}
