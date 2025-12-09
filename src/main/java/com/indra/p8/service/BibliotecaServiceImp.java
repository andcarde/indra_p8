package com.indra.p8.service;

import com.indra.p8.DTOs.CrearAutorDTO;
import com.indra.p8.model.*;
import com.indra.p8.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BibliotecaServiceImp implements BibliotecaService {

    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private CopiaRepository copiaRepository;
    @Autowired
    private LibroRepository libroRepository;

    @Override
    public Autor crearAutor(CrearAutorDTO dto) {
        if(autorRepository.existsByNombre(dto.getNombre())){
            throw new RuntimeException("El autor ya esta registrado");
        }
        Autor autor = new Autor();
        autor.setNombre(dto.getNombre());
        autor.setNacionalidad(dto.getNacionalidad());
        autor.setFechaNac(dto.getFechaNac());
        autorRepository.save(autor);
        return autor;
    }

    @Override
    public String crearCopia(Long idLibro, int nCopias) {

        List<Copia> copias = new ArrayList<>();

        Optional<Libro> optLibro = libroRepository.findById(idLibro);
        Libro libro;
        if (optLibro.isPresent())
            libro = optLibro.get();
        else
            return "Libro no encontrado";

        for (int i = 0; i< nCopias; i++){
            Copia copia = new Copia();
            copia.setLibro(libro);
            copia.setEstado(EstadoCopia.BIBLIOTECA);
            copiaRepository.save(copia);
            copias.add(copia);
        }

        return "Copias creadas correctamente";
    }


    @Override
    public boolean repararCopia(Long idCopia){
        try {
            Optional<Copia> optCopia = copiaRepository.findById(idCopia);
            if (optCopia.isPresent()) {
                Copia copia = optCopia.get();
                copia.setEstado(EstadoCopia.REPARACION);
                copiaRepository.save(copia);
                return true;
            }
        } catch (Exception ignored) {}
        return false;
    }

    @Override
    public void deleteCopia(Long idCopia){
        copiaRepository.deleteById(idCopia);
    }

    @Override
    public boolean mandarBibliotecaCopia(Long idCopia){
        try {
            Optional<Copia> optCopia = copiaRepository.findById(idCopia);
            if (optCopia.isPresent()) {
                Copia copia = optCopia.get();
                copia.setEstado(EstadoCopia.BIBLIOTECA);
                copiaRepository.save(copia);
                return true;
            }
        } catch (Exception ignored) {}
        return false;
    }

    /* Se utilizará la llamada al servidor
    * /getlibro/{idLibro} mediante GET.
    * Si existe se devolvera un objeto Libro { titulo:String,
    * nombreAutor:String, copias:List<Copia> }
    * Copia { idCopia:Long, estado:String }.
    * El estado será "prestado", "retraso", "biblioteca", "reparacion".
    * En caso de que no exista se devuelve null.
    */

    @Override
    public List<Copia> getCopiasByLibroId(Long idLibro){
        Libro libro = libroRepository.findById(idLibro).
                orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        return copiaRepository.findByLibro(libro);
    }

    @Override
    public Copia getCopiaById(Long idCopia){
        Copia copia= copiaRepository.findById(idCopia).orElseThrow(()-> new RuntimeException("Copia no encontrada"));
        return copia;
    }
}