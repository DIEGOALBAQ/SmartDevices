package com.smartdevices.controller;

import com.google.gson.JsonSyntaxException;
import com.smartdevices.model.Dispositivo;
import com.smartdevices.service.DispositivoService;
import com.smartdevices.util.JsonUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/api/dispositivos/*")
public class DispositivoApiServlet extends HttpServlet {

    private final DispositivoService service = new DispositivoService();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {
            Integer id = obtenerId(request);

            if (id == null) {
                JsonUtil.enviarJson(response, service.listarTodos());
                return;
            }

            Dispositivo dispositivo = service.obtenerPorId(id);

            if (dispositivo == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                JsonUtil.enviarError(response, "Dispositivo no encontrado");
                return;
            }

            JsonUtil.enviarJson(response, dispositivo);

        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.enviarError(response, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {
            Dispositivo dispositivo = leerDispositivo(request);
            int idCreado = service.crearDispositivo(dispositivo);

            if (idCreado <= 0) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                JsonUtil.enviarError(response, "No se pudo crear el dispositivo");
                return;
            }

            dispositivo.setId(idCreado);
            response.setStatus(HttpServletResponse.SC_CREATED);
            JsonUtil.enviarJson(response, dispositivo);

        } catch (IllegalArgumentException | JsonSyntaxException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.enviarError(response, "Datos inválidos: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {
            Integer id = obtenerId(request);

            if (id == null) {
                throw new IllegalArgumentException(
                        "Debe indicar el ID en la dirección"
                );
            }

            if (service.obtenerPorId(id) == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                JsonUtil.enviarError(response, "Dispositivo no encontrado");
                return;
            }

            Dispositivo dispositivo = leerDispositivo(request);
            dispositivo.setId(id);

            boolean actualizado
                    = service.actualizarDispositivo(dispositivo);

            if (actualizado) {
                JsonUtil.enviarJson(response, dispositivo);
            } else {
                response.setStatus(
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR
                );
                JsonUtil.enviarError(
                        response,
                        "No se pudo actualizar el dispositivo"
                );
            }

        } catch (IllegalArgumentException | JsonSyntaxException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.enviarError(response, "Datos inválidos: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {
            Integer id = obtenerId(request);

            if (id == null) {
                throw new IllegalArgumentException(
                        "Debe indicar el ID en la dirección"
                );
            }

            if (service.obtenerPorId(id) == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                JsonUtil.enviarError(response, "Dispositivo no encontrado");
                return;
            }

            boolean eliminado = service.eliminarDispositivo(id);

            if (eliminado) {
                JsonUtil.enviarExito(
                        response,
                        "Dispositivo eliminado correctamente"
                );
            } else {
                response.setStatus(
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR
                );
                JsonUtil.enviarError(
                        response,
                        "No se pudo eliminar el dispositivo"
                );
            }

        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.enviarError(response, e.getMessage());
        }
    }

    private Dispositivo leerDispositivo(HttpServletRequest request)
            throws IOException {

        request.setCharacterEncoding("UTF-8");

        try (BufferedReader reader = request.getReader()) {
            Dispositivo dispositivo = JsonUtil.getGson()
                    .fromJson(reader, Dispositivo.class);

            if (dispositivo == null) {
                throw new IllegalArgumentException(
                        "El cuerpo JSON está vacío"
                );
            }

            return dispositivo;
        }
    }

    private Integer obtenerId(HttpServletRequest request) {
        String ruta = request.getPathInfo();

        if (ruta == null || ruta.equals("/") || ruta.isBlank()) {
            return null;
        }

        String valor = ruta.replace("/", "");

        try {
            return Integer.valueOf(valor);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "El ID debe ser un número entero"
            );
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request,
            HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader(
                "Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS"
        );
        response.setHeader(
                "Access-Control-Allow-Headers",
                "Content-Type, Authorization"
        );
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
