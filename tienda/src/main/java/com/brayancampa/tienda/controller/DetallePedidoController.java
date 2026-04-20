package com.brayancampa.tienda.controller;

import com.brayancampa.tienda.Service.DetallePedidoService;
import com.brayancampa.tienda.Service.PedidoService;
import com.brayancampa.tienda.Service.ProductoService;
import com.brayancampa.tienda.entity.DetallePedido;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/detalle-pedido")
public class DetallePedidoController {

    private final DetallePedidoService detallePedidoService;
    private final ProductoService productoService;
    private final PedidoService pedidoService;

    public DetallePedidoController(DetallePedidoService detallePedidoService,ProductoService productoService, PedidoService pedidoService) {
        this.detallePedidoService = detallePedidoService;
        this.productoService = productoService;
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("detalles", detallePedidoService.listar());
        return "detalle-pedido";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model){
        model.addAttribute("detalle", new DetallePedido());
        model.addAttribute("productos", productoService.listar());
        model.addAttribute("pedidos", pedidoService.listar());
        model.addAttribute("modoEdicion", false);
        return "detalle-pedido-formulario";
    }

    @PostMapping("/guardar")
    public String crear(@Valid @ModelAttribute("detalle") DetallePedido detalle,
                        BindingResult result,
                        Model model) {
        if (result.hasErrors()) {
            model.addAttribute("productos", productoService.listar());
            model.addAttribute("pedidos", pedidoService.listar());
            model.addAttribute("modoEdicion", false);
            return "detalle-pedido-formulario";
        }
        detallePedidoService.crear(detalle);
        return "redirect:/detalle-pedido";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("detalle", detallePedidoService.obtenerPorId(id));
        model.addAttribute("productos", productoService.listar());
        model.addAttribute("pedidos", pedidoService.listar());
        model.addAttribute("modoEdicion", true);
        return "detalle-pedido-formulario";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Integer id,
                             @Valid @ModelAttribute("detalle") DetallePedido detalle,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("productos", productoService.listar());
            model.addAttribute("pedidos", pedidoService.listar());
            model.addAttribute("modoEdicion", true);
            return "detalle-pedido-formulario";
        }
        detallePedidoService.actualizar(id, detalle);
        return "redirect:/detalle-pedido";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        detallePedidoService.eliminar(id);
        return "redirect:/detalle-pedido";
    }
}