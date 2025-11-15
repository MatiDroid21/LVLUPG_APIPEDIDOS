package com.lvlupgamer.pedidos.apipedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponse {
    private Long idPedido;
    private Long idUsuario;
    private Double total;
    private String direccion;
    private String estado;
    private LocalDateTime fechaPedido;
    private LocalDateTime fechaEstimada;
    private List<PedidoDetalleDTO> detalles;
}
