-- ============================================================
-- Base de datos: smartdevices
-- Proyecto: Sitio web de dispositivos inteligentes
-- Arquitectura: Por capas (Servlet -> Service -> Repository -> BD)
-- ============================================================

-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS smartdevices
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE smartdevices;

-- ============================================================
-- Tabla: marca
-- Almacena las marcas de dispositivos (Apple, Samsung, etc.)
-- ============================================================
CREATE TABLE marca (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    nombre      VARCHAR(100) NOT NULL UNIQUE,
    pais        VARCHAR(50),
    logo_url    VARCHAR(255),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================
-- Tabla: tipo_dispositivo
-- Categorías: Celular, Portatil, Tablet, Smartwatch, etc.
-- ============================================================
CREATE TABLE tipo_dispositivo (
    id      INT AUTO_INCREMENT PRIMARY KEY,
    nombre  VARCHAR(50) NOT NULL UNIQUE
);

-- ============================================================
-- Tabla: dispositivo
-- Información principal de cada dispositivo inteligente
-- ============================================================
CREATE TABLE dispositivo (
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    marca_id            INT NOT NULL,
    tipo_id             INT NOT NULL,
    nombre              VARCHAR(200) NOT NULL,
    fecha_lanzamiento   DATE NOT NULL,
    precio              DECIMAL(10, 2),
    imagen_url          VARCHAR(255),
    resena              TEXT,          -- Reseña extensa del dispositivo
    sinopsis            VARCHAR(500),  -- Resumen corto
    especificaciones    TEXT,           -- Características técnicas
    fecha_creacion      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (marca_id) REFERENCES marca(id) ON DELETE RESTRICT,
    FOREIGN KEY (tipo_id)  REFERENCES tipo_dispositivo(id) ON DELETE RESTRICT,

    INDEX idx_fecha_lanzamiento (fecha_lanzamiento),
    INDEX idx_marca (marca_id),
    INDEX idx_tipo (tipo_id)
);

-- ============================================================
-- Tabla: comentario
-- Opiniones de usuarios sobre un dispositivo
-- ============================================================
CREATE TABLE comentario (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    dispositivo_id  INT NOT NULL,
    autor           VARCHAR(100) NOT NULL,
    comentario      TEXT NOT NULL,
    calificacion    INT DEFAULT 5,  -- Calificación de 1 a 5 estrellas
    fecha           TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (dispositivo_id) REFERENCES dispositivo(id) ON DELETE CASCADE,

    INDEX idx_dispositivo (dispositivo_id)
);

-- ============================================================
-- Tabla: admin_usuario
-- Usuarios administradores para gestionar el contenido
-- ============================================================
CREATE TABLE admin_usuario (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    usuario     VARCHAR(50) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    nombre      VARCHAR(100),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================
-- Vistas para consultas frecuentes (opcional, facilita reportes)
-- ============================================================
CREATE OR REPLACE VIEW v_dispositivo_completo AS
SELECT
    d.id,
    d.nombre,
    d.fecha_lanzamiento,
    d.precio,
    d.imagen_url,
    d.resena,
    d.sinopsis,
    d.especificaciones,
    m.nombre AS marca,
    m.pais   AS marca_pais,
    t.nombre AS tipo
FROM dispositivo d
INNER JOIN marca m ON d.marca_id = m.id
INNER JOIN tipo_dispositivo t ON d.tipo_id = t.id;
