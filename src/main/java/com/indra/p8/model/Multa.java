package com.indra.p8.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "multas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Multa {

    @Column
    Date fInicio;
    @Column
    Date fFin;

    @OneToOne(mappedBy = "")
    Lector lector;
}
