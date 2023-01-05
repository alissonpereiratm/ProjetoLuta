package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

	private static String url = "jdbc:postgresql://localhost:5432/Mma"; // Nome da base de dados
	private static String user = "postgres"; // nome do usuário do postgres
	private static String password = "admin"; // senha do postgres

	public static Connection conectarBD() {
		try {
			Class.forName("org.postgresql.Driver");
			Connection conexao = DriverManager.getConnection(url, user, password);
			return conexao;
		} catch (SQLException sqlErro) {
			System.out.println("ERRO: Conexão");
			return null;
		} catch (ClassNotFoundException e) {
			System.out.println("ERRO: Driver conexão");
			return null;
		}
	}
}
