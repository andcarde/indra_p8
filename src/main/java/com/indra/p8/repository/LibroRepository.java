package com.indra.p8.repository;

import com.indra.p8.model.Autor;
import com.indra.p8.model.TipoLibro;
import org.springframework.data.jpa.repository.JpaRepository;
import com.indra.p8.model.Libro;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    boolean existsByTituloAndAutor(String titulo, Autor autor);

    boolean existsByIsbn(int isbn);

    List<Libro> findByTipo(TipoLibro tipoLibro);
    List<Libro> findByAutorId(Long autorId);
    List<Libro> findByTituloContainingIgnoreCase(String fragmento);
}
