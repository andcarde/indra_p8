package com.indra.p8.controller;

import com.indra.p8.DTOs.CrearAutorDTO;
import com.indra.p8.DTOs.CrearLectorDTO;
import com.indra.p8.model.Lector;
import com.indra.p8.service.LectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/biblioteca/lectores")
public class LectorController {

    private final LectorService lectorService;

    public LectorController(LectorService lectorService) {
        this.lectorService = lectorService;
    }

    // LISTAR TODOS
    @GetMapping
    public List<Lector> getLectores() {
        return lectorService.getLectores();
    }

    // OBTENER UNO POR ID
    @GetMapping("/{idLector}")
    public ResponseEntity<Lector> getLector(@PathVariable Long idLector) {
        Lector lector = lectorService.getLector(idLector);
        if (lector == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lector);
    }

    // CREAR
    @PostMapping
    public ResponseEntity<Lector> crearLector(@RequestBody CrearLectorDTO dto) {
        Lector creado = lectorService.crearLector(dto);
        return ResponseEntity.ok(creado);
    }

    // ACTUALIZAR
    @PutMapping("/{idLector}")
    public ResponseEntity<?> updateLector(@PathVariable Long idLector,
                                             @RequestBody CrearLectorDTO lectorDto) {
        try {
            lectorService.updateLector(idLector, lectorDto);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    // ELIMINAR
    @DeleteMapping("/{idLector}")
    public ResponseEntity<Void> deleteLector(@PathVariable Long idLector) {
        lectorService.deleteLector(idLector);
        return ResponseEntity.noContent().build();
    }
}

