package com.indra.p8.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "lectores")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long nSocio;

    @Column
    private String nombre;

    @Column
    private String telefono;

    @Column
    private String direccion;

    @OneToMany(mappedBy = "lector_id")
    private List<Prestamo> prestamos;

    @OneToOne(mappedBy = "lector", cascade = CascadeType.ALL)
    private Multa multa;
}
