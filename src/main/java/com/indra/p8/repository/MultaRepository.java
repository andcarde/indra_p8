package com.indra.p8.repository;

import com.indra.p8.model.EstadoMulta;
import com.indra.p8.model.Multa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MultaRepository extends JpaRepository<Multa,Long> {
    List<Multa> findByEstado (EstadoMulta estado);
}
