package com.indra.p8.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "lectores")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;

    @Column
    private String telefono;

    @Column
    private String direccion;

    @OneToMany(mappedBy = "lector")
    private List<Prestamo> prestamos;

    @OneToOne(mappedBy = "lector", cascade = CascadeType.ALL)
    private Multa multa;
}
