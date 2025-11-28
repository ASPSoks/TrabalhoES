package com.example.bibliosys.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por gerenciar a conexão com o banco de dados.
 * Implementa o padrão de projeto Singleton.
 */
public class DataSource {

    private static DataSource instance;
    private Connection connection;

    private static final String DB_URL = "jdbc:h2:mem:biblioteca;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    private DataSource() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar com o banco de dados.", e);
        }
    }

    /**
     * Retorna a única instância da classe DataSource.
     * @return a instância Singleton de DataSource.
     */
    public static synchronized DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }

    /**
     * Retorna a conexão com o banco de dados.
     * @return a conexão SQL.
     */
    public Connection getConnection() {
        return connection;
    }
}
