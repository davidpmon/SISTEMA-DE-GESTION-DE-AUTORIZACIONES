package com.example.demo.controllers;

import com.example.demo.models.*;
import com.example.demo.services.MedicoService;
import java.util.List;
import com.example.demo.repositories.HistoriaClinicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/medico")
public class MedicoController {
    @Autowired
    private HistoriaClinicaRepository historiaClinicaRepository;

    @Autowired
    private MedicoService medicoService;

    // Página inicial del historial clínico (lista de pacientes)
    @GetMapping("/historial")
    public String verHistorial(Model model) {
        List<Paciente> pacientes = medicoService.obtenerTodosLosPacientes();
        model.addAttribute("pacientes", pacientes);
        return "medico/historial";
    }

    // Endpoint que devuelve los datos de historial de un paciente en formato JSON
    @GetMapping("/historial/{idPaciente}/detalle")
    @ResponseBody
    public Map<String, Object> obtenerHistorialPaciente(@PathVariable Integer idPaciente) {
        Map<String, Object> respuesta = new HashMap<>();

        Paciente paciente = medicoService.obtenerPacientePorId(idPaciente);
        if (paciente == null) {
            respuesta.put("error", "Paciente no encontrado");
            return respuesta;
        }

        List<HistoriaClinica> historias = medicoService.obtenerHistoriasPorPaciente(idPaciente);

        List<Map<String, Object>> historiasDTO = historias.stream()
                .sorted(Comparator.comparing(HistoriaClinica::getFecha).reversed())
                .map(h -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("fecha", h.getFecha());
                    map.put("descripcion", h.getDescripcion());
                    map.put("diagnostico", h.getDiagnostico());
                    return map;
                })
                .toList();

        respuesta.put("paciente", Map.of(
                "nombre", paciente.getUsuario().getNombre(),
                "apellido", paciente.getUsuario().getApellido(),
                "documento", paciente.getNumeroDocumento(),
                "telefono", paciente.getTelefono()
        ));
        respuesta.put("historias", historiasDTO);

        return respuesta;
    }
   /* @GetMapping("/ordenes")
    public String listarOrdenes(Model model) {
        List<HistoriaClinica> historias = historiaClinicaRepository.buscarConPacienteYUsuario();
        model.addAttribute("historias", historias);
        return "medico/ordenes";
    }*/

   /*@GetMapping("/ordenes")
    public String verOrdenes(@AuthenticationPrincipal Usuario medico, Model model) {
        List<OrdenMedica> ordenes = medicoService.obtenerOrdenesPorMedico(medico);
        model.addAttribute("ordenes", ordenes);
        return "medico/ordenes";
    }*/

    /*@PostMapping("/ordenes/nueva")
    public String crearOrden(@ModelAttribute OrdenMedica orden) {
        medicoService.crearOrden(orden);
        return "redirect:/medico/ordenes";
    }*/
}
