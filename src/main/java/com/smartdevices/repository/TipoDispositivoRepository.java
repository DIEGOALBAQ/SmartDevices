package com.smartdevices.repository;

import com.smartdevices.model.TipoDispositivo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio: TipoDispositivoRepository
 * Capa: Repository (acceso a datos)
 *
 * Gestiona las operaciones CRUD sobre la tabla "tipo_dispositivo".
 */
public class TipoDispositivoRepository {

    /**
     * Obtiene todos los tipos de dispositivo.
     */
    public List<TipoDispositivo> findAll() throws SQLException {
        List<TipoDispositivo> tipos = new ArrayList<>();
        String sql = "SELECT id, nombre FROM tipo_dispositivo ORDER BY nombre ASC";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                TipoDispositivo tipo = new TipoDispositivo();
                tipo.setId(rs.getInt("id"));
                tipo.setNombre(rs.getString("nombre"));
                tipos.add(tipo);
            }
        }
        return tipos;
    }

    /**
     * Busca un tipo por su ID.
     */
    public TipoDispositivo findById(int id) throws SQLException {
        String sql = "SELECT id, nombre FROM tipo_dispositivo WHERE id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    TipoDispositivo tipo = new TipoDispositivo();
                    tipo.setId(rs.getInt("id"));
                    tipo.setNombre(rs.getString("nombre"));
                    return tipo;
                }
            }
        }
        return null;
    }
}
