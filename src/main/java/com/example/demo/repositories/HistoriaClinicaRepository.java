package com.example.demo.repositories;

import com.example.demo.models.HistoriaClinica;
import com.example.demo.models.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HistoriaClinicaRepository extends JpaRepository<HistoriaClinica, Integer> {

    @Query("SELECT h FROM HistoriaClinica h " +
            "JOIN FETCH h.paciente p " +
            "JOIN FETCH p.usuario u")
    List<HistoriaClinica> buscarConPacienteYUsuario();

    List<HistoriaClinica> findByPaciente(Paciente paciente);

}