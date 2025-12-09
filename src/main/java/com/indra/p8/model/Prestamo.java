package com.indra.p8.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "prestamos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_retiro", nullable = false)
    private LocalDate inicio;

    @Column(name = "fecha_devolucion")
    private LocalDate fin;

    @Column(name = "fecha_limite")
    private LocalDate limite;

    @ManyToOne(optional = false)
    @JoinColumn(name = "lector_id")
    @JsonIgnore
    private Lector lector;

    @ManyToOne(optional = false)
    @JoinColumn(name = "copia_id")
    @JsonIgnore
    private Copia copia;
}
