package com.smartdevices.repository;

import com.smartdevices.model.Dispositivo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio: DispositivoRepository
 * Capa: Repository (acceso a datos)
 *
 * Responsabilidad:
 *   - Ejecutar consultas SQL sobre la tabla "dispositivo".
 *   - Incluye filtros por marca, tipo, búsqueda de texto y ordenamiento.
 *
 * NOTA: Este repositorio hace JOIN con marca y tipo_dispositivo para
 * llenar los campos marcaNombre y tipoNombre del modelo Dispositivo.
 */
public class DispositivoRepository {

    /**
     * Obtiene una lista de dispositivos con filtros opcionales.
     *
     * @param marcaId  Filtrar por marca (0 = todas)
     * @param tipoId   Filtrar por tipo (0 = todos)
     * @param busqueda Texto a buscar en el nombre (null o vacío = sin filtro)
     * @param orden    Campo para ordenar: "fecha" (default), "precio_asc", "precio_desc", "nombre"
     * @return Lista de dispositivos que cumplen los filtros
     */
    public List<Dispositivo> findAll(int marcaId, int tipoId, String busqueda, String orden) throws SQLException {
        List<Dispositivo> dispositivos = new ArrayList<>();

        // Construimos la consulta SQL con JOIN para obtener nombre de marca y tipo
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT d.id, d.nombre, d.fecha_lanzamiento, d.precio, d.imagen_url, ");
        sql.append("       d.resena, d.sinopsis, d.especificaciones, ");
        sql.append("       d.marca_id, d.tipo_id, ");
        sql.append("       m.nombre AS marca_nombre, t.nombre AS tipo_nombre ");
        sql.append("FROM dispositivo d ");
        sql.append("INNER JOIN marca m ON d.marca_id = m.id ");
        sql.append("INNER JOIN tipo_dispositivo t ON d.tipo_id = t.id ");
        sql.append("WHERE 1=1 ");

        // Lista de parámetros para el PreparedStatement
        List<Object> parametros = new ArrayList<>();

        // Filtro por marca
        if (marcaId > 0) {
            sql.append("AND d.marca_id = ? ");
            parametros.add(marcaId);
        }

        // Filtro por tipo
        if (tipoId > 0) {
            sql.append("AND d.tipo_id = ? ");
            parametros.add(tipoId);
        }

        // Filtro por búsqueda de texto (en nombre o sinopsis)
        if (busqueda != null && !busqueda.trim().isEmpty()) {
            sql.append("AND (d.nombre LIKE ? OR d.sinopsis LIKE ?) ");
            String patron = "%" + busqueda.trim() + "%";
            parametros.add(patron);
            parametros.add(patron);
        }

        // Ordenamiento
        String ordenSQL = "ORDER BY d.fecha_lanzamiento DESC"; // Default: más recientes primero
        if (orden != null) {
            switch (orden) {
                case "precio_asc":
                    ordenSQL = "ORDER BY d.precio ASC";
                    break;
                case "precio_desc":
                    ordenSQL = "ORDER BY d.precio DESC";
                    break;
                case "nombre":
                    ordenSQL = "ORDER BY d.nombre ASC";
                    break;
                // case "fecha" o default: ya está asignado arriba
            }
        }
        sql.append(ordenSQL);

        // Ejecutar la consulta
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            // Asignar los parámetros en orden
            for (int i = 0; i < parametros.size(); i++) {
                stmt.setObject(i + 1, parametros.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    dispositivos.add(mapearDispositivo(rs));
                }
            }
        }

        return dispositivos;
    }

    /**
     * Obtiene todos los dispositivos sin filtros (ordenados por fecha descendente).
     */
    public List<Dispositivo> findAll() throws SQLException {
        return findAll(0, 0, null, "fecha");
    }

    /**
     * Busca un dispositivo por su ID, incluyendo marca y tipo.
     */
    public Dispositivo findById(int id) throws SQLException {
        String sql = "SELECT d.id, d.nombre, d.fecha_lanzamiento, d.precio, d.imagen_url, "
                + "       d.resena, d.sinopsis, d.especificaciones, "
                + "       d.marca_id, d.tipo_id, "
                + "       m.nombre AS marca_nombre, t.nombre AS tipo_nombre "
                + "FROM dispositivo d "
                + "INNER JOIN marca m ON d.marca_id = m.id "
                + "INNER JOIN tipo_dispositivo t ON d.tipo_id = t.id "
                + "WHERE d.id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearDispositivo(rs);
                }
            }
        }
        return null; // No encontrado
    }

    /**
     * Inserta un nuevo dispositivo.
     * @return ID generado del nuevo dispositivo
     */
    public int insert(Dispositivo d) throws SQLException {
        String sql = "INSERT INTO dispositivo (marca_id, tipo_id, nombre, fecha_lanzamiento, "
                + "precio, imagen_url, resena, sinopsis, especificaciones) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, d.getMarcaId());
            stmt.setInt(2, d.getTipoId());
            stmt.setString(3, d.getNombre());
            stmt.setDate(4, new java.sql.Date(d.getFechaLanzamiento().getTime()));
            stmt.setBigDecimal(5, d.getPrecio());
            stmt.setString(6, d.getImagenUrl());
            stmt.setString(7, d.getResena());
            stmt.setString(8, d.getSinopsis());
            stmt.setString(9, d.getEspecificaciones());

            int filas = stmt.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        }
        return -1;
    }

    /**
     * Actualiza un dispositivo existente.
     */
    public boolean update(Dispositivo d) throws SQLException {
        String sql = "UPDATE dispositivo SET marca_id = ?, tipo_id = ?, nombre = ?, "
                + "fecha_lanzamiento = ?, precio = ?, imagen_url = ?, resena = ?, "
                + "sinopsis = ?, especificaciones = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, d.getMarcaId());
            stmt.setInt(2, d.getTipoId());
            stmt.setString(3, d.getNombre());
            stmt.setDate(4, new java.sql.Date(d.getFechaLanzamiento().getTime()));
            stmt.setBigDecimal(5, d.getPrecio());
            stmt.setString(6, d.getImagenUrl());
            stmt.setString(7, d.getResena());
            stmt.setString(8, d.getSinopsis());
            stmt.setString(9, d.getEspecificaciones());
            stmt.setInt(10, d.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Elimina un dispositivo por su ID.
     */
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM dispositivo WHERE id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Método auxiliar: mapea un ResultSet a un objeto Dispositivo.
     */
    private Dispositivo mapearDispositivo(ResultSet rs) throws SQLException {
        Dispositivo d = new Dispositivo();
        d.setId(rs.getInt("id"));
        d.setMarcaId(rs.getInt("marca_id"));
        d.setTipoId(rs.getInt("tipo_id"));
        d.setNombre(rs.getString("nombre"));
        d.setFechaLanzamiento(rs.getDate("fecha_lanzamiento"));
        d.setPrecio(rs.getBigDecimal("precio"));
        d.setImagenUrl(rs.getString("imagen_url"));
        d.setResena(rs.getString("resena"));
        d.setSinopsis(rs.getString("sinopsis"));
        d.setEspecificaciones(rs.getString("especificaciones"));
        d.setMarcaNombre(rs.getString("marca_nombre"));
        d.setTipoNombre(rs.getString("tipo_nombre"));
        return d;
    }
}
