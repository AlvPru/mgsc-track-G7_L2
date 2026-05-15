# Casos de Uso — API Solicitudes

Casos de prueba para verificar la lógica de negocio desde Swagger UI (`/swagger-ui.html`).

---

## Caso 1 – Crear solicitud correctamente

**Request:**
```
POST /api/solicitudes
{
  "descripcion": "Avería en instalación eléctrica",
  "clienteId": 1
}
```
**Respuesta esperada:** `200 OK`
```json
{
  "id": 1,
  "descripcion": "Avería en instalación eléctrica",
  "estado": "PENDIENTE",
  "clienteNombre": "Carlos"
}
```

---

## Caso 2 – Consultar solicitud existente

**Request:**
```
GET /api/solicitudes/1
```
**Respuesta esperada:** `200 OK` con todos los campos de la solicitud.

---

## Caso 3 – Consultar solicitud inexistente

**Request:**
```
GET /api/solicitudes/9999
```
**Respuesta esperada:** `404 Not Found`

---

## Caso 4 – Asignar técnico activo

**Precondición:** Solicitud en estado `PENDIENTE`, técnico activo existente.

**Request:**
```
PUT /api/solicitudes/1/tecnico
{
  "tecnicoId": 1
}
```
**Respuesta esperada:** `200 OK`
```json
{
  "estado": "EN_PROCESO",
  "tecnicoNombre": "Juan"
}
```

---

## Caso 5 – Asignar técnico inactivo

**Precondición:** Técnico existe pero está inactivo.

**Request:**
```
PUT /api/solicitudes/1/tecnico
{
  "tecnicoId": 2
}
```
**Respuesta esperada:** `400 Bad Request`

> Regla: no se puede asignar un técnico inactivo a una solicitud.

---

## Caso 6 – Cambiar estado a valor válido

**Request:**
```
PUT /api/solicitudes/1/estado
{
  "estado": "EN_PROCESO"
}
```
**Respuesta esperada:** `200 OK` con `"estado": "EN_PROCESO"`

---

## Caso 7 – Cambiar estado a valor inválido

**Request:**
```
PUT /api/solicitudes/1/estado
{
  "estado": "ESTADO_QUE_NO_EXISTE"
}
```
**Respuesta esperada:** `400 Bad Request`

> Regla: solo se admiten los valores `PENDIENTE`, `EN_PROCESO`, `CERRADA`.

---

## Caso 8 – Reabrir solicitud cerrada

**Precondición:** Solicitud en estado `CERRADA`.

**Request:**
```
PATCH /api/solicitudes/1/reabrir
```
**Respuesta esperada:** `200 OK`
```json
{
  "estado": "EN_PROCESO"
}
```

> Regla: solo se pueden reabrir solicitudes en estado `CERRADA`.

---

## Caso 9 – Reabrir solicitud que no está cerrada

**Precondición:** Solicitud en estado `PENDIENTE` o `EN_PROCESO`.

**Request:**
```
PATCH /api/solicitudes/1/reabrir
```
**Respuesta esperada:** `400 Bad Request`

> Regla: no se puede reabrir una solicitud que no esté cerrada.

---

## Caso 10 – Listar solicitudes (prioridad premium)

**Request:**
```
GET /api/solicitudes
```
**Respuesta esperada:** `200 OK` con lista donde los clientes `PREMIUM` aparecen primero.
