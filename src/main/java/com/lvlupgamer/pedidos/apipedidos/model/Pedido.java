package com.lvlupgamer.pedidos.apipedidos.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

      @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pedidos")
    @SequenceGenerator(name = "seq_pedidos", sequenceName = "seq_pedidos", allocationSize = 1)
    @Column(name = "id_pedido")
    private Long idPedido;

    @Column(name = "usuario_id", nullable = false)
    private Long idUsuario;

    @Column(nullable = false)
    private Double total;

    private String direccion;

    private String estado = "Pendiente";

    @Column(name = "fecha_pedido")
    private LocalDateTime fechaPedido = LocalDateTime.now();

    @Column(name = "fecha_estimada")
    private LocalDateTime fechaEstimada;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PedidoDetalle> detalles;
}
