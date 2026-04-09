package com.example.demo.controllers;

import com.example.demo.dto.HistoriaClinicaDTO;
import com.example.demo.models.*;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/medico/ordenes")
public class OrdenMedicaController {

    @Autowired
    private OrdenMedicaRepository ordenRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private HistoriaClinicaRepository historiaRepo;

    @Autowired
    private PacienteRepository pacienteRepo;

    // Mostrar lista de órdenes del médico logueado
    @GetMapping
    public String listarOrdenes(Model model, Authentication authentication) {
        String cedula = authentication.getName(); // obtenida del login
        Usuario medico = usuarioRepo.findByCedula(cedula).orElse(null);

        if (medico == null) {
            return "redirect:/login?errorUsuarioNoEncontrado";
        }

        List<OrdenMedica> ordenes = ordenRepo.findByMedico(medico);
        List<HistoriaClinica> historias = historiaRepo.findAll();

        model.addAttribute("ordenes", ordenes);
        model.addAttribute("historias", historias);
        return "medico/ordenes"; // plantilla HTML de creación de órdenes
    }

    // Crear nueva orden médica
    @PostMapping("/nueva")
    public String crearOrden(
            @RequestParam("descripcion") String descripcion,
            @RequestParam("id_historia") Integer idHistoria,
            Authentication authentication) {

        String cedula = authentication.getName();
        Usuario medico = usuarioRepo.findByCedula(cedula).orElse(null);
        HistoriaClinica historia = historiaRepo.findById(idHistoria).orElse(null);

        if (medico == null || historia == null) {
            return "redirect:/medico/ordenes?error";
        }

        Paciente paciente = historia.getPaciente();

        if (paciente == null) {
            System.out.println("La historia clínica no tiene paciente asociado.");
            return "redirect:/medico/ordenes?errorPaciente";
        }

        OrdenMedica orden = new OrdenMedica();
        orden.setDescripcion(descripcion);
        orden.setFecha(LocalDateTime.now());
        orden.setEstado(OrdenMedica.EstadoOrden.PENDIENTE);
        orden.setMedico(medico);
        orden.setHistoria(historia);
        orden.setPaciente(paciente);

        ordenRepo.save(orden);
        Integer idGenerado = orden.getIdOrden();
        System.out.println("Orden médica creada con paciente ID: " + paciente.getIdPaciente());

        return "redirect:/medico/ordenes?success&id=" + idGenerado;
    }

    // Mostrar historial de órdenes médicas del médico logueado
    @GetMapping("/historial-ordenes")
    public String verHistorialOrdenes(Model model, Authentication authentication) {
        String cedula = authentication.getName();
        Usuario medico = usuarioRepo.findByCedula(cedula).orElse(null);

        if (medico == null) {
            return "redirect:/login?errorUsuarioNoEncontrado";
        }

        List<OrdenMedica> ordenes = ordenRepo.findByMedico(medico);
        model.addAttribute("ordenes", ordenes);

        return "medico/historial-ordenes";
    }

    // Buscar historia clínica por ID para la creacion de ordenes
    @GetMapping("/buscar-historia/{id}")
    @ResponseBody
    public ResponseEntity<?> obtenerHistoria(@PathVariable Integer id) {
        var historia = historiaRepo.findById(id).orElse(null);

        if (historia == null) {
            return ResponseEntity.status(404).body("Historia clínica no encontrada");
        }

        var paciente = historia.getPaciente();
        var usuario = paciente.getUsuario();

        var dto = new HistoriaClinicaDTO(
                historia.getIdHistoria(),
                usuario.getNombre(),
                usuario.getApellido(),
                paciente.getNumeroDocumento(),
                paciente.getFechaNacimiento()
        );

        return ResponseEntity.ok(dto);
    }

    // Devuelve los detalles de una orden médica como JSON
    @GetMapping("/{id}/detalle")
    @ResponseBody
    public Map<String, Object> obtenerDetalleOrden(@PathVariable Integer id) {
        Map<String, Object> data = new HashMap<>();
        OrdenMedica orden = ordenRepo.findById(id).orElse(null);

        if (orden != null) {
            data.put("id", orden.getIdOrden());
            data.put("fecha", orden.getFecha());
            data.put("descripcion", orden.getDescripcion());
            data.put("estado", orden.getEstado());
            data.put("paciente", orden.getPaciente().getUsuario().getNombre() + " " +
                    orden.getPaciente().getUsuario().getApellido());
            data.put("diagnostico", orden.getHistoria().getDiagnostico());
        } else {
            data.put("error", "Orden no encontrada");
        }

        return data;
    }
}
