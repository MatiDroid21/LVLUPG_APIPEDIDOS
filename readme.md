# LVLUPG_APIPEDIDOS

API para la gestión de pedidos y sus detalles en LevelUpGamer DuocUC.

---

## Información General

- **Lenguaje:** Java + Spring Boot
- **Puerto por defecto:** 8083 (ajustar según configuración)
- **Base URL:** `http://localhost:8083/api/pedidos`

---

## Estructura del DTO Pedido

{
"idPedido": 1,
"idUsuario": 123,
"fechaCreacion": "2025-11-15T10:00:00",
"total": 1200.50,
"direccion": "Calle Falsa 123",
"estado": "CREADO",
"detalles": [
{
"idProducto": 10,
"cantidad": 2,
"precioUnitario": 300.25
},
{
"idProducto": 11,
"cantidad": 1,
"precioUnitario": 600.00
}
]
}

text

---

## Endpoints

### Crear pedido

- **POST** `/api/pedidos`
- **Tipo:** JSON
- **Body ejemplo:**
{
"idUsuario": 123,
"total": 1200.50,
"direccion": "Calle Falsa 123",
"detalles": [
{ "idProducto": 10, "cantidad": 2, "precioUnitario": 300.25 },
{ "idProducto": 11, "cantidad": 1, "precioUnitario": 600.00 }
]
}

text
- **Respuesta exitosa:**
{
"success": true,
"message": "Pedido creado exitosamente",
"data": { ...pedido creado con ID y fechas... },
"code": 200
}

text

---

### Cancelar pedido

- **PUT** `/api/pedidos/{id}/cancelar`
- **Body:** ninguno
- **Respuesta:**
{
"success": true,
"message": "Pedido cancelado correctamente",
"code": 200
}

text

---

### Obtener pedido por ID

- **GET** `/api/pedidos/{id}`
- **Respuesta:** pedido con detalles

---

### Obtener pedidos de un usuario

- **GET** `/api/pedidos/usuario/{idUsuario}`
- **Respuesta:** lista de pedidos realizados por usuario

---

### Listar todos los pedidos

- **GET** `/api/pedidos`
- **Respuesta:** lista completa

---

## Ejemplos de pruebas con curl

Crear pedido:

curl -X POST http://localhost:8083/api/pedidos
-H "Content-Type: application/json"
-d '{
"idUsuario": 123,
"total": 1200.50,
"direccion": "Calle Falsa 123",
"detalles": [{"idProducto":10,"cantidad":2,"precioUnitario":300.25},{"idProducto":11,"cantidad":1,"precioUnitario":600.00}]
}'

text

Cancelar pedido:

curl -X PUT http://localhost:8083/api/pedidos/1/cancelar

text

---

## Validaciones y consideraciones

- `total` debe ser igual a suma de detalles.
- `idUsuario` debe existir en usuarios.
- `estado` cambia según acciones (CREADO, CANCELADO, ENTREGADO, etc.).
- Se debe validar stock antes de crear pedido (según lógica de negocio).
- Las fechas y estados son manejados internamente.

---

## Notas finales

- Todas las respuestas usarán el wrapper común ApiResponse con códigos y mensajes claros.
- En caso de errores, el campo `message` indicará el motivo.
- Usar Postman o curl para pruebas durante el desarrollo es recomendado.

---
