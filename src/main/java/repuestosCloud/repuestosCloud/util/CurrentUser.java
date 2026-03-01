package com.repuestos.repuestoscloud.util;

import com.repuestos.repuestoscloud.entity.Usuario;
import com.repuestos.repuestoscloud.repository.UsuarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser {

    private final UsuarioRepository usuarioRepo;

    public CurrentUser(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    public Usuario get() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepo.findByUsername(username).orElseThrow();
    }
}