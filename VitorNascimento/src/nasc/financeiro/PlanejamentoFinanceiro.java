package nasc.financeiro;

import javax.swing.JOptionPane;

import nasc.financeiro.bd.Database;
import nasc.financeiro.gui.IgPlanejamentoFinanceiro;
import nasc.financeiro.modelo.Orcamento;
public class PlanejamentoFinanceiro {
final String URL_BD="jdbc:postgresql://localhost:5432/orcamentos";
Database database;
	public PlanejamentoFinanceiro() {
		
		try{
			Database database = new Database(URL_BD, "admin", "pf@orcamento");
			Orcamento inicial = new Orcamento();
			
			new IgPlanejamentoFinanceiro(inicial,database);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possível conectar ao banco de dados.\nO programa será finalizado.", "Planejamento Financeiro", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}

	}

	public static void main(String[] args) {
		new PlanejamentoFinanceiro();
	}
}