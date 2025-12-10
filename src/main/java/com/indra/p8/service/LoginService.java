package com.indra.p8.service;

import com.indra.p8.model.Bibliotecario;
import com.indra.p8.model.Rol;

public interface LoginService {
    Bibliotecario getBibliotecario(String username);
    boolean login(String username, String password);
    boolean crearUsuario(String username, String password, Rol rol) ;
    boolean crearBibliotecario(String username, String password);
}
