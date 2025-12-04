package com.indra.p8.DTOs;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.List;

@Data
public class CrearLectorDTO {

    private Long id;
    private String nombre;
    private String telefono;
    private String direccion;
    private List<Long> idPrestamos;
    private Long idMulta;
}
