package com.indra.p8.service;

import com.indra.p8.model.Copia;
import com.indra.p8.model.EstadoCopia;
import com.indra.p8.model.Lector;
import com.indra.p8.model.Prestamo;
import com.indra.p8.repository.CopiaRepository;
import com.indra.p8.repository.LectorRepository;
import com.indra.p8.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class PrestamoServiceImp implements PrestamoService {

    private static final int MAX_PRESTAMOS_PER_LECTOR = 3;

    @Autowired
    private PrestamoRepository prestamoRepository;
    @Autowired
    private CopiaRepository copiaRepository;
    @Autowired
    private MultaService multaService;
    @Autowired
    private LectorRepository lectorRepository;

    @Override
    public Error devolverByPrestamoId(Long idPrestamo) {
        Optional<Prestamo> optionalPrestamo = prestamoRepository.findById(idPrestamo);
        if (optionalPrestamo.isEmpty())
            return new Error("PRESTAMO_NOT_EXISTS");
        Prestamo prestamo = optionalPrestamo.get();
        return devolver(prestamo);
    }

    private Error devolver(Prestamo prestamo) {
        try {
            Copia copia = prestamo.getCopia();
            copia.setEstado(EstadoCopia.BIBLIOTECA);
            copiaRepository.save(copia);
            prestamo.setFin(LocalDate.now());
            prestamoRepository.save(prestamo);

            // Gestión de multas
            LocalDate fin = prestamo.getFin();
            LocalDate limite = prestamo.getLimite();
            long diasRetraso = fin.toEpochDay() - limite.toEpochDay();

            if (diasRetraso > 0) {
                Lector lector = prestamo.getLector();
                int diasMulta = (int) diasRetraso * 2;
                multaService.multar(lector, diasMulta);
            }

            return new Error();
        } catch (Exception e) {
            return new Error("DATABASE_ERROR");
        }
    }

    @Override
    public Error devolverByCopiaId(Long idCopia) {
        Optional<Copia> optionalCopia = copiaRepository.findById(idCopia);
        if (optionalCopia.isEmpty())
            return new Error("COPIA_NOT_EXIST");

        List<Prestamo> prestamos = prestamoRepository.findByCopiaId(idCopia);
        Optional<Prestamo> optionalPrestamo = prestamos.stream()
                .filter(p -> p.getFin() == null)
                .max(Comparator.comparing(Prestamo::getInicio));

        if (optionalPrestamo.isEmpty())
            return new Error("PRESTAMO_NOT_EXIST");

        Prestamo prestamo = optionalPrestamo.get();
        return devolver(prestamo);
    }

    @Override
    public Error prestar(Long idLector, Long idCopia, LocalDate fechaLimite) {
        // · Validaciones

        if (idLector == null || idCopia == null)
            return new Error("BAD_REQUEST");

        Lector lector = lectorRepository.findById(idLector).orElseThrow();
        Copia copia = copiaRepository.findById(idCopia).orElseThrow();


        // ACTIVE_MULTA: "No se puede prestar debido a que el lector tiene una multa activa."
        if (lector.getMulta() != null && lector.getMulta().getFFin().isAfter(LocalDate.now()))
            return new Error("ACTIVE_MULTA");

        // ACTIVE_MULTA: "La copia no está disponible."
        if (copia.getEstado() != EstadoCopia.BIBLIOTECA)
            return new Error("NOT_AVAILABLE_COPY");

        List<Prestamo> prestamos = prestamoRepository.findByLectorId(idLector);
        List<Prestamo> prestamosActivos = prestamos.stream().filter(p -> p.getFin()==null).toList();

        // TOO_MANY_PRESTAMOS: "El lector ha alcanzado el límite de préstamos activos."
        if (!prestamosActivos.isEmpty() && prestamosActivos.size() >= MAX_PRESTAMOS_PER_LECTOR)
            return new Error("TOO_MANY_PRESTAMOS");

        // · Creación del préstamo

        Prestamo prestamo = new Prestamo();
        prestamo.setInicio(LocalDate.now());
        prestamo.setLimite(fechaLimite);
        prestamo.setLector(lector);
        prestamo.setCopia(copia);
        prestamoRepository.save(prestamo);

        copia.setEstado(EstadoCopia.PRESTADO);
        copiaRepository.save(copia);

        return new Error();
    }

    @Override
    public List<Prestamo> getPrestamosActivos() {
        return prestamoRepository
                .findAll()
                .stream()
                .filter(p -> p.getFin() == null)
                .toList();
    }

    @Override
    public List<Prestamo> getPrestamosHistoricos() {
        return prestamoRepository
                .findAll()
                .stream()
                .filter(p -> p.getFin() != null)
                .toList();
    }

    @Override
    public Resultado<List<Prestamo>> getPrestamosLector(Long idLector) {
        if (!lectorRepository.existsById(idLector))
            return new Resultado<List<Prestamo>>("LECTOR_NOT_EXIST");

        return new Resultado<List<Prestamo>>(prestamoRepository.findByLectorId(idLector));
    }
}
