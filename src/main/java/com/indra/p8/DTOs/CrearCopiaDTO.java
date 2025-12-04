package com.indra.p8.DTOs;

import com.indra.p8.model.Libro;

import lombok.Data;

@Data
public class CrearCopiaDTO {

    private String estado;
    private Libro idLibro;
}
