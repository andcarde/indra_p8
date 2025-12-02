package com.indra.p8.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "multas")
@Data
public class Multa {

    Date fInicio;
    Date fFin;

    @OneToOne(mappedBy = "")
    Lector lector;
}
