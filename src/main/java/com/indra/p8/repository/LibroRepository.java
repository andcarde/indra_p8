package com.indra.p8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.indra.p8.model.Libro;

public interface LibroRepository extends JpaRepository<Libro,Long> {}
