package com.smartdevices.controller;

import com.smartdevices.model.Comentario;
import com.smartdevices.service.ComentarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ComentarioServlet", urlPatterns = {"/comentario"})
public class ComentarioServlet extends HttpServlet {

    private ComentarioService comentarioService;

    @Override
    public void init() throws ServletException {
        comentarioService = new ComentarioService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        int dispositivoId = leerEntero(request, "dispositivoId", 0);
        String autor = request.getParameter("autor");
        String textoComentario = request.getParameter("comentario");
        int calificacion = leerEntero(request, "calificacion", 5);

        Comentario comentario = new Comentario();
        comentario.setDispositivoId(dispositivoId);
        comentario.setAutor(autor);
        comentario.setComentario(textoComentario);
        comentario.setCalificacion(calificacion);

        String mensajeExito = null;
        String mensajeError = null;

        try {
            boolean creado = comentarioService.crearComentario(comentario);
            if (creado) {
                mensajeExito = "¡Gracias! Tu comentario se ha publicado correctamente.";
            } else {
                mensajeError = "No se pudo guardar el comentario. Intenta nuevamente.";
            }
        } catch (IllegalArgumentException e) {
            mensajeError = e.getMessage();
        }

        if (mensajeExito != null) {
            request.getSession().setAttribute("mensajeExito", mensajeExito);
        }
        if (mensajeError != null) {
            request.getSession().setAttribute("mensajeError", mensajeError);
        }

        response.sendRedirect(request.getContextPath() + "/detalle?id=" + dispositivoId);
    }

    private int leerEntero(HttpServletRequest request, String nombre, int defecto) {
        String valor = request.getParameter(nombre);
        if (valor == null || valor.trim().isEmpty()) {
            return defecto;
        }
        try {
            return Integer.parseInt(valor.trim());
        } catch (NumberFormatException e) {
            return defecto;
        }
    }
}
