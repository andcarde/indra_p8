package com.indra.p8.service;

import com.indra.p8.DTOs.CrearAutorDTO;
import com.indra.p8.DTOs.CrearLibroDTO;
import com.indra.p8.model.*;

import java.util.Date;
import java.util.List;

public interface BibliotecaService {
    Autor crearAutor(CrearAutorDTO dto);
    Libro crearLibro(CrearLibroDTO dto);
    List<Copia> crearCopia(Long idLibro, int nCopias);
    boolean devolver(Long idPrestamo);
    String prestar(Long idLector, Long idCopia);
    List<Libro> getLibros();
    boolean repararCopia(Long idCopia);
    boolean deleteCopia(Long idCopia);
    List<Prestamo> getPrestamosLector(Long idLector);
}
