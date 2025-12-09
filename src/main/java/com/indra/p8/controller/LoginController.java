package com.indra.p8.controller;

import com.indra.p8.model.Rol;
import com.indra.p8.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "login"; // templates/login.html
    }

    @GetMapping("/registro")
    public String mostrarFormularioRegistro() {
        return "register"; // templates/registro.html
    }

    @PostMapping("/admin/usuarios")
    @PreAuthorize("hasRole('ADMIN')")
    public String crearUsuarioDesdeAdmin(@RequestParam String username,
                                         @RequestParam String password,
                                         @RequestParam Rol rol) {
        loginService.crearBibliotecario(username, password);
        return "redirect:/admin/usuarios";
    }

    @PostMapping("/registro")
    public String procesarRegistro(@RequestParam String username,
                                   @RequestParam String password) {
        loginService.crearBibliotecario(username, password);
        return "redirect:/login?registrado=true";
    }

    @GetMapping("/biblioteca")
    public String getBibliotecaPage() {
        return "biblioteca";
    }

    @GetMapping("/libro")
    public String paginaLibro() {
        return "libro";
    }

    @GetMapping("/copia")
    public String paginaCopia() {
        return "copia";
    }
}
