package com.indra.p8.service;

import com.indra.p8.DTOs.CrearLectorDTO;
import com.indra.p8.model.Lector;
import com.indra.p8.repository.LectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LectorServiceImp implements LectorService {
    @Autowired
    private LectorRepository lectorRepository;

    @Override
    public Lector crearLector(CrearLectorDTO dto){
        Lector socio = lectorRepository.findByemail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("No existe el socio"));

        Lector lector = new Lector();
        lector.setNombre(socio.getNombre());
        lector.setApellido(socio.getApellido());
        lector.setTelefono(socio.getTelefono());
        lector.setEmail(socio.getEmail());
        lector.setEstado(socio.getEstado());
        lectorRepository.save(lector);
        return lector;
    }

    @Override
    public void updateLector(Long idLector, CrearLectorDTO dto) {

        Lector lector = lectorRepository.findById(idLector)
                .orElseThrow(() -> new RuntimeException("No existe el socio"));

        lector.setNombre(dto.getNombre());
        lector.setApellido(dto.getApellido());
        lector.setTelefono(dto.getTelefono());
        lector.setEmail(dto.getEmail());
        lector.setEstado(dto.getEstado());
        lectorRepository.save(lector);
    }

    @Override
    public void deleteLector(Long idLector){
        lectorRepository.deleteById(idLector);
    }

    @Override
    public Lector getLector(Long idLector){
        Lector lector = lectorRepository.findById(idLector)
                .orElseThrow(() -> new RuntimeException("No existe el socio"));

        return lector;
    }

}
