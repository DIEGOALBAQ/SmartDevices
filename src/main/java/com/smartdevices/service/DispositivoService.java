package com.smartdevices.service;

import com.smartdevices.model.Dispositivo;
import com.smartdevices.repository.ComentarioRepository;
import com.smartdevices.repository.DispositivoRepository;
import java.sql.SQLException;
import java.util.List;

/**
 * Servicio: DispositivoService
 * Capa: Service (lógica de negocio)
 *
 * Responsabilidad:
 *   - Orquestar las operaciones relacionadas con dispositivos.
 *   - Actuar como intermediario entre el Controller (Servlet) y el Repository.
 *   - Manejar excepciones y validaciones básicas.
 *
 * El Controller NUNCA accede directamente al Repository,
 * siempre pasa por el Service.
 */
public class DispositivoService {

    private final DispositivoRepository dispositivoRepository;
    private final ComentarioRepository comentarioRepository;

    // Constructor: inicializa los repositorios
    public DispositivoService() {
        this.dispositivoRepository = new DispositivoRepository();
        this.comentarioRepository = new ComentarioRepository();
    }

    /**
     * Obtiene la lista de dispositivos con filtros.
     *
     * @param marcaId  ID de marca (0 = todas)
     * @param tipoId   ID de tipo (0 = todos)
     * @param busqueda Texto a buscar
     * @param orden    Campo de ordenamiento
     * @return Lista de dispositivos
     */
    public List<Dispositivo> listarDispositivos(int marcaId, int tipoId, String busqueda, String orden) {
        try {
            return dispositivoRepository.findAll(marcaId, tipoId, busqueda, orden);
        } catch (SQLException e) {
            System.err.println("[DispositivoService] Error al listar dispositivos: " + e.getMessage());
            return List.of(); // Retorna lista vacía en caso de error
        }
    }

    /**
     * Obtiene todos los dispositivos (sin filtros).
     * @return 
     */
    public List<Dispositivo> listarTodos() {
        return listarDispositivos(0, 0, null, "fecha");
    }

    /**
     * Obtiene un dispositivo por su ID, incluyendo comentarios.
     * Si no existe, retorna null.
     * @param id
     * @return 
     */
    public Dispositivo obtenerPorId(int id) {
        try {
            return dispositivoRepository.findById(id);
        } catch (SQLException e) {
            System.err.println("[DispositivoService] Error al obtener dispositivo " + id + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Crea un nuevo dispositivo.
     * @param dispositivo
     * @return ID del nuevo dispositivo, o -1 si falla
     */
    public int crearDispositivo(Dispositivo dispositivo) {
        // Validación básica
        if (dispositivo.getNombre() == null || dispositivo.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del dispositivo es obligatorio");
        }
        if (dispositivo.getMarcaId() <= 0) {
            throw new IllegalArgumentException("Debe seleccionar una marca");
        }
        if (dispositivo.getTipoId() <= 0) {
            throw new IllegalArgumentException("Debe seleccionar un tipo de dispositivo");
        }

        try {
            return dispositivoRepository.insert(dispositivo);
        } catch (SQLException e) {
            System.err.println("[DispositivoService] Error al crear dispositivo: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Actualiza un dispositivo existente.
     * @param dispositivo
     * @return 
     */
    public boolean actualizarDispositivo(Dispositivo dispositivo) {
        try {
            return dispositivoRepository.update(dispositivo);
        } catch (SQLException e) {
            System.err.println("[DispositivoService] Error al actualizar dispositivo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un dispositivo por su ID.
     * @param id
     * @return 
     */
    public boolean eliminarDispositivo(int id) {
        try {
            return dispositivoRepository.delete(id);
        } catch (SQLException e) {
            System.err.println("[DispositivoService] Error al eliminar dispositivo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene el promedio de calificación de un dispositivo.
     * @param dispositivoId
     * @return 
     */
    public double obtenerPromedioCalificacion(int dispositivoId) {
        try {
            return comentarioRepository.getPromedioCalificacion(dispositivoId);
        } catch (SQLException e) {
            System.err.println("[DispositivoService] Error al obtener promedio: " + e.getMessage());
            return 0.0;
        }
    }

    /**
     * Cuenta cuántos comentarios tiene un dispositivo.
     * @param dispositivoId
     * @return 
     */
    public int contarComentarios(int dispositivoId) {
        try {
            return comentarioRepository.countByDispositivoId(dispositivoId);
        } catch (SQLException e) {
            System.err.println("[DispositivoService] Error al contar comentarios: " + e.getMessage());
            return 0;
        }
    }
}
