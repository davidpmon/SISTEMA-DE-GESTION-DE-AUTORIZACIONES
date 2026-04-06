package com.example.demo.services;

import com.example.demo.models.OrdenMedica;
import com.example.demo.models.OrdenMedica.EstadoOrden;
import com.example.demo.models.Rol;
import com.example.demo.models.Usuario;
import com.example.demo.repositories.OrdenMedicaRepository;
import com.example.demo.repositories.RolRepository;
import com.example.demo.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private OrdenMedicaRepository ordenMedicaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // ── ÓRDENES ────────────────────────────────────────────────────────────────

    public List<OrdenMedica> listarTodasLasOrdenes() {
        return ordenMedicaRepository.obtenerOrdenesRecientes();
    }

    public List<OrdenMedica> listarOrdenesPorEstado(EstadoOrden estado) {
        return ordenMedicaRepository.findByEstado(estado);
    }

    public List<OrdenMedica> filtrarOrdenes(EstadoOrden estado, Integer medicoId) {
        if (estado != null && medicoId != null) {
            return ordenMedicaRepository.findByEstadoAndMedicoIdUsuario(estado, medicoId);
        } else if (estado != null) {
            return ordenMedicaRepository.findByEstado(estado);
        } else if (medicoId != null) {
            return ordenMedicaRepository.findByMedicoIdUsuario(medicoId);
        }
        return ordenMedicaRepository.obtenerOrdenesRecientes();
    }

    public OrdenMedica buscarOrdenPorId(Integer id) {
        return ordenMedicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con id: " + id));
    }

    public void aprobarOrden(Integer id, String observaciones) {
        OrdenMedica orden = buscarOrdenPorId(id);
        orden.setEstado(EstadoOrden.APROBADA);

        if (observaciones != null && !observaciones.isBlank()) {
            orden.setDescripcion(orden.getDescripcion() + "\n[Admin]: " + observaciones);
        }

        ordenMedicaRepository.save(orden);
    }

    public void rechazarOrden(Integer id, String observaciones) {
        OrdenMedica orden = buscarOrdenPorId(id);
        orden.setEstado(EstadoOrden.RECHAZADA);

        if (observaciones != null && !observaciones.isBlank()) {
            orden.setDescripcion(orden.getDescripcion() + "\n[Admin rechazó]: " + observaciones);
        }

        ordenMedicaRepository.save(orden);
    }

    public void solicitarCorreccion(Integer id, String observaciones) {
        OrdenMedica orden = buscarOrdenPorId(id);
        orden.setEstado(EstadoOrden.EN_REVISION);

        if (observaciones != null && !observaciones.isBlank()) {
            orden.setDescripcion(orden.getDescripcion() + "\n[Admin requiere]: " + observaciones);
        }

        ordenMedicaRepository.save(orden);
    }

    // ── USUARIOS ───────────────────────────────────────────────────────────────

    public List<Usuario> listarTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> listarMedicos() {
        return usuarioRepository.findByRolNombreRol("Médico");
    }

    public Usuario buscarUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    // ── ROLES ─────────────────────────────────────────────────────────────────

    public List<Rol> listarRoles() {
        return rolRepository.findAll();
    }

    // ── CRUD USUARIOS ─────────────────────────────────────────────────────────

    public void crearUsuario(String nombre, String apellido, String cedula,
                             String correo, String telefono, String contraseña, Integer rolId) {

        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + rolId));

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setCedula(cedula);
        usuario.setCorreo(correo);
        usuario.setTelefono(telefono);
        usuario.setRol(rol);

        // contraseña encriptada
        usuario.setContraseña(encoder.encode(contraseña));

        usuarioRepository.save(usuario);
    }

    public void actualizarUsuario(Integer id, String nombre, String apellido, String cedula,
                                  String correo, String telefono, Integer rolId) {

        Usuario usuario = buscarUsuarioPorId(id);

        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + rolId));

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setCedula(cedula);
        usuario.setCorreo(correo);
        usuario.setTelefono(telefono);
        usuario.setRol(rol);

        usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(Integer id) {
        Usuario usuario = buscarUsuarioPorId(id);
        usuarioRepository.delete(usuario);
    }

    // ── MÉDICOS ────────────────────────────────────────────────────────────────

    public void actualizarMedico(Integer id, String nombre, String apellido, String correo, String telefono) {
        Usuario medico = buscarUsuarioPorId(id);

        medico.setNombre(nombre);
        medico.setApellido(apellido);

        if (correo != null) medico.setCorreo(correo);
        if (telefono != null) medico.setTelefono(telefono);

        usuarioRepository.save(medico);
    }

    public void cambiarEstadoMedico(Integer id) {
        Usuario medico = buscarUsuarioPorId(id);

        if (medico.getEstado() == Usuario.EstadoUsuario.Activo) {
            medico.setEstado(Usuario.EstadoUsuario.Inactivo);
        } else {
            medico.setEstado(Usuario.EstadoUsuario.Activo);
        }

        usuarioRepository.save(medico);
    }

    // ── ESTADÍSTICAS ───────────────────────────────────────────────────────────

    public Map<String, Long> obtenerEstadisticas() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("total", ordenMedicaRepository.count());
        stats.put("pendientes", ordenMedicaRepository.countByEstado(EstadoOrden.PENDIENTE));
        stats.put("aprobadas", ordenMedicaRepository.countByEstado(EstadoOrden.APROBADA));
        stats.put("rechazadas", ordenMedicaRepository.countByEstado(EstadoOrden.RECHAZADA));
        stats.put("enRevision", ordenMedicaRepository.countByEstado(EstadoOrden.EN_REVISION));
        return stats;
    }
}