<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- Administración de marcas --%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Administrar Marcas - TechDevices</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>

    <!-- Navbar de administración -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/admin/dispositivos">
                <i class="bi bi-gear"></i> Panel Admin
            </a>
            <div class="collapse navbar-collapse" id="navbarAdmin">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/dispositivos">
                            <i class="bi bi-phone"></i> Dispositivos
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="${pageContext.request.contextPath}/admin/marcas">
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
                            <i class="bi bi-box-arrow-right"></i> Salir
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container py-4">

        <h2 class="mb-4"><i class="bi bi-tags"></i> Administrar Marcas</h2>

        <c:if test="${not empty msg}">
            <div class="alert alert-success">
                <i class="bi bi-check-circle"></i> Marca guardada correctamente.
            </div>
        </c:if>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">
                <i class="bi bi-exclamation-triangle"></i> ${error}
            </div>
        </c:if>

        <div class="row">

            <!-- Formulario para agregar/editar marca -->
            <div class="col-md-4">
                <div class="card shadow-sm">
                    <div class="card-header bg-light">
                        <h5 class="mb-0"><i class="bi bi-plus-lg"></i> Agregar nueva marca</h5>
                    </div>
                    <div class="card-body">
                        <form method="post" action="${pageContext.request.contextPath}/admin/marcas">

                            <div class="mb-3">
                                <label class="form-label">Nombre *</label>
                                <input type="text" class="form-control" name="nombre" required
                                       placeholder="Ej: Apple">
                            </div>

                            <div class="mb-3">
                                <label class="form-label">País de origen</label>
                                <input type="text" class="form-control" name="pais"
                                       placeholder="Ej: Estados Unidos">
                            </div>

                            <div class="mb-3">
                                <label class="form-label">URL del logo</label>
                                <input type="url" class="form-control" name="logoUrl"
                                       placeholder="https://...">
                            </div>

                            <button type="submit" class="btn btn-primary w-100">
                                <i class="bi bi-save"></i> Guardar marca
                            </button>
                        </form>
                    </div>
                </div>
            </div>

            <!-- Lista de marcas existentes -->
            <div class="col-md-8">
                <div class="card shadow-sm">
                    <div class="card-header bg-light">
                        <h5 class="mb-0"><i class="bi bi-list"></i> Marcas registradas (${marcas.size()})</h5>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-hover align-middle">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Nombre</th>
                                        <th>País</th>
                                        <th class="text-center">Acciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:choose>
                                        <c:when test="${empty marcas}">
                                            <tr>
                                                <td colspan="4" class="text-center text-muted py-3">
                                                    No hay marcas registradas.
                                                </td>
                                            </tr>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="marca" items="${marcas}">
                                                <tr>
                                                    <td>${marca.id}</td>
                                                    <td>
                                                        <strong>${marca.nombre}</strong>
                                                    </td>
                                                    <td>${marca.pais}</td>
                                                    <td class="text-center">
                                                        <a href="${pageContext.request.contextPath}/admin/marcas?accion=eliminar&id=${marca.id}"
                                                           class="btn btn-sm btn-outline-danger"
                                                           onclick="return confirm('¿Eliminar la marca ${marca.nombre}?\n\nNota: no se puede eliminar si tiene dispositivos asociados.')">
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

        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
