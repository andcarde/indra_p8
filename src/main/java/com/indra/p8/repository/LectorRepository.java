package com.indra.p8.repository;

import com.indra.p8.model.Bibliotecario;
import com.indra.p8.model.Lector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LectorRepository extends JpaRepository<Lector,Long> {
    Optional<Lector> findByemail(String email);
}
