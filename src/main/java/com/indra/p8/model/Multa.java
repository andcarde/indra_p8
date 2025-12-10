package com.indra.p8.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "multas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Multa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "f_inicio")
    private LocalDate fInicio;

    @Column(name = "f_fin")
    private LocalDate fFin;

    @Column(name = "monto", nullable = false)
    private float monto;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoMulta estado;

    @ManyToOne
    @JoinColumn(name = "id_lector", nullable = false)
    private Lector lector;
}
