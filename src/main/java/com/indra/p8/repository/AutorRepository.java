package com.indra.p8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.indra.p8.model.Autor;

public interface AutorRepository extends JpaRepository<Autor,Long> {
    boolean existsByNombre(String nombre);
}
