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

    @Column
    Date inicio;
    @Column
    Date fin;
    @ManyToOne
    @JoinColumn(name = "libro_id")
    Libro libro;
}
