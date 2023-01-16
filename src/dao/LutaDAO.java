package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import modelo.Luta;
import modelo.Lutador;

public class LutaDAO {
	

	public static void marcarLutaBD(Luta luta) throws SQLException {
		String sql = "INSERT INTO luta (rounds,lutador1,lutador2) VALUES (?,?,?)";

		Connection con = null;
		PreparedStatement codigo = null;

		try {
			con = Conexao.conectarBD();

			codigo = con.prepareStatement(sql);
			codigo.setInt(1, luta.getRound());
			codigo.setString(2, luta.getLutador1());
			codigo.setString(3, luta.getLutador2());

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
	
	
	public static void deletarLutaBD(Luta luta) throws SQLException {

		String sql1 = "DELETE FROM luta WHERE idLuta = ?";

		Connection con = null;
		PreparedStatement codigo = null;

		try {
			con = Conexao.conectarBD();
			codigo = con.prepareStatement(sql1);
			codigo.setInt(1, luta.getId());
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
	
	

	public static ArrayList<Luta> lerDadosBD() throws SQLException {
		String sql = "SELECT * from luta";

		ArrayList<Luta> lutas = new ArrayList<>();

		Connection con = null;
		PreparedStatement codigo = null;
		ResultSet dadosBD = null;

		try {
			con = Conexao.conectarBD();
			codigo = con.prepareStatement(sql);
			dadosBD = codigo.executeQuery();

			while (dadosBD.next()) {
				Luta luta = new Luta();

				luta.setRound(dadosBD.getInt("rounds"));
				luta.setId(dadosBD.getInt("idluta"));
				luta.setLutador1(dadosBD.getString("lutador1"));
				luta.setLutador2(dadosBD.getString("lutador2"));

				lutas.add(luta);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERRO SELECT.....");
		} finally {
			if (codigo != null) {
				codigo.close();
			}
			if (con != null)
				con.close();
		}
		return lutas;
	}

	public static Luta pesquisarLutaBD(int id) throws SQLException {
		String sql = "SELECT * FROM luta WHERE idluta = ? ";

		Luta luta = null;
		Connection con = null;
		PreparedStatement codigo = null;
		ResultSet dadosBD = null;

		try {
			con = Conexao.conectarBD();
			codigo = con.prepareStatement(sql);
			codigo.setInt(1, id);
			dadosBD = codigo.executeQuery();

			if (dadosBD.next() != false) {
				 luta = new Luta();

				luta.setRound(dadosBD.getInt("rounds"));
				luta.setId(dadosBD.getInt("idluta"));
				luta.setLutador1(dadosBD.getString("lutador1"));
				luta.setLutador2(dadosBD.getString("lutador2"));

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
		return luta;
	}

}
