package com.indra.p8.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "autores")
@Data
public class Autor {

    @Id
    private Long id;
    private String nombre;
    private String nacionalidad;
    private Date fechaNac;

    @OneToMany(mappedBy = "autor")
    private List<Libro> libros;

}
