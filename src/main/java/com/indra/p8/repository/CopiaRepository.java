package com.indra.p8.repository;

import com.indra.p8.model.Copia;
import com.indra.p8.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CopiaRepository extends JpaRepository<Copia,Long> {
    List<Copia> findByLibro(Libro libro);
}
