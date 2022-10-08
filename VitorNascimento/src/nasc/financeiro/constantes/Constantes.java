package nasc.financeiro.constantes;

public interface Constantes {
	
	String[] MESES= {"JANEIRO","FEVEREIRO","MARÇO","ABRIL","MAIO","JUNHO","JULHO","AGOSTO","SETEMBRO","OUTUBRO","NOVEMBRO","DEZEMBRO"};
	String ANOAPLICACAO="2022",DEV="VITOR NASCIMENTO",VERSAO="1.0",NOME="PLANEJAMENTO FINANCEIRO",MSG_ERROMES="Mes diferente do mes selecionado",
			TITULO_ERROMES="Mês Invalido",MSG_ERROPESQUISA="A Pesquisa não encontrou nenhum resultado",TITULO_ERROPESQUISA="Erro de Busca",
			MSG_NOVO="Deseja abrir um novo orcamento? Os dados não salvos serão perdidos",TITULO_NOVO="Novo Orçamento",
			MSG_ERROREGISTRO="Erro ao inserir registro",TITULO_ERROREGISTRO="Erro banco de dados",
			MSG_DADOSSUCESSO="Os dados foram gravados com sucesso",TITULO_DADOSSUCESSO="Dados Gravados",MSG_ERROABRIRORCAMENTO="Erro ao abrir o orcamento",
			TITULO_ERROABRIRORCAMENTO="Não foi possivel abrir o orcamento",
			MSG_SAIR="Deseja salvar o arquivo antes de fechar?\nOs dados não salvos serão perdidos",TITULO_SAIR="Finalizar";
	String[] COLUNAS= {"Data","Descrição","Valor","Situação"};
	int NUMEROLINHAS=25;

}
