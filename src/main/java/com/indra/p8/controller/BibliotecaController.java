package com.indra.p8.controller;


import com.indra.p8.DTOs.CrearAutorDTO;
import com.indra.p8.DTOs.CrearLectorDTO;
import com.indra.p8.DTOs.CrearLibroDTO;
import com.indra.p8.DTOs.CrearPrestamoDTO;
import com.indra.p8.model.*;
import com.indra.p8.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // viene en la dependencia WEB (spring-boot-starter-web) del pom
@RequestMapping("/biblioteca")
public class BibliotecaController {

    @Autowired
    private BibliotecaService service;

    @Autowired
    private MultaService multaService;

    @PostMapping("/crearAutor")
    public void crearAutor(@RequestBody CrearAutorDTO dto) {
        service.crearAutor(dto);
    }

    @PostMapping("/crearcopia{idLibro}")
    public ResponseEntity<String> crearCopia(@PathVariable Long idLibro, @RequestParam int ncopias) {
        String message = service.crearCopia(idLibro, ncopias);
        return ResponseEntity.ok(message);
    }

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

    @PutMapping("/copia/{idCopia}/biblioteca")
    public void mandarBibliotecaCopia(@PathVariable Long idCopia){
        service.mandarBibliotecaCopia(idCopia);
    }

    @PutMapping("/pagar_multa/{multaId}")
    public void pagarMulta(@PathVariable Long multaId){
        multaService.pagarMulta(multaId);
    }
    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]> exportPdf() {
        byte[] bytes = multaService.exportarMultasPdf();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=multas.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);
    }

}
