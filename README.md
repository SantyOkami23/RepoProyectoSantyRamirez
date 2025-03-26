# Sistema de Reservas de Hotel - Documentación Técnica

![Java](https://img.shields.io/badge/Java-17%2B-blue)
![License](https://img.shields.io/badge/License-MIT-green)

## Índice

1. [Introducción](#introducción)
2. [Requisitos del Sistema](#requisitos-del-sistema)
3. [Instalación y Configuración](#instalación-y-configuración)
4. [Arquitectura del Sistema](#arquitectura-del-sistema)
5. [Patrones de Diseño](#patrones-de-diseño)
6. [Uso de la API](#uso-de-la-api)
7. [Manejo de Excepciones](#manejo-de-excepciones)
8. [Ejemplos de Uso](#ejemplos-de-uso)
9. [Pruebas](#pruebas)
10. [Extensión del Sistema](#extensión-del-sistema)
11. [Limitaciones](#limitaciones)
12. [Contribución](#contribución)
13. [Licencia](#licencia)

## Introducción

El Sistema de Reservas de Hotel es una aplicación Java que permite gestionar:
- Registro de habitaciones (Individual, Doble, Suite)
- Estados de habitación (Disponible, Ocupada, Mantenimiento)
- Reservas y liberación de habitaciones
- Estrategias dinámicas de precios

## Requisitos del Sistema

- **Java JDK**: Versión 11 o superior
- **Memoria**: Mínimo 512MB RAM
- **Sistema Operativo**: Cualquier sistema compatible con Java
- **Dependencias**:
  - JUnit 5 (para pruebas)
  - Maven (opcional)

## Instalación y Configuración

### Método 1: Usando Maven

```bash
git clone https://github.com/tu-usuario/hotel-reservation-system.git
cd hotel-reservation-system
mvn clean install
