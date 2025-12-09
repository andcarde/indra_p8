package com.indra.p8.controller;

import com.indra.p8.DTOs.CrearPrestamoDTO;
import com.indra.p8.model.Prestamo;
import com.indra.p8.service.Error;
import com.indra.p8.service.PrestamoService;
import com.indra.p8.service.Resultado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/biblioteca")
public class PrestamoController {

    /*
    // Writers
    Resultado devolverByPrestamoId(Long idPrestamo);
    Resultado devolverByCopiaId(Long idCopia);
    Resultado prestar(Long idLector, Long idCopia, LocalDate fechaDevolucion);

    // Readers
    List<Prestamo> getPrestamosActivos();
    List<Prestamo> getPrestamosHistoricos();
    Resultado getPrestamosLector(Long idLector);
    */
    @Autowired
    private PrestamoService service;

    @GetMapping("/prestamosLector/{idLector}")
    public ResponseEntity<?> getPrestamosLector(@PathVariable Long idLector) {
        Resultado<List<Prestamo>> resultado = service.getPrestamosLector(idLector);

        if (resultado.hasError()) {
            return ResponseEntity
                    .badRequest()
                    .body(resultado.getError());
        }

        return ResponseEntity.ok(resultado.getResultado());
    }

    @PostMapping("/prestar")
    public ResponseEntity<?> prestar(@RequestBody CrearPrestamoDTO body) {
        Long idLector = body.getIdLector();
        Long idCopia = body.getIdCopia();
        LocalDate fechaDevolucion =  body.getFechaDevolucion();

        Error error = service.prestar(idLector, idCopia, fechaDevolucion);
        if (error.hasError()) {
            return ResponseEntity
                    .badRequest()
                    .body(error.getError());
        }

        return ResponseEntity.ok("");
    }

    @PutMapping("/devolucion{idPrestamo}")
    public ResponseEntity<?> devolver(@PathVariable Long idPrestamo) {
        Error error = service.devolverByPrestamoId(idPrestamo);

        if (error.hasError())
            return ResponseEntity
                    .badRequest()
                    .body(error.getError());

        return ResponseEntity.ok("");
    }

    @PutMapping("/devolver/{idCopia}")
    public ResponseEntity<?> devolverCopia(@PathVariable Long idCopia){
        Error error = service.devolverByCopiaId(idCopia);

        if (error.hasError())
            return ResponseEntity
                    .badRequest()
                    .body(error.getError());

        return ResponseEntity.ok("");
    }
}