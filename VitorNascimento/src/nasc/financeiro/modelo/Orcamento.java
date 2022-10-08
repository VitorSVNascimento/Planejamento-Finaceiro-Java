package nasc.financeiro.modelo;


import java.util.ArrayList;
import java.util.List;

public class Orcamento {
	
private int id;
private String titulo;
private List<Receita> receitas;
private List<Despesa> despesas;


public Orcamento() {
	receitas = new ArrayList<Receita>();
	despesas = new ArrayList<Despesa>();
	
}


public int getId() {
	return id;
}


public void setId(int id) {
	this.id = id;
}


public String getTitulo() {
	return titulo;
}


public List<Receita> getReceitas() {
	return receitas;
}


public void setReceitas(List<Receita> receitas) {
	this.receitas = receitas;
}


public List<Despesa> getDespesas() {
	return despesas;
}


public void setDespesas(List<Despesa> despesas) {
	this.despesas = despesas;
}


public void setTitulo(String titulo) {
	this.titulo = titulo;
}


public void adicionarReceita(Receita receita) {
	for(Receita r : receitas) {
		if(r.getMes()==receita.getMes() && r.getTipo().equals(receita.getTipo()) ) {
			r.setValor(receita.getValor());
			return;
		}
		
		
	}
	
	receitas.add(receita);
	return;	
}

public float valorMensalReceitas(int mes){
	
	if(receitas.size()>0) {
		
		float valorTotal=0f;
		for(Receita r : receitas) {
			
			if(mes==r.getMes()) {
				
				valorTotal+=r.getValor();
			}
			
		}
		return valorTotal;
		
	}
	return 0f;
}

public float valorMensalPorTipo(int mes,String tipo) {
	
	if(receitas.size()>0) {

		float valorTotal=0f;
		for(Receita r : receitas) {
			if(mes==r.getMes() && tipo.equals(r.getTipo())) {
				valorTotal+=r.getValor();
			}
		
		}
		return valorTotal;
		
		}
	return 0f;
}//valor Mensal por tipo

public void adicionarDespesa(Despesa despesa) {
	for(Despesa d: despesas) {
		
		if(d.getId()==despesa.getId() && d.getMes()==despesa.getMes()) {
			d.setData(despesa.getData());
			d.setValor(despesa.getValor());
			d.setSituacao(despesa.getSituacao());
			d.setDescricao(despesa.getDescricao());
			
			return;
		}
	}
	
	despesas.add(despesa);
	return;
}//adicona despesa

public void removeDespesa(int id, int mes) {
	
	for(Despesa d: despesas) {
		
		if(d.getId()==id && d.getMes()==mes) {
			
			
			despesas.remove(d);
			
			return;
		}
	}
	
	return;
	
}

public List<Despesa> despesasPorMes(int mes) {
	 List<Despesa> despesasMes=new ArrayList<>();
	for(Despesa d: despesas) {
		
		if(d.getMes()==mes) {
			despesasMes.add(d);
		}
	}
	
	return despesasMes;
}

public float totalAPagarPorMes(int mes) {
	float total=0f;
	for(Despesa d:despesas) {
		
		if(d.getMes()==mes && d.getSituacao()==null) {
		total+=d.getValor();	
			
		}
	}
	
	return total;
	
}//total a pagar por mes

public float totalPagoPorMes(int mes) {
	float total=0f;
	for(Despesa d:despesas) {
		if(d.getSituacao()==null) {
		}
		
		if(d.getMes()==mes && d.getSituacao()!=null) {
			
		total+=d.getValor();	
			
		}
	}
	
	return total;
	
}//total a pagar por mes

public void defineIdOrcamento() {
	
	for(Despesa d : despesas) {
		
		d.setIdOrcamento(id);
	}
	
	for(Receita r : receitas) {
		r.setIdOrcamento(id);
		
	}
	
	
}//define IdOrcamento

}//class Orcamento
