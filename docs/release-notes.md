# Notas de Lanzamiento - Versión 1.0.1

## Justificación del Versionado Semántico
De acuerdo con las directrices de la asignatura y el estándar de gestión de versiones (MAJOR.MINOR.PATCH), se establece el incremento a la versión **1.0.1**.

### Análisis de Impacto de los Cambios:
* **MAJOR (1 -> 1):** No se han realizado modificaciones estructurales ni rupturas en los contratos de la API existntes.
* **MINOR (0 -> 0):** No se han añadido nuevas funcionalidades de negocio para el usuario final.
* **PATCH (0 -> 1):** Los cambios introducidos se limitan estrictamente a la infraestructura de automatización (GitHub Actions) para el flujo de liberación del software y la contenedorización del sistema mediante Docker. Al no alterar la logica del dominio se garantiza la total compatibilidad hacia atras.