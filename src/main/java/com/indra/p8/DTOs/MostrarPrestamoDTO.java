package com.indra.p8.DTOs;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MostrarPrestamoDTO {

    private Long idCopia;
    private Long idLector;
    private LocalDate inicio;
    private LocalDate limite;
    private LocalDate fin;
}
