package com.smartdevices.service;

import com.smartdevices.model.TipoDispositivo;
import com.smartdevices.repository.TipoDispositivoRepository;
import java.sql.SQLException;
import java.util.List;

/**
 * Servicio: TipoDispositivoService
 * Capa: Service (lógica de negocio)
 *
 * Intermediario entre el Controller y TipoDispositivoRepository.
 */
public class TipoDispositivoService {

    private final TipoDispositivoRepository tipoRepository;

    public TipoDispositivoService() {
        this.tipoRepository = new TipoDispositivoRepository();
    }

    /**
     * Lista todos los tipos de dispositivo.
     */
    public List<TipoDispositivo> listarTipos() {
        try {
            return tipoRepository.findAll();
        } catch (SQLException e) {
            System.err.println("[TipoDispositivoService] Error al listar tipos: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * Obtiene un tipo por ID.
     */
    public TipoDispositivo obtenerPorId(int id) {
        try {
            return tipoRepository.findById(id);
        } catch (SQLException e) {
            System.err.println("[TipoDispositivoService] Error al obtener tipo: " + e.getMessage());
            return null;
        }
    }
}
