package com.indra.p8.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "libros")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String titulo;

    @Enumerated(EnumType.STRING)
    private TipoLibro tipo;

    @Column
    private String editorial;

    @Column
    private int anyo;


    @Column(unique = true)
    private int isbn;


    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false)
    private Autor autor;

    @OneToMany(mappedBy = "libro",cascade = CascadeType.REMOVE,orphanRemoval = true)
    @JsonIgnore
    private List<Copia> copias;
}
