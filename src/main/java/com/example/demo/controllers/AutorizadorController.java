package com.example.demo.controllers;

import com.example.demo.models.OrdenMedica;
import com.example.demo.models.OrdenMedica.EstadoOrden;
import com.example.demo.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/autorizador")
public class AutorizadorController {

    @Autowired
    private AdminService adminService;

    // ── ÓRDENES ─────────────────────────────────────────────

    @GetMapping("/ordenes")
    public String listarOrdenes(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Integer medicoId,
            Model model,
            @AuthenticationPrincipal UserDetails userDetails) {

        EstadoOrden estadoEnum = null;
        if (estado != null && !estado.isBlank()) {
            try { estadoEnum = EstadoOrden.valueOf(estado); } catch (Exception ignored) {}
        }

        model.addAttribute("ordenes", adminService.filtrarOrdenes(estadoEnum, medicoId));
        model.addAttribute("medicos", adminService.listarMedicos());
        model.addAttribute("estadoFiltro", estado);
        model.addAttribute("medicoFiltro", medicoId);

        if (userDetails != null) {
            model.addAttribute("usuario", userDetails.getUsername());
        }

        return "autorizador/ordenes";
    }

    @GetMapping("/ordenes/{id}")
    public String verOrden(@PathVariable Integer id,
                           Model model,
                           @AuthenticationPrincipal UserDetails userDetails) {

        model.addAttribute("orden", adminService.buscarOrdenPorId(id));

        if (userDetails != null) {
            model.addAttribute("usuario", userDetails.getUsername());
        }

        return "autorizador/detalle-orden";
    }

    @PostMapping("/ordenes/{id}/aprobar")
    public String aprobar(@PathVariable Integer id,
                          @RequestParam(required = false) String observaciones,
                          RedirectAttributes redirectAttributes) {

        adminService.aprobarOrden(id, observaciones);
        redirectAttributes.addFlashAttribute("exito", "Orden aprobada");

        return "redirect:/autorizador/ordenes";
    }

    @PostMapping("/ordenes/{id}/rechazar")
    public String rechazar(@PathVariable Integer id,
                           @RequestParam String observaciones,
                           RedirectAttributes redirectAttributes) {

        adminService.rechazarOrden(id, observaciones);
        redirectAttributes.addFlashAttribute("exito", "Orden rechazada");

        return "redirect:/autorizador/ordenes";
    }

    @PostMapping("/ordenes/{id}/solicitar-correccion")
    public String solicitarCorreccion(@PathVariable Integer id,
                                      @RequestParam String observaciones,
                                      RedirectAttributes redirectAttributes) {

        adminService.solicitarCorreccion(id, observaciones);
        redirectAttributes.addFlashAttribute("exito", "Corrección solicitada");

        return "redirect:/autorizador/ordenes/" + id;
    }
}
