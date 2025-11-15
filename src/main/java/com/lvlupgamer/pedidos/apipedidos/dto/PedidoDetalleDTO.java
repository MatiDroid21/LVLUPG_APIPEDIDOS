package com.lvlupgamer.pedidos.apipedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDetalleDTO {
    private Long idProducto;
    private Integer cantidad;
    private Double precioUnitario;
}
