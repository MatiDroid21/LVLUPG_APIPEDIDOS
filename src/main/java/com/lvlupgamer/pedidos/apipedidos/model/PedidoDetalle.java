package com.lvlupgamer.pedidos.apipedidos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pedidos_detalle")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDetalle {

   @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pedidos_detalle")
    @SequenceGenerator(name = "seq_pedidos_detalle", sequenceName = "seq_pedidos_detalle", allocationSize = 1)
    @Column(name = "id_detalle")
    private Long idDetalle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    @Column(name = "id_producto", nullable = false)
    private Long idProducto;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false)
    private Double precioUnitario;
}
