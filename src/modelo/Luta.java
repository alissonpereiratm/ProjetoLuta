package modelo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import aplicacao.Principal;
import dao.LutadorDAO;

public class Luta {
	private int id;
	private int round;
	private String lutador1;
	private String lutador2;

	/*
	 * 
	 * Marcar luta antes de implementar o BD!
	 * 
	 * public Luta marcarLuta(ArrayList<Lutador> lutadores) { Luta luta = null;
	 * String nomes = ""; for (Lutador lutador : lutadores) { nomes += "\n" +
	 * lutador.getNome(); } if (lutadores.size() > 1) { String primeiroLutador =
	 * JOptionPane.showInputDialog("Digite o nome do primeiro lutador" + nomes); for
	 * (Lutador lutador : lutadores) { if
	 * (lutador.getNome().equalsIgnoreCase(primeiroLutador)) { this.lutador1 =
	 * lutador.getNome(); String segundoLutador =
	 * JOptionPane.showInputDialog("Digite o nome do segundo lutador" + nomes); for
	 * (Lutador lutador2 : lutadores) { if
	 * (lutador2.getNome().equalsIgnoreCase(segundoLutador)) { if
	 * ((lutador.getCategoria().equalsIgnoreCase(lutador2.getCategoria())) &&
	 * (!lutador.getNome().equalsIgnoreCase(lutador2.getNome()))) { this.lutador2 =
	 * lutador2.getNome(); this.round = Integer
	 * .parseInt(JOptionPane.showInputDialog(null,
	 * "Digite a quantidade de rounds"));
	 * 
	 * JOptionPane.showMessageDialog(null, "A luta entre " + lutador.getNome() +
	 * " e " + lutador2.getNome() + " foi marcada!"); luta = new Luta(this.round,
	 * this.lutador1, this.lutador2); return luta; } else {
	 * JOptionPane.showMessageDialog(null,
	 * "Luta nao aprovada!\nVerificar a categoria ou o nome dos lutadores");
	 * marcarLuta(lutadores); } } }
	 * 
	 * } } } else JOptionPane.showMessageDialog(null,
	 * "Não há lutadores o suficiente para marcar uma luta"); return null; }
	 */

	public static Luta marcarLuta() {
		int op = 0;
		Luta luta = null;

		try {
			do {
				Principal.mostraListaLutadores();
				String nome = JOptionPane.showInputDialog("Digite o nome do lutador 1");

				Lutador lutador1 = LutadorDAO.pesquisarLutadorBD(nome);
				if (lutador1 != null) {
					Principal.mostraListaLutadores();
					String nome2 = JOptionPane.showInputDialog("Digite o nome do lutador 2");

					Lutador lutador2 = LutadorDAO.pesquisarLutadorBD(nome2);
					if (lutador2 != null) {
						if (lutador1.getCategoria().equalsIgnoreCase(lutador2.getCategoria())) {
							int round = Integer.parseInt(JOptionPane.showInputDialog("Digite a quantidade de rounds"));

							JOptionPane.showMessageDialog(null, "Luta marcada!");
							luta = new Luta(round, nome, nome2);
							op = 1;
						} else
							JOptionPane.showMessageDialog(null, "Categoria dos lutadores diferentes\nDigite novamente o nome dos 2 lutadores");
					} else
						JOptionPane.showMessageDialog(null, "Lutador não cadastrado\nDigite novamente o nome dos 2 lutadores");
				} else
					JOptionPane.showMessageDialog(null, "Lutador não cadastrado\nDigite novamente o nome dos 2 lutadores");
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

	public void iniciarLuta(ArrayList<Lutador> lutadores) {
		Random gerador = new Random(2);
		int lutador01 = 0;
		int lutador02 = 0;
		String vitoria = "";
		if (this.round != 0) {
			JOptionPane.showMessageDialog(null, "A luta será " + this.lutador1 + " VS " + this.lutador2 + " e possui "
					+ this.round + " Rounds no total");
			for (int i = 0; i < this.round; i++) {
				int resultado1 = gerador.nextInt(10);
				int resultado2 = gerador.nextInt(10);
				JOptionPane.showMessageDialog(null, "Round " + (i + 1) + "\nO Lutador " + this.lutador1 + " obtve "
						+ resultado1 + " pontos" + "\nO Lutador " + this.lutador2 + " obtve " + resultado2 + " pontos");
				if (resultado1 > resultado2) {
					vitoria = this.lutador1;
					JOptionPane.showMessageDialog(null, "O lutador " + vitoria + " ganhou o Round");
				} else if (resultado1 < resultado2) {
					vitoria = this.lutador2;
					JOptionPane.showMessageDialog(null, "O lutador " + vitoria + " ganhou o Round");
				} else
					JOptionPane.showMessageDialog(null, "Round empatado");
				if (this.lutador1.equalsIgnoreCase(vitoria)) {
					lutador01++;
				} else
					lutador02++;

			}
			if (lutador01 > lutador02) {
				for (Lutador lutador : lutadores) {
					if (lutador.getNome().equalsIgnoreCase(this.lutador1)) {
						lutador.vitoria(1);
						JOptionPane.showMessageDialog(null, "O lutador " + this.lutador1 + " venceu a luta");
					}
				}
				for (Lutador lutador2 : lutadores) {
					if (lutador2.getNome().equalsIgnoreCase(this.lutador2)) {
						lutador2.derrota(1);
					}
				}
			} else if (lutador01 < lutador02) {
				for (Lutador lutador : lutadores) {
					if (lutador.getNome().equalsIgnoreCase(this.lutador2)) {
						lutador.vitoria(1);
						JOptionPane.showMessageDialog(null, "O lutador " + this.lutador2 + " venceu a luta");

					}
				}
				for (Lutador lutador2 : lutadores) {
					if (lutador2.getNome().equalsIgnoreCase(this.lutador1)) {
						lutador2.derrota(1);
					}
				}
			} else if (lutador01 == lutador02) {
				for (Lutador lutador : lutadores) {
					if (lutador.getNome().equalsIgnoreCase(this.lutador2)) {
						lutador.empate(1);

					}
				}
				for (Lutador lutador2 : lutadores) {
					if (lutador2.getNome().equalsIgnoreCase(this.lutador1)) {
						lutador2.empate(1);
					}
				}
				JOptionPane.showMessageDialog(null, "Luta empatada");
			}
		} else
			JOptionPane.showMessageDialog(null, "Vc precisa marcar uma luta antes");

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
