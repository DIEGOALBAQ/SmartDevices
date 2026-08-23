package com.smartdevices.controller;

import com.smartdevices.model.Dispositivo;
import com.smartdevices.model.Marca;
import com.smartdevices.model.TipoDispositivo;
import com.smartdevices.service.DispositivoService;
import com.smartdevices.service.MarcaService;
import com.smartdevices.service.TipoDispositivoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeServlet", urlPatterns = {"/inicio"})
public class HomeServlet extends HttpServlet {

    private DispositivoService dispositivoService;
    private MarcaService marcaService;
    private TipoDispositivoService tipoService;

    @Override
    public void init() throws ServletException {
        dispositivoService = new DispositivoService();
        marcaService = new MarcaService();
        tipoService = new TipoDispositivoService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int marcaId = leerEntero(request, "marcaId", 0);
        int tipoId = leerEntero(request, "tipoId", 0);
        String busqueda = request.getParameter("busqueda");
        String orden = request.getParameter("orden");

        List<Dispositivo> dispositivos = dispositivoService.listarDispositivos(marcaId, tipoId, busqueda, orden);
        List<Marca> marcas = marcaService.listarMarcas();
        List<TipoDispositivo> tipos = tipoService.listarTipos();

        request.setAttribute("dispositivos", dispositivos);
        request.setAttribute("marcas", marcas);
        request.setAttribute("tipos", tipos);
        request.setAttribute("marcaIdSel", marcaId);
        request.setAttribute("tipoIdSel", tipoId);
        request.setAttribute("busquedaSel", busqueda != null ? busqueda : "");
        request.setAttribute("ordenSel", orden != null ? orden : "fecha");

        request.getRequestDispatcher("/index.jsp").forward(request, response);
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
