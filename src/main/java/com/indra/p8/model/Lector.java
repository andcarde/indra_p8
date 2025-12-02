package com.indra.p8.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "lectores")
@Data
public class Lector {

    private long nSocio;
    private String nombre;
    private String telefono;
    private String direccion;

    public void devolver(long id, Date fechaAct) {

    }
    public void prestar(long id, Date fechaAct) {

    }
    private void multar(int dias) {

    }
}
