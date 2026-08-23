package com.smartdevices.controller;

import com.smartdevices.model.Comentario;
import com.smartdevices.model.Dispositivo;
import com.smartdevices.service.ComentarioService;
import com.smartdevices.service.DispositivoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DispositivoDetalleServlet", urlPatterns = {"/detalle"})
public class DispositivoDetalleServlet extends HttpServlet {

    private DispositivoService dispositivoService;
    private ComentarioService comentarioService;

    @Override
    public void init() throws ServletException {
        dispositivoService = new DispositivoService();
        comentarioService = new ComentarioService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int dispositivoId = leerEntero(request, "id", 0);

        if (dispositivoId <= 0) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        Dispositivo dispositivo = dispositivoService.obtenerPorId(dispositivoId);

        if (dispositivo == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Dispositivo no encontrado");
            return;
        }

        List<Comentario> comentarios = comentarioService.listarPorDispositivo(dispositivoId);
        double promedio = dispositivoService.obtenerPromedioCalificacion(dispositivoId);
        int totalComentarios = dispositivoService.contarComentarios(dispositivoId);

        request.setAttribute("dispositivo", dispositivo);
        request.setAttribute("comentarios", comentarios);
        request.setAttribute("promedio", promedio);
        request.setAttribute("totalComentarios", totalComentarios);

        request.getRequestDispatcher("/pages/detalle.jsp").forward(request, response);
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
