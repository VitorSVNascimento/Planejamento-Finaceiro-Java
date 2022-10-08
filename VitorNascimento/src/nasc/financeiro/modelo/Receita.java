package nasc.financeiro.modelo;

public class Receita {
	
	public static final String[] TIPOS= {"SALÁRIO","ALUGUEL","BOLSA DE ESTUDOS","BOLSA DE PESQUISA","BONIFICAÇÃO","COMISSÃO","INVESTIMENTOS"};
	private int idOrcamento=0,mes;
	private String tipo;
	private Float valor;
	

	public Receita() {
	
	}
	
	
	
	
	public Receita(int mes, String tipo, Float valor) {
		this.mes = mes;
		this.tipo = tipo;
		this.valor = valor;
	}




	public int getIdOrcamento() {
		return idOrcamento;
	}





	public void setIdOrcamento(int idOrcamento) {
		this.idOrcamento = idOrcamento;
	}





	public int getMes() {
		return mes;
	}





	public void setMes(int mes) {
		this.mes = mes;
	}





	public String getTipo() {
		return tipo;
	}





	public void setTipo(String tipo) {
		this.tipo = tipo;
	}





	public Float getValor() {
		return valor;
	}





	public void setValor(Float valor) {
		this.valor = valor;
	}





	public String[] getTIPOS() {
		return TIPOS;
	}
	
	
	
	

}
