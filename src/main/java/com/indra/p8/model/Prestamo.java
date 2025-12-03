package com.indra.p8.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "prestamos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prestamo {

    @Column(name = "libro_id")
    @Id
    long idLibro;
    @Column(name = "lector_id")
    @Id
    long idLector;
    @Column
    @Id
    Date inicio;
    @Column
    Date fin;
    @ManyToOne
    @JoinColumn(name = "libro_id")
    Libro libro;
    @ManyToOne
    @JoinColumn(name = "lector_id")
    Lector lector;
}
