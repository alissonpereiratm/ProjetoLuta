package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modelo.Lutador;

public class LutadorDAO {

	public static void salvarDadosBD(Lutador lutador) throws SQLException {

		String sql = "INSERT INTO lutador (nome, nacionalidade, idade, altura, peso, categoria, vitoria, derrota, empate) VALUES (?,?,?,?,?,?,?,?,?)";

		Connection con = null;
		PreparedStatement codigo = null;

		try {
			con = Conexao.conectarBD();

			codigo = con.prepareStatement(sql);

			codigo.setString(1, lutador.getNome());
			codigo.setString(2, lutador.getNacionalidade());
			codigo.setInt(3, lutador.getIdade());
			codigo.setDouble(4, lutador.getAltura());
			codigo.setDouble(5, lutador.getPeso());
			codigo.setString(6, lutador.getCategoria());
			codigo.setInt(7, lutador.getVitorias());
			codigo.setInt(8, lutador.getDerrotas());
			codigo.setInt(9, lutador.getEmpates());

			codigo.execute();

		} catch (SQLException s) {
			System.out.println("ERRO INSERT....");
			s.printStackTrace();
		} finally {
			if (codigo != null) {
				codigo.close();
			}
			if (con != null)
				con.close();
		}

	}

	public static ArrayList<Lutador> lerDadosBD() throws SQLException {
		String sql = "SELECT * from lutador";

		ArrayList<Lutador> lutadores = new ArrayList<>();

		Connection con = null;
		PreparedStatement codigo = null;
		ResultSet dadosBD = null;

		try {
			con = Conexao.conectarBD();
			codigo = con.prepareStatement(sql);
			dadosBD = codigo.executeQuery();

			while (dadosBD.next()) {
				Lutador lutador = new Lutador();

				lutador.setNome(dadosBD.getString("nome"));
				lutador.setNacionalidade(dadosBD.getString("nacionalidade"));
				lutador.setIdade(dadosBD.getInt("idade"));
				lutador.setAltura(dadosBD.getDouble("altura"));
				lutador.setPeso(dadosBD.getDouble("peso"));
				lutador.setCategoria(dadosBD.getString("categoria"));
				lutador.setVitorias(dadosBD.getInt("vitoria"));
				lutador.setDerrotas(dadosBD.getInt("derrota"));
				lutador.setEmpates(dadosBD.getInt("empate"));
				lutadores.add(lutador);
			}
		} catch (Exception e) {
			System.out.println("ERRO SELECT.....");
		} finally {
			if (codigo != null) {
				codigo.close();
			}
			if (con != null)
				con.close();
		}
		return lutadores;
	}

	public static Lutador pesquisarLutadorBD(String nome) throws SQLException {
		String sql = "SELECT * FROM lutador WHERE nome = ? ";

		Lutador lutador = null;
		Connection con = null;
		PreparedStatement codigo = null;
		ResultSet dadosBD = null;

		try {
			con = Conexao.conectarBD();
			codigo = con.prepareStatement(sql);
			codigo.setString(1, nome);
			dadosBD = codigo.executeQuery();

			if (dadosBD.next() != false) {
				lutador = new Lutador();
				lutador.setNome(dadosBD.getString("nome"));
				lutador.setNacionalidade(dadosBD.getString("nacionalidade"));
				lutador.setIdade(dadosBD.getInt("idade"));
				lutador.setAltura(dadosBD.getDouble("altura"));
				lutador.setPeso(dadosBD.getDouble("peso"));
				lutador.setCategoria(dadosBD.getString("categoria"));
				lutador.setVitorias(dadosBD.getInt("vitoria"));
				lutador.setDerrotas(dadosBD.getInt("derrota"));
				lutador.setEmpates(dadosBD.getInt("empate"));

			}

		} catch (Exception e) {
			System.out.println("ERRO SELECT.....");
		} finally {
			if (codigo != null) {
				codigo.close();
			}
			if (con != null)
				con.close();
		}
		return lutador;
	}

	
	public static void vitoriaLutador(Lutador lutador) throws SQLException {

		String sql1 = "UPDATE lutador SET vitoria = ? WHERE nome = ?";

		Connection con = null;
		PreparedStatement codigo = null;

		try {
			con = Conexao.conectarBD();
			codigo = con.prepareStatement(sql1);
			codigo.setDouble(1, lutador.getVitorias());
			codigo.setString(2, lutador.getNome());
			codigo.execute();
		} catch (SQLException e) {
			System.out.println("ERRO UPDATE.....");
			e.printStackTrace();
		} finally {
			if (codigo != null) {
				codigo.close();
			}
			if (con != null)
				con.close();
		}
	}
	
	public static void derrotaLutador(Lutador lutador) throws SQLException {

		String sql1 = "UPDATE lutador SET derrota = ? WHERE nome = ?";

		Connection con = null;
		PreparedStatement codigo = null;

		try {
			con = Conexao.conectarBD();
			codigo = con.prepareStatement(sql1);
			codigo.setDouble(1, lutador.getDerrotas());
			codigo.setString(2, lutador.getNome());
			codigo.execute();
		} catch (SQLException e) {
			System.out.println("ERRO UPDATE.....");
			e.printStackTrace();
		} finally {
			if (codigo != null) {
				codigo.close();
			}
			if (con != null)
				con.close();
		}
	}
	
	public static void empateLutador(Lutador lutador) throws SQLException {

		String sql1 = "UPDATE lutador SET empate = ? WHERE nome = ?";

		Connection con = null;
		PreparedStatement codigo = null;

		try {
			con = Conexao.conectarBD();
			codigo = con.prepareStatement(sql1);
			codigo.setDouble(1, lutador.getEmpates());
			codigo.setString(2, lutador.getNome());
			codigo.execute();
		} catch (SQLException e) {
			System.out.println("ERRO UPDATE.....");
			e.printStackTrace();
		} finally {
			if (codigo != null) {
				codigo.close();
			}
			if (con != null)
				con.close();
		}
	}

	
	
	public static void alterarPesoLutadorBD(Lutador lutador) throws SQLException {

		String sql1 = "UPDATE lutador SET peso = ? , categoria = ? WHERE nome = ?";

		Connection con = null;
		PreparedStatement codigo = null;

		try {
			con = Conexao.conectarBD();
			codigo = con.prepareStatement(sql1);
			codigo.setDouble(1, lutador.getPeso());
			codigo.setString(2, lutador.getCategoria());
			codigo.setString(3, lutador.getNome());
			codigo.execute();
		} catch (SQLException e) {
			System.out.println("ERRO UPDATE.....");
			e.printStackTrace();
		} finally {
			if (codigo != null) {
				codigo.close();
			}
			if (con != null)
				con.close();
		}
	}

	public static void deletarLutadorBD(Lutador lutador) throws SQLException {

		String sql1 = "DELETE FROM lutador WHERE nome = ?";

		Connection con = null;
		PreparedStatement codigo = null;

		try {
			con = Conexao.conectarBD();
			codigo = con.prepareStatement(sql1);
			codigo.setString(1, lutador.getNome());
			codigo.execute();
		} catch (SQLException e) {
			System.out.println("ERRO DELETE.....");
			e.printStackTrace();
		} finally {
			if (codigo != null) {
				codigo.close();
			}
			if (con != null)
				con.close();
		}
	}

}
