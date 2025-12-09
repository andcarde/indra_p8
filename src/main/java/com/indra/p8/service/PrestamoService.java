package com.indra.p8.service;

import com.indra.p8.model.Prestamo;

import java.time.LocalDate;
import java.util.List;

public interface PrestamoService {

    // Writers
    Error devolverByPrestamoId(Long idPrestamo);
    Error devolverByCopiaId(Long idCopia);
    Error prestar(Long idLector, Long idCopia, LocalDate fechaDevolucion);

    // Readers
    List<Prestamo> getPrestamosActivos();
    List<Prestamo> getPrestamosHistoricos();
    Resultado<List<Prestamo>> getPrestamosLector(Long idLector);
}
