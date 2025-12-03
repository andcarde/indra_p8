package com.indra.p8.controller;


import com.indra.p8.model.*;
import com.indra.p8.service.BibliotecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController // viene en la dependencia WEB (spring-boot-starter-web) del pom
@RequestMapping("/biblioteca")
public class BibliotecaController {

    @GetMapping("/biblioteca")
    public String getBibliotecaPage() {
        return "biblioteca";
    }

    @Autowired
    private BibliotecaService service;

    @GetMapping("/")
    public List<Libro> getLibros() {
        return service.getLibros();
    }

    @PostMapping("/crearAutor")
    public void crearAutor(@RequestBody Autor autor) {
        service.crearAutor(autor);
    }

    @PostMapping("/crearlibro")
    public void crearLibro(@RequestBody Libro libro) {
        service.crearLibro(libro);
    }
    @PostMapping("/crearcopia")
    public List<Copia> crearCopia(@RequestBody Long idLibro, @RequestBody int ncopias) {
        List<Copia> copias;
        copias = service.crearCopia(idLibro, ncopias);
        return copias;
    }
    @GetMapping("/prestamosLector/{idLector}")
    public ResponseEntity<List<Prestamo>> getPrestamosLector(@PathVariable Long idLector){
        return ResponseEntity.ok(service.getPrestamosLector(idLector));
    }

    @PutMapping("/devolucion")
    public ResponseEntity<Boolean> devolver(@RequestBody Long idPrestamo) {
        boolean isOk = service.devolver(idPrestamo);
        return ResponseEntity.ok(isOk);
    }

    @PutMapping("/prestar/{idLector}{idCopia}")
    public ResponseEntity<String> prestar(@PathVariable Long idLector, @PathVariable Long idCopia) {
        String responseText = service.prestar(idLector, idCopia);
        return ResponseEntity.ok(responseText);
    }
}
