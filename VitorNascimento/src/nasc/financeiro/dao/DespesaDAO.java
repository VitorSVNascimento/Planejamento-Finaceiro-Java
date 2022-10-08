package nasc.financeiro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nasc.financeiro.bd.Database;
import nasc.financeiro.modelo.Despesa;


public class DespesaDAO {
	Database database;
	public DespesaDAO(Database database) {
		this.database=database;
	}
	
	/** 
	 * Cria uma instrução SQL parametrizada para inserir os registros na tabela.
	 * 
	 */
	public void inserirRegistro(Despesa despesa) throws SQLException {

		if(despesa.getValor()>0) {
			PreparedStatement preparedStatement = database.getConnection().prepareStatement(String.format("INSERT INTO despesa VALUES (?,?,?,?,?,?,?);"));
			
			preparedStatement.setInt(1, despesa.getId()); 
			preparedStatement.setInt(2, despesa.getMes()); 
			preparedStatement.setString(3, despesa.getDescricao());
			preparedStatement.setString(4, despesa.getData());
			preparedStatement.setString(5, despesa.getSituacao());
			preparedStatement.setFloat(6, despesa.getValor());
			preparedStatement.setInt(7, despesa.getIdOrcamento());
			preparedStatement.executeUpdate();
		}

	
}//inserir registro
	
	/**
	 * Recebe um id de orçamento por parametro e exclui todas as despesas referentes a aquele orçamento no banco de dados
	 * @param id de um orçamento
	 * @throws SQLException
	 */
	public void removeTodosPorIdOrcamento(int id) throws SQLException{
		
	PreparedStatement preparedStatement = database.getConnection().prepareStatement(String.format("DELETE FROM despesa WHERE idorcamento=?;"));
		
		preparedStatement.setInt(1, id); 
		preparedStatement.executeUpdate();
		
	}//removeTodosOrcamento
	
	/**
	 * Recebe um id de orçamento por parametro e armazena todas as despesas referentes a aquele orçamento em uma lista de despesas
	 * @param id de um orçamento
	 * @return listDespesas
	 * @throws SQLException
	 */
	public List<Despesa> getListaPorIdOrcamento(int id) throws SQLException {
		
		ResultSet result;
		PreparedStatement preparedStatement = database.getConnection().prepareStatement(String.format("SELECT * FROM despesa WHERE idorcamento=?;"));
		preparedStatement.setInt(1, id); 
		result=preparedStatement.executeQuery();
		List<Despesa> despesas = new ArrayList<Despesa>();
		while(result.next()) {
			Despesa despesa = new Despesa();
			
			despesa.setId(result.getInt(1));
			despesa.setMes(result.getInt(2));
			despesa.setDescricao(result.getString(3));
			despesa.setData(result.getString(4));
			despesa.setSituacao(result.getString(5));
			despesa.setValor(result.getFloat(6));
			despesa.setIdOrcamento(result.getInt(7));
			
			despesas.add(despesa);
		}
		return despesas;
	}

}
