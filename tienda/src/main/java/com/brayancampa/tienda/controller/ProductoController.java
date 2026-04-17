package com.brayancampa.tienda.controller;

import com.brayancampa.tienda.Service.CategoriaService;
import com.brayancampa.tienda.Service.ProductoService;
import com.brayancampa.tienda.entity.Categoria;
import com.brayancampa.tienda.entity.Producto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    public ProductoController(ProductoService productoService, CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("productos", productoService.listar());
        return "producto";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model){
        model.addAttribute("producto", new Producto());
        model.addAttribute("listaCategorias", categoriaService.listar());
        model.addAttribute("modoEdicion", false);
        return "producto-formulario";
    }

    @PostMapping("/guardar")
    public String crear(@Valid @ModelAttribute("producto") Producto producto,
                        BindingResult result,
                        Model model) {

        if (result.hasErrors()) {
            model.addAttribute("modoEdicion", false);
            return "producto-formulario";
        }

        productoService.crear(producto);
        return "redirect:/producto";
    }

    // Editar producto
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Integer id, Model model) {
        Producto producto = productoService.obtenerPorId(id);
        model.addAttribute("producto", producto);
        model.addAttribute("modoEdicion", true);
        return "producto-formulario";
    }

    // Actualizar producto
    @PostMapping("/actualizar")
    public String actualizar(@Valid @ModelAttribute("producto") Producto producto,
                             BindingResult result,
                             Model model) {

        if (result.hasErrors()) {
            model.addAttribute("modoEdicion", true);
            return "producto-formulario";
        }

        productoService.actualizar(producto.getIdProducto(), producto);
        return "redirect:/producto";
    }

    // Eliminar producto
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Integer id) {
        if (id != null) {
            productoService.eliminar(id);
        }
        return "redirect:/producto";
    }
}