package modelo;

public class Lutador {

	private String nome;
	private String nacionalidade;
	private int idade;
	private double altura;
	private double peso;
	private String categoria;
	private int vitorias;
	private int derrotas;
	private int empates;

	public Lutador() {
		this.nome = null;
		this.nacionalidade = null;
		this.idade = 0;
		this.altura = 0;
		this.peso = 0;
		this.categoria = null;
		this.vitorias = 0;
		this.derrotas = 0;
		this.empates = 0;
	}

	public Lutador(String nome, String nacionalidade, int idade, double altura, double peso, int vitorias, int derrotas,
			int empates) {

		this.nome = nome;
		this.nacionalidade = nacionalidade;
		this.idade = idade;
		this.altura = altura;
		this.peso = peso;
		this.vitorias = vitorias;
		this.derrotas = derrotas;
		this.empates = empates;
	}

	@Override
	public String toString() {
		return "Lutador \nnome=" + this.nome + "\n nacionalidade=" + this.nacionalidade + "\n idade=" + this.idade
				+ "\n altura=" + this.altura + "\n peso=" + this.peso + "\n categoria=" + this.categoria
				+ "\n vitorias=" + this.vitorias + "\n derrotas=" + this.derrotas + "\n empates=" + this.empates
				+ "\n\n";
	}

	public void vitoria(int vitoria) {
		this.vitorias += vitoria;
	}

	public void derrota(int derrota) {
		this.derrotas += derrota;
	}

	public void empate(int empate) {
		this.empates += empate;
	}

	public void categoriaPeso(double peso) {
		if (peso >= 52.2 && peso < 70.3)
			this.categoria = "Leve";
		else if (peso >= 70.3 && peso < 83.9)
			this.categoria = "Médio";
		else if (peso >= 83.9 && peso < 120.2)
			this.categoria = "Pesado";

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public int getVitorias() {
		return vitorias;
	}

	public void setVitorias(int vitorias) {
		this.vitorias = vitorias;
	}

	public int getDerrotas() {
		return derrotas;
	}

	public void setDerrotas(int derrotas) {
		this.derrotas = derrotas;
	}

	public int getEmpates() {
		return empates;
	}

	public void setEmpates(int empates) {
		this.empates = empates;
	}

}
