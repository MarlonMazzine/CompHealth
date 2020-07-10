package br.com.prova.hermano.src.main.java.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.prova.hermano.src.main.java.model.Paciente;
import br.com.prova.hermano.src.main.java.repository.RepositorioPaciente;

public class PacienteController {
	
	RepositorioPaciente repositorioCliente = new RepositorioPaciente();
	
	public List<Paciente> listarTodos() {
		try {
			return repositorioCliente.listarTodos();
		} catch (SQLException e) {
			return new ArrayList<>();
		}
	}
	
	public String cadastrar(Paciente paciente) {
		try {
			repositorioCliente.cadastrar(paciente);
			return "Paciente cadastrado com sucesso!";
		} catch (SQLException e) {
			return "Erro ao cadastrar paciente. Motivo: " + e.getMessage();
		}
	}
	
	public String editar(Paciente paciente) {
		try {
			repositorioCliente.editar(paciente);
			return "Cadastro alterado com sucesso!";
		} catch (SQLException e) {
			return "Erro ao alterar o cadastro. Motivo: " + e.getMessage();
		}
	}
	
	public String apagar(Paciente paciente) {
		try {
			repositorioCliente.apagar(paciente);
			return "Paciente excluído com sucesso!";
		} catch (SQLException e) {
			return "Erro ao excluir paciente. Motivo: " + e.getMessage();
		}
	}
}
