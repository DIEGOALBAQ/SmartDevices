package com.smartdevices.repository;

import com.smartdevices.model.Comentario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio: ComentarioRepository
 * Capa: Repository (acceso a datos)
 *
 * Gestiona las operaciones sobre la tabla "comentario".
 * Permite listar comentarios de un dispositivo y crear nuevos.
 */
public class ComentarioRepository {

    /**
     * Obtiene todos los comentarios de un dispositivo, ordenados por fecha descendente
     * (los más recientes primero).
     */
    public List<Comentario> findByDispositivoId(int dispositivoId) throws SQLException {
        List<Comentario> comentarios = new ArrayList<>();
        String sql = "SELECT id, dispositivo_id, autor, comentario, calificacion, fecha "
                + "FROM comentario WHERE dispositivo_id = ? ORDER BY fecha DESC";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, dispositivoId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    comentarios.add(mapearComentario(rs));
                }
            }
        }
        return comentarios;
    }

    /**
     * Cuenta cuántos comentarios tiene un dispositivo.
     */
    public int countByDispositivoId(int dispositivoId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM comentario WHERE dispositivo_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, dispositivoId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    /**
     * Calcula el promedio de calificación de un dispositivo.
     * @return Promedio (0.0 si no hay comentarios)
     */
    public double getPromedioCalificacion(int dispositivoId) throws SQLException {
        String sql = "SELECT AVG(calificacion) FROM comentario WHERE dispositivo_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, dispositivoId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    double promedio = rs.getDouble(1);
                    if (rs.wasNull()) {
                        return 0.0; // No hay comentarios
                    }
                    return promedio;
                }
            }
        }
        return 0.0;
    }

    /**
     * Inserta un nuevo comentario en la base de datos.
     * @return ID generado del nuevo comentario, o -1 si falla
     */
    public int insert(Comentario comentario) throws SQLException {
        String sql = "INSERT INTO comentario (dispositivo_id, autor, comentario, calificacion) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, comentario.getDispositivoId());
            stmt.setString(2, comentario.getAutor());
            stmt.setString(3, comentario.getComentario());
            stmt.setInt(4, comentario.getCalificacion());

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
     * Elimina un comentario por su ID (para administración).
     */
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM comentario WHERE id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Método auxiliar: mapea un ResultSet a un objeto Comentario.
     */
    private Comentario mapearComentario(ResultSet rs) throws SQLException {
        Comentario c = new Comentario();
        c.setId(rs.getInt("id"));
        c.setDispositivoId(rs.getInt("dispositivo_id"));
        c.setAutor(rs.getString("autor"));
        c.setComentario(rs.getString("comentario"));
        c.setCalificacion(rs.getInt("calificacion"));
        c.setFecha(rs.getTimestamp("fecha"));
        return c;
    }
}
