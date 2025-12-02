package com.indra.p8.service;

import com.indra.p8.repository.CopiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CopiaServiceImp implements CopiaService{
    private final CopiaRepository copiaRepository;
}
