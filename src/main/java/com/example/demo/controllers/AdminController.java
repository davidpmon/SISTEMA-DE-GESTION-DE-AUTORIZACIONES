package com.example.demo.controllers;

import com.example.demo.models.OrdenMedica;
import com.example.demo.models.OrdenMedica.EstadoOrden;
import com.example.demo.models.Usuario;
import com.example.demo.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/administrador")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // ── ÓRDENES ────────────────────────────────────────────────────────────────

    @GetMapping("/ordenes")
    public String listarOrdenes(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Integer medicoId,
            Model model,
            @AuthenticationPrincipal UserDetails userDetails) {

        EstadoOrden estadoEnum = null;
        if (estado != null && !estado.isBlank()) {
            try {
                estadoEnum = EstadoOrden.valueOf(estado);
            } catch (Exception ignored) {}
        }

        model.addAttribute("ordenes", adminService.filtrarOrdenes(estadoEnum, medicoId));
        model.addAttribute("medicos", adminService.listarMedicos());
        model.addAttribute("estadoFiltro", estado);
        model.addAttribute("medicoFiltro", medicoId);
        model.addAttribute("adminCedula", userDetails.getUsername());

        return "admin/ordenes";
    }

    @GetMapping("/ordenes/{id}")
    public String verOrden(@PathVariable Integer id,
                           Model model,
                           @AuthenticationPrincipal UserDetails userDetails) {

        model.addAttribute("orden", adminService.buscarOrdenPorId(id));
        model.addAttribute("adminCedula", userDetails.getUsername());

        return "admin/detalle-orden";
    }

    @PostMapping("/ordenes/{id}/aprobar")
    public String aprobar(@PathVariable Integer id,
                          @RequestParam(required = false) String observaciones,
                          RedirectAttributes redirectAttributes) {

        adminService.aprobarOrden(id, observaciones);
        redirectAttributes.addFlashAttribute("exito", "Orden #" + id + " aprobada exitosamente.");

        return "redirect:/administrador/ordenes";
    }

    @PostMapping("/ordenes/{id}/rechazar")
    public String rechazar(@PathVariable Integer id,
                           @RequestParam String observaciones,
                           RedirectAttributes redirectAttributes) {

        adminService.rechazarOrden(id, observaciones);
        redirectAttributes.addFlashAttribute("exito", "Orden #" + id + " rechazada.");

        return "redirect:/administrador/ordenes";
    }

    @PostMapping("/ordenes/{id}/solicitar-correccion")
    public String solicitarCorreccion(@PathVariable Integer id,
                                      @RequestParam String observaciones,
                                      RedirectAttributes redirectAttributes) {

        adminService.solicitarCorreccion(id, observaciones);
        redirectAttributes.addFlashAttribute("exito", "Se solicitó corrección al médico para la orden #" + id + ".");

        return "redirect:/administrador/ordenes/" + id;
    }

    // ── USUARIOS ───────────────────────────────────────────────────────────────

    @GetMapping("/usuarios")
    public String listarUsuarios(Model model,
                                 @AuthenticationPrincipal UserDetails userDetails) {

        model.addAttribute("usuarios", adminService.listarTodosLosUsuarios());
        model.addAttribute("adminCedula", userDetails.getUsername());

        return "admin/usuarios";
    }

    // ── 👨‍⚕️ MÉDICOS ───────────────────────────────────────────────────────────

    @GetMapping("/medicos")
    public String verMedicos(Model model,
                             @AuthenticationPrincipal UserDetails userDetails) {

        model.addAttribute("medicos", adminService.listarMedicos());
        model.addAttribute("adminCedula", userDetails.getUsername());

        return "admin/medicos";
    }

    // ✅ EDITAR MÉDICO (FORMULARIO)
    @GetMapping("/medicos/{id}/editar")
    public String editarMedico(@PathVariable Integer id,
                               Model model,
                               @AuthenticationPrincipal UserDetails userDetails) {

        Usuario medico = adminService.buscarUsuarioPorId(id);

        model.addAttribute("medico", medico);
        model.addAttribute("adminCedula", userDetails.getUsername());

        return "admin/editar-medico";
    }

    // ✅ GUARDAR CAMBIOS DEL MÉDICO
    @PostMapping("/medicos/{id}/editar")
    public String guardarEdicionMedico(@PathVariable Integer id,
                                       @RequestParam String nombre,
                                       @RequestParam String apellido,
                                       @RequestParam(required = false) String correo,
                                       @RequestParam(required = false) String telefono,
                                       RedirectAttributes redirectAttributes) {

        adminService.actualizarMedico(id, nombre, apellido, correo, telefono);

        redirectAttributes.addFlashAttribute("exito", "Médico actualizado correctamente.");
        return "redirect:/administrador/medicos";
    }

    // ✅ ACTIVAR / DESACTIVAR MÉDICO (PERMISOS)
    @PostMapping("/medicos/{id}/estado")
    public String cambiarEstadoMedico(@PathVariable Integer id,
                                      RedirectAttributes redirectAttributes) {

        adminService.cambiarEstadoMedico(id);

        redirectAttributes.addFlashAttribute("exito", "Estado del médico actualizado.");
        return "redirect:/administrador/medicos";
    }

    // ── 📊 REPORTES ────────────────────────────────────────────────────────────

    @GetMapping("/reportes")
    public String reportes(Model model,
                           @AuthenticationPrincipal UserDetails userDetails) {

        model.addAttribute("stats", adminService.obtenerEstadisticas());
        model.addAttribute("ordenes", adminService.listarTodasLasOrdenes());
        model.addAttribute("adminCedula", userDetails.getUsername());

        return "admin/reportes";
    }

    // ── ⚙️ CONFIGURACIÓN ───────────────────────────────────────────────────────

    @GetMapping("/configuracion")
    public String configuracion(Model model,
                                @AuthenticationPrincipal UserDetails userDetails) {

        model.addAttribute("adminCedula", userDetails.getUsername());

        return "admin/configuracion";
    }
}