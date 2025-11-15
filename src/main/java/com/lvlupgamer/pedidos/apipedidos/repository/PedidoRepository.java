package com.lvlupgamer.pedidos.apipedidos.repository;

import com.lvlupgamer.pedidos.apipedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByIdUsuario(Long idUsuario);
}
