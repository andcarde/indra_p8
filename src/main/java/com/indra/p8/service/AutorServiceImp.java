package com.indra.p8.service;

import com.indra.p8.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AutorServiceImp {

    private final AutorRepository autorRepository;

}
