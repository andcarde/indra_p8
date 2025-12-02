package com.indra.p8.service;

import com.indra.p8.repository.MultaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MultaServiceImp implements MultaService{
    private final MultaRepository multaRepository;
}
