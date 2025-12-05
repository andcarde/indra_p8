package com.indra.p8.controller;


import com.indra.p8.DTOs.CrearAutorDTO;
import com.indra.p8.DTOs.CrearLibroDTO;
import com.indra.p8.model.*;
import com.indra.p8.service.BibliotecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // viene en la dependencia WEB (spring-boot-starter-web) del pom
@RequestMapping("/biblioteca")
public class BibliotecaController {

    @Autowired
    private BibliotecaService service;

    @GetMapping("/libros")
    public List<Libro> getLibros() {
        return service.getLibros();
    }

    @PostMapping("/crearAutor")
    public void crearAutor(@RequestBody CrearAutorDTO dto) {
        service.crearAutor(dto);
    }

    @PostMapping("/crearlibro")
    public void crearLibro(@RequestBody CrearLibroDTO dto) {
        service.crearLibro(dto);
    }

    @PostMapping("/crearcopia{idLibro}")
    public List<Copia> crearCopia(@PathVariable Long idLibro, @RequestParam int ncopias) {
        List<Copia> copias;
        copias = service.crearCopia(idLibro, ncopias);
        return copias;
    }
    @GetMapping("/prestamosLector/{idLector}")
    public ResponseEntity<List<Prestamo>> getPrestamosLector(@PathVariable Long idLector){
        return ResponseEntity.ok(service.getPrestamosLector(idLector));
    }

    @PutMapping("/devolucion{idPrestamo}")
    public ResponseEntity<Boolean> devolver(@PathVariable Long idPrestamo) {
        boolean isOk = service.devolver(idPrestamo);
        return ResponseEntity.ok(isOk);
    }

    @PutMapping("/prestar/{idLector}/{idCopia}")
    public ResponseEntity<String> prestar(@PathVariable Long idLector,
                                          @PathVariable Long idCopia) {
        String responseText = service.prestar(idLector, idCopia);
        return ResponseEntity.ok(responseText);
    }

    @PutMapping("/devolver/{idCopia}")
    public ResponseEntity<Boolean> prestarCopia(@PathVariable Long idCopia){
        boolean isOk=service.devolverCopia(idCopia);
        return ResponseEntity.ok(isOk);
    }

    @GetMapping("/getLibro{idLibro}")
    public ResponseEntity<Libro> getLibro(@PathVariable Long idLibro){
        Libro libro = service.getLibroById(idLibro);
        return ResponseEntity.ok(libro);
    }
    /*

    @GetMapping("/getLibro/{idLibro}")
    public String mostrarLibro(@PathVariable Long idLibro, Model model) {
        Libro libro = service.getLibroById(idLibro);
        model.addAttribute("libro", libro);
        return "libro"; // Thymeleaf buscará libro.html en /templates
    }
     */

    @GetMapping("/getCopias{idLibro}")
    public ResponseEntity<List<Copia>> getCopiasByLibro(@PathVariable Long idLibro){
        return ResponseEntity.ok(service.getCopiasByLibroId(idLibro));
    }

    @GetMapping("/getCopia{idCopia}")
    public ResponseEntity<Copia> getCopia(@PathVariable Long idCopia){
        return ResponseEntity.ok(service.getCopiaById(idCopia));
    }

    @PutMapping("/copia/{idCopia}/reparar")
    public void repararCopia(@PathVariable Long idCopia){
        service.repararCopia(idCopia);
    }

    @DeleteMapping("/copia/{idCopia}/baja")
    public void bajaCopia(@PathVariable Long idCopia){
        service.deleteCopia(idCopia);
    }

}
