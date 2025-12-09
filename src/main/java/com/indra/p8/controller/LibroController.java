package com.indra.p8.controller;


import com.indra.p8.DTOs.CrearLibroDTO;
import com.indra.p8.model.Libro;
import com.indra.p8.model.TipoLibro;
import com.indra.p8.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // viene en la dependencia WEB (spring-boot-starter-web) del pom
@RequestMapping("/biblioteca")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping("/libros")
    public List<Libro> getLibros() {
        return libroService.getLibros();
    }

    @PostMapping("/crearlibro")
    public void crearLibro(@RequestBody CrearLibroDTO dto) {
        libroService.crearLibro(dto);
    }

    @GetMapping("/getLibro{idLibro}")
    public ResponseEntity<Libro> getLibro(@PathVariable Long idLibro){
        Libro libro = libroService.getLibroById(idLibro);
        return ResponseEntity.ok(libro);
    }

    @DeleteMapping("/libro/{idLibro}/delete")
    public void deleteLibro(@PathVariable Long idLibro){libroService.deleteLibro(idLibro);}

    @PutMapping("/libro/{idLibro}/update")
    public void updateLibro(@PathVariable Long idLibro, @RequestBody CrearLibroDTO libro){
        libroService.updateLibro(idLibro,libro);
    }

    @GetMapping("/libro/autor/{idAutor}")
    public List<Libro> getLibrosByAutor(@PathVariable Long idAutor){
        return libroService.getLibrosAutor(idAutor);
    }

    @GetMapping("/libro/tipo/{tipo}")
    public List<Libro> getLibrosByGenero(@PathVariable TipoLibro tipoLibro){
        return libroService.getLibrosGeneros(tipoLibro);
    }

    @GetMapping("/libro/titulo/{tipo}")
    public List<Libro> getLibrosByTitulo(@PathVariable String titulo){
        return libroService.getLibrosTitulo(titulo);
    }
}
