package com.indra.p8.service;


import com.indra.p8.DTOs.CrearAutorDTO;
import com.indra.p8.DTOs.CrearLibroDTO;
import com.indra.p8.model.*;
import com.indra.p8.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BibliotecaServiceImp implements BibliotecaService {

    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private CopiaRepository copiaRepository;
    @Autowired
    private LectorRepository lectorRepository;
    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private MultaRepository multaRepository;
    @Autowired
    private PrestamoRepository prestamoRepository;


    @Override
    public Autor crearAutor(CrearAutorDTO dto) {
        if(autorRepository.existsByNombre(dto.getNombre())){
            throw new RuntimeException("El autor ya esta registrado");
        }
        Autor autor = new Autor();
        autor.setNombre(dto.getNombre());
        autor.setNacionalidad(dto.getNacionalidad());
        autor.setFechaNac(dto.getFechaNac());
        autorRepository.save(autor);
        return autor;
    }

    @Override
    public Libro crearLibro(CrearLibroDTO dto) {
        Long idAutor=dto.getIdAutor();
        Autor autor = autorRepository.findById(idAutor)
                .orElseThrow(() -> new RuntimeException("Autor no encontrado"));

        if(libroRepository.existsByTituloAndAutor(dto.getTitulo(),autor)){
            throw new RuntimeException("El libro ya esta registrado");
        }

        Libro libro = new Libro();
        libro.setTitulo(dto.getTitulo());
        libro.setTipo(dto.getTipo());
        libro.setEditorial(dto.getEditorial());
        libro.setAnyo(dto.getAnyo());
        libro.setAutor(autor);
        libroRepository.save(libro);
        return libro;
    }

    @Override
    public String crearCopia(Long idLibro, int nCopias) {

        List<Copia> copias = new ArrayList<>();

        Optional<Libro> optLibro = libroRepository.findById(idLibro);
        Libro libro;
        if (optLibro.isPresent())
            libro = optLibro.get();
        else
            return "Libro no encontrado";

        for (int i = 0; i< nCopias; i++){
            Copia copia = new Copia();
            copia.setLibro(libro);
            copia.setEstado(EstadoCopia.BIBLIOTECA);
            copiaRepository.save(copia);
            copias.add(copia);
        }

        return "Copias creadas correctamente";
    }

    @Override
    public boolean devolver(Long idPrestamo) {
        try {
            Prestamo prestamo = prestamoRepository.findById(idPrestamo).orElseThrow();
            Copia copia = prestamo.getCopia();
            Lector lector = prestamo.getLector();

            prestamo.setFin(LocalDate.now());
            prestamoRepository.save(prestamo);

            // Verificar retraso
            LocalDate inicio = prestamo.getInicio();
            LocalDate fin = prestamo.getFin();
            long diasPrestamo = fin.toEpochDay() - inicio.toEpochDay();

            if (diasPrestamo > 30) {
                long diasRetraso = diasPrestamo - 30;
                int diasMulta = (int) diasRetraso * 2;
                multar(lector, diasMulta);
            }
            copiaRepository.save(copia);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean devolverCopia(Long idCopia) {
        try{
            Copia copia = copiaRepository.findById(idCopia).orElseThrow();
            List<Prestamo> prestamos=  prestamoRepository.findByCopiaId(idCopia);
            Optional<Prestamo> ultimoPrestamoOp = prestamos.stream().filter(p -> p.getFin() != null)
                    .findFirst();
            if (!ultimoPrestamoOp.isPresent()) {
                return false;
            }
            Prestamo ultimoPrestamo = ultimoPrestamoOp.get();
            Lector lector = ultimoPrestamo.getLector();

            ultimoPrestamo.setFin(LocalDate.now());
            prestamoRepository.save(ultimoPrestamo);

            // Verificar retraso
            LocalDate inicio = ultimoPrestamo.getInicio();
            LocalDate fin = ultimoPrestamo.getFin();
            long diasPrestamo = fin.toEpochDay() - inicio.toEpochDay();

            if (diasPrestamo > 30) {
                long diasRetraso = diasPrestamo - 30;
                int diasMulta = (int) diasRetraso * 2;
                multar(lector, diasMulta);
            }
            copiaRepository.save(copia);
            return true;

    } catch (Exception e) {
        return false;
    }

    }

    @Override
    public String prestar(Long idLector, Long idCopia) {
        try {
            if (idLector == null || idCopia == null) {
                throw new Exception("illegal arguments");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Lector lector = lectorRepository.findById(idLector).orElseThrow();
        Copia copia = copiaRepository.findById(idCopia).orElseThrow();

        // Validaciones
        if (lector.getMulta() != null && lector.getMulta().getFFin().isAfter(LocalDate.now())) {
            return "No se puede prestar: lector tiene multa activa.";
        }
        if (copia.getEstado() != EstadoCopia.BIBLIOTECA) {
            return "No se puede prestar: copia no disponible.";
        }
        List<Prestamo> prestamos = getPrestamosLector(idLector);
        List<Prestamo> prestamosActivos = prestamos.stream().filter(p -> p.getFin()==null).toList();

        if (!prestamosActivos.isEmpty() && prestamosActivos.size() >= 3) {
            return "Demasiados prestamos activos.";
        }

        // Crear préstamo
        Prestamo prestamo = new Prestamo();
        prestamo.setInicio(LocalDate.now());
        prestamo.setLector(lector);
        prestamo.setCopia(copia);
        prestamoRepository.save(prestamo);
        copia.setEstado(EstadoCopia.PRESTADO);
        copiaRepository.save(copia);

        copia.setEstado(EstadoCopia.PRESTADO);
        copiaRepository.save(copia);

        return "Préstamo realizado con éxito.";
    }

    @Override
    public List<Libro> getLibros() {
        return libroRepository.findAll();
    }

    @Override
    public List<Prestamo> getPrestamosLector(Long idLector){
        if(!lectorRepository.existsById(idLector)){
            throw new RuntimeException();
        }
        Optional<Lector> lector = lectorRepository.findById(idLector);
        return prestamoRepository.findByLector(lector);

    }

    private void multar(Lector lector, int dias) {
        Multa multa = new Multa();
        multa.setFInicio(LocalDate.now());
        multa.setFFin(LocalDate.now().plusDays(dias));
        multa.setLector(lector);
        multaRepository.save(multa);

        lector.setMulta(multa);
        lectorRepository.save(lector);
    }

    @Override
    public boolean repararCopia(Long idCopia){
        try {
            Optional<Copia> optCopia = copiaRepository.findById(idCopia);
            if (optCopia.isPresent()) {
                Copia copia = optCopia.get();
                copia.setEstado(EstadoCopia.REPARACION);
                copiaRepository.save(copia);
                return true;
            }
        } catch (Exception ignored) {}
        return false;
    }

    @Override
    public boolean deleteCopia(Long idCopia){
        try {
            Optional<Copia> copia = copiaRepository.findById(idCopia);
            if (copia.isPresent()) {
                copiaRepository.delete(copia.get());
                return true;
            }
        } catch (Exception ignored) {}
        return false;
    }

    /* Se utilizará la llamada al servidor
    * /getlibro/{idLibro} mediante GET.
    * Si existe se devolvera un objeto Libro { titulo:String,
    * nombreAutor:String, copias:List<Copia> }
    * Copia { idCopia:Long, estado:String }.
    * El estado será "prestado", "retraso", "biblioteca", "reparacion".
    * En caso de que no exista se devuelve null.
    */
    @Override
    public Libro getLibroById(Long idLibro){
        Libro libro = libroRepository.findById(idLibro).
                orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        return libro;
    }

    @Override
    public List<Copia> getCopiasByLibroId(Long idLibro){
        Libro libro = libroRepository.findById(idLibro).
                orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        return copiaRepository.findByLibro(libro);
    }
    @Override
    public Copia getCopiaById(Long idCopia){
        Copia copia= copiaRepository.findById(idCopia).orElseThrow(()-> new RuntimeException("Copia no encontrada"));
        return copia;
    }
}
