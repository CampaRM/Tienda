package com.brayancampa.tienda.repository;

import com.brayancampa.tienda.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
