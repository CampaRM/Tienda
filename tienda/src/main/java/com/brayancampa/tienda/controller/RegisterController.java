package com.brayancampa.tienda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RegisterController {

    private static List<String> usuariosRegistrados = new ArrayList<>();

    //Método para buscar si un usuario existe en la lista
    public static String buscarPassword(String username) {
        for (String dato : usuariosRegistrados) {
            String nombre = dato.substring(0, dato.indexOf(" (")).trim();
            String contra = dato.substring(dato.indexOf("(") + 1, dato.indexOf(")")).trim();

            if (nombre.equalsIgnoreCase(username)) {
                return contra;
            }
        }
        return null;
    }


    @GetMapping("/register")
    public String mostrarFormulario() {
        return "register";
    }

    @PostMapping("/enviar-registro")
    public String registrar(@RequestParam String nombre, @RequestParam String contra) {
        usuariosRegistrados.add(nombre + " (" + contra + ")");

        System.out.println("Nuevo usuario registrado: " + nombre + " - " + contra);
        System.out.println("Total registrados: " + usuariosRegistrados.size());

        return "redirect:/login";
    }

}