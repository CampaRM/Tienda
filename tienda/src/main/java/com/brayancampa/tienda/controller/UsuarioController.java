package com.brayancampa.tienda.controller;

import com.brayancampa.tienda.Service.UsuarioService;
import com.brayancampa.tienda.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Validated
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Método para listar los registros
    @GetMapping
    public String lista(Model model) {
        model.addAttribute("usuarios", usuarioService.listar());
        return "usuario";
    }

    // Metodo para abrir una vista
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model){
        model.addAttribute("usuario",new Usuario());
        model.addAttribute("modoEdicion", false);
        return "usuario-formulario";
    }

    // Método para crear un nuevo usuario
    @PostMapping("/guardar")
    public String crear(@Valid @ModelAttribute("usuario") Usuario usuario, Model model, BindingResult result) {

        if (result.hasErrors()) {
            model.addAttribute("modeEdicion", false);
            return "usuario-formulario";
        }

        usuarioService.crear(usuario);
        return "redirect:/usuario";
    }

    // Método para eliminar un usuario
    @GetMapping("/eliminar/{id}")
    public String eliminarUser(@PathVariable("id") Integer id) {
        if (id != null) {
            usuarioService.eliminar(id);
        }
        return "redirect:/usuario";
    }

    // Método para actualizar un usuario
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Integer id, Model model) {
        Usuario usuario = usuarioService.obtenerPorId(id);
        model.addAttribute("usuario", usuario);
        model.addAttribute("modoEdicion", true);
        return "usuario-formulario";
    }

    @PostMapping("/actualizar")
    public String actualizar(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result) {
        if (result.hasErrors()) {
            return "usuario-formulario";
        }

        usuarioService.actualizar(usuario.getIdUsuario(), usuario);
        return "redirect:/usuario";
    }

}