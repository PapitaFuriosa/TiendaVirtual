package repuestos.repuestoscloud.service;

import repuestos.repuestoscloud.repository.RolRepository;
import repuestos.repuestoscloud.repository.UsuarioRolRepository;
import repuestos.repuestoscloud.repository.UsuarioRepository;
import repuestos.repuestoscloud.entity.Rol;
import repuestos.repuestoscloud.entity.UsuarioRol;
import repuestos.repuestoscloud.entity.Usuario;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService implements UserDetailsService {

  private final UsuarioRepository usuarioRepo;          // ✅ CAMBIO AQUÍ
  private final UsuarioRolRepository usuarioRolRepo;    // ✅ y aquí
  private final RolRepository rolRepo;

  public AuthService(UsuarioRepository u, UsuarioRolRepository ur, RolRepository r) { // ✅ CAMBIO AQUÍ
    this.usuarioRepo = u;
    this.usuarioRolRepo = ur;
    this.rolRepo = r;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Usuario u = usuarioRepo.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario no existe"));

    List<UsuarioRol> rolesLink = usuarioRolRepo.findByIdUsuario(u.getIdUsuario());

    List<GrantedAuthority> auths = new ArrayList<>();
    for (UsuarioRol link : rolesLink) {
      Rol rol = rolRepo.findById(link.getIdRol()).orElse(null);
      if (rol != null) auths.add(new SimpleGrantedAuthority("ROLE_" + rol.getNombre()));
    }

    return org.springframework.security.core.userdetails.User
        .withUsername(u.getUsername())
        .password(u.getPasswordHash())
        .authorities(auths)
        .disabled(Boolean.FALSE.equals(u.getActivo()))
        .build();
  }
}