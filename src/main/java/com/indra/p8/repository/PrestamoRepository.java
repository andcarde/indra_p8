package com.indra.p8.repository;

import com.indra.p8.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findByLectorId(Long lectorId);
}
