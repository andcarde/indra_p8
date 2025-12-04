package com.indra.p8.repository;

import com.indra.p8.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import com.indra.p8.model.Libro;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    boolean existsByTituloAndAutor(String titulo, Autor autor);
}
