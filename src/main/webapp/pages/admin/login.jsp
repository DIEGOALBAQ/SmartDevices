<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- Página de login del administrador --%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Acceso Administrador - TechDevices</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #1a1a2e 0%, #16213e 50%, #0f3460 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
        }
        .login-card {
            max-width: 400px;
            margin: 0 auto;
            border-radius: 15px;
            box-shadow: 0 10px 40px rgba(0,0,0,0.3);
        }
    </style>
</head>
<body>

    <div class="container">
        <div class="login-card card">
            <div class="card-body p-5">

                <div class="text-center mb-4">
                    <i class="bi bi-shield-lock" style="font-size: 4rem; color: #0d6efd;"></i>
                    <h3 class="mt-3 mb-0">Panel de Administración</h3>
                    <p class="text-muted">Ingrese sus credenciales para continuar</p>
                </div>

                <!-- Mensaje de error -->
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">
                        <i class="bi bi-exclamation-triangle"></i> ${error}
                    </div>
                </c:if>

                <!-- Formulario de login -->
                <form method="post" action="${pageContext.request.contextPath}/admin/login">

                    <div class="mb-3">
                        <label class="form-label">
                            <i class="bi bi-person"></i> Usuario
                        </label>
                        <input type="text" class="form-control" name="usuario"
                               required autofocus placeholder="admin">
                    </div>

                    <div class="mb-4">
                        <label class="form-label">
                            <i class="bi bi-lock"></i> Contraseña
                        </label>
                        <input type="password" class="form-control" name="password"
                               required placeholder="••••••••">
                    </div>

                    <button type="submit" class="btn btn-primary w-100 py-2">
                        <i class="bi bi-box-arrow-in-right"></i> Iniciar sesión
                    </button>
                </form>

                <!-- Credenciales de prueba (solo para proyecto educativo) -->
                <div class="alert alert-info mt-4 mb-0">
                    <small>
                        <strong><i class="bi bi-info-circle"></i> Credenciales de prueba:</strong><br>
                        Usuario: <code>admin</code><br>
                        Contraseña: <code>admin123</code>
                    </small>
                </div>

                <div class="text-center mt-3">
                    <a href="${pageContext.request.contextPath}/" class="text-decoration-none">
                        <i class="bi bi-arrow-left"></i> Volver al inicio
                    </a>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
