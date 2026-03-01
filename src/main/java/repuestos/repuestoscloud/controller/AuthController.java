package repuestos.repuestoscloud.controller;

import repuestos.repuestoscloud.repository.RolRepository;
import repuestos.repuestoscloud.repository.UsuarioRolRepository;
import repuestos.repuestoscloud.repository.UsuarioRepository;
import repuestos.repuestoscloud.entity.Rol;
import repuestos.repuestoscloud.entity.UsuarioRol;
import repuestos.repuestoscloud.entity.Usuario;
import jakarta.validation.constraints.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

  private final UsuarioRepository usuarioRepo;          // ✅ CAMBIO
  private final RolRepository rolRepo;
  private final UsuarioRolRepository usuarioRolRepo;
  private final PasswordEncoder encoder;

  public AuthController(UsuarioRepository u, RolRepository r, UsuarioRolRepository ur, PasswordEncoder e) { // ✅ CAMBIO
    this.usuarioRepo = u;
    this.rolRepo = r;
    this.usuarioRolRepo = ur;
    this.encoder = e;
  }

  @GetMapping("/login")
  public String login() { return "login"; }

  public static class RegistroForm {
    @NotBlank public String username;
    @NotBlank public String password;
    @NotBlank public String nombre;
    @NotBlank public String apellidos;
    @Email public String correo;
    public String telefono;
  }

  @GetMapping("/registro")
  public String registro(Model model) {
    model.addAttribute("form", new RegistroForm());
    return "registro";
  }

  @PostMapping("/registro")
  public String registrar(@ModelAttribute("form") RegistroForm form, Model model) {

    if (usuarioRepo.findByUsername(form.username).isPresent()) {
      model.addAttribute("error", "Ese username ya existe");
      return "registro";
    }

    Usuario u = new Usuario();
    u.setUsername(form.username);
    u.setPasswordHash(encoder.encode(form.password));
    u.setNombre(form.nombre);
    u.setApellidos(form.apellidos);
    u.setCorreo(form.correo);
    u.setTelefono(form.telefono);
    u.setActivo(true);
    u = usuarioRepo.save(u); // ✅ ahora sí

    Rol cliente = rolRepo.findByNombre("CLIENTE").orElseThrow();
    UsuarioRol ur = new UsuarioRol();
    ur.setIdUsuario(u.getIdUsuario());
    ur.setIdRol(cliente.getIdRol());
    usuarioRolRepo.save(ur);

    return "redirect:/login";
  }
}