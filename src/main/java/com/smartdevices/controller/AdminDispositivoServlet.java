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
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Servlet: AdminDispositivoServlet
 * Capa: Controller (Controlador)
 *
 * Responsabilidad:
 *   - Listar dispositivos (panel de administración).
 *   - Mostrar formulario para crear/editar dispositivo.
 *   - Guardar (crear o actualizar) un dispositivo.
 *   - Eliminar un dispositivo.
 *
 * Nota: Todas las acciones requieren que el administrador esté logueado.
 */
@WebServlet(name = "AdminDispositivoServlet", urlPatterns = {"/admin/dispositivos"})
public class AdminDispositivoServlet extends HttpServlet {

    private DispositivoService dispositivoService;
    private MarcaService marcaService;
    private TipoDispositivoService tipoService;

    @Override
    public void init() throws ServletException {
        dispositivoService = new DispositivoService();
        marcaService = new MarcaService();
        tipoService = new TipoDispositivoService();
    }

    /**
     * GET: Maneja las diferentes acciones del panel de administración.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ====== Verificar que el administrador esté logueado ======
        if (!verificarSesion(request, response)) {
            return; // Ya redirigió al login
        }

        String accion = request.getParameter("accion");

        if (accion == null) {
            accion = "listar";
        }

        switch (accion) {
            case "nuevo":
                mostrarFormulario(request, response, null);
                break;
            case "editar":
                int idEditar = leerEntero(request, "id", 0);
                Dispositivo dispositivo = dispositivoService.obtenerPorId(idEditar);
                if (dispositivo != null) {
                    mostrarFormulario(request, response, dispositivo);
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/dispositivos");
                }
                break;
            case "eliminar":
                int idEliminar = leerEntero(request, "id", 0);
                dispositivoService.eliminarDispositivo(idEliminar);
                response.sendRedirect(request.getContextPath() + "/admin/dispositivos?msg=eliminado");
                break;
            default:
                listarDispositivos(request, response);
                break;
        }
    }

    /**
     * POST: Procesa el formulario de crear/editar dispositivo.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!verificarSesion(request, response)) {
            return;
        }

        request.setCharacterEncoding("UTF-8");

        // Leer los datos del formulario
        Dispositivo dispositivo = new Dispositivo();

        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            dispositivo.setId(Integer.parseInt(idStr));
        }

        dispositivo.setMarcaId(leerEntero(request, "marcaId", 0));
        dispositivo.setTipoId(leerEntero(request, "tipoId", 0));
        dispositivo.setNombre(request.getParameter("nombre"));
        dispositivo.setSinopsis(request.getParameter("sinopsis"));
        dispositivo.setResena(request.getParameter("resena"));
        dispositivo.setEspecificaciones(request.getParameter("especificaciones"));
        dispositivo.setImagenUrl(request.getParameter("imagenUrl"));

        // Leer y convertir la fecha
        String fechaStr = request.getParameter("fechaLanzamiento");
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = sdf.parse(fechaStr);
            dispositivo.setFechaLanzamiento(fecha);
        } catch (Exception e) {
            dispositivo.setFechaLanzamiento(new Date()); // Fecha actual como fallback
        }

        // Leer y convertir el precio
        String precioStr = request.getParameter("precio");
        try {
            BigDecimal precio = new BigDecimal(precioStr);
            dispositivo.setPrecio(precio);
        } catch (Exception e) {
            dispositivo.setPrecio(BigDecimal.ZERO);
        }

        // Guardar (crear o actualizar)
        boolean exito;
        if (dispositivo.getId() > 0) {
            exito = dispositivoService.actualizarDispositivo(dispositivo);
        } else {
            int nuevoId = dispositivoService.crearDispositivo(dispositivo);
            exito = nuevoId > 0;
        }

        if (exito) {
            response.sendRedirect(request.getContextPath() + "/admin/dispositivos?msg=guardado");
        } else {
            request.setAttribute("error", "No se pudo guardar el dispositivo");
            mostrarFormulario(request, response, dispositivo);
        }
    }

    /**
     * Lista todos los dispositivos para el panel de administración.
     */
    private void listarDispositivos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Dispositivo> dispositivos = dispositivoService.listarTodos();
        request.setAttribute("dispositivos", dispositivos);

        // Mensaje de éxito opcional
        String msg = request.getParameter("msg");
        if (msg != null) {
            request.setAttribute("msg", msg);
        }

        request.getRequestDispatcher("/pages/admin/lista.jsp").forward(request, response);
    }

    /**
     * Muestra el formulario para crear o editar un dispositivo.
     */
    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response,
                                    Dispositivo dispositivo)
            throws ServletException, IOException {

        // Cargar marcas y tipos para los selects del formulario
        List<Marca> marcas = marcaService.listarMarcas();
        List<TipoDispositivo> tipos = tipoService.listarTipos();

        request.setAttribute("marcas", marcas);
        request.setAttribute("tipos", tipos);
        request.setAttribute("dispositivo", dispositivo);

        request.getRequestDispatcher("/pages/admin/formulario.jsp").forward(request, response);
    }

    /**
     * Verifica si el administrador tiene sesión activa.
     * Si no, redirige al login.
     * @return true si está logueado, false si fue redirigido
     */
    private boolean verificarSesion(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminUsuario") == null) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return false;
        }
        return true;
    }

    /**
     * Método auxiliar: lee un parámetro entero del request.
     */
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
