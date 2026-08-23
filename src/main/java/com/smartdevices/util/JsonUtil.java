package com.smartdevices.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Utilidad: JsonUtil
 *
 * Métodos helper para enviar respuestas JSON desde los Servlets.
 * Usa la librería Gson para convertir objetos Java a JSON.
 */
public class JsonUtil {

    // Instancia de Gson configurada con indentación (pretty printing)
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    /**
     * Envía un objeto como JSON en la respuesta HTTP.
     *
     * @param response  Respuesta HTTP del Servlet
     * @param objeto    Objeto a convertir a JSON
     */
    public static void enviarJson(HttpServletResponse response, Object objeto) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Permitir CORS desde cualquier origen (útil para desarrollo)
        response.setHeader("Access-Control-Allow-Origin", "*");

        PrintWriter out = response.getWriter();
        out.print(gson.toJson(objeto));
        out.flush();
    }

    /**
     * Envía una respuesta JSON de éxito con un mensaje.
     */
    public static void enviarExito(HttpServletResponse response, String mensaje) throws IOException {
        enviarJson(response, new RespuestaSimple(true, mensaje, null));
    }

    /**
     * Envía una respuesta JSON de error con un mensaje.
     */
    public static void enviarError(HttpServletResponse response, String mensaje) throws IOException {
        enviarJson(response, new RespuestaSimple(false, null, mensaje));
    }

    /**
     * Clase interna para respuestas simples de éxito/error.
     */
    private static class RespuestaSimple {
        boolean exito;
        String mensaje;
        String error;

        RespuestaSimple(boolean exito, String mensaje, String error) {
            this.exito = exito;
            this.mensaje = mensaje;
            this.error = error;
        }
    }

    /**
     * Obtiene la instancia de Gson para uso externo.
     */
    public static Gson getGson() {
        return gson;
    }
}
