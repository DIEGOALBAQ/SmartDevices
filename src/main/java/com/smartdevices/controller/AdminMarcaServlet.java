package com.smartdevices.controller;

import com.smartdevices.model.Marca;
import com.smartdevices.service.MarcaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminMarcaServlet", urlPatterns = {"/admin/marcas"})
public class AdminMarcaServlet extends HttpServlet {

    private MarcaService marcaService;

    @Override
    public void init() throws ServletException {
        marcaService = new MarcaService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!verificarSesion(request, response)) {
            return;
        }

        String accion = request.getParameter("accion");

        if ("eliminar".equals(accion)) {
            int id = leerEntero(request, "id", 0);
            marcaService.eliminarMarca(id);
            response.sendRedirect(request.getContextPath() + "/admin/marcas?msg=eliminado");
            return;
        }

        List<Marca> marcas = marcaService.listarMarcas();
        request.setAttribute("marcas", marcas);

        String msg = request.getParameter("msg");
        if (msg != null) {
            request.setAttribute("msg", msg);
        }

        request.getRequestDispatcher("/pages/admin/marcas.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!verificarSesion(request, response)) {
            return;
        }

        request.setCharacterEncoding("UTF-8");

        Marca marca = new Marca();
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            marca.setId(Integer.parseInt(idStr));
        }
        marca.setNombre(request.getParameter("nombre"));
        marca.setPais(request.getParameter("pais"));
        marca.setLogoUrl(request.getParameter("logoUrl"));

        boolean exito;
        if (marca.getId() > 0) {
            exito = marcaService.actualizarMarca(marca);
        } else {
            int nuevoId = marcaService.crearMarca(marca);
            exito = nuevoId > 0;
        }

        if (exito) {
            response.sendRedirect(request.getContextPath() + "/admin/marcas?msg=guardado");
        } else {
            request.setAttribute("error", "No se pudo guardar la marca");
            request.getRequestDispatcher("/pages/admin/marcas.jsp").forward(request, response);
        }
    }

    private boolean verificarSesion(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminUsuario") == null) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return false;
        }
        return true;
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
