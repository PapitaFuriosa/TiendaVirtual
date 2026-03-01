package com.repuestos.repuestoscloud.repository;

import com.repuestos.repuestoscloud.entity.UsuarioRol;
import com.repuestos.repuestoscloud.entity.UsuarioRolId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, UsuarioRolId> {
    List<UsuarioRol> findByIdUsuario(Long idUsuario);
}