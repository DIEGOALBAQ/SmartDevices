<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- Formulario para crear o editar un dispositivo --%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulario Dispositivo - TechDevices</title>
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
                        <a class="nav-link text-danger" href="${pageContext.request.contextPath}/admin/login?accion=logout">
                            <i class="bi bi-box-arrow-right"></i> Salir
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container py-4">

        <div class="row justify-content-center">
            <div class="col-md-10">

                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2>
                        <c:choose>
                            <c:when test="${not empty dispositivo and dispositivo.id > 0}">
                                <i class="bi bi-pencil"></i> Editar dispositivo
                            </c:when>
                            <c:otherwise>
                                <i class="bi bi-plus-lg"></i> Nuevo dispositivo
                            </c:otherwise>
                        </c:choose>
                    </h2>
                    <a href="${pageContext.request.contextPath}/admin/dispositivos" class="btn btn-outline-secondary">
                        <i class="bi bi-arrow-left"></i> Volver
                    </a>
                </div>

                <c:if test="${not empty error}">
                    <div class="alert alert-danger">
                        <i class="bi bi-exclamation-triangle"></i> ${error}
                    </div>
                </c:if>

                <!-- Formulario -->
                <div class="card shadow-sm">
                    <div class="card-body">
                        <form method="post" action="${pageContext.request.contextPath}/admin/dispositivos">

                            <!-- ID oculto (solo si es edición) -->
                            <c:if test="${not empty dispositivo and dispositivo.id > 0}">
                                <input type="hidden" name="id" value="${dispositivo.id}">
                            </c:if>

                            <div class="row">
                                <!-- Nombre -->
                                <div class="col-md-8 mb-3">
                                    <label class="form-label">Nombre del dispositivo *</label>
                                    <input type="text" class="form-control" name="nombre" required
                                           value="${dispositivo.nombre}"
                                           placeholder="Ej: iPhone 15 Pro Max">
                                </div>

                                <!-- Precio -->
                                <div class="col-md-4 mb-3">
                                    <label class="form-label">Precio (USD) *</label>
                                    <div class="input-group">
                                        <span class="input-group-text">$</span>
                                        <input type="number" step="0.01" class="form-control" name="precio"
                                               value="${dispositivo.precio}" required>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <!-- Marca -->
                                <div class="col-md-4 mb-3">
                                    <label class="form-label">Marca *</label>
                                    <select class="form-select" name="marcaId" required>
                                        <option value="">-- Seleccionar --</option>
                                        <c:forEach var="marca" items="${marcas}">
                                            <option value="${marca.id}"
                                                <c:if test="${dispositivo.marcaId == marca.id}">selected</c:if>>
                                                ${marca.nombre}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <!-- Tipo -->
                                <div class="col-md-4 mb-3">
                                    <label class="form-label">Tipo *</label>
                                    <select class="form-select" name="tipoId" required>
                                        <option value="">-- Seleccionar --</option>
                                        <c:forEach var="tipo" items="${tipos}">
                                            <option value="${tipo.id}"
                                                <c:if test="${dispositivo.tipoId == tipo.id}">selected</c:if>>
                                                ${tipo.nombre}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <!-- Fecha de lanzamiento -->
                                <div class="col-md-4 mb-3">
                                    <label class="form-label">Fecha de lanzamiento *</label>
                                    <input type="date" class="form-control" name="fechaLanzamiento" required
                                           value="${dispositivo.fechaLanzamientoISO}">
                                    <small class="text-muted">Formato: AAAA-MM-DD</small>
                                </div>
                            </div>

                            <!-- URL de imagen -->
                            <div class="mb-3">
                                <label class="form-label">URL de la imagen</label>
                                <input type="url" class="form-control" name="imagenUrl"
                                       value="${dispositivo.imagenUrl}"
                                       placeholder="https://ejemplo.com/imagen.jpg">
                            </div>

                            <!-- Sinopsis (resumen corto) -->
                            <div class="mb-3">
                                <label class="form-label">Sinopsis (resumen corto)</label>
                                <textarea class="form-control" name="sinopsis" rows="2"
                                          placeholder="Breve descripción de una línea...">${dispositivo.sinopsis}</textarea>
                            </div>

                            <!-- Especificaciones técnicas -->
                            <div class="mb-3">
                                <label class="form-label">Especificaciones técnicas</label>
                                <textarea class="form-control" name="especificaciones" rows="3"
                                          placeholder="Ej: Pantalla: 6.7 pulgadas | RAM: 8GB | ...">${dispositivo.especificaciones}</textarea>
                            </div>

                            <!-- Reseña completa -->
                            <div class="mb-3">
                                <label class="form-label">Reseña completa</label>
                                <textarea class="form-control" name="resena" rows="6"
                                          placeholder=" Reseña detallada del dispositivo...">${dispositivo.resena}</textarea>
                            </div>

                            <!-- Botones -->
                            <div class="d-flex gap-2">
                                <button type="submit" class="btn btn-primary">
                                    <i class="bi bi-save"></i> Guardar dispositivo
                                </button>
                                <a href="${pageContext.request.contextPath}/admin/dispositivos" class="btn btn-outline-secondary">
                                    Cancelar
                                </a>
                            </div>
                        </form>
                    </div>
                </div>

            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
