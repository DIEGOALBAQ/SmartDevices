package com.smartdevices.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * Modelo: Dispositivo
 * Representa un dispositivo inteligente (celular, portatil, tablet, etc.)
 *
 * Mapea la tabla "dispositivo" de la base de datos.
 * Incluye referencias a la marca y al tipo de dispositivo.
 */
public class Dispositivo {

    private int id;
    private int marcaId;
    private int tipoId;
    private String nombre;
    private Date fechaLanzamiento;   // java.util.Date
    private BigDecimal precio;
    private String imagenUrl;
    private String resena;           // Reseña detallada del dispositivo
    private String sinopsis;         // Resumen corto
    private String especificaciones; // Características técnicas

    // Campos adicionales (se llenan con JOIN, no existen como columna propia)
    private String marcaNombre;      // Nombre de la marca (de la tabla marca)
    private String tipoNombre;       // Nombre del tipo (de la tabla tipo_dispositivo)

    // Constructor vacío
    public Dispositivo() {
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMarcaId() {
        return marcaId;
    }

    public void setMarcaId(int marcaId) {
        this.marcaId = marcaId;
    }

    public int getTipoId() {
        return tipoId;
    }

    public void setTipoId(int tipoId) {
        this.tipoId = tipoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(Date fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    /**
     * Devuelve la fecha de lanzamiento formateada como String (dd/MM/yyyy)
     * Útil para mostrar en las vistas JSP.
     * @return 
     */
    public String getFechaLanzamientoFormateada() {
        if (fechaLanzamiento == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(fechaLanzamiento);
    }

    /**
     * Devuelve la fecha en formato ISO (yyyy-MM-dd)
     * Necesario para el campo <input type="date"> en formularios HTML.
     * @return 
     */
    public String getFechaLanzamientoISO() {
        if (fechaLanzamiento == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(fechaLanzamiento);
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getResena() {
        return resena;
    }

    public void setResena(String resena) {
        this.resena = resena;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getEspecificaciones() {
        return especificaciones;
    }

    public void setEspecificaciones(String especificaciones) {
        this.especificaciones = especificaciones;
    }

    public String getMarcaNombre() {
        return marcaNombre;
    }

    public void setMarcaNombre(String marcaNombre) {
        this.marcaNombre = marcaNombre;
    }

    public String getTipoNombre() {
        return tipoNombre;
    }

    public void setTipoNombre(String tipoNombre) {
        this.tipoNombre = tipoNombre;
    }

    @Override
    public String toString() {
        return "Dispositivo{id=" + id + ", nombre='" + nombre + "', marcaId=" + marcaId
                + ", tipoId=" + tipoId + "}";
    }
}
