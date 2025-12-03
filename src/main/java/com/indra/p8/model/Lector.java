package com.indra.p8.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "lectores")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lector {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long nSocio;

    @Column
    private String nombre;

    @Column
    private String telefono;

    @Column
    private String direccion;

    public void devolver(long id, Date fechaAct) {

    }
    public void prestar(long id, Date fechaAct) {

    }
    private void multar(int dias) {

    }
}
