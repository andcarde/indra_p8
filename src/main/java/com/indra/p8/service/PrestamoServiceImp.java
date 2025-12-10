package com.indra.p8.service;

import com.indra.p8.model.*;
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
        try {
            Prestamo prestamo = prestamoRepository.findById(idPrestamo).orElseThrow();
            Copia copia = prestamo.getCopia();
            Lector lector = prestamo.getLector();

            prestamo.setFin(LocalDate.now());
            copia.setEstado(EstadoCopia.BIBLIOTECA);

            // Gestión de multas
            LocalDate fin = prestamo.getFin();
            LocalDate limite = prestamo.getLimite();
            long diasRetraso = fin.toEpochDay() - limite.toEpochDay();

            if (diasRetraso > 0) {
                int diasMulta = (int) diasRetraso * 2;
                multaService.multar(lector.getId(), diasMulta);
            }
            prestamoRepository.save(prestamo);
            copiaRepository.save(copia);
            return new Error();
        } catch (Exception e) {
            System.out.println("Excepción saltada en idPrestamo");
            return new Error("Error al devolver copia");
        }
    }

    @Override
    public Error devolverByCopiaId(Long idCopia) {
        try{
            Copia copia = copiaRepository.findById(idCopia).orElseThrow();
            List<Prestamo> prestamos=  prestamoRepository.findByCopiaId(idCopia);
            Optional<Prestamo> prestamoActivoOpt = prestamos.stream()
                    .filter(p -> p.getFin() == null)
                    .sorted(
                            Comparator.comparing(Prestamo::getInicio).reversed())
                    .findFirst();

            if (prestamoActivoOpt.isEmpty()) {
                return new Error("El prestamo no existe");
            }
            Prestamo prestamoActivo = prestamoActivoOpt.get();
            Lector lector = prestamoActivo.getLector();

            prestamoActivo.setFin(LocalDate.now());
            copia.setEstado(EstadoCopia.BIBLIOTECA);

            // Gestión de multas
            LocalDate fin = prestamoActivo.getFin();
            LocalDate limite = prestamoActivo.getLimite();
            long diasRetraso = fin.toEpochDay() - limite.toEpochDay();

            if (diasRetraso > 0) {
                int diasMulta = (int) diasRetraso * 2;
                multaService.multar(lector.getId(), diasMulta);
            }
            prestamoRepository.save(prestamoActivo);
            copiaRepository.save(copia);
            return new Error();

        } catch (Exception e) {
            return new Error("Error al devolver copia");
        }
    }

    @Override
    public Error prestar(Long idLector, Long idCopia, LocalDate fechaLimite) {
        // · Validaciones

        if (idLector == null || idCopia == null)
            return new Error("BAD_REQUEST");

        Lector lector = lectorRepository.findById(idLector).orElseThrow();
        Copia copia = copiaRepository.findById(idCopia).orElseThrow();


        Multa ultimaMulta = lector.getMultas().stream()
                .filter(m -> m.getEstado() == EstadoMulta.PENDIENTE
                        || (m.getEstado() == EstadoMulta.PAGADA
                        && (m.getFFin() == null || !m.getFFin().isBefore(LocalDate.now()))))
                .max(Comparator.comparing(Multa::getFInicio, Comparator.nullsLast(Comparator.naturalOrder())))
                .orElse(null);


        // ACTIVE_MULTA: "No se puede prestar debido a que el lector tiene una multa activa."
        if (ultimaMulta != null)
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
