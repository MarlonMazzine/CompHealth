package br.com.prova.hermano.src.main.java.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	private ConnectionFactory() {
		throw new IllegalStateException("Utility class");
	}
	
	public static Connection obterConexao() throws SQLException {
        String stringDeConexao = "jdbc:postgresql://172.31.48.1:5432/prova-do-hermano";
        return DriverManager.getConnection(stringDeConexao, "postgres", "26f4re56j");
    }
}
