package com.indra.p8.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "lectores")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lector {

    @Autowired
    private EntityManager em;

    @Column(name = "n_socio")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long nSocio;

    @Column
    private String nombre;

    @Column
    private String telefono;

    @Column
    private String direccion;

    @OneToMany(mappedBy = "lector_id")
    private List<Prestamo> prestamos;

    public void devolver(long id, Date fechaAct) {
        Copia copia = service.getCopia(id).orElse(null);
        if (copia != null) {
            Date fechaActual = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaActual);
            calendar.add(Calendar.DAY_OF_MONTH, 30); // suma 30 días
            Date fechaDevolucion = calendar.getTime();
            Prestamo prestamo = new Prestamo();
            prestamo.setLector(this);
            prestamo.setCopia(copia);
            prestamo.setInicio(fechaActual);
            prestamo.setFin(fechaDevolucion);
            em.persist(prestamo);
        }
    }
    public void prestar(long id, Date fechaAct) {
        Copia copia = service.getCopia(id).orElse(null);
        if (copia != null) {
            Date fechaActual = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaActual);
            calendar.add(Calendar.DAY_OF_MONTH, 30); // suma 30 días
            Date fechaDevolucion = calendar.getTime();
            Prestamo prestamo = new Prestamo();
            prestamo.setLector(this);
            prestamo.setCopia(copia);
            prestamo.setInicio(fechaActual);
            prestamo.setFin(fechaDevolucion);
        }
    }
    private void multar(int dias) {

    }
}
