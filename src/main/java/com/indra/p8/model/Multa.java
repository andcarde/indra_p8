package com.indra.p8.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "multas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(MultaId.class)
public class Multa {

    @Column(name = "lector_id")
    @Id
    long idLector;

    @Id
    @Column(name = "f_inicio")
    Date fInicio;

    @Column(name = "f_fin")
    Date fFin;

    @OneToOne
    @JoinColumn(name = "id_lector", nullable = false, insertable = false, updatable = false)
    Lector lector;
}
