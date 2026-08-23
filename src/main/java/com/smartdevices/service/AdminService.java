package com.smartdevices.service;

import com.smartdevices.repository.AdminUsuarioRepository;
import java.sql.SQLException;

/**
 * Servicio: AdminService
 * Capa: Service (lógica de negocio)
 *
 * Gestiona la autenticación del administrador.
 */
public class AdminService {

    private final AdminUsuarioRepository adminRepository;

    public AdminService() {
        this.adminRepository = new AdminUsuarioRepository();
    }

    /**
     * Valida las credenciales del administrador.
     * @return true si son válidas, false en caso contrario
     */
    public boolean login(String usuario, String password) {
        if (usuario == null || usuario.trim().isEmpty()) {
            return false;
        }
        if (password == null || password.trim().isEmpty()) {
            return false;
        }
        try {
            return adminRepository.validarCredenciales(usuario.trim(), password);
        } catch (SQLException e) {
            System.err.println("[AdminService] Error al validar credenciales: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene el nombre real del administrador a partir de su usuario.
     */
    public String getNombreAdministrador(String usuario) {
        try {
            return adminRepository.getNombreByUsuario(usuario);
        } catch (SQLException e) {
            System.err.println("[AdminService] Error al obtener nombre: " + e.getMessage());
            return "Administrador";
        }
    }
}
