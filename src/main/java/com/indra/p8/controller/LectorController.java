package com.indra.p8.controller;

import com.indra.p8.DTOs.CrearAutorDTO;
import com.indra.p8.DTOs.CrearLectorDTO;
import com.indra.p8.model.Lector;
import com.indra.p8.service.LectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/biblioteca")
public class LectorController {
    @Autowired
    private LectorService lectorService;

    @GetMapping("/lector/crear/{idLector}")
    public ResponseEntity<Lector> getLector(@PathVariable Long idLector){
        Lector lector = lectorService.getLector(idLector);
        return ResponseEntity.ok(lector);
    }

    @PostMapping("/lector/crear/{idLector}")
    public void crearLector(@RequestBody CrearLectorDTO dto) {
        lectorService.crearLector(dto);
    }

    @DeleteMapping("/lector/eliminar/{idLector}")
    public void deleteLector(@PathVariable Long idLector){lectorService.deleteLector(idLector);}

    @PutMapping("/lector/editar/{idLector}")
    public void updateLector(@PathVariable Long idLector, @RequestBody CrearLectorDTO lector){
        lectorService.updateLector(idLector,lector);
    }
}
