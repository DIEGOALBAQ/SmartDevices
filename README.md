Proyecto realizado en Grupo

---

# Enlace a mi repositorio 

https://github.com/DIEGOALBAQ/SmartDevices


# SmartDevices

Sitio web para explorar y reseñar dispositivos inteligentes (celulares, portátiles, tablets) con panel de administración.

## Tecnologías

- Java 17 + Servlets (Jakarta EE)
- JSP + JSTL + Bootstrap 5
- MySQL 8 + JDBC
- Maven + Tomcat 11
- Gson para JSON

## Arquitectura

Proyecto en capas:
- **controller/** → Servlets (reciben peticiones)
- **service/** → Lógica de negocio
- **repository/** → Consultas SQL
- **model/** → Entidades
- **webapp/** → Vistas JSP

## Requisitos

- JDK 17+
- MySQL 8
- Tomcat 11
- NetBeans (o cualquier IDE con Maven)

## Instalación

1. Ejecutar los scripts SQL en `sql/` para crear la base de datos y llenarla con los datos
2. Abrir el proyecto en NetBeans y ejecutarlo en Tomcat.
4. Entrar a `http://localhost:8080/`.

## Acceso admin

- URL: `http://localhost:8080/admin/login`
- Usuario: `admin`
- Contraseña: `admin123`

## Funcionalidades

- Listado de dispositivos con filtros y búsqueda
- Detalle con reseñas y comentarios
- CRUD de dispositivos y marcas
- Login de administrador

