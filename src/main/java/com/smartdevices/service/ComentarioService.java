package com.smartdevices.service;

import com.smartdevices.model.Comentario;
import com.smartdevices.repository.ComentarioRepository;
import java.sql.SQLException;
import java.util.List;

/**
 * Servicio: ComentarioService
 * Capa: Service (lógica de negocio)
 *
 * Gestiona las operaciones relacionadas con comentarios de usuarios.
 */
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;

    public ComentarioService() {
        this.comentarioRepository = new ComentarioRepository();
    }

    /**
     * Lista los comentarios de un dispositivo específico.
     */
    public List<Comentario> listarPorDispositivo(int dispositivoId) {
        try {
            return comentarioRepository.findByDispositivoId(dispositivoId);
        } catch (SQLException e) {
            System.err.println("[ComentarioService] Error al listar comentarios: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * Crea un nuevo comentario.
     *
     * Validaciones:
     *   - El autor no puede estar vacío
     *   - El comentario no puede estar vacío
     *   - La calificación debe estar entre 1 y 5
     */
    public boolean crearComentario(Comentario comentario) {
        // Validaciones de negocio
        if (comentario.getAutor() == null || comentario.getAutor().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del autor es obligatorio");
        }
        if (comentario.getComentario() == null || comentario.getComentario().trim().isEmpty()) {
            throw new IllegalArgumentException("El comentario no puede estar vacío");
        }
        if (comentario.getCalificacion() < 1 || comentario.getCalificacion() > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5");
        }
        if (comentario.getDispositivoId() <= 0) {
            throw new IllegalArgumentException("Debe especificar un dispositivo válido");
        }

        try {
            return comentarioRepository.insert(comentario) > 0;
        } catch (SQLException e) {
            System.err.println("[ComentarioService] Error al crear comentario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un comentario (para administración).
     */
    public boolean eliminarComentario(int id) {
        try {
            return comentarioRepository.delete(id);
        } catch (SQLException e) {
            System.err.println("[ComentarioService] Error al eliminar comentario: " + e.getMessage());
            return false;
        }
    }
}
