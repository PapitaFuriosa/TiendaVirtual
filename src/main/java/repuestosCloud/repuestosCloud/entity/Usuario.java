package com.repuestos.repuestoscloud.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity @Table(name="usuario")
@Getter @Setter @NoArgsConstructor
public class Usuario {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id_usuario")
  private Long idUsuario;

  @Column(nullable=false, unique=true)
  private String username;

  @Column(name="password_hash", nullable=false)
  private String passwordHash;

  @Column(nullable=false)
  private String nombre;

  @Column(nullable=false)
  private String apellidos;

  @Column(unique=true)
  private String correo;

  private String telefono;

  private Boolean activo = true;

  @Column(name="fecha_creacion")
  private Instant fechaCreacion;

  @Column(name="fecha_modificacion")
  private Instant fechaModificacion;
}
