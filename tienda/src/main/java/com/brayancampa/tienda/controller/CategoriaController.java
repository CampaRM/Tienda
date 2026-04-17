package com.brayancampa.tienda.controller;

import com.brayancampa.tienda.Service.CategoriaService;
import com.brayancampa.tienda.entity.Categoria;
import com.brayancampa.tienda.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    /*
    @GetMapping("/get")
    public List<Categoria> listar(){
        return categoriaService.listar();
    }

    @GetMapping("/get/{id}")
    public Categoria obtenerPorId(@PathVariable Integer id){
        return categoriaService.obtenerPorId(id);
    }

    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public Categoria crear(@RequestBody Categoria categoria){
        return categoriaService.crear(categoria);
    }

    @DeleteMapping("/delete/{id}")
    public void eliminar(@PathVariable Integer id){
        categoriaService.eliminar(id);
    }

    @PutMapping("/put/{id}")
    public Categoria actualizar(@PathVariable Integer id, @RequestBody Categoria categoria){
        return categoriaService.actualizar(id, categoria);
    }
    */

    // Método para listar los registros
    @GetMapping
    public String lista(Model model) {
        model.addAttribute("categorias", categoriaService.listar());
        return "categoria";
    }

    // Metodo para abrir una vista
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model){
        model.addAttribute("categoria",new Categoria());
        model.addAttribute("modoEdicion", false);
        return "categoria-formulario";
    }

    // Método para crear un nuevo usuario
    @PostMapping("/guardar")
    public String crear(@Valid @ModelAttribute("categoria") Categoria categoria, Model model, BindingResult result) {

        if (result.hasErrors()) {
            model.addAttribute("modeEdicion", false);
            return "categoria-formulario";
        }

        categoriaService.crear(categoria);
        return "redirect:/categoria";
    }

    // Método para eliminar un usuario
    @GetMapping("/eliminar/{id}")
    public String eliminarUser(@PathVariable("id") Integer id) {
        if (id != null) {
            categoriaService.eliminar(id);
        }
        return "redirect:/categoria";
    }

    // Método para actualizar un usuario
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Integer id, Model model) {
        Categoria categoria = categoriaService.obtenerPorId(id);
        model.addAttribute("categoria", categoria);
        model.addAttribute("modoEdicion", true);
        return "categoria-formulario";
    }

    @PostMapping("/actualizar")
    public String actualizar(@Valid @ModelAttribute("categoria") Categoria categoria, BindingResult result) {
        if (result.hasErrors()) {
            return "categoria-formulario";
        }

        categoriaService.actualizar(categoria.getIdCategoria(), categoria);
        return "redirect:/categoria";
    }


}