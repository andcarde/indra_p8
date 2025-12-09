package com.indra.p8.repository;

import com.indra.p8.model.Lector;
import com.indra.p8.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findByLector(Optional<Lector> lector);
    List<Prestamo> findByCopiaId(Long idCopia);
    List<Prestamo> findByLectorId(Long idLector);
}
