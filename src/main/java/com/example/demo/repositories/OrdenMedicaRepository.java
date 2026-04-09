package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.models.OrdenMedica;
import com.example.demo.models.Usuario;
import java.util.List;
import java.util.Optional;

public interface OrdenMedicaRepository extends JpaRepository<OrdenMedica, Integer> {
    List<OrdenMedica> findByMedico(Usuario medico);
    Optional<OrdenMedica> findByIdOrden(Integer idOrden);

}

