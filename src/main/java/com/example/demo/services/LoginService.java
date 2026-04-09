package com.example.demo.services;

import com.example.demo.models.Usuario;
import com.example.demo.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class LoginService {

    private final UsuarioRepository usuarioRepository;

    public LoginService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Optional<Usuario> login(String cedula, String contraseña) {
        return usuarioRepository.findByCedula(cedula)
                .filter(u -> u.getContraseña().equals(contraseña));
    }
}
