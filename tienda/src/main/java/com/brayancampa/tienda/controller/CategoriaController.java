package com.brayancampa.tienda.controller;

import com.brayancampa.tienda.Service.CategoriaService;
import com.brayancampa.tienda.entity.Categoria;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tienditaperrona/categoria")
public class CategoriaController {
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

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
}