## Tests

El proyecto incluye dos tipos de pruebas:

### Tests unitarios
implementados con JUnit, validan la lógica de negocio de forma aislada.

### Tests de integración
Realizados con `@DataJpaTest`, verifican el correcto funcionamiento de la capa de persistencia, incluyendo:
- Repositorios JPA
- Mapeo de entidades
- Interacción con la base de datos

### Estado de los tests

![Tests](https://github.com/AlvPru/mgsc-track-G7_L2/actions/workflows/build.yml/badge.svg)

---

## Calidad del codigo
Este proyecto utiliza SonarQube para analizar la calidad del código de forma continua, el análisis de calidad se ejecuta automáticamente en cada push mediante SonarQube.

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=AlvPru_mgsc-track-G7_L2&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=AlvPru_mgsc-track-G7_L2)

### Metricas monitorizadas.
- Cobertura de tests
- Bugs
- Vulnerabilidades
- Code Smells
- Duplicación de código

### 📈 Estado del proyecto

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=AlvPru_mgsc-track-G7_L2&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=AlvPru_mgsc-track-G7_L2)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=AlvPru_mgsc-track-G7_L2&metric=coverage)](https://sonarcloud.io/summary/new_code?id=AlvPru_mgsc-track-G7_L2)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=AlvPru_mgsc-track-G7_L2&metric=bugs)](https://sonarcloud.io/summary/new_code?id=AlvPru_mgsc-track-G7_L2)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=AlvPru_mgsc-track-G7_L2&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=AlvPru_mgsc-track-G7_L2)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=AlvPru_mgsc-track-G7_L2&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=AlvPru_mgsc-track-G7_L2)
[![Duplicated Lines](https://sonarcloud.io/api/project_badges/measure?project=AlvPru_mgsc-track-G7_L2&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=AlvPru_mgsc-track-G7_L2)

### Análisis completo

Puedes consultar el informe detallado aquí:  
👉 https://sonarcloud.io/project/overview?id=AlvPru_mgsc-track-G7_L2
