package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.models.*;
import com.example.demo.repositories.*;

@Service
public class MedicoService {

    @Autowired
    private HistoriaClinicaRepository historiaRepo;

    @Autowired
    private OrdenMedicaRepository ordenRepo;

    @Autowired
    private PacienteRepository pacienteRepo;

    // Obtener todas las historias clínicas de un paciente
    public List<HistoriaClinica> obtenerHistoriasPorPaciente(Integer idPaciente) {
        return pacienteRepo.findById(idPaciente)
                .map(historiaRepo::findByPaciente)
                .orElse(List.of());
    }

    // Crear nueva orden médica
    public OrdenMedica crearOrden(OrdenMedica orden) {
        orden.setEstado(OrdenMedica.EstadoOrden.PENDIENTE);
        orden.setFecha(LocalDateTime.now());
        return ordenRepo.save(orden);
    }

    // Obtener todas las órdenes realizadas por un médico
    public List<OrdenMedica> obtenerOrdenesPorMedico(Usuario medico) {
        return ordenRepo.findByMedico(medico);
    }

    // Obtener todos los pacientes
    public List<Paciente> obtenerTodosLosPacientes() {
        return pacienteRepo.findAll();
    }

    // Obtener un paciente específico por ID
    public Paciente obtenerPacientePorId(Integer idPaciente) {
        return pacienteRepo.findById(idPaciente).orElse(null);
    }
}
