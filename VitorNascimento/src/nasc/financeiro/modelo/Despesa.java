package nasc.financeiro.modelo;

public class Despesa {

	private int id,idOrcamento,mes;
	private String descricao,data,situacao;
	private float valor;

	
	public Despesa() {
		
	}



	public Despesa(int id, int idOrcamento, int mes, String descricao, String data, float valor, String situacao) {
		this.id = id;
		this.idOrcamento = idOrcamento;
		this.mes = mes;
		this.descricao = descricao;
		this.data = data;
		this.valor = valor;
		this.situacao = situacao;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdOrcamento() {
		return idOrcamento;
	}

	public void setIdOrcamento(int idOrcamento) {
		this.idOrcamento = idOrcamento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public String getSituacao() {
		return situacao;
	}



	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	public int getMes() {
		return mes;
	}



	public void setMes(int mes) {
		this.mes = mes;
	}



	@Override
	public String toString() {
		System.out.println("Situacao----------"+situacao);
		StringBuilder builder = new StringBuilder();
	builder.append(String.format("Data:%s\nMes:%d\nValor:%.2f\nDescrição:%s", data,mes,valor,descricao));
	builder.append(String.format("\nSituacao:%s", situacao));
	return builder.toString();
		
	}
	
	
	
	
}
