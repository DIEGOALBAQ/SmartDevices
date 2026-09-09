<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<%
    // Si no hay datos (acceso directo a /), redirigir al servlet /inicio
    if (request.getAttribute("dispositivos") == null) {
        response.sendRedirect(request.getContextPath() + "/inicio");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TechDevices - Dispositivos Inteligentes</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>

    <!-- Barra de navegación -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top">
        <div class="container">
            <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/">
                <i class="bi bi-cpu"></i> SmartDevices
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarMain">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarMain">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="${pageContext.request.contextPath}/">
                            <i class="bi bi-house"></i> Inicio
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/login">
                            <i class="bi bi-gear"></i> Administración
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Encabezado -->
    <header class="bg-primary text-white py-5 mb-4">
        <div class="container text-center">
            <h1 class="display-5 fw-bold">
                <i class="bi bi-phone"></i> Encuentra tu próximo dispositivo inteligente
            </h1>
            <p class="lead mt-3">
                Explora los últimos celulares, portátiles, tablets y más.
                Compara características, lee reseñas y opiniones de otros usuarios.
            </p>
        </div>
    </header>

    <div class="container">

        <!-- Mensajes -->
        <c:if test="${not empty sessionScope.mensajeExito}">
            <div class="alert alert-success alert-dismissible fade show">
                <i class="bi bi-check-circle"></i> ${sessionScope.mensajeExito}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <c:remove var="mensajeExito" scope="session" />
        </c:if>
        <c:if test="${not empty sessionScope.mensajeError}">
            <div class="alert alert-danger alert-dismissible fade show">
                <i class="bi bi-exclamation-circle"></i> ${sessionScope.mensajeError}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <c:remove var="mensajeError" scope="session" />
        </c:if>

        <!-- Formulario de filtros -->
        <div class="card shadow-sm mb-4">
            <div class="card-body">
                <form method="get" action="${pageContext.request.contextPath}/inicio" class="row g-3 align-items-end">
                    <div class="col-md-4">
                        <label class="form-label fw-semibold"><i class="bi bi-search"></i> Buscar</label>
                        <input type="text" class="form-control" name="busqueda" value="${busquedaSel}" placeholder="Nombre del dispositivo...">
                    </div>
                    <div class="col-md-2">
                        <label class="form-label fw-semibold">Marca</label>
                        <select class="form-select" name="marcaId">
                            <option value="0">Todas</option>
                            <c:forEach var="marca" items="${marcas}">
                                <option value="${marca.id}" <c:if test="${marcaIdSel == marca.id}">selected</c:if>>${marca.nombre}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <label class="form-label fw-semibold">Tipo</label>
                        <select class="form-select" name="tipoId">
                            <option value="0">Todos</option>
                            <c:forEach var="tipo" items="${tipos}">
                                <option value="${tipo.id}" <c:if test="${tipoIdSel == tipo.id}">selected</c:if>>${tipo.nombre}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <label class="form-label fw-semibold">Ordenar</label>
                        <select class="form-select" name="orden">
                            <option value="fecha" <c:if test="${ordenSel == 'fecha'}">selected</c:if>>Más recientes</option>
                            <option value="precio_asc" <c:if test="${ordenSel == 'precio_asc'}">selected</c:if>>Precio: menor a mayor</option>
                            <option value="precio_desc" <c:if test="${ordenSel == 'precio_desc'}">selected</c:if>>Precio: mayor a menor</option>
                            <option value="nombre" <c:if test="${ordenSel == 'nombre'}">selected</c:if>>Nombre A-Z</option>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <button type="submit" class="btn btn-primary w-100"><i class="bi bi-funnel"></i> Filtrar</button>
                        <a href="${pageContext.request.contextPath}/inicio" class="btn btn-outline-secondary w-100 mt-1"><i class="bi bi-arrow-clockwise"></i> Limpiar</a>
                    </div>
                </form>
            </div>
        </div>

        <!-- Resultados -->
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h4 class="mb-0">
                <c:choose>
                    <c:when test="${empty dispositivos}">No se encontraron dispositivos</c:when>
                    <c:otherwise>${fn:length(dispositivos)} dispositivo(s) encontrado(s)</c:otherwise>
                </c:choose>
            </h4>
        </div>

        <!-- Grid -->
        <c:choose>
            <c:when test="${empty dispositivos}">
                <div class="text-center py-5">
                    <i class="bi bi-search" style="font-size: 4rem; color: #ccc;"></i>
                    <h5 class="mt-3 text-muted">No se encontraron dispositivos con los filtros seleccionados</h5>
                    <p>Intenta cambiar los criterios de búsqueda</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4 mb-5">
                    <c:forEach var="disp" items="${dispositivos}">
                        <div class="col">
                            <a href="${pageContext.request.contextPath}/detalle?id=${disp.id}" class="text-decoration-none text-dark">
                                <div class="card h-100 shadow-sm device-card">
                                    <div class="card-img-wrapper">
                                        <c:choose>
                                            <c:when test="${not empty disp.imagenUrl}">
                                                <img src="${disp.imagenUrl}" class="card-img-top" alt="${disp.nombre}">
                                            </c:when>
                                            <c:otherwise>
                                                <div class="card-img-placeholder"><i class="bi bi-phone" style="font-size: 3rem;"></i></div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="card-body">
                                        <span class="badge bg-info text-dark mb-2">${disp.tipoNombre}</span>
                                        <span class="badge bg-secondary mb-2">${disp.marcaNombre}</span>
                                        <h5 class="card-title">${disp.nombre}</h5>
                                        <p class="card-text text-muted small">${disp.sinopsis}</p>
                                        <p class="card-text"><small class="text-muted"><i class="bi bi-calendar3"></i> ${disp.fechaLanzamientoFormateada}</small></p>
                                    </div>
                                    <div class="card-footer bg-white">
                                        <div class="d-flex justify-content-between align-items-center">
                                            <span class="h5 text-primary mb-0">$${disp.precio}</span>
                                            <span class="btn btn-sm btn-outline-primary">Ver detalle <i class="bi bi-arrow-right"></i></span>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>

    </div>

    <footer class="bg-dark text-white py-4 mt-5">
        <div class="container text-center">
            <p class="mb-1"><i class="bi bi-cpu"></i> <strong>SmartDevices - Trabajo en Grupo</strong></p>
            <p class="mb-0 text-muted small">Arquitectura por capas con Java Servlets + JDBC + Gson + Bootstrap</p>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>