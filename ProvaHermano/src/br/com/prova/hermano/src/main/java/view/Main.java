package br.com.prova.hermano.src.main.java.view;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;

import br.com.prova.hermano.src.main.java.controller.PacienteController;
import br.com.prova.hermano.src.main.java.model.Paciente;

public class Main extends JFrame{
	
	private static final long serialVersionUID = -529335583753631346L;
	private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private JFrame frame;
	private JTextField txtCpf;	
	private JTextField txtNome;
	private JTextField txtEndereco;
	private JTextField txtTelefone;
	private JComboBox<String> comboBoxSexo;
	private JFormattedTextField txtDataDeNascimento;
	private JTable table;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				new Main();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public Main() {
		initialize();
	}
	
	public void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtCpf = new JTextField();
		txtCpf.setBounds(12, 42, 154, 30);
		frame.getContentPane().add(txtCpf);
		txtCpf.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("CPF");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(12, 13, 56, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNome.setBounds(178, 13, 56, 16);
		frame.getContentPane().add(lblNome);
		
		txtNome = new JTextField();
		txtNome.setColumns(10);
		txtNome.setBounds(178, 42, 154, 30);
		frame.getContentPane().add(txtNome);
		
		JLabel lblEndereco = new JLabel("Endereco");
		lblEndereco.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEndereco.setBounds(342, 13, 218, 16);
		frame.getContentPane().add(lblEndereco);
		
		txtEndereco = new JTextField();
		txtEndereco.setColumns(10);
		txtEndereco.setBounds(342, 42, 154, 30);
		frame.getContentPane().add(txtEndereco);
		
		JLabel lblTelefone = new JLabel("Telefone");
		lblTelefone.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTelefone.setBounds(508, 13, 218, 16);
		frame.getContentPane().add(lblTelefone);
		
		txtTelefone = new JTextField();
		txtTelefone.setColumns(10);
		txtTelefone.setBounds(508, 42, 154, 30);
		frame.getContentPane().add(txtTelefone);
		
		JLabel lblSexo = new JLabel("Sexo");
		lblSexo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSexo.setBounds(840, 13, 71, 16);
		frame.getContentPane().add(lblSexo);
		
		comboBoxSexo = new JComboBox<>();
		comboBoxSexo.setModel(new DefaultComboBoxModel<String>(new String[] {"M", "F"}));
		comboBoxSexo.setBounds(840, 42, 56, 30);
		frame.getContentPane().add(comboBoxSexo);
		
		txtDataDeNascimento = new JFormattedTextField(mask());
		txtDataDeNascimento.setColumns(10);
		txtDataDeNascimento.setBounds(674, 42, 154, 30);
		frame.getContentPane().add(txtDataDeNascimento);
		
		JLabel lblDataDeNascimento = new JLabel("Data de nascimento");
		lblDataDeNascimento.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDataDeNascimento.setBounds(674, 13, 218, 16);
		frame.getContentPane().add(lblDataDeNascimento);
		
		JButton botaoCadastrar = new JButton("Cadastrar");
		botaoCadastrar.setBounds(120, 110, 101, 30);
		botaoCadastrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (isNullOrEmpty(txtCpf, txtNome, txtEndereco, txtTelefone, txtDataDeNascimento)) {
					JOptionPane.showMessageDialog(null, "Todos os campos são de preenchimento obrigatório");
					return;
				}
				
				JOptionPane.showMessageDialog(null, new PacienteController().cadastrar(criarNovoPaciente()));
				popularTabela();
				resetarDados();
			}
		});
		frame.getContentPane().add(botaoCadastrar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1) {
					JOptionPane.showMessageDialog(null, new PacienteController().apagar(criarNovoPaciente()));
					popularTabela();
					resetarDados();
				} else {
					JOptionPane.showMessageDialog(null, "Selecione 1 paciente para excluir");
				}
			}
		});
		btnExcluir.setBounds(233, 110, 101, 30);
		frame.getContentPane().add(btnExcluir);
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isNullOrEmpty(txtCpf, txtNome, txtEndereco, txtTelefone, txtDataDeNascimento)) {
					JOptionPane.showMessageDialog(null, "Todos os campos são de preenchimento obrigatório");
					return;
				}
				
				JOptionPane.showMessageDialog(null, new PacienteController().editar(criarNovoPaciente()));
				popularTabela();
				resetarDados();
			}
		});
		btnAtualizar.setBounds(346, 110, 101, 30);
		frame.getContentPane().add(btnAtualizar);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 169, 958, 471);
		frame.getContentPane().add(scrollPane_1);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int i = table.getSelectedRow();
				
				TableModel model = table.getModel();
				
				txtCpf.setText(model.getValueAt(i, 0).toString());
				txtNome.setText(model.getValueAt(i, 1).toString());
				txtEndereco.setText(model.getValueAt(i, 2).toString());
				txtTelefone.setText(model.getValueAt(i, 3).toString());
				comboBoxSexo.setSelectedIndex(model.getValueAt(i, 4).equals("M") ? 0 : 1);
				txtDataDeNascimento.setText(((LocalDate) model.getValueAt(i, 5)).format(DTF));
			}
		});
		scrollPane_1.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"CPF", "Nome", "Endereco", "Telefone", "Sexo", "Nascimento"
			}
		));
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetarDados();
			}
		});
		btnLimpar.setBounds(459, 110, 101, 30);
		frame.getContentPane().add(btnLimpar);
		
		popularTabela();
	}

	private void popularTabela() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setNumRows(0);
		
		List<Paciente> pacientes = new PacienteController().listarTodos();
		
		for (Paciente paciente : pacientes) {
			model.addRow(new Object[] {
				paciente.getCpf(),
				paciente.getNome(),
				paciente.getEndereco(),
				paciente.getTelefone(),
				paciente.getSexo(),
				paciente.getDataDeNascimento()
			});
		}
	}	

	private MaskFormatter mask() {
		try {
			return new MaskFormatter("##/##/####");
		} catch (ParseException e) {
			return null;
		}
	}
	
	private Paciente criarNovoPaciente() {
		Paciente paciente = new Paciente();
		
		paciente.setCpf(txtCpf.getText());
		paciente.setNome(txtNome.getText());
		paciente.setEndereco(txtEndereco.getText());
		paciente.setTelefone(txtTelefone.getText());
		paciente.setSexo(comboBoxSexo.getSelectedItem().toString());
		paciente.setDataDeNascimento(LocalDate.parse(txtDataDeNascimento.getText(), DTF));
		
		return paciente;
	}
	
	private boolean isNullOrEmpty(JTextField...jTextField) {
		for(JTextField string : jTextField) {
			if (string.getText() == null || string.getText().isEmpty()) {
				return true;
			}
		}
		return false;
	}
	
	private void resetarDados() {
		txtCpf.setText("");
		txtNome.setText("");
		txtEndereco.setText("");
		txtTelefone.setText("");
		comboBoxSexo.setSelectedIndex(0);
		txtDataDeNascimento.setText("");
	}
}
