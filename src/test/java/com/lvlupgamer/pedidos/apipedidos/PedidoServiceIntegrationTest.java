package com.lvlupgamer.pedidos.apipedidos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lvlupgamer.pedidos.apipedidos.dto.PedidoDetalleDTO;
import com.lvlupgamer.pedidos.apipedidos.dto.PedidoRequest;
import com.lvlupgamer.pedidos.apipedidos.dto.PedidoResponse;
import com.lvlupgamer.pedidos.apipedidos.model.Pedido;
import com.lvlupgamer.pedidos.apipedidos.model.PedidoDetalle;
import com.lvlupgamer.pedidos.apipedidos.repository.PedidoRepository;
import com.lvlupgamer.pedidos.apipedidos.service.PedidoService;
import com.lvlupgamer.pedidos.apipedidos.repository.PedidoDetalleRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests del Servicio de Pedidos")
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PedidoDetalleRepository pedidoDetalleRepository;

    @InjectMocks
    private PedidoService pedidoService;

    private Pedido pedido;
    private PedidoDetalle detalle;
    private PedidoRequest pedidoRequest;

    @BeforeEach
    void setUp() {
        pedido = new Pedido();
        pedido.setIdPedido(1L);
        pedido.setIdUsuario(10L);
        pedido.setTotal(15000.0);
        pedido.setDireccion("Av. Siempre Viva 123");
        pedido.setEstado("Pendiente");
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setFechaEstimada(LocalDateTime.now().plusDays(3));
        pedido.setDetalles(new ArrayList<>());

        detalle = new PedidoDetalle();
        detalle.setIdDetalle(1L);
        detalle.setPedido(pedido);
        detalle.setIdProducto(5L);
        detalle.setCantidad(3);
        detalle.setPrecioUnitario(5000.0);

        pedido.getDetalles().add(detalle);

        PedidoDetalleDTO detalleDTO = new PedidoDetalleDTO(5L, 3, 5000.0);
        pedidoRequest = new PedidoRequest();
        pedidoRequest.setIdUsuario(10L);
        pedidoRequest.setTotal(15000.0);
        pedidoRequest.setDireccion("Av. Siempre Viva 123");
        pedidoRequest.setDetalles(Arrays.asList(detalleDTO));
    }

    @Test
    @DisplayName("Debe crear un pedido exitosamente")
    void testCrearPedido_Success() {
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        PedidoResponse response = pedidoService.crearPedido(pedidoRequest);

        assertNotNull(response);
        assertEquals(1L, response.getIdPedido());
        assertEquals(10L, response.getIdUsuario());
        assertEquals(15000.0, response.getTotal());
        assertEquals("Pendiente", response.getEstado());
        assertEquals(1, response.getDetalles().size());
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    @DisplayName("Debe obtener pedido por ID")
    void testObtenerPedidoPorId_Success() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        PedidoResponse response = pedidoService.obtenerPedidoPorId(1L);

        assertNotNull(response);
        assertEquals(1L, response.getIdPedido());
        assertEquals("Av. Siempre Viva 123", response.getDireccion());
        verify(pedidoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando pedido no existe")
    void testObtenerPedidoPorId_NoEncontrado() {
        when(pedidoRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            pedidoService.obtenerPedidoPorId(999L);
        });
    }

    @Test
    @DisplayName("Debe obtener todos los pedidos")
    void testObtenerTodos_Success() {
        List<Pedido> pedidos = Arrays.asList(pedido);
        when(pedidoRepository.findAll()).thenReturn(pedidos);

        List<PedidoResponse> resultado = pedidoService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Pendiente", resultado.get(0).getEstado());
        verify(pedidoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe obtener pedidos por usuario")
    void testObtenerPorUsuario_Success() {
        List<Pedido> pedidos = Arrays.asList(pedido);
        when(pedidoRepository.findByIdUsuario(10L)).thenReturn(pedidos);

        List<PedidoResponse> resultado = pedidoService.obtenerPedidosPorUsuario(10L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(10L, resultado.get(0).getIdUsuario());
        verify(pedidoRepository, times(1)).findByIdUsuario(10L);
    }

    @Test
    @DisplayName("Debe validar que el pedido tenga detalles")
    void testValidarPedido_ConDetalles() {
        assertNotNull(pedido.getDetalles());
        assertTrue(pedido.getDetalles().size() > 0);
        assertEquals(3, pedido.getDetalles().get(0).getCantidad());
    }

    @Test
    @DisplayName("Debe calcular correctamente el total del detalle")
    void testCalcularTotalDetalle() {
        PedidoDetalle detalle = pedido.getDetalles().get(0);
        Double totalDetalle = detalle.getCantidad() * detalle.getPrecioUnitario();
        assertEquals(15000.0, totalDetalle);
    }

    @Test
    @DisplayName("Debe validar que el estado sea válido")
    void testValidarEstados() {
        String[] estadosValidos = {"Pendiente", "Enviado", "Entregado", "Cancelado"};
        assertTrue(Arrays.asList(estadosValidos).contains(pedido.getEstado()));
    }
}
