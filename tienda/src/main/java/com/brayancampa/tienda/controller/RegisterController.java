package com.brayancampa.tienda.controller;

import com.brayancampa.tienda.entity.Usuario;
import com.brayancampa.tienda.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterController(UsuarioRepository usuarioRepository,
                             PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    @PostMapping("/enviar-registro")
    public String registrar(@ModelAttribute("usuario") Usuario usuario, Model model) {

        System.out.println("ENTRÓ AL POST");

        // Verificar si existe en BD
        if (usuarioRepository.findByNombreUsuario(usuario.getNombreUsuario()).isPresent()) {
            model.addAttribute("error", "El nombre de usuario ya está en uso");
            model.addAttribute("usuario", usuario);
            return "register";
        }

        if (usuario.getEdadUsuario() < 0) {
            model.addAttribute("error", "La edad no puede ser negativa");
            return "register";
        }

        // Encriptar contraseña
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));

        // Guardar en BD
        usuarioRepository.save(usuario);

        System.out.println("Usuario guardado en BD: " + usuario.getNombreUsuario());

        return "redirect:/login";
    }

}