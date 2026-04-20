package com.brayancampa.tienda.controller;

import com.brayancampa.tienda.Service.PedidoService;
import com.brayancampa.tienda.Service.UsuarioService;
import com.brayancampa.tienda.entity.Pedido;
import com.brayancampa.tienda.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

    private final PedidoService pedidoService;
    private final UsuarioService usuarioService;

    public PedidoController(PedidoService pedidoService, UsuarioService usuarioService) {
        this.pedidoService = pedidoService;
        this.usuarioService = usuarioService;
    }

    // Listar pedidos
    @GetMapping
    public String lista(Model model) {
        model.addAttribute("pedidos", pedidoService.listar());
        return "pedido";
    }

    // Mostrar formulario nuevo
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model){
        model.addAttribute("pedido", new Pedido());
        model.addAttribute("modoEdicion", false);
        model.addAttribute("usuarios", usuarioService.listar());
        return "pedido-formulario";
    }

    // Guardar nuevo pedido
    @PostMapping("/guardar")
    public String crear(@Valid @ModelAttribute("pedido") Pedido pedido,
                        BindingResult result,
                        Model model) {

        if (result.hasErrors()) {
            model.addAttribute("usuarios", usuarioService.listar());
            model.addAttribute("modoEdicion", false);
            return "pedido-formulario";
        }

        pedidoService.crear(pedido);
        return "redirect:/pedido";
    }

    // Editar pedido
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Integer id, Model model) {
        Pedido pedido = pedidoService.obtenerPorId(id);
        model.addAttribute("pedido", pedido);
        model.addAttribute("modoEdicion", true);
        return "pedido-formulario";
    }

    // Actualizar pedido
    @PostMapping("/actualizar")
    public String actualizar(@Valid @ModelAttribute("pedido") Pedido pedido,
                             BindingResult result,
                             Model model) {

        if (result.hasErrors()) {
            model.addAttribute("usuarios", usuarioService.listar());
            model.addAttribute("modoEdicion", true);
            return "pedido-formulario";
        }

        pedidoService.actualizar(pedido.getIdPedido(), pedido);
        return "redirect:/pedido";
    }

    // Eliminar pedido
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Integer id) {
        if (id != null) {
            pedidoService.eliminar(id);
        }
        return "redirect:/pedido";
    }
}