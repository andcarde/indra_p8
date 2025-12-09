package com.indra.p8.service;

import com.indra.p8.DTOs.CrearAutorDTO;
import com.indra.p8.DTOs.CrearLectorDTO;
import com.indra.p8.DTOs.CrearLibroDTO;
import com.indra.p8.model.*;

import java.util.Date;
import java.util.List;

public interface BibliotecaService {
    Autor crearAutor(CrearAutorDTO dto);

    String crearCopia(Long idLibro, int nCopias);

    boolean repararCopia(Long idCopia);
    void deleteCopia(Long idCopia);
    List<Copia> getCopiasByLibroId(Long idLibro);
    Copia getCopiaById(Long idCopia);
    boolean mandarBibliotecaCopia(Long idCopia);

}
