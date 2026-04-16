package com.brayancampa.tienda.Service;

import com.brayancampa.tienda.entity.DetallePedido;
import com.brayancampa.tienda.repository.DetallePedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetallePedidoServiceImpl implements DetallePedidoService{

    private final DetallePedidoRepository detallePedidoRepository;

    public DetallePedidoServiceImpl(DetallePedidoRepository detallePedidoRepository) {
        this.detallePedidoRepository = detallePedidoRepository;
    }

    @Override
    public List<DetallePedido> listar() {
        return detallePedidoRepository.findAll();
    }

    @Override
    public DetallePedido obtenerPorId(Integer id) {
        return detallePedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("DetallePedido no encontrado: " + id));
    }

    @Override
    public DetallePedido crear(DetallePedido detallePedido) {
        return detallePedidoRepository.save(detallePedido);
    }

    @Override
    public DetallePedido actualizar(Integer id, DetallePedido detallePedido) {
        detallePedido.setIdDetalle(id);
        return detallePedidoRepository.save(detallePedido);
    }

    @Override
    public void eliminar(Integer id) {
        detallePedidoRepository.deleteById(id);
    }
}