package com.smartdevices.model;

/**
 * Modelo: TipoDispositivo
 * Categoría del dispositivo: Celular, Portatil, Tablet, etc.
 *
 * Mapea la tabla "tipo_dispositivo" de la base de datos.
 */
public class TipoDispositivo {

    private int id;
    private String nombre;

    // Constructor vacío
    public TipoDispositivo() {
    }

    // Constructor con todos los campos
    public TipoDispositivo(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "TipoDispositivo{id=" + id + ", nombre='" + nombre + "'}";
    }
}
