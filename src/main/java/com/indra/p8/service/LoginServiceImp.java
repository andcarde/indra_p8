package com.indra.p8.service;

import com.indra.p8.model.Bibliotecario;
import com.indra.p8.repository.BibliotecarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Service
public class LoginServiceImp implements LoginService {

    private final PasswordEncoder encoder;
    private final BibliotecarioRepository bibliotecarioRepository;

    public LoginServiceImp(PasswordEncoder encoder,
                           BibliotecarioRepository bibliotecarioRepository) {
        this.encoder = encoder;
        this.bibliotecarioRepository = bibliotecarioRepository;
    }

    @Override
    public Bibliotecario getBibliotecario(String username) {
        Optional<Bibliotecario> optBibliotecario =
                bibliotecarioRepository.findByUsername(username);
        if (optBibliotecario.isEmpty())
            return null;

        return optBibliotecario.get();
    }


    @Override
    public boolean login(String username, String password) {
        Optional<Bibliotecario> optBibliotecario =
                bibliotecarioRepository.findByUsername(username);

        if (optBibliotecario.isEmpty())
            return false;

        Bibliotecario bibliotecario = optBibliotecario.get();
        String hash = bibliotecario.getPassword();
        return encoder.matches(password, hash);
    }

    @Override
    public boolean crearBibliotecario(String username, String password) {
        if (bibliotecarioRepository.findByUsername(username).isPresent()) {
            System.out.println("Registro: el usuario '" + username + "' ya existe.");
            return false;
        }
        String hash = encoder.encode(password);
        Bibliotecario bibliotecario = new Bibliotecario();
        bibliotecario.setUsername(username);
        bibliotecario.setPassword(hash);

        bibliotecarioRepository.save(bibliotecario);
        System.out.println("Registro: usuario '" + username + "' creado correctamente.");

        return true;
    }


}
