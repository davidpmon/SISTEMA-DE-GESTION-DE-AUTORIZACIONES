package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index"; // templates/index.html
    }
    @GetMapping("/terminos")
    public String terminos()
    {
        return "terminos";
    }
    @GetMapping("/admin")
    public String adminHome() {
        return "admin/home"; // templates/admin/home.html
    }

    @GetMapping("/medico")
    public String medicoHome() {
        return "medico/home"; // templates/medico/home.html
    }

    @GetMapping("/paciente")
    public String pacienteHome() {
        return "paciente/home"; // templates/paciente/home.html
    }

    @GetMapping("/autorizador")
    public String autorizadorHome() {
        return "autorizador/home"; // templates/autorizador/home.html
    }
   //esto no debe ir aqui
   @GetMapping("/medico/terminos")
    public String mostrarTerminosMedico() {
        return "medico/terminos_medico"; // ruta dentro de templates
    }

    @GetMapping("/terminos/admin")
    public String terminosAdmin() {
        return "Admin/terminos";
    }
    @GetMapping("/admin/home")
    public String homeAdmin() {
        return "admin/home";
    }
    @GetMapping("/terminos/autorizador")
    public String terminosAutorizador() {
        return "autorizador/terminos";
    }
    @GetMapping("/autorizador/home")
    public String homeAutorizador() {
        return "autorizador/home";
    }


}
