package com.indra.p8.service;

import com.indra.p8.model.Bibliotecario;
import com.indra.p8.repository.BibliotecarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final BibliotecarioRepository bibliotecarioRepository;

    public CustomUserDetailsService(BibliotecarioRepository bibliotecarioRepository) {
        this.bibliotecarioRepository = bibliotecarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Bibliotecario biblio = bibliotecarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No existe el usuario: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(biblio.getUsername())
                .password(biblio.getPassword())
                .roles("BIBLIOTECARIO")
                .build();
    }
}
