package com.indra.p8.repository;

import com.indra.p8.DTOs.LibroMasPrestadoDTO;
import com.indra.p8.model.Lector;
import com.indra.p8.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    List<Prestamo> findByCopiaId(Long idCopia);
    List<Prestamo> findByLectorId(Long idLector);

    @Query("""
           select new com.indra.p8.DTOs.LibroMasPrestadoDTO(
               p.copia.libro.id,
               p.copia.libro.titulo,
               count(p)
           )
           from Prestamo p
           group by p.copia.libro.id, p.copia.libro.titulo
           order by count(p) desc
           """)
    List<LibroMasPrestadoDTO> librosMasPrestados();

}
