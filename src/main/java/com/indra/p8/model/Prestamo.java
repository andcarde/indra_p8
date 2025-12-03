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
@IdClass(PrestamoId.class)
public class Prestamo {

    @Column(name = "copia_id")
    @Id
    long idCopia;

    @Column(name = "lector_id")
    @Id
    long idLector;

    @Column(nullable = false)
    @Id
    Date inicio;

    @Column
    Date fin;

    @ManyToOne(optional = false)
    @JoinColumn(name = "copia_id")
    Copia copia;

    @ManyToOne(optional = false)
    @JoinColumn(name = "lector_id")
    Lector lector;
}
