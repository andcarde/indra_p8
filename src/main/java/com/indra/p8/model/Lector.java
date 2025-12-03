package com.indra.p8.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lectores")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lector {

    @Column
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
