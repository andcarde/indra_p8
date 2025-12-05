package com.indra.p8.service;

import com.indra.p8.model.Bibliotecario;

public interface LoginService {
    Bibliotecario getBibliotecario(String username);
    boolean login(String username, String password);
    boolean crearBibliotecario(String username, String password);
}
