package com.indra.p8.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoId implements Serializable {

    private Long idLibro;
    private Long idLector;
    private Date inicio;
}