package com.indra.p8.controller;


import com.indra.p8.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "login";
    }

    /*
    @RequestParam = “coge el parámetro de la petición HTTP con ese nombre”.
    Se rellena con los campos del formulario (name="...")*/
    @PostMapping("/login")
    public String hacerLogin(@RequestParam String username,
                             @RequestParam String password,
                             Model model,
                             HttpSession session) {

        System.out.println("POST /login: username=" + username);

        boolean ok = loginService.login(username, password);
        System.out.println("Resultado loginService.login = " + ok);

        if (!ok) {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "login";
        }

        session.setAttribute("usuarioLogueado", username);
        return "redirect:/biblioteca";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }

    @PostMapping("/crearBibliotecario")
    public ResponseEntity<String> crearBibliotecario(@RequestParam String username, @RequestParam String password) {
        loginService.crearBibliotecario(username, password);
        return ResponseEntity.ok("Bibliotecario creado");
    }

    @GetMapping("/biblioteca")
    public String getBibliotecaPage() {
        // busca templates/biblioteca.html
        return "biblioteca";
    }
}
