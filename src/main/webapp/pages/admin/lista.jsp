<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- Lista de dispositivos - Panel de administración --%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Administrar Dispositivos - TechDevices</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>

    <!-- ====== Navbar de administración ====== -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/admin/dispositivos">
                <i class="bi bi-gear"></i> Panel Admin
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarAdmin">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarAdmin">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="${pageContext.request.contextPath}/admin/dispositivos">
                            <i class="bi bi-phone"></i> Dispositivos
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/marcas">
                            <i class="bi bi-tags"></i> Marcas
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/">
                            <i class="bi bi-house"></i> Ver sitio
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-danger" href="${pageContext.request.contextPath}/admin/login?accion=logout">
                            <i class="bi bi-box-arrow-right"></i> Cerrar sesión
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container py-4">

        <!-- Bienvenida -->
        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <h2><i class="bi bi-phone"></i> Administrar Dispositivos</h2>
                <p class="text-muted mb-0">
                    Bienvenido, <strong>${sessionScope.adminNombre}</strong>
                </p>
            </div>
            <a href="${pageContext.request.contextPath}/admin/dispositivos?accion=nuevo"
               class="btn btn-success">
                <i class="bi bi-plus-lg"></i> Nuevo dispositivo
            </a>
        </div>

        <!-- Mensaje de éxito -->
        <c:if test="${not empty msg}">
            <div class="alert alert-success">
                <c:choose>
                    <c:when test="${msg == 'guardado'}">
                        <i class="bi bi-check-circle"></i> Dispositivo guardado correctamente.
                    </c:when>
                    <c:when test="${msg == 'eliminado'}">
                        <i class="bi bi-check-circle"></i> Dispositivo eliminado correctamente.
                    </c:when>
                </c:choose>
            </div>
        </c:if>

        <!-- Tabla de dispositivos -->
        <div class="card shadow-sm">
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-hover align-middle">
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Imagen</th>
                                <th>Nombre</th>
                                <th>Marca</th>
                                <th>Tipo</th>
                                <th>Lanzamiento</th>
                                <th>Precio</th>
                                <th class="text-center">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty dispositivos}">
                                    <tr>
                                        <td colspan="8" class="text-center text-muted py-4">
                                            No hay dispositivos registrados.
                                            <a href="${pageContext.request.contextPath}/admin/dispositivos?accion=nuevo">
                                                Crear el primero
                                            </a>
                                        </td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="disp" items="${dispositivos}">
                                        <tr>
                                            <td>${disp.id}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${not empty disp.imagenUrl}">
                                                        <img src="${disp.imagenUrl}" width="50" height="50"
                                                             style="object-fit: contain;">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <i class="bi bi-image text-muted"></i>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${disp.nombre}</td>
                                            <td>${disp.marcaNombre}</td>
                                            <td>${disp.tipoNombre}</td>
                                            <td>${disp.fechaLanzamientoFormateada}</td>
                                            <td>$${disp.precio}</td>
                                            <td class="text-center">
                                                <a href="${pageContext.request.contextPath}/detalle?id=${disp.id}"
                                                   class="btn btn-sm btn-outline-info" title="Ver">
                                                    <i class="bi bi-eye"></i>
                                                </a>
                                                <a href="${pageContext.request.contextPath}/admin/dispositivos?accion=editar&id=${disp.id}"
                                                   class="btn btn-sm btn-outline-primary" title="Editar">
                                                    <i class="bi bi-pencil"></i>
                                                </a>
                                                <a href="${pageContext.request.contextPath}/admin/dispositivos?accion=eliminar&id=${disp.id}"
                                                   class="btn btn-sm btn-outline-danger" title="Eliminar"
                                                   onclick="return confirm('¿Eliminar el dispositivo ${disp.nombre}?')">
                                                    <i class="bi bi-trash"></i>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
