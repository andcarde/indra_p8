package com.indra.p8.service;

import com.indra.p8.repository.PrestamoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrestamoServiceImp implements PrestamoService{
    private final PrestamoRepository prestamoRepository;
}
