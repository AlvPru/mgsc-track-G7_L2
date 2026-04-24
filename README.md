## Tests

El proyecto incluye dos tipos de pruebas:

- **Tests unitarios**: implementados con JUnit, validan la lógica de negocio de forma aislada.
- **Tests de integración**: realizados con `@DataJpaTest`, verifican el correcto funcionamiento de la capa de persistencia (repositorios JPA y mapeo de entidades).

![Tests](https://github.com/AlvPru/mgsc-track-G7_L2/actions/workflows/build.yml/badge.svg)

## Calidad
Este proyecto utiliza SonarQube para analizar la calidad del código de forma continua, el análisis de calidad se ejecuta automáticamente en cada push mediante SonarQube.

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=AlvPru_mgsc-track-G7_L2&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=AlvPru_mgsc-track-G7_L2)

Se monitorizan:
- Cobertura de tests
- Code Smells
- Duplicidad del codigo
- Calidad del codigo

Puedes ver el análisis completo aquí: https://sonarcloud.io/project/overview?id=AlvPru_mgsc-track-G7_L2
