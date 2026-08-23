package com.smartdevices.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Modelo: Comentario
 * Representa la opinión de un usuario sobre un dispositivo.
 *
 */
public class Comentario {

    private int id;
    private int dispositivoId;
    private String autor;
    private String comentario;
    private int calificacion;  // De 1 a 5 estrellas
    private Date fecha;

    // Constructor vacío
    public Comentario() {
    }

    // Constructor con todos los campos
    public Comentario(int dispositivoId, String autor, String comentario, int calificacion) {
        this.dispositivoId = dispositivoId;
        this.autor = autor;
        this.comentario = comentario;
        this.calificacion = calificacion;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDispositivoId() {
        return dispositivoId;
    }

    public void setDispositivoId(int dispositivoId) {
        this.dispositivoId = dispositivoId;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Devuelve la fecha formateada como String (dd/MM/yyyy HH:mm)
     */
    public String getFechaFormateada() {
        if (fecha == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(fecha);
    }

    /**
     * Devuelve las estrellas en formato HTML (★☆)
     * Ejemplo: calificacion=3 → "★★★☆☆"
     */
    public String getEstrellas() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 5; i++) {
            if (i <= calificacion) {
                sb.append("★");
            } else {
                sb.append("☆");
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Comentario{id=" + id + ", autor='" + autor + "', calificacion=" + calificacion + "}";
    }
}
