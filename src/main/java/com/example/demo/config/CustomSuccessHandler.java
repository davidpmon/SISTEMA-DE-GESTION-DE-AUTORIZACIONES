package com.example.demo.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        String redirectUrl = "/"; // por defecto

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            String role = auth.getAuthority(); // ej: ROLE_Administrador

            if (role.equals("ROLE_Administrador")) {
                redirectUrl = "/admin";
                break;
            } else if (role.equals("ROLE_Médico")) {
                redirectUrl = "/medico";
                break;
            } else if (role.equals("ROLE_Autorizador")) {
                redirectUrl = "/autorizador";
                break;
            } else if (role.equals("ROLE_Paciente")) {
                redirectUrl = "/paciente";
                break;
            }
        }

        response.sendRedirect(redirectUrl);
    }
}
