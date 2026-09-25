package com.smartdevices.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
            obtenerVariable("DB_URL",
                    "jdbc:mysql://localhost:3306/smartdevices");

    private static final String USUARIO =
            obtenerVariable("DB_USER", "root");

    private static final String PASSWORD =
            obtenerVariable("DB_PASSWORD", "");

    private static DatabaseConnection instancia;

    private DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println(
                    "[DatabaseConnection] Driver MySQL cargado correctamente."
            );
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                    "No se encontró el driver de MySQL.", e
            );
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instancia == null) {
            instancia = new DatabaseConnection();
        }
        return instancia;
    }

    public Connection getConnection() throws SQLException {
        System.out.println("DB_URL = " + URL);
        System.out.println("DB_USER = " + USUARIO);

        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }

    private static String obtenerVariable(
            String nombre, String valorPredeterminado) {

        String valor = System.getenv(nombre);

        if (valor == null || valor.isBlank()) {
            return valorPredeterminado;
        }

        return valor.trim();
    }
}