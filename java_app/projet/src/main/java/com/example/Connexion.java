package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    private static final String URL = "jdbc:mysql://localhost:3306/maBase";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    public Connection connexion;

    public void connect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connexion = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC introuvable.");
            throw new SQLException(e);
        }
    }

    public void close() throws SQLException {
        if (connexion != null && !connexion.isClosed()) {
            connexion.close();
        }
    }
}
