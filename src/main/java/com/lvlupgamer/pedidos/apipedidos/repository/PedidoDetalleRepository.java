package com.lvlupgamer.pedidos.apipedidos.repository;

import com.lvlupgamer.pedidos.apipedidos.model.PedidoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoDetalleRepository extends JpaRepository<PedidoDetalle, Long> {}
