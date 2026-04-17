package com.brayancampa.tienda.controller;

import com.brayancampa.tienda.entity.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller("/register")
public class RegisterController {

    private static List<Usuario> usuariosRegistrados = new ArrayList<>();

    //Método para buscar si un usuario existe en la lista
    public static boolean buscarUser(String username) {
        for (Usuario u : usuariosRegistrados) {

            if (u.getNombreUsuario().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }


    @GetMapping("/register")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    @PostMapping("/enviar-registro")
    public String registrar(@ModelAttribute("usuario") Usuario usuario, Model model) {

        if (buscarUser(usuario.getNombreUsuario())) {
            model.addAttribute("error", "El nombre de usuario '" + usuario.getNombreUsuario() + "' ya está en uso.");

            model.addAttribute("usuario", usuario);

            return "/register";
        }
        usuariosRegistrados.add(usuario);

        return "redirect:/login";
    }
}