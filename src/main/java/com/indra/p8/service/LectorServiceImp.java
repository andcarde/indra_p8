package com.indra.p8.service;

import com.indra.p8.DTOs.CrearLectorDTO;
import com.indra.p8.model.Lector;
import com.indra.p8.repository.LectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LectorServiceImp implements LectorService {
    @Autowired
    private LectorRepository lectorRepository;

    @Override
    public Lector crearLector(CrearLectorDTO dto){
        Lector lector = new Lector();
        lector.setNombre(dto.getNombre());
        lector.setApellido(dto.getApellido());
        lector.setTelefono(dto.getTelefono());
        lector.setEmail(dto.getEmail());
        lector.setEstado(dto.getEstado());
        lector.setDireccion(dto.getDireccion());
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
        lector.setDireccion(dto.getDireccion());
        lectorRepository.save(lector);
    }

    @Override
    public void deleteLector(Long idLector){
        lectorRepository.deleteById(idLector);
    }

    @Override
    public List<Lector> getLectores() {
        List<Lector> lectores = new ArrayList();
        lectorRepository.findAll().forEach(lectores::add);
        return lectores;
    }

    @Override
    public Lector getLector(Long idLector){
        Lector lector = lectorRepository.findById(idLector)
                .orElseThrow(() -> new RuntimeException("No existe el socio"));

        return lector;
    }

}
