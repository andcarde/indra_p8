package com.indra.p8.service;

import com.indra.p8.model.Libro;
import com.indra.p8.repository.LibroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LibroServiceImp implements LibroService {

    @Autowired
    private final LibroRepository libroRepository;

    @Override
    public void agregarLibro(Libro libro, int cantidad) {

    }
    @Override
    public List<Libro> obtenerLibros() {
        return libroRepository.findAll();
    }
    @Override
    public void prestarLibro(Libro libro, Lector lector) {
        lector.prestar(id, new Date());
    }
}
