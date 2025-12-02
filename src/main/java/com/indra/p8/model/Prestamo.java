package com.indra.p8.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "prestamos")
@Data
public class Prestamo {

    Date inicio;
    Date fin;
    @ManyToOne
    @JoinColumn(name = "libro_id")
    Libro libro;
}
