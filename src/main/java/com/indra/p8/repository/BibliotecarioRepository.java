package com.indra.p8.repository;

import com.indra.p8.model.Bibliotecario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BibliotecarioRepository extends JpaRepository<Bibliotecario, Long> {
    Optional<Bibliotecario> findByUsername(String username);
}
