package com.smartdevices.model;

/**
 * Modelo: Marca
 * Representa una marca de dispositivos (Apple, Samsung, Lenovo, etc.)
 *
 * Esta clase es un POJO (Plain Old Java Object) que mapea la tabla "marca"
 * de la base de datos.
 */
public class Marca {

    private int id;
    private String nombre;
    private String pais;
    private String logoUrl;

    // Constructor vacío (necesario para JDBC y Gson)
    public Marca() {
    }

    // Constructor con todos los campos
    public Marca(int id, String nombre, String pais, String logoUrl) {
        this.id = id;
        this.nombre = nombre;
        this.pais = pais;
        this.logoUrl = logoUrl;
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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    @Override
    public String toString() {
        return "Marca{id=" + id + ", nombre='" + nombre + "', pais='" + pais + "'}";
    }
}
