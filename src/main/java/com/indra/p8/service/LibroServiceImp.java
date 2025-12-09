package com.indra.p8.service;

import com.indra.p8.DTOs.CrearLibroDTO;
import com.indra.p8.model.Autor;
import com.indra.p8.model.Libro;
import com.indra.p8.model.TipoLibro;
import com.indra.p8.repository.AutorRepository;
import com.indra.p8.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroServiceImp implements LibroService{

    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private AutorRepository autorRepository;

    @Override
    public Libro crearLibro(CrearLibroDTO dto) {
        Long idAutor=dto.getIdAutor();
        Autor autor = autorRepository.findById(idAutor)
                .orElseThrow(() -> new RuntimeException("Autor no encontrado"));

        if(libroRepository.existsByTituloAndAutor(dto.getTitulo(),autor)){
            throw new RuntimeException("El libro ya esta registrado");
        }
        if(libroRepository.existsByIsbn(dto.getIsbn())){
            throw new RuntimeException("El libro ya esta registrado");
        }
        if(dto.getAnyo()<1500){
            throw new RuntimeException("El libro es demasiado antiguo");
        }
        Libro libro = new Libro();
        libro.setTitulo(dto.getTitulo());
        libro.setTipo(dto.getTipo());
        libro.setEditorial(dto.getEditorial());
        libro.setIsbn(dto.getIsbn());
        libro.setAnyo(dto.getAnyo());
        libro.setAutor(autor);
        libroRepository.save(libro);
        return libro;
    }

    @Override
    public Libro getLibroById(Long idLibro){
        Libro libro = libroRepository.findById(idLibro).
                orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        return libro;
    }

    @Override
    public void deleteLibro(Long idLibro){
        libroRepository.deleteById(idLibro);
    }

    @Override
    public void updateLibro(Long idLibro, CrearLibroDTO dto) {
        Long idAutor=dto.getIdAutor();
        Autor autor = autorRepository.findById(idAutor)
                .orElseThrow(() -> new RuntimeException("Autor no encontrado"));

        if(libroRepository.existsByTituloAndAutor(dto.getTitulo(),autor)){
            throw new RuntimeException("El libro ya esta registrado");
        }
        if(libroRepository.existsByIsbn(dto.getIsbn())){
            throw new RuntimeException("El libro ya esta registrado");
        }
        if(dto.getAnyo()<1500){
            throw new RuntimeException("El libro es demasiado antiguo");
        }
        try{
            Optional<Libro> optlibro = libroRepository.findById(idLibro);
            if(optlibro.isPresent()){
                Libro libro = optlibro.get();
                libro.setTitulo(dto.getTitulo());
                libro.setEditorial(dto.getEditorial());
                libro.setIsbn(dto.getIsbn());
                libro.setAnyo(dto.getAnyo());
                libro.setTipo(dto.getTipo());
                libro.setAutor(autor);
                libroRepository.save(libro);
            }

        } catch (Exception e){}

    }
    @Override
    public List<Libro> getLibros() {
        return libroRepository.findAll();
    }

    @Override
    public List<Libro> getLibrosAutor(Long idAutor) {
        return libroRepository.findByAutorId(idAutor);
    }
    @Override
    public List<Libro> getLibrosGeneros(TipoLibro tipoLibro) {
        return libroRepository.findByTipo(tipoLibro);
    }
    @Override
    public List<Libro> getLibrosTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo);
    }
}
