package br.com.prova.hermano.src.main.java.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.prova.hermano.src.main.java.model.Paciente;

public class RepositorioPaciente {
	
	private static final Logger LOGGER = LogManager.getLogger(RepositorioPaciente.class.getName());
	private Connection connection;
	
	public RepositorioPaciente() {
		try {
			connection = ConnectionFactory.obterConexao();
		} catch (SQLException e) {
			LOGGER.warn(e.getMessage(), e);
		}
	}
	
	public List<Paciente> listarTodos() throws SQLException {
		List<Paciente> pacientes = new ArrayList<>();
		
		String sql = "SELECT * FROM prova.\"PACIENTE\"";
		
		try (ResultSet result = connection.prepareStatement(sql).executeQuery();) {
			while (result.next()) {
				Paciente paciente = new Paciente();
				
				paciente.setCpf(result.getString(1));
				paciente.setNome(result.getString(2));
				paciente.setEndereco(result.getString(3));
				paciente.setTelefone(result.getString(4));
				paciente.setSexo(result.getString(5));
				paciente.setDataDeNascimento(LocalDate.parse(result.getDate(6).toString()));
				
				pacientes.add(paciente);
			}
		} catch (SQLException e) {
			throw new SQLException(e.getMessage(), e);
		} finally {
			connection.close();
		}
		
		return pacientes;
	}
	
	public void cadastrar(Paciente paciente) throws SQLException {
		String sql = "INSERT INTO prova.\"PACIENTE\"(\"Cpf\", \"Nome\", \"Endereco\", \"Telefone\", \"Sexo\", \"Data_Nascimento\")" + 
			"VALUES(?, ?, ?, ?, ?, ?)";
		
		try (PreparedStatement comando = connection.prepareStatement(sql)) {
			comando.setString(1, paciente.getCpf());
			comando.setString(2, paciente.getNome());
			comando.setString(3, paciente.getEndereco());
			comando.setString(4, paciente.getTelefone());
			comando.setString(5, paciente.getSexo());
			comando.setDate(6, Date.valueOf(paciente.getDataDeNascimento()));
			
			comando.execute();
		}
	}
	
	public void editar(Paciente paciente) throws SQLException {
		String sql = String.format("UPDATE prova.\"PACIENTE\"" + 
			"SET \"Cpf\" = ?, \"Nome\" = ?, \"Endereco\" = ?, \"Telefone\" = ?, \"Sexo\" = ?, \"Data_Nascimento\" = ?" + 
			"WHERE \"Cpf\" = '%s'", paciente.getCpf());
		
		try (PreparedStatement comando = connection.prepareStatement(sql)) {
			comando.setString(1, paciente.getCpf());
			comando.setString(2, paciente.getNome());
			comando.setString(3, paciente.getEndereco());
			comando.setString(4, paciente.getTelefone());
			comando.setString(5, paciente.getSexo());
			comando.setDate(6, Date.valueOf(paciente.getDataDeNascimento()));
			
			comando.execute();
		}
	}
	
	public void apagar(Paciente paciente) throws SQLException {
		String sql = String.format("DELETE FROM prova.\"PACIENTE\" WHERE \"Cpf\" = '%s'", paciente.getCpf());
		
		try (PreparedStatement comando = connection.prepareStatement(sql)) {
			comando.execute();
		}
	}
}
