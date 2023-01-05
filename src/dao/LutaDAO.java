package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modelo.Luta;

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
			System.out.println("ERRO....");
			s.printStackTrace();
		} finally {
			if (codigo != null) {
				codigo.close();
			}
			if (con != null)
				con.close();
		}

	}

}
