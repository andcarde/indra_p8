package com.indra.p8.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "copias")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Copia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private EstadoCopia estado;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_libro", nullable = false)
    private Libro libro;

    @OneToMany(mappedBy = "copia")
    private List<Prestamo> prestamos;
}
