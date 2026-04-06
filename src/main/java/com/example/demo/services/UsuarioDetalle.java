package com.example.demo.services;

import com.example.demo.models.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

public class UsuarioDetalle implements UserDetails {

    private final Usuario usuario; // Aquí vive tu entidad Usuario con nombre y apellido

    public UsuarioDetalle(Usuario usuario) {
        this.usuario = usuario;
    }

    // ESTE MÉTODO ES EL QUE USAREMOS EN EL HTML
    public String getNombreCompleto() {
        return usuario.getNombre() + " " + usuario.getApellido();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombreRol()));
    }

    @Override public String getPassword() { return usuario.getContraseña(); }
    @Override public String getUsername() { return usuario.getCedula(); }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}