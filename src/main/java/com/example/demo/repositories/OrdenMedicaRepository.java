package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.demo.models.OrdenMedica;
import com.example.demo.models.OrdenMedica.EstadoOrden;
import com.example.demo.models.Usuario;
import java.util.List;
import java.util.Optional;

public interface OrdenMedicaRepository extends JpaRepository<OrdenMedica, Integer> {

    // 🔹 Métodos que ya tenías
    List<OrdenMedica> findByMedico(Usuario medico);
    Optional<OrdenMedica> findByIdOrden(Integer idOrden);

    // 🔹 Ordenar por fecha (el que arreglamos)
    @Query("SELECT o FROM OrdenMedica o ORDER BY o.fecha DESC")
    List<OrdenMedica> obtenerOrdenesRecientes();

    // 🔥 MÉTODOS QUE TE FALTABAN (para AdminService)
    List<OrdenMedica> findByEstado(EstadoOrden estado);

    List<OrdenMedica> findByEstadoAndMedicoIdUsuario(EstadoOrden estado, Integer idUsuario);

    List<OrdenMedica> findByMedicoIdUsuario(Integer idUsuario);

    long countByEstado(EstadoOrden estado);
}