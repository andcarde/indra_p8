package com.indra.p8.service;

import com.indra.p8.DTOs.CrearLibroDTO;
import com.indra.p8.model.Libro;
import com.indra.p8.model.TipoLibro;

import java.util.List;

public interface LibroService {
    Libro crearLibro(CrearLibroDTO dto);
    void deleteLibro(Long idLibro);
    void updateLibro(Long idLibro, CrearLibroDTO libro);
    Libro getLibroById (Long idLibro);
    List<Libro> getLibros();
    List<Libro> getLibrosAutor(Long idAutor);
    List<Libro> getLibrosGeneros(TipoLibro tipoLibro);
    List<Libro> getLibrosTitulo(String titulo);

}
