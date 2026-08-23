package com.smartdevices.repository;

import java.sql.*;

/**
 * Repositorio: AdminUsuarioRepository
 * Capa: Repository (acceso a datos)
 *
 * Gestiona la autenticación del administrador.
 *
 * NOTA DE SEGURIDAD:
 *   En este proyecto de aprendizaje, la contraseña se compara en texto plano.
 *   En un proyecto REAL, debe usarse hash (BCrypt, Argon2, etc.) y nunca
 *   almacenar ni comparar contraseñas en texto plano.
 */
public class AdminUsuarioRepository {

    /**
     * Verifica si el usuario y password coinciden con un registro en la BD.
     * @return true si las credenciales son válidas, false en caso contrario
     */
    public boolean validarCredenciales(String usuario, String password) throws SQLException {
        String sql = "SELECT id FROM admin_usuario WHERE usuario = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Si hay resultado, las credenciales son válidas
            }
        }
    }

    /**
     * Obtiene el nombre del administrador a partir de su usuario.
     */
    public String getNombreByUsuario(String usuario) throws SQLException {
        String sql = "SELECT nombre FROM admin_usuario WHERE usuario = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nombre");
                }
            }
        }
        return null;
    }
}
