package com.indra.p8.controller;


import com.indra.p8.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

        boolean ok = loginService.login(username, password);

        if (!ok) {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "login"; // volvemos al formulario con mensaje de error
        }

        session.setAttribute("usuarioLogueado", username);

        return "redirect:/admin";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }
}
