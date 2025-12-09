package com.indra.p8.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LibroMasPrestadoDTO {
    private Long libroId;
    private String titulo;
    private Long totalPrestamos;
}

