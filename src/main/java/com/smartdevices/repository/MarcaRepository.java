package com.smartdevices.repository;

import com.smartdevices.model.Marca;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio: MarcaRepository
 * Capa: Repository (acceso a datos)
 *
 * Responsabilidad:
 *   - Ejecutar consultas SQL relacionadas con la tabla "marca".
 *   - Devolver objetos Marca a la capa Service.
 *
 * NO contiene lógica de negocio, solo acceso a datos.
 */
public class MarcaRepository {

    /**
     * Obtiene todas las marcas de la base de datos, ordenadas por nombre.
     */
    public List<Marca> findAll() throws SQLException {
        List<Marca> marcas = new ArrayList<>();
        String sql = "SELECT id, nombre, pais, logo_url FROM marca ORDER BY nombre ASC";

        // try-with-resources: cierra automáticamente la conexión y el Statement
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Marca marca = mapearMarca(rs);
                marcas.add(marca);
            }
        }
        return marcas;
    }

    /**
     * Busca una marca por su ID.
     */
    public Marca findById(int id) throws SQLException {
        String sql = "SELECT id, nombre, pais, logo_url FROM marca WHERE id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearMarca(rs);
                }
            }
        }
        return null; // No encontrada
    }

    /**
     * Inserta una nueva marca en la base de datos.
     * @return el ID generado de la nueva marca
     */
    public int insert(Marca marca) throws SQLException {
        String sql = "INSERT INTO marca (nombre, pais, logo_url) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, marca.getNombre());
            stmt.setString(2, marca.getPais());
            stmt.setString(3, marca.getLogoUrl());

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // Retorna el ID generado
                    }
                }
            }
        }
        return -1; // Error
    }

    /**
     * Actualiza una marca existente.
     */
    public boolean update(Marca marca) throws SQLException {
        String sql = "UPDATE marca SET nombre = ?, pais = ?, logo_url = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, marca.getNombre());
            stmt.setString(2, marca.getPais());
            stmt.setString(3, marca.getLogoUrl());
            stmt.setInt(4, marca.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Elimina una marca por su ID.
     */
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM marca WHERE id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Método auxiliar: mapea un ResultSet a un objeto Marca.
     * Evita repetir código en cada método.
     */
    private Marca mapearMarca(ResultSet rs) throws SQLException {
        Marca marca = new Marca();
        marca.setId(rs.getInt("id"));
        marca.setNombre(rs.getString("nombre"));
        marca.setPais(rs.getString("pais"));
        marca.setLogoUrl(rs.getString("logo_url"));
        return marca;
    }
}
