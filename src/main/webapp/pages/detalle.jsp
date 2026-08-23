<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- Página de detalle: información completa del dispositivo + comentarios --%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${dispositivo.nombre} - TechDevices</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>

    <!-- ====== Barra de navegación ====== -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top">
        <div class="container">
            <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/">
                <i class="bi bi-cpu"></i> TechDevices
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarMain">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarMain">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/">
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

    <div class="container py-4">

        <!-- ====== Migas de pan ====== -->
        <nav aria-label="breadcrumb" class="mb-4">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Inicio</a></li>
                <li class="breadcrumb-item active">${dispositivo.nombre}</li>
            </ol>
        </nav>

        <!-- ====== Mensajes de éxito/error ====== -->
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

        <!-- ====== Sección principal: detalle del dispositivo ====== -->
        <div class="row">

            <!-- Columna izquierda: imagen -->
            <div class="col-md-5 mb-4">
                <div class="card shadow-sm">
                    <c:choose>
                        <c:when test="${not empty dispositivo.imagenUrl}">
                            <img src="${dispositivo.imagenUrl}" class="card-img-top p-3" alt="${dispositivo.nombre}"
                                 style="max-height: 400px; object-fit: contain;">
                        </c:when>
                        <c:otherwise>
                            <div class="text-center py-5">
                                <i class="bi bi-phone" style="font-size: 6rem; color: #ccc;"></i>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <!-- Columna derecha: información -->
            <div class="col-md-7">
                <span class="badge bg-info text-dark">${dispositivo.tipoNombre}</span>
                <span class="badge bg-secondary">${dispositivo.marcaNombre}</span>

                <h1 class="mt-2 mb-3">${dispositivo.nombre}</h1>

                <!-- Calificación promedio -->
                <div class="mb-3">
                    <c:choose>
                        <c:when test="${totalComentarios > 0}">
                            <span class="text-warning fs-5">
                                <c:forEach begin="1" end="5" var="i">
                                    <c:choose>
                                        <c:when test="${i <= promedio}">★</c:when>
                                        <c:otherwise>☆</c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </span>
                            <span class="text-muted ms-2">
                                ${promedio} / 5 (${totalComentarios} comentario(s))
                            </span>
                        </c:when>
                        <c:otherwise>
                            <span class="text-muted">Sin calificaciones aún</span>
                        </c:otherwise>
                    </c:choose>
                </div>

                <!-- Sinopsis -->
                <p class="lead text-muted">${dispositivo.sinopsis}</p>

                <!-- Datos clave -->
                <table class="table table-borderless">
                    <tr>
                        <th style="width: 180px;"><i class="bi bi-tag"></i> Marca:</th>
                        <td>${dispositivo.marcaNombre}</td>
                    </tr>
                    <tr>
                        <th><i class="bi bi-diagram-3"></i> Tipo:</th>
                        <td>${dispositivo.tipoNombre}</td>
                    </tr>
                    <tr>
                        <th><i class="bi bi-calendar3"></i> Lanzamiento:</th>
                        <td>${dispositivo.fechaLanzamientoFormateada}</td>
                    </tr>
                    <tr>
                        <th><i class="bi bi-currency-dollar"></i> Precio:</th>
                        <td><span class="h4 text-primary">$${dispositivo.precio}</span></td>
                    </tr>
                </table>

                <!-- Especificaciones técnicas -->
                <c:if test="${not empty dispositivo.especificaciones}">
                    <h5 class="mt-4"><i class="bi bi-list-check"></i> Especificaciones técnicas</h5>
                    <div class="card bg-light">
                        <div class="card-body">
                            <p class="mb-0">${dispositivo.especificaciones}</p>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>

        <!-- ====== Reseña completa ====== -->
        <c:if test="${not empty dispositivo.resena}">
            <hr class="my-5">
            <h3><i class="bi bi-pencil-square"></i> Reseña completa</h3>
            <p class="text-justify" style="line-height: 1.8;">${dispositivo.resena}</p>
        </c:if>

        <!-- ====== Sección de comentarios ====== -->
        <hr class="my-5">
        <h3>
            <i class="bi bi-chat-dots"></i> Opiniones de usuarios
            <span class="badge bg-secondary">${totalComentarios}</span>
        </h3>

        <!-- Formulario para dejar un comentario -->
        <div class="card shadow-sm mb-4">
            <div class="card-header bg-light">
                <h5 class="mb-0"><i class="bi bi-pencil"></i> Deja tu opinión</h5>
            </div>
            <div class="card-body">
                <form method="post" action="${pageContext.request.contextPath}/comentario">

                    <!-- ID del dispositivo (oculto) -->
                    <input type="hidden" name="dispositivoId" value="${dispositivo.id}">

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Tu nombre *</label>
                            <input type="text" class="form-control" name="autor" required
                                   placeholder="Ej: Juan Pérez">
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Calificación</label>
                            <select class="form-select" name="calificacion">
                                <option value="5">★★★★★ (Excelente)</option>
                                <option value="4">★★★★☆ (Muy bueno)</option>
                                <option value="3">★★★☆☆ (Regular)</option>
                                <option value="2">★★☆☆☆ (Malo)</option>
                                <option value="1">★☆☆☆☆ (Muy malo)</option>
                            </select>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Tu comentario *</label>
                        <textarea class="form-control" name="comentario" rows="3" required
                                  placeholder="Escribe tu opinión sobre este dispositivo..."></textarea>
                    </div>

                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-send"></i> Publicar comentario
                    </button>
                </form>
            </div>
        </div>

        <!-- Lista de comentarios existentes -->
        <c:choose>
            <c:when test="${empty comentarios}">
                <div class="text-center py-4 text-muted">
                    <i class="bi bi-chat" style="font-size: 3rem;"></i>
                    <p class="mt-2">Aún no hay comentarios. ¡Sé el primero en opinar!</p>
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach var="com" items="${comentarios}">
                    <div class="card mb-3 shadow-sm">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-center mb-2">
                                <div>
                                    <h6 class="mb-0">
                                        <i class="bi bi-person-circle"></i> ${com.autor}
                                    </h6>
                                    <small class="text-muted">
                                        <i class="bi bi-clock"></i> ${com.fechaFormateada}
                                    </small>
                                </div>
                                <span class="text-warning fs-5">${com.estrellas}</span>
                            </div>
                            <p class="card-text mb-0">${com.comentario}</p>
                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>

        <!-- Botón volver -->
        <div class="text-center mt-4 mb-5">
            <a href="${pageContext.request.contextPath}/" class="btn btn-outline-secondary">
                <i class="bi bi-arrow-left"></i> Volver al listado
            </a>
        </div>

    </div>

    <!-- ====== Pie de página ====== -->
    <footer class="bg-dark text-white py-4 mt-5">
        <div class="container text-center">
            <p class="mb-0 text-muted small">
                Proyecto educativo - Arquitectura por capas con Java Servlets + JDBC + Gson + Bootstrap
            </p>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
