package com.indra.p8.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "libros")
@Data
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String titulo;
    private String tipo;
    private String editorial;
    private int anyo;

    @ManyToOne(mappedBy = "libro")
    Autor autor;
}
