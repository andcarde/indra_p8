package com.indra.p8.service;

import com.indra.p8.DTOs.CrearLectorDTO;
import com.indra.p8.model.Lector;

public interface LectorService {
    Lector crearLector(CrearLectorDTO dto);
    void updateLector(Long idLector, CrearLectorDTO dto);
    Lector getLector(Long idLector);
    void deleteLector(Long idLector);
}
