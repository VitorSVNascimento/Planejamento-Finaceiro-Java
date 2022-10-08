package nasc.financeiro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import nasc.financeiro.bd.Database;
import nasc.financeiro.modelo.Receita;

public class ReceitaDAO {

	Database database;
	public ReceitaDAO(Database database) {
		this.database=database;
	}
	
	/** 
	 * Cria uma instrução SQL parametrizada para inserir os registros na tabela.
	 */
	public void inserirRegistro(Receita receita) throws SQLException {

		if(receita.getValor()>0) {
	PreparedStatement preparedStatement = database.getConnection().prepareStatement(String.format("INSERT INTO receita VALUES (?,?,?,?);"));
		
		preparedStatement.setInt(1, receita.getMes()); 
		preparedStatement.setString(2, receita.getTipo());
		preparedStatement.setFloat(3, receita.getValor());
		preparedStatement.setInt(4, receita.getIdOrcamento());
		preparedStatement.executeUpdate();
		}
		
}//inserir registro
	
	/**
	 * Recebe um id de orçamento por parametro e exclui todas as receitas referentes a aquele orçamento no banco de dados
	 * @param id de um orçamento
	 * @throws SQLException
	 */
	public void removeTodosporIdOrcamento(int id) throws SQLException{
		
	PreparedStatement preparedStatement = database.getConnection().prepareStatement(String.format("DELETE FROM receita WHERE idorcamento=?;"));
		
		preparedStatement.setInt(1, id); 
		preparedStatement.executeUpdate();
		
	}//removeTodosOrcamento
	
	
	/**
	 * Recebe um id de orçamento por parametro e armazena todas as receitas referentes a aquele orçamento em uma lista de receitas
	 * @param id de um orçamento
	 * @return listReceitas
	 * @throws SQLException
	 */
	public List<Receita> getListaPorIdOrcamento(int id) throws SQLException {
		
		ResultSet result;
		PreparedStatement preparedStatement = database.getConnection().prepareStatement(String.format("SELECT * FROM receita WHERE idorcamento=?;"));
		preparedStatement.setInt(1, id); 
		result=preparedStatement.executeQuery();
		List<Receita> receitas = new ArrayList<Receita>();
		while(result.next()) {
			Receita receita = new Receita();
			
			receita.setMes(result.getInt(1));
			receita.setTipo(result.getString(2));
			receita.setValor(result.getFloat(3));
			receita.setIdOrcamento(result.getInt(4));
			
			receitas.add(receita);
		}
		return receitas;
	}
	
	
}//class ReceitaDAO
