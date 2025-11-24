package com.lvlupgamer.pedidos.apipedidos.controller;

import com.lvlupgamer.pedidos.apipedidos.dto.*;
import com.lvlupgamer.pedidos.apipedidos.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse as SwaggerApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Pedidos", description = "API para gestión de pedidos y compras")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    @Operation(
        summary = "Crear nuevo pedido",
        description = "Crea un nuevo pedido con los productos del carrito del usuario. El pedido se genera en estado 'Pendiente'"
    )
    @ApiResponses(value = {
        @SwaggerApiResponse(
            responseCode = "200",
            description = "Pedido creado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PedidoResponse.class)
            )
        ),
        @SwaggerApiResponse(
            responseCode = "400",
            description = "Datos inválidos o error en la creación"
        ),
        @SwaggerApiResponse(
            responseCode = "404",
            description = "Usuario no encontrado"
        ),
        @SwaggerApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
        )
    })
    public ResponseEntity<ApiResponse<PedidoResponse>> crear(
        @Parameter(
            description = "Datos del pedido a crear (ID usuario, dirección, detalles)",
            required = true
        )
        @RequestBody PedidoRequest request
    ) {
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
    @Operation(
        summary = "Obtener pedido por ID",
        description = "Retorna los detalles completos de un pedido específico incluyendo todos sus items"
    )
    @ApiResponses(value = {
        @SwaggerApiResponse(
            responseCode = "200",
            description = "Pedido encontrado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PedidoResponse.class)
            )
        ),
        @SwaggerApiResponse(
            responseCode = "404",
            description = "Pedido no encontrado"
        ),
        @SwaggerApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
        )
    })
    public ResponseEntity<ApiResponse<PedidoResponse>> obtenerPorId(
        @Parameter(description = "ID del pedido", required = true, example = "1")
        @PathVariable Long id
    ) {
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
    @Operation(
        summary = "Obtener pedidos de un usuario",
        description = "Retorna todos los pedidos realizados por un usuario específico, ordenados por fecha"
    )
    @ApiResponses(value = {
        @SwaggerApiResponse(
            responseCode = "200",
            description = "Pedidos obtenidos exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PedidoResponse.class)
            )
        ),
        @SwaggerApiResponse(
            responseCode = "400",
            description = "Datos inválidos"
        ),
        @SwaggerApiResponse(
            responseCode = "404",
            description = "Usuario no encontrado"
        ),
        @SwaggerApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
        )
    })
    public ResponseEntity<ApiResponse<List<PedidoResponse>>> obtenerPorUsuario(
        @Parameter(description = "ID del usuario", required = true, example = "1")
        @PathVariable Long idUsuario
    ) {
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
    @Operation(
        summary = "Obtener todos los pedidos",
        description = "Retorna la lista completa de todos los pedidos del sistema (Admin)"
    )
    @ApiResponses(value = {
        @SwaggerApiResponse(
            responseCode = "200",
            description = "Pedidos obtenidos exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PedidoResponse.class)
            )
        ),
        @SwaggerApiResponse(
            responseCode = "400",
            description = "Error en la solicitud"
        ),
        @SwaggerApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
        )
    })
    public ResponseEntity<ApiResponse<List<PedidoResponse>>> obtenerTodos() {
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
    @Operation(
        summary = "Cancelar pedido",
        description = "Cancela un pedido específico. Solo se pueden cancelar pedidos en estado 'Pendiente'"
    )
    @ApiResponses(value = {
        @SwaggerApiResponse(
            responseCode = "200",
            description = "Pedido cancelado correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PedidoResponse.class)
            )
        ),
        @SwaggerApiResponse(
            responseCode = "400",
            description = "El pedido no puede ser cancelado (ya fue enviado o entregado)"
        ),
        @SwaggerApiResponse(
            responseCode = "404",
            description = "Pedido no encontrado"
        ),
        @SwaggerApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
        )
    })
    public ResponseEntity<ApiResponse<PedidoResponse>> cancelarPedido(
        @Parameter(description = "ID del pedido a cancelar", required = true, example = "1")
        @PathVariable Long id
    ) {
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
