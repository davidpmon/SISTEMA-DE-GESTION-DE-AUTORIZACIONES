package com.example.demo.services;

import com.example.demo.models.Usuario;
import com.example.demo.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String cedula) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCedula(cedula)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // AQUÍ ESTÁ EL CAMBIO:
        // En lugar de devolver un objeto genérico, devolvemos el "paquete completo"
        return new UsuarioDetalle(usuario);
    }
}
/*@Service
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String cedula) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCedula(cedula)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return User.builder()
                .username(usuario.getCedula())
                .password(usuario.getContraseña()) // contraseña encriptada
                .roles(usuario.getRol().getNombreRol())
                .build();
    }
}*/