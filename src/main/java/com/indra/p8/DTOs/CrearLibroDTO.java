package com.indra.p8.DTOs;

import com.indra.p8.model.Copia;
import com.indra.p8.model.TipoLibro;
import lombok.Data;

@Data
public class CrearLibroDTO {
    private String titulo;
    private TipoLibro tipo;
    private String editorial;
    private int anyo;
    private Long idAutor;
}
