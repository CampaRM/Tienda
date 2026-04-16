package com.brayancampa.tienda.Service;

import com.brayancampa.tienda.entity.DetallePedido;

import java.util.List;

public interface DetallePedidoService {
    List<DetallePedido> listar();
    DetallePedido obtenerPorId(Integer id);
    DetallePedido crear(DetallePedido detallePedido);
    DetallePedido actualizar(Integer id, DetallePedido detallePedido);
    void eliminar(Integer id);
}