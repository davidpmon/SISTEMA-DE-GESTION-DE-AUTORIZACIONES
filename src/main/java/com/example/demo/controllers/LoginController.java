package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(Model model, String error, String logout, Authentication authentication) {
        if(authentication != null && authentication.isAuthenticated()){
            var roles = authentication.getAuthorities();

            //funciones lambda: funciones anonimas las cuales podemos implementar sin darle nombre
            // dado un parámetro r (que representa un rol del usuario),
            // devuelve true si r.getAuthority() es igual a "ROLE_Administrador"; de lo contrario, devuelve false.
            if(roles.stream().anyMatch(r-> r.getAuthority().equals("ROLE_Administrador")))
            {
                return "redirect:/admin";
            }
            else if(roles.stream().anyMatch(r-> r.getAuthority().equals("ROLE_Médico")))
            {
                return "redirect:/medico";
            }
            else if(roles.stream().anyMatch(r-> r.getAuthority().equals("ROLE_Autorizador")))
            {
                return "redirect:/autorizador";
            }
            return "redirect:/home";
        }
        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
        }
        if (logout != null) {
            model.addAttribute("mensaje", "Sesión cerrada correctamente");
        }
        return "login"; // muestra login.html
    }

    @GetMapping("/home")
    public String homePage() {
        return "home"; // muestra home.html cuando el login es correcto
    }

    @GetMapping("/logout")
    public String logout() {
        // Redirige al login (simulación de cierre de sesión)
        return "redirect:/login?logout=true";
    }
}
