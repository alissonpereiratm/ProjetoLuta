package modelo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import aplicacao.Principal;
import dao.LutaDAO;
import dao.LutadorDAO;

public class Luta {
	private int id;
	private int round;
	private String lutador1;
	private String lutador2;

	@Override
	public String toString() {
		return "A Luta entre " + lutador1 + " e " + lutador2 + " foi iniciada e terá " + round + " rounds ";
	}

	public void iniciarLuta() throws SQLException {
		mostralutasMarcadas();
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

					JOptionPane.showMessageDialog(null, "Luta empatada");

				}

				JOptionPane.showMessageDialog(null, "Dados atualizados");
			}

			LutaDAO.deletarLutaBD(pesquisaLuta);
		} else
			JOptionPane.showMessageDialog(null, "Vc precisa marcar uma luta antes");

	}

	public void mostralutasMarcadas() {
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

	public Luta marcarLuta() {
		int op = 0;
		Luta luta = null;

		try {
			do {
				Principal.mostraListaLutadores();
				String nome = JOptionPane.showInputDialog("Digite o nome do primeiro lutador ");

				Lutador lutador1 = LutadorDAO.pesquisarLutadorBD(nome);
				if (lutador1 != null) {
					Principal.mostraListaLutadores();
					String nome2 = JOptionPane.showInputDialog("Digite o nome do segundo lutador");

					Lutador lutador2 = LutadorDAO.pesquisarLutadorBD(nome2);
					if (lutador2 != null) {
						if (lutador1.getCategoria().equalsIgnoreCase(lutador2.getCategoria())) {
							int round = Integer.parseInt(JOptionPane.showInputDialog("Digite a quantidade de rounds"));

							JOptionPane.showMessageDialog(null, "Luta marcada!");
							luta = new Luta(round, nome, nome2);
							op = 1;
						} else
							JOptionPane.showMessageDialog(null,
									"Categoria dos lutadores diferentes\nDigite novamente o nome dos 2 lutadores");
					} else
						JOptionPane.showMessageDialog(null,
								"Lutador não cadastrado\nDigite novamente o nome dos 2 lutadores");
				} else
					JOptionPane.showMessageDialog(null,
							"Lutador não cadastrado\nDigite novamente o nome dos 2 lutadores");
			} while (op == 0);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return luta;

	}

	public Luta(int round, String lutador1, String lutador2) {
		this.round = round;
		this.lutador1 = lutador1;
		this.lutador2 = lutador2;
	}

	public Luta() {

		this.round = 0;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public String getLutador1() {
		return lutador1;
	}

	public void setLutador1(String lutador1) {
		this.lutador1 = lutador1;
	}

	public String getLutador2() {
		return lutador2;
	}

	public void setLutador2(String lutador2) {
		this.lutador2 = lutador2;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
