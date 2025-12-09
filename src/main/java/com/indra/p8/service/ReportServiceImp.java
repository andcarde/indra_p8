package com.indra.p8.service;

import com.indra.p8.DTOs.LibroMasPrestadoDTO;
import com.indra.p8.repository.LibroRepository;
import com.indra.p8.repository.MultaRepository;
import com.indra.p8.repository.PrestamoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImp implements ReportService{

    private final PrestamoRepository prestamoRepository;
    private final MultaRepository multaRepository;
    private final LibroRepository libroRepository;


    @Override
    public List<LibroMasPrestadoDTO> librosMasPrestados() {
        return prestamoRepository.librosMasPrestados();
    }

}
