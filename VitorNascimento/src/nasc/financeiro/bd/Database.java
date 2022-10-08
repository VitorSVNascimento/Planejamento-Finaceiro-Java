package nasc.financeiro.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database implements AutoCloseable {
	private String url, user="admin", password="pf@orcamentos";
	
	// Uma conexão (sessão) com um banco de dados específico. As instruções SQL são executadas e os resultados são retornados dentro do contexto de uma conexão.
	private Connection connection;
	
	// Objeto usado para executar uma instrução SQL e retornar os resultados produzidos.
	private Statement statement;
	

	private ResultSet resultSet; 
	
	/**
	  * Obtém uma conexão com o banco de dados.  
	  * 
	  * A URL do banco de dados deve ser especificada na forma jdbc:subprotocolo:localização, onde localização pode ser especificada 
	  * como //url_servidor:porta/url_banco_de_dados ou simplesmente o nome do banco de dados.
	  * 
	  * Exemplos: 
	  * 						1. jdbc:mysql://localhost:5432/books 
	  * 						2. jdbc:postgresql://localhost:5432/cursos
	  * 						3. jdbc:postgresql:cursos
	  */
	public Database(String url, String user, String password) throws SQLException {
		this.url = url;
		this.user = user;
		this.password = password;
		
		// Cria uma conexão com o banco de dados.
		connection = DriverManager.getConnection(url, user, password);
		
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
	} 

	public String getUrl() {
		return url;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public Connection getConnection() {
		return connection;
	}
	
	public Statement getStatement() {
		return statement;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}


	@Override
	public void close() throws SQLException { 
		if (statement != null) statement.close();
		if (connection != null) connection.close();
		if (resultSet != null) resultSet.close();
	} 


	public ResultSet select(String instructionSql) throws SQLException {
		try { 		
					resultSet = statement.executeQuery(instructionSql);
					return resultSet;
					
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
				throw new SQLException("Erro ao executar uma consulta SQL SELECT.", sqlException);
				
			}	
	} 
	

	public int query(String instructionSql) throws SQLException {
		try {  
					return statement.executeUpdate(instructionSql);

				} catch (SQLException sqlException) {
					throw new SQLException("Erro ao executar uma consulta SQL INSERT, UPDATE ou DELETE.");
				}
	}
} // class Database 