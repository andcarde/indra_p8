package com.indra.p8.service;

import com.indra.p8.model.Libro;

public interface LibroService {
    void agregarLibro(Libro libro, int cantidad);
    List<Libro> obtenerLibros();
    void prestarLibro(Libro libro, Usuario usuario);
}
