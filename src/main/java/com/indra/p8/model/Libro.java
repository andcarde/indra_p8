package com.indra.p8.model;

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

    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false)
    private Autor autor;

    @OneToMany(mappedBy = "libro")
    private List<Copia> copias;
}
