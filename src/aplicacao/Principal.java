package aplicacao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

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
							+ "\n8-Iniciar Luta " + "\n9-Finaliza Sistema"));
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
				iniciarLuta();
			}
		} while (op != 9);
	}

	private static void iniciarLuta() throws SQLException {
		Principal.mostralutasMarcadas();
		int id = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID da luta:"));
		Luta pesquisaLuta = LutaDAO.pesquisarLutaBD(id);
		JOptionPane.showMessageDialog(null, pesquisaLuta.toString());

		pesquisaLuta.getLutador1();

		Random gerador = new Random(2);
		int lutador01 = 0;
		int lutador02 = 0;
		String vitoria = "";
		if (pesquisaLuta.getRound() != 0) {

			for (int i = 0; i < pesquisaLuta.getRound(); i++) {
				int resultado1 = gerador.nextInt(10);
				int resultado2 = gerador.nextInt(10);
				JOptionPane.showMessageDialog(null,
						"Round " + (i + 1) + "\nO Lutador " + pesquisaLuta.getLutador1() + " obtve " + resultado1
								+ " pontos" + "\nO Lutador " + pesquisaLuta.getLutador2() + " obtve " + resultado2
								+ " pontos");
				if (resultado1 > resultado2) {
					vitoria = pesquisaLuta.getLutador1();
					JOptionPane.showMessageDialog(null, "O lutador " + vitoria + " ganhou o Round");
				} else if (resultado1 < resultado2) {
					vitoria = pesquisaLuta.getLutador2();
					JOptionPane.showMessageDialog(null, "O lutador " + vitoria + " ganhou o Round");
				} else
					JOptionPane.showMessageDialog(null, "Round empatado");
				if (pesquisaLuta.getLutador1().equalsIgnoreCase(vitoria)) {
					lutador01++;
				} else
					lutador02++;

			}
			if (lutador01 > lutador02) {

				Lutador lutador = LutadorDAO.pesquisarLutadorBD(pesquisaLuta.getLutador1());
				if (lutador != null) {
					lutador.vitoria(1);

					LutadorDAO.vitoriaLutador(lutador);

					JOptionPane.showMessageDialog(null, "Dados atualizados");
					JOptionPane.showMessageDialog(null, "O lutador " + lutador.getNome() + " venceu a luta");

				}
				Lutador lutador1 = LutadorDAO.pesquisarLutadorBD(pesquisaLuta.getLutador2());
				if (lutador1 != null) {
					lutador1.derrota(1);

					LutadorDAO.derrotaLutador(lutador1);

					JOptionPane.showMessageDialog(null, "Dados atualizados");

				}
			} else if (lutador01 < lutador02) {
				Lutador lutador = LutadorDAO.pesquisarLutadorBD(pesquisaLuta.getLutador2());
				if (lutador != null) {
					lutador.setVitorias(1);

					LutadorDAO.vitoriaLutador(lutador);

					JOptionPane.showMessageDialog(null, "Dados atualizados");
					JOptionPane.showMessageDialog(null, "O lutador " + lutador.getNome() + " venceu a luta");

				}

				Lutador lutador1 = LutadorDAO.pesquisarLutadorBD(pesquisaLuta.getLutador2());
				if (lutador1 != null) {
					lutador1.setDerrotas(1);

					LutadorDAO.derrotaLutador(lutador1);

					JOptionPane.showMessageDialog(null, "Dados atualizados");
				}
			} else if (lutador01 == lutador02) {
				Lutador lutador1 = LutadorDAO.pesquisarLutadorBD(pesquisaLuta.getLutador2());
				if (lutador1 != null) {
					lutador1.setEmpates(1);

					LutadorDAO.empateLutador(lutador1);
				}
				Lutador lutador = LutadorDAO.pesquisarLutadorBD(pesquisaLuta.getLutador1());
				if (lutador != null) {
					lutador.setEmpates(1);

					LutadorDAO.empateLutador(lutador);

					JOptionPane.showMessageDialog(null, "Dados atualizados");
				}

				JOptionPane.showMessageDialog(null, "Luta empatada");
			}
			
			LutaDAO.deletarLutaBD(pesquisaLuta);
		} else
			JOptionPane.showMessageDialog(null, "Vc precisa marcar uma luta antes");

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
			mostrarLutas += "ID da Luta " + luta.getId() + "\n" + luta.getLutador1() + " VS " + luta.getLutador2()
					+ "\n" + luta.getRound() + " Rounds" + "\n\n";
		}
		JOptionPane.showMessageDialog(null, "Lutas marcadas\n\n" + mostrarLutas);
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