package com.lvlupgamer.pedidos.apipedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequest {
    private Long idUsuario;
    private Double total;
    private String direccion;
    private List<PedidoDetalleDTO> detalles;
}
