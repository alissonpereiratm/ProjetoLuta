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
		Lutador lutador = new Lutador();
		Luta luta = new Luta();

		int op = 0;
		do {
			op = Integer.parseInt(JOptionPane.showInputDialog(
					"MENU\n1-Cadastrar\n2-Pesquisar Lutador\n3-Mostrar lutadores\n4-Alterar peso\n5-Excluir lutador\n6-Marcar Luta\n7-Mostrar lutas marcadas"
							+ "\n8-Iniciar Luta " + "\n9-Finaliza Sistema"));
			try {
				switch (op) {

				case 1:

					LutadorDAO.salvarDadosBD(cadastrar());

					break;
				case 2:
					lutador.pesquisarLutador();
					break;
				case 3:
					mostraListaLutadores();
					break;
				case 4:
					lutador.alterarPesoLutador();
					break;
				case 5:
					lutador.excluirLutador();
					break;
				case 6:
					LutaDAO.marcarLutaBD(luta.marcarLuta());
					break;
				case 7:
					luta.mostralutasMarcadas();
					break;
				case 8:
					luta.iniciarLuta();
					break;
				case 9:
					JOptionPane.showMessageDialog(null, "Sistema Finalizado!");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} while (op != 9);	
		}

	public static void mostraListaLutadores() {
		ArrayList<Lutador> lutadores = new ArrayList<>();
		try {
			lutadores = LutadorDAO.lerDadosBD();
		} catch (SQLException e) {
			System.out.println("ERRO AO CARREGAR DADOS DO BANCO");
		}
		Principal.mostrarDados(lutadores);
	}

	public static void mostrarDados(Lutador lutador) {
		JOptionPane.showMessageDialog(null, lutador.toString());

	}

	public static void mostrarDados(ArrayList<Lutador> lutadores) {
		String mostrarLutadores = "";
		for (Lutador lutador : lutadores) {
			mostrarLutadores += "Nome: " + lutador.getNome() + "\nCategoria: " + lutador.getCategoria() + "\nVitoria: "
					+ lutador.getVitorias() + "\nDerrota: " + lutador.getDerrotas() + "\nEmpate: "
					+ lutador.getEmpates() + "\n\n";
		}
		JOptionPane.showMessageDialog(null, mostrarLutadores);
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