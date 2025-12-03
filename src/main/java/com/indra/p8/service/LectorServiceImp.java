package com.indra.p8.service;

import com.indra.p8.repository.LectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LectorServiceImp implements LectorService{
    @Autowired
    private final LectorRepository lectorRepository;
}
