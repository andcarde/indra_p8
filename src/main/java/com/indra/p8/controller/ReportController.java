package com.indra.p8.controller;

import com.indra.p8.DTOs.LibroMasPrestadoDTO;
import com.indra.p8.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/libros_mas_prestados")
    public ResponseEntity<List<LibroMasPrestadoDTO>> librosMasPrestados(){
        List<LibroMasPrestadoDTO> libros = reportService.librosMasPrestados();
        return ResponseEntity.ok(libros);
    }
}
