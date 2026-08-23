package com.smartdevices.controller;

import com.smartdevices.service.AdminService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;



/**
 * Servlet: AdminLoginServlet
 * Capa: Controller (Controlador)
 *
 * Responsabilidad:
 *   - Mostrar el formulario de login (GET).
 *   - Procesar el inicio de sesión (POST).
 *   - Cerrar sesión (GET con parámetro ?accion=logout).
 *
 */
@WebServlet(name = "AdminLoginServlet", urlPatterns = {"/admin/login"})
public class AdminLoginServlet extends HttpServlet {

    private AdminService adminService;

    @Override
    public void init() throws ServletException {
        adminService = new AdminService();
    }

    /**
     * GET: Muestra el formulario de login o cierra sesión.
     * @param request
     * @param response
     * @throws jakarta.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        // Si la acción es "logout", cerrar sesión
        if ("logout".equals(accion)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate(); // Cierra la sesión
            }
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        // Si ya está logueado, redirigir al dashboard
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("adminUsuario") != null) {
            response.sendRedirect(request.getContextPath() + "/admin/dispositivos");
            return;
        }

        // Mostrar el formulario de login
        request.getRequestDispatcher("/pages/admin/login.jsp").forward(request, response);
    }

    /**
     * POST: Procesa el formulario de login.
     * @param request
     * @param response
     * @throws jakarta.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Leer credenciales del formulario
        String usuario = request.getParameter("usuario");
        String password = request.getParameter("password");

        // Validar credenciales con el Service
        if (adminService.login(usuario, password)) {
            // Credenciales correctas: crear sesión
            HttpSession session = request.getSession(true);
            session.setAttribute("adminUsuario", usuario);
            session.setAttribute("adminNombre", adminService.getNombreAdministrador(usuario));

            // Redirigir al dashboard de administración
            response.sendRedirect(request.getContextPath() + "/admin/dispositivos");
        } else {
            // Credenciales incorrectas: mostrar error
            request.setAttribute("error", "Usuario o contraseña incorrectos");
            request.getRequestDispatcher("/pages/admin/login.jsp").forward(request, response);
        }
    }
}
