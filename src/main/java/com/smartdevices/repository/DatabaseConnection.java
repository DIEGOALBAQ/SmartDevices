package com.smartdevices.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase: DatabaseConnection
 * Capa: Repository
 *
 * Responsabilidad:
 *   - Gestionar la conexión a la base de datos MySQL.
 *   - Centralizar los parámetros de conexión (URL, usuario, password).
 *   - Implementar el patrón Singleton para reutilizar la configuración.
 *
 * NOTA PARA PRINCIPIANTES:
 *   En un proyecto real, estos datos deberían estar en un archivo .properties
 *   o variables de entorno. Aquí se ponen directamente para facilitar el aprendizaje.
 */
public class DatabaseConnection {

    // ====== Parámetros de conexión a MySQL ======
    // Cambia estos valores según tu configuración local de MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/smartdevices"
            + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "";

    // ====== Patrón Singleton ======
    private static DatabaseConnection instancia;

    // Constructor privado (solo se puede crear una instancia)
    private DatabaseConnection() {
        try {
            // Cargar el driver de MySQL (necesario para versiones antiguas de JDBC)
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("[DatabaseConnection] Driver MySQL cargado correctamente.");
        } catch (ClassNotFoundException e) {
            System.err.println("[DatabaseConnection] ERROR: No se encontró el driver de MySQL.");
            System.err.println("Asegúrate de tener mysql-connector-j en el pom.xml");
            e.printStackTrace();
        }
    }

    /**
     * Obtiene la única instancia de DatabaseConnection (patrón Singleton)
     * @return 
     */
    public static synchronized DatabaseConnection getInstance() {
        if (instancia == null) {
            instancia = new DatabaseConnection();
        }
        return instancia;
    }

    /**
     * Crea y devuelve una nueva conexión a la base de datos.
     *
     * IMPORTANTE: Quien llama a este método es responsable de cerrar la conexión
     * con conn.close() o usando try-with-resources.
     * @return 
     * @throws java.sql.SQLException
     */
    public Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USUARIO, PASSWORD);
        return conn;
    }

    /**
     * Método de prueba para verificar que la conexión funciona.
     * Ejecútalo desde un main para probar antes de usar el proyecto completo.
     * @param args
     */
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            if (conn != null) {
                System.out.println("✓ Conexión exitosa a la base de datos smartdevices!");
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("✗ Error de conexión: " + e.getMessage());
            System.err.println("Verifica que:");
            System.err.println("  1. MySQL esté corriendo en localhost:3306");
            System.err.println("  2. La base de datos 'smartdevices' exista (ejecuta sql/01_schema.sql)");
            System.err.println("  3. Usuario y password sean correctos");
        }
    }
}
