package com.lvlupgamer.pedidos.apipedidos.controller;

import com.lvlupgamer.pedidos.apipedidos.dto.*;
import com.lvlupgamer.pedidos.apipedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<ApiResponse<PedidoResponse>> crear(@RequestBody PedidoRequest request){
        try {
            PedidoResponse pedido = pedidoService.crearPedido(request);
            return ResponseEntity.ok(ApiResponse.<PedidoResponse>builder()
                    .success(true)
                    .message("Pedido creado exitosamente")
                    .data(pedido)
                    .code(200)
                    .build());
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.<PedidoResponse>builder()
                    .success(false)
                    .message(e.getMessage())
                    .code(400)
                    .build());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PedidoResponse>> obtenerPorId(@PathVariable Long id){
        try {
            PedidoResponse pedido = pedidoService.obtenerPedidoPorId(id);
            return ResponseEntity.ok(ApiResponse.<PedidoResponse>builder()
                    .success(true)
                    .message("Pedido encontrado")
                    .data(pedido)
                    .code(200)
                    .build());
        } catch(Exception e) {
            return ResponseEntity.status(404).body(ApiResponse.<PedidoResponse>builder()
                    .success(false)
                    .message(e.getMessage())
                    .code(404)
                    .build());
        }
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<ApiResponse<List<PedidoResponse>>> obtenerPorUsuario(@PathVariable Long idUsuario){
        try {
            List<PedidoResponse> pedidos = pedidoService.obtenerPedidosPorUsuario(idUsuario);
            return ResponseEntity.ok(ApiResponse.<List<PedidoResponse>>builder()
                    .success(true)
                    .message("Pedidos obtenidos")
                    .data(pedidos)
                    .code(200)
                    .build());
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.<List<PedidoResponse>>builder()
                    .success(false)
                    .message(e.getMessage())
                    .code(400)
                    .build());
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PedidoResponse>>> obtenerTodos(){
        try {
            List<PedidoResponse> pedidos = pedidoService.obtenerTodos();
            return ResponseEntity.ok(ApiResponse.<List<PedidoResponse>>builder()
                    .success(true)
                    .message("Todos los pedidos")
                    .data(pedidos)
                    .code(200)
                    .build());
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.<List<PedidoResponse>>builder()
                    .success(false)
                    .message(e.getMessage())
                    .code(400)
                    .build());
        }
    }

    @PutMapping("/{id}/cancelar")
public ResponseEntity<ApiResponse<PedidoResponse>> cancelarPedido(@PathVariable Long id) {
    try {
        PedidoResponse pedido = pedidoService.cancelarPedido(id);
        return ResponseEntity.ok(ApiResponse.<PedidoResponse>builder()
            .success(true)
            .message("Pedido cancelado correctamente")
            .data(pedido)
            .code(200)
            .build());
    } catch (Exception e) {
        return ResponseEntity.status(404).body(ApiResponse.<PedidoResponse>builder()
            .success(false)
            .message(e.getMessage())
            .code(404)
            .build());
    }
}

}
