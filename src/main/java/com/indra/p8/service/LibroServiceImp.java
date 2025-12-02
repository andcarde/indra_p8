package com.indra.p8.service;

import com.indra.p8.model.Libro;
import com.indra.p8.repository.LibroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LibroServiceImp implements LibroService{
    private final LibroRepository libroRepository;

    @Override
    void agregarLibro(Libro libro, int cantidad){

    }
    @Override
    List<Libro> obtenerLibros(){

    }
    @Override
    void prestarLibro(Libro libro, Usuario usuario){

    }
}
