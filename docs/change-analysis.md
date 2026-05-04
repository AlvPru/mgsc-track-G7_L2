# Change Analysis — Allow Reopen and State History

**Issue:** Change Request: Allow reopen and state history  
**Rama:** feature/reopen-and-history  
**Fecha:** 2026-05-04

---

## 1. ¿Qué métodos del dominio se ven afectados?

| Componente | Cambio |
|---|---|
| `Solicitud.setEstado()` | Debe registrar cada cambio en el historial |
| `Solicitud` | Nuevo método `reabrir()` |
| `EstadoSolicitud` | No requiere nuevo valor; la reapertura vuelve a `EN_PROCESO` |

## 2. ¿Qué reglas actuales cambian?

- **Antes:** Una solicitud CERRADA era un estado terminal.  
- **Después:** Una solicitud CERRADA puede volver a `EN_PROCESO` mediante `reabrir()`.  
- El resto de transiciones no cambia: PENDIENTE → EN_PROCESO → CERRADA sigue siendo válido.

## 3. ¿Qué tests deberían romperse?

Ningún test existente debe romperse:
- Los tests de `setEstado()` siguen válidos (el setter sigue funcionando igual).
- Los tests de `asignarTecnico()` y `cambiarEstado()` no tocan la lógica de reapertura.
- El historial se añade de forma aditiva; no elimina comportamiento previo.

## 4. ¿Qué parte del modelo debe extenderse?

- **`Solicitud`**: añadir `List<CambioEstado> historial`, inicializado en el constructor con el estado `PENDIENTE`. Modificar `setEstado()` para que registre cada transición.  
- **`CambioEstado`** (nuevo): Value Object con `EstadoSolicitud estado` y `LocalDateTime fecha`.

## 5. ¿Qué impacto tiene en persistencia?

### Decisión técnica: el historial NO se persiste en esta iteración.

**Justificación:**
- El sistema usa `SolicitudRepositoryMemoria` como adaptador de persistencia; añadir persistencia del historial requeriría una nueva tabla / colección.
- El `SolicitudEntity` JPA mapea los campos actuales; extenderlo para historial implicaría una relación `@OneToMany` adicional con su propia tabla `cambio_estado`.
- En esta iteración el historial vive solo en memoria durante el ciclo de vida del objeto. Esto cumple el requisito funcional sin introducir deuda de esquema.

**Impacto en mantenibilidad:**
- Si en el futuro se requiere auditoría persistente, la extensión es localizada: añadir `CambioEstadoEntity` + relación en `SolicitudEntity` + migración de BD.
- El dominio ya está preparado (`CambioEstado` es un Value Object limpio); no habrá refactorización de dominio al persistir.
