package com.lvlupgamer.pedidos.apipedidos.service;

import com.lvlupgamer.pedidos.apipedidos.dto.*;
import com.lvlupgamer.pedidos.apipedidos.model.*;
import com.lvlupgamer.pedidos.apipedidos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;


   public PedidoResponse crearPedido(PedidoRequest request) {
    Pedido pedido = new Pedido();
    pedido.setIdUsuario(request.getIdUsuario());
    pedido.setDireccion(request.getDireccion());
    pedido.setEstado("Pendiente");
    pedido.setFechaPedido(LocalDateTime.now());
    pedido.setFechaEstimada(LocalDateTime.now().plusDays(3));

    // Use un array para mutabilidad dentro de lambda
    final double[] total = {0};

    List<PedidoDetalle> detalles = request.getDetalles()
        .stream()
        .map(detalleDTO -> {
            PedidoDetalle detalle = new PedidoDetalle();
            detalle.setPedido(pedido);
            detalle.setIdProducto(detalleDTO.getIdProducto());
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecioUnitario(detalleDTO.getPrecioUnitario());

            // Sumar total dentro del lambda
            total[0] += detalleDTO.getCantidad() * detalleDTO.getPrecioUnitario();

            return detalle;
        }).collect(Collectors.toList());

    pedido.setTotal(total[0]);
    pedido.setDetalles(detalles);

    Pedido pedidoGuardado = pedidoRepository.save(pedido);

    return convertirRespuesta(pedidoGuardado);
}


    public PedidoResponse obtenerPedidoPorId(Long idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        return convertirRespuesta(pedido);
    }

    public List<PedidoResponse> obtenerPedidosPorUsuario(Long idUsuario) {
        List<Pedido> pedidos = pedidoRepository.findByIdUsuario(idUsuario);
        return pedidos.stream()
            .map(this::convertirRespuesta)
            .collect(Collectors.toList());
    }

    public List<PedidoResponse> obtenerTodos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidos.stream()
            .map(this::convertirRespuesta)
            .collect(Collectors.toList());
    }

    private PedidoResponse convertirRespuesta(Pedido pedido) {
        List<PedidoDetalleDTO> detallesDTO = pedido.getDetalles().stream()
            .map(d -> new PedidoDetalleDTO(d.getIdProducto(), d.getCantidad(), d.getPrecioUnitario()))
            .collect(Collectors.toList());

        return new PedidoResponse(
            pedido.getIdPedido(),
            pedido.getIdUsuario(),
            pedido.getTotal(),
            pedido.getDireccion(),
            pedido.getEstado(),
            pedido.getFechaPedido(),
            pedido.getFechaEstimada(),
            detallesDTO
        );
    }

    public PedidoResponse cancelarPedido(Long idPedido) {
    Pedido pedido = pedidoRepository.findById(idPedido)
        .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    pedido.setEstado("Cancelado");
    Pedido actualizado = pedidoRepository.save(pedido);
    return convertirRespuesta(actualizado);
}

}
