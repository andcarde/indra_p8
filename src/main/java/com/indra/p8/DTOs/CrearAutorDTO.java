package com.indra.p8.DTOs;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CrearAutorDTO {
    private String nombre;
    private String nacionalidad;
    private LocalDate fechaNac;
}
