package com.example.demo.cifrado;

import com.example.demo.models.Usuario;
import com.example.demo.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;


import java.util.List;

@Component
public class EncriptarUsuarios {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void encriptar() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        for (Usuario u : usuarios) {
            // Encriptar solo si no está en BCrypt
            if (!u.getContraseña().startsWith("$2a$")) {
                u.setContraseña(passwordEncoder.encode(u.getContraseña()));
            }
        }
        usuarioRepository.saveAll(usuarios);
        System.out.println("Contraseñas encriptadas correctamente");
    }
}