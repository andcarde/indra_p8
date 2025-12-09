package com.indra.p8.DTOs;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CrearPrestamoDTO {

    private Long idLector;
    private Long idCopia;
    private LocalDate fechaLimite;
}
