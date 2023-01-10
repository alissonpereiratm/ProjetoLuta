package aplicacao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import dao.LutaDAO;
import dao.LutadorDAO;
import modelo.Luta;
import modelo.Lutador;

public class Principal {

	public static void main(String[] args) throws SQLException {

		int op = 0;
		do {
			op = Integer.parseInt(JOptionPane.showInputDialog(
					"MENU\n1-Cadastrar\n2-Pesquisar Lutador\n3-Mostrar lutadores\n4-Alterar peso\n5-Excluir lutador\n6-Marcar Luta\n7-Mostrar lutas marcadas"
							+"\n8-Iniciar Luta "+"\n9-Finaliza Sistema"));
			switch (op) {
			case 1:
				try {
					LutadorDAO.salvarDadosBD(cadastrar());
				} catch (SQLException e) {
					e.printStackTrace();
				}

				break;

			case 2:

				pesquisarLutador();
				break;
			case 3:
				mostraListaLutadores();
				break;
			case 4:
				alterarPesoLutador();
				break;
			case 5:
				excluirLutador();
				break;
			case 6:
				LutaDAO.marcarLutaBD(Luta.marcarLuta());
				break;
			case 7:
				mostralutasMarcadas();
				break;
			case 8:
				marcarLuta();
			}
		} while (op != 9);
	}
	
	
	
	
	private static void marcarsarLuta() throws SQLException {
		Principal.mostralutasMarcadas();
		int id=	Integer.parseInt(JOptionPane.showInputDialog("Digite o ID da luta:"));
		Luta pesquisaLuta = LutaDAO.pesquisarLutaBD(id);
		JOptionPane.showMessageDialog(null, pesquisaLuta.toString());
	}
	

	
	
	
	
	
	public static void mostralutasMarcadas() {
		ArrayList<Luta> lutas = new ArrayList<>();
		try {
			lutas = LutaDAO.lerDadosBD();
		} catch (SQLException e) {
			System.out.println("ERRO AO CARREGAR DADOS DO BANCO");
		}
		String mostrarLutas = "";
		for (Luta luta : lutas) {
			mostrarLutas +="ID da Luta "+luta.getId()+"\n"+ luta.getLutador1() + " VS " + luta.getLutador2() + "\n" + luta.getRound() + " Rounds"
					+ "\n\n";
		}
		JOptionPane.showMessageDialog(null,"Lutas marcadas\n\n"+ mostrarLutas);
	}

	public static void mostraListaLutadores() {
		ArrayList<Lutador> lutadores = new ArrayList<>();
		try {
			lutadores = LutadorDAO.lerDadosBD();
		} catch (SQLException e) {
			System.out.println("ERRO AO CARREGAR DADOS DO BANCO");
		}
		mostrarDados(lutadores);
	}

	private static void excluirLutador() {
		String nome = JOptionPane.showInputDialog("Digite o nome do lutador para excluir");
		try {
			Lutador lutador = LutadorDAO.pesquisarLutadorBD(nome);
			if (lutador != null) {
				mostrarDados(lutador);
				int res = JOptionPane.showConfirmDialog(null, "Deseja excluir " + lutador.getNome() + " ?");
				if (res == 0) {
					LutadorDAO.deletarLutadorBD(lutador);
				}

			} else
				JOptionPane.showMessageDialog(null, "Lutador não cadastrado");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Luta marcarLuta() {
		Luta luta = null;
		String nome = JOptionPane.showInputDialog("Digite o nome do lutador 1");
		try {
			Lutador lutador1 = LutadorDAO.pesquisarLutadorBD(nome);
			if (lutador1 != null) {
				mostrarDados(lutador1);
				String nome2 = JOptionPane.showInputDialog("Digite o nome do lutador 2");

				Lutador lutador2 = LutadorDAO.pesquisarLutadorBD(nome2);
				if (lutador2 != null) {
					mostrarDados(lutador2);
					int round = Integer.parseInt(JOptionPane.showInputDialog("Digite a quantidade de rounds"));

					JOptionPane.showMessageDialog(null, "Luta marcada!");
					luta = new Luta(round, nome, nome2);

				} else
					JOptionPane.showMessageDialog(null, "Lutador não cadastrado");
			} else
				JOptionPane.showMessageDialog(null, "Lutador não cadastrado");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return luta;

	}

	private static void alterarPesoLutador() {

		String nome = JOptionPane.showInputDialog("Digite o nome do lutador para alterar o peso");
		try {
			Lutador lutador = LutadorDAO.pesquisarLutadorBD(nome);
			if (lutador != null) {
				mostrarDados(lutador);

				double peso = Double.parseDouble(JOptionPane.showInputDialog("Digite o novo peso:"));

				lutador.setPeso(peso);
				lutador.categoriaPeso(peso);

				LutadorDAO.alterarPesoLutadorBD(lutador);

				JOptionPane.showMessageDialog(null, "Dados atualizados");

			} else
				JOptionPane.showMessageDialog(null, "Lutador não cadastrado");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void mostrarDados(Lutador lutador) {
		JOptionPane.showMessageDialog(null, lutador.toString());

	}

	private static void mostrarDados(ArrayList<Lutador> lutadores) {
		String mostrarLutadores = "";
		for (Lutador lutador : lutadores) {
			mostrarLutadores += "Nome: " + lutador.getNome() + "\nCategoria: " + lutador.getCategoria() + "\nVitoria: "
					+ lutador.getVitorias() + "\nDerrota: " + lutador.getDerrotas() + "\nEmpate: "
					+ lutador.getEmpates() + "\n\n";
		}
		JOptionPane.showMessageDialog(null, mostrarLutadores);
	}

	private static void pesquisarLutador() throws SQLException {
		String nome = JOptionPane.showInputDialog("Digite o nome");
		Lutador pesquisaLutador = LutadorDAO.pesquisarLutadorBD(nome);
		JOptionPane.showMessageDialog(null, pesquisaLutador.toString());
	}

	
	
	
	
	
	
	
	
	
	
	
	private static Lutador cadastrar() throws SQLException {

		String nome = "";

		nome = (JOptionPane.showInputDialog("Insira seu nome:"));

		double peso = 0;
		String nacionalidade = (JOptionPane.showInputDialog("Insira a sua nacionalidade:"));
		int idade = (Integer.parseInt(JOptionPane.showInputDialog("Insira a sua idade:")));
		double altura = (Double.parseDouble(JOptionPane.showInputDialog("Insira a sua altura:")));
		do {
			peso = (Double.parseDouble(JOptionPane.showInputDialog("Insira o seu peso:")));
			if (peso < 52.2 || peso > 120.2) {
				JOptionPane.showMessageDialog(null, "Nenhuma categoria disponível para seu peso!\nDigite novamente");
			}
		} while (peso < 52.2 || peso > 120.2);
		int vitorias = 0;
		int derrotas = 0;
		int empates = 0;

		Lutador lutador = new Lutador(nome, nacionalidade, idade, altura, peso, vitorias, derrotas, empates);
		lutador.categoriaPeso(peso);
		return lutador;

	}
}