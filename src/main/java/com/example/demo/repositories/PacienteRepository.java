package com.example.demo.repositories;

import com.example.demo.models.Paciente;
import com.example.demo.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

    // Buscar paciente por su usuario (útil si quieres vincular el login)
    Optional<Paciente> findByUsuario(Usuario usuario);

    // Buscar paciente por número de documento
    Optional<Paciente> findByNumeroDocumento(String numeroDocumento);
}