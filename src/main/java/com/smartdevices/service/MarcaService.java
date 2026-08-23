package com.smartdevices.service;

import com.smartdevices.model.Marca;
import com.smartdevices.repository.MarcaRepository;
import java.sql.SQLException;
import java.util.List;

/**
 * Servicio: MarcaService
 * Capa: Service (lógica de negocio)
 *
 * Intermediario entre el Controller y MarcaRepository.
 */
public class MarcaService {

    private final MarcaRepository marcaRepository;

    public MarcaService() {
        this.marcaRepository = new MarcaRepository();
    }

    /**
     * Lista todas las marcas.
     */
    public List<Marca> listarMarcas() {
        try {
            return marcaRepository.findAll();
        } catch (SQLException e) {
            System.err.println("[MarcaService] Error al listar marcas: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * Obtiene una marca por ID.
     */
    public Marca obtenerPorId(int id) {
        try {
            return marcaRepository.findById(id);
        } catch (SQLException e) {
            System.err.println("[MarcaService] Error al obtener marca: " + e.getMessage());
            return null;
        }
    }

    /**
     * Crea una nueva marca.
     */
    public int crearMarca(Marca marca) {
        if (marca.getNombre() == null || marca.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la marca es obligatorio");
        }
        try {
            return marcaRepository.insert(marca);
        } catch (SQLException e) {
            System.err.println("[MarcaService] Error al crear marca: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Actualiza una marca existente.
     */
    public boolean actualizarMarca(Marca marca) {
        try {
            return marcaRepository.update(marca);
        } catch (SQLException e) {
            System.err.println("[MarcaService] Error al actualizar marca: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina una marca por ID.
     */
    public boolean eliminarMarca(int id) {
        try {
            return marcaRepository.delete(id);
        } catch (SQLException e) {
            System.err.println("[MarcaService] Error al eliminar marca: " + e.getMessage());
            return false;
        }
    }
}
