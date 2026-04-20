package com.brayancampa.tienda.Service;

import com.brayancampa.tienda.entity.Usuario;

import java.util.List;

public interface UsuarioService {
    List<Usuario> listar();
    Usuario obtenerPorId(Integer id);
    Usuario crear(Usuario usuario);
    Usuario actualizar(Integer id, Usuario usuario);
    void eliminar(Integer id);
    Usuario buscarPorNombre(String nombre);
}