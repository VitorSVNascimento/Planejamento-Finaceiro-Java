package nasc.financeiro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nasc.financeiro.bd.Database;
import nasc.financeiro.modelo.Orcamento;



public class OrcamentoDAO {
Database database;
	public OrcamentoDAO(Database database) {
		this.database=database;
	}

	public void inserirRegistro(Orcamento orcamento) throws SQLException {
	/* 
	 * Cria uma instrução SQL parametrizada para inserir os registros na tabela.
	 * Um objeto PreparedStatement permite criar uma instrução SQL parametrizada que pode ser executada várias vezes simplesmente alterando os parâmetros da instrução por novos valores. 
	 * O PreparedStatement possui um desempenho mais eficiente na execução de instruções SQL do que Statement, porque elas são compiladas em um formato para agilizar a sua execução.
	 */
	PreparedStatement preparedStatement = database.getConnection().prepareStatement(String.format("INSERT INTO orcamento VALUES (?,?);"));
		
		preparedStatement.setInt(1, orcamento.getId() ); 
		preparedStatement.setString(2, orcamento.getTitulo());
		preparedStatement.executeUpdate();
	
}
	
	public int buscaOcamentoPorNome(String titulo) throws SQLException{
		/* 
		 * Cria uma instrução SQL parametrizada para buscar um orçamento no banco de dados .
		 * 
		 */
		ResultSet result;
		PreparedStatement preparedStatement = database.getConnection().prepareStatement(String.format("SELECT * FROM orcamento WHERE titulo=?;"));
		preparedStatement.setString(1, titulo); 
		result=preparedStatement.executeQuery();
		
		return result.next()?result.getInt(1):-1;
	
}//buscaOrcamentoPorId

/**
 * obtem o nome de todos os orçamentos do banco de dados e os armazena em uma String[]
 * @return String[] orcamento
 * @throws SQLException
 */
public String[] listaOrcamentos() throws SQLException{
	
	ResultSet result;
	PreparedStatement preparedStatement = database.getConnection().prepareStatement(String.format("SELECT * FROM orcamento;"));
	 
	result=preparedStatement.executeQuery();
	List<String> lista = new ArrayList<String>();
	while(result.next()) {
		
		lista.add(result.getString(2));
		
	}
	
	if(lista.size()!=0) {
		String[] orcamentos = new String[lista.size()];
		
		for(int i=0;i<lista.size();i++) {
			
			orcamentos[i]=lista.get(i);
			
		}
		
		return orcamentos;
	}else {
		return null;
	}

	
}
	
	
}//class Orcamento DAO
