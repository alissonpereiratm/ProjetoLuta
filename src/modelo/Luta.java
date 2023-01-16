package modelo;

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

	@Override
	public String toString() {
		return "A Luta entre " + lutador1 + " e " + lutador2 + " foi iniciada e terá " + round + " rounds ";
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
