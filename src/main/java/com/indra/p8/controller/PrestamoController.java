package com.indra.p8.controller;

import com.indra.p8.DTOs.CrearPrestamoDTO;
import com.indra.p8.DTOs.MostrarPrestamoDTO;
import com.indra.p8.model.Prestamo;
import com.indra.p8.service.Error;
import com.indra.p8.service.PrestamoService;
import com.indra.p8.service.Resultado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/biblioteca")
public class PrestamoController {

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

        List<Prestamo> prestamos = resultado.getResultado();
        List<MostrarPrestamoDTO> prestamosDTO = transformToDTO(prestamos);
        return ResponseEntity.ok(prestamosDTO);
    }

    private List<MostrarPrestamoDTO> transformToDTO(List<Prestamo> prestamos) {
        return prestamos.stream()
                .map(p -> transformToDTO(p))
                .collect(Collectors.toList());
    }

    private MostrarPrestamoDTO transformToDTO(Prestamo prestamo) {
        MostrarPrestamoDTO dto = new MostrarPrestamoDTO();
        dto.setIdCopia(prestamo.getCopia().getId());
        dto.setIdLector(prestamo.getLector().getId());
        dto.setInicio(prestamo.getInicio());
        dto.setLimite(prestamo.getLimite());
        dto.setFin(prestamo.getFin());
        return dto;
    }

    @GetMapping("/prestamosActivos")
    public ResponseEntity<?> getPrestamosActivos() {
        List<Prestamo> prestamos = service.getPrestamosActivos();
        List<MostrarPrestamoDTO> prestamosDTO = transformToDTO(prestamos);
        return ResponseEntity.ok(prestamosDTO);
    }

    @GetMapping("/prestamosHistoricos")
    public ResponseEntity<?> getPrestamosHistoricos() {
        List<Prestamo> prestamos = service.getPrestamosHistoricos();
        List<MostrarPrestamoDTO> prestamosDTO = transformToDTO(prestamos);
        return ResponseEntity.ok(prestamosDTO);
    }

    @PostMapping("/prestar")
    public ResponseEntity<?> prestar(@RequestBody CrearPrestamoDTO body) {
        Long idLector = body.getIdLector();
        Long idCopia = body.getIdCopia();
        LocalDate fechaLimite =  body.getFechaLimite();

        Error error = service.prestar(idLector, idCopia, fechaLimite);
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