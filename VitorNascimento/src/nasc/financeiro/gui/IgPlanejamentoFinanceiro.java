package nasc.financeiro.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import nasc.financeiro.bd.Database;
import nasc.financeiro.constantes.Constantes;
import nasc.financeiro.dao.DespesaDAO;
import nasc.financeiro.dao.OrcamentoDAO;
import nasc.financeiro.dao.ReceitaDAO;
import nasc.financeiro.modelo.Despesa;
import nasc.financeiro.modelo.Orcamento;
import nasc.financeiro.modelo.Receita;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JSeparator;

public class IgPlanejamentoFinanceiro extends JFrame {
	private JTextField saldoTextField;
	private JTextField totalPagoTextField;
	private JTextField totalAPagarTextField;
	private JTextField totalMensaltextField;
	private JTextField valorTextField;
	private Orcamento orcamento;
	private Receita receita;
	

	/**
	 * Cria e exibe a GUI
	 */
	public IgPlanejamentoFinanceiro(Orcamento inicial,Database database) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				finaliza(database);
			}
		});
		
		orcamento = inicial;
		receita = new Receita(0, Receita.TIPOS[0], 0f);
		final int WIDTH=900,HEIGHT=600;
		setBackground(Color.LIGHT_GRAY);
		
		setForeground(Color.WHITE);
		setTitle("Planejamento Financeiro");
		setBounds(100, 100, WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu orcamentoMenu = new JMenu("Orçamento");
		orcamentoMenu.setMnemonic(KeyEvent.VK_O);
		menuBar.add(orcamentoMenu);
		
		JMenuItem novoMenuItem = new JMenuItem("Novo");
		
		novoMenuItem.setMnemonic(KeyEvent.VK_N);
		orcamentoMenu.add(novoMenuItem);
		
		JMenuItem abrirMenuItem = new JMenuItem("Abrir...");

		abrirMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		abrirMenuItem.setMnemonic(KeyEvent.VK_A);
		orcamentoMenu.add(abrirMenuItem);
		
		JMenuItem gravarMenuItem = new JMenuItem("Gravar");

		gravarMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK));
		gravarMenuItem.setMnemonic(KeyEvent.VK_G);
		orcamentoMenu.add(gravarMenuItem);
		
		JMenuItem gravarComoMenuItem = new JMenuItem("Gravar Como...");

		gravarComoMenuItem.setMnemonic(KeyEvent.VK_C);
		gravarComoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		orcamentoMenu.add(gravarComoMenuItem);
		
		JSeparator separator = new JSeparator();
		orcamentoMenu.add(separator);
		
		JMenuItem sairMenuItem = new JMenuItem("Sair");

		sairMenuItem.setMnemonic(KeyEvent.VK_S);
		orcamentoMenu.add(sairMenuItem);
		
		JMenu pesquisarMenu = new JMenu("Pesquisar");
		pesquisarMenu.setMnemonic(KeyEvent.VK_P);
		menuBar.add(pesquisarMenu);
		
		JMenuItem despesaMenuItem = new JMenuItem("Despesa...");
		despesaMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
		despesaMenuItem.setMnemonic(KeyEvent.VK_D);
		pesquisarMenu.add(despesaMenuItem);
		
		JMenu ajudaMenu = new JMenu("Ajuda");
		ajudaMenu.setMnemonic(KeyEvent.VK_A);
		menuBar.add(ajudaMenu);
		
		JMenuItem sobreMenuItem = new JMenuItem("Sobre o Planejamento Financeiro");
		sobreMenuItem.setMnemonic(KeyEvent.VK_S);
		ajudaMenu.add(sobreMenuItem);
		
		JPanel balancoPanel = new JPanel();
		balancoPanel.setBorder(new TitledBorder(null, "Balanço Mensal", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
		balancoPanel.setToolTipText("Informações sobre o banlanço mensal\r\n");
		getContentPane().add(balancoPanel, BorderLayout.SOUTH);
		balancoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel totalMensalLabel = new JLabel("Total Mensal:");
		balancoPanel.add(totalMensalLabel);
		
		totalMensaltextField = new JTextField();
		totalMensaltextField.setEditable(false);
		totalMensaltextField.setToolTipText("Total das receitas no mes");
		balancoPanel.add(totalMensaltextField);
		totalMensaltextField.setColumns(10);
		
		JLabel totalAPagarLabel = new JLabel("Total a Pagar:");
		balancoPanel.add(totalAPagarLabel);
		
		totalAPagarTextField = new JTextField();
		totalAPagarTextField.setEditable(false);
		totalAPagarLabel.setLabelFor(totalAPagarTextField);
		totalAPagarTextField.setToolTipText("Valor que ainda resta pagar no Mês");
		balancoPanel.add(totalAPagarTextField);
		totalAPagarTextField.setColumns(10);
		
		JLabel totalPagoLabel = new JLabel("Total Pago:");
		balancoPanel.add(totalPagoLabel);
		
		totalPagoTextField = new JTextField();
		totalPagoTextField.setEditable(false);
		totalPagoLabel.setLabelFor(totalPagoTextField);
		totalPagoTextField.setToolTipText("Valor total que ja foi pago no mês");
		balancoPanel.add(totalPagoTextField);
		totalPagoTextField.setColumns(10);
		
		JLabel saldoLabel = new JLabel("Saldo:");
		balancoPanel.add(saldoLabel);
		
		saldoTextField = new JTextField();
		saldoLabel.setLabelFor(saldoTextField);
		saldoTextField.setToolTipText("Total do saldo");
		saldoTextField.setEditable(false);
		balancoPanel.add(saldoTextField);
		saldoTextField.setColumns(10);
		
		JPanel receitaPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) receitaPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		receitaPanel.setBorder(new TitledBorder(null, "Receita", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		receitaPanel.setToolTipText("Informções relacionadas a receita");
		getContentPane().add(receitaPanel, BorderLayout.NORTH);
		
		JLabel mesLabel = new JLabel("Mês:");
		mesLabel.setDisplayedMnemonic(KeyEvent.VK_M);
		mesLabel.setToolTipText("Selecione o mes da receita\r\n");
		receitaPanel.add(mesLabel);
		
		
		JComboBox<String> mesComboBox = new JComboBox<>();
		mesComboBox.setMaximumRowCount(12);
		mesComboBox.setModel(new DefaultComboBoxModel<>(Constantes.MESES));
		mesComboBox.setSelectedIndex(0);
		mesLabel.setLabelFor(mesComboBox);
		
		receitaPanel.add(mesComboBox);
		
		
		JLabel tipoLabel = new JLabel("Tipo:");
		tipoLabel.setDisplayedMnemonic(KeyEvent.VK_T);
		tipoLabel.setToolTipText("Selecione o tipo da receita");
		receitaPanel.add(tipoLabel);

		JComboBox<String> tipoComboBox = new JComboBox<>();
		tipoLabel.setLabelFor(tipoComboBox);
		tipoComboBox.setModel(new DefaultComboBoxModel<>( Receita.TIPOS));
		tipoComboBox.setSelectedIndex(0);
		receitaPanel.add(tipoComboBox);
		
		JLabel valorLabel = new JLabel("Valor:");
		valorLabel.setDisplayedMnemonic(KeyEvent.VK_V);
		receitaPanel.add(valorLabel);
		
		JLabel erroValorLabel = new JLabel("Valor Invalido");
		erroValorLabel.setVisible(false);
		erroValorLabel.setForeground(Color.RED);
		
//----------------------------------------------------------------*****VALOR TEXT FIELD*****------------------------------------------------------------------------------
		valorTextField = new JTextField();
		valorTextField.addKeyListener(new KeyAdapter() {

			
			@Override
			public void keyReleased(KeyEvent event) {
				
				//verifica se o caracter presionado foi uma virgula
				if(event.getKeyChar()==(',')) {
					
					valorTextField.setText(trocarVirgula(valorTextField.getText()));
				}
					
				String valorString=valorTextField.getText();
				Float valor=0f;
				//verifica se o campo esta em branco
				if(valorString.isBlank()) {
					
					erroValorLabel.setVisible(false);
					
				}else {
					valor = validarValor(valorString);	
				}
				
					 
					if(valor != null) {
						//seta o valor da receita 
						receita.setValor(valor);
						//salva a receita no orçamento
						orcamento.adicionarReceita(receita);
						//modifca o valor total do mes
						atualizaBalanco(mesComboBox.getSelectedIndex());
						erroValorLabel.setVisible(false);
						
					}else {
						erroValorLabel.setVisible(true);
					}
				
				
			}//keyPressed
		});//valor text field
		valorLabel.setLabelFor(valorTextField);
		valorTextField.setToolTipText("Forneça o valor da receita selecionada");
		receitaPanel.add(valorTextField);
		valorTextField.setColumns(10);
		receitaPanel.add(erroValorLabel);
//----------------------------------------------------------------*****DESPESAS PANEL*****----------------------------------------------------------------------------------------				
		
		JPanel despesasPanel = new JPanel();
		despesasPanel.setBorder(new TitledBorder(null, "Despesas Mensais", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(despesasPanel, BorderLayout.CENTER);
		despesasPanel.setLayout(new BorderLayout(0, 0));
		
		
		JTable despesasTable = new JTable();

		despesasTable.setShowVerticalLines(true);
		despesasTable.setShowHorizontalLines(true);

		
		DefaultTableModel defaultTableModel = new DefaultTableModel(Constantes.COLUNAS, Constantes.NUMEROLINHAS);
		despesasTable.setModel(defaultTableModel);
		despesasTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		despesasTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		despesasTable.getColumnModel().getColumn(2).setPreferredWidth(300);
		despesasTable.getColumnModel().getColumn(3).setPreferredWidth(100);
		despesasPanel.add(new JScrollPane(despesasTable), BorderLayout.CENTER);
//----------------------------------------------------------------*****TIPO COMBO BOX ITEM LISTENER*****----------------------------------------------------------------------------------------				
		tipoComboBox.addItemListener(new ItemListener(){

			public void itemStateChanged(ItemEvent e) {
			
				if(e.getStateChange()== ItemEvent.SELECTED) {
					receita = new Receita(mesComboBox.getSelectedIndex(), Receita.TIPOS[tipoComboBox.getSelectedIndex()], 0f);
					if(orcamento.valorMensalPorTipo(receita.getMes(),receita.getTipo())>0) {
					valorTextField.setText(trocarVirgula(String.format("%.2f", orcamento.valorMensalPorTipo(receita.getMes(),receita.getTipo()))));
					
					}else {
					valorTextField.setText("");
					}
				}
				
			}//itemState
		});//itemListener
		
//----------------------------------------------------------------*****MES COMBO BOX ITEM LISTENER*****----------------------------------------------------------------------------------------		
		
		mesComboBox.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent e) {
				//verifica se o item mes é o mes alterado
				if(e.getStateChange()== ItemEvent.SELECTED) {
					//cria uma nova receita o mes selecionado e o tipo default
					receita = new Receita(mesComboBox.getSelectedIndex(),Receita.TIPOS[0],0f);
					tipoComboBox.setSelectedIndex(0);
					//verifica se o valor mensal desse tipo é maior que 0 caso não seja seta uma String vazia
					if(orcamento.valorMensalPorTipo(receita.getMes(), receita.getTipo())>0) { 
					valorTextField.setText(trocarVirgula(String.format("%.2f", orcamento.valorMensalPorTipo(receita.getMes(),receita.getTipo()))));
					}else {
						valorTextField.setText("");
					}
					List<Despesa> novaLista = orcamento.despesasPorMes(mesComboBox.getSelectedIndex());
					
					atualizaTabela(novaLista,despesasTable);
					atualizaBalanco(mesComboBox.getSelectedIndex());
					
				}
			}//itemState
			
		});//item listener
//----------------------------------------------------------------*****NOVO MENU ITEM MENU CLICK EVENT****----------------------------------------------------------------------------------------				
		
		novoMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int s_n =JOptionPane.showConfirmDialog(IgPlanejamentoFinanceiro.this, Constantes.MSG_NOVO, Constantes.TITULO_NOVO,JOptionPane.YES_NO_OPTION ,JOptionPane.QUESTION_MESSAGE) ;
				if(s_n==0) {
					novoOrcamento(despesasTable);
					mesComboBox.setSelectedIndex(0);
					tipoComboBox.setSelectedIndex(0);
					valorTextField.setText(String.format("%.2f", orcamento.valorMensalPorTipo(mesComboBox.getSelectedIndex(), Receita.TIPOS[0]))); 
					atualizaBalanco(0);
				}
			
			}
		});

//----------------------------------------------------------------*****DESPESA MENU ITEM MENU CLICK EVENT****----------------------------------------------------------------------------------------				
		despesaMenuItem.addActionListener( (e)  -> {
				new IgPesquisaDespesa(despesasTable);
			});	

//-----------------------------------------------------------------****ABRIR MENU ITEM CLICK EVENT****----------------------------------------------------------------------------------------				
		
		abrirMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrir(database,despesasTable,mesComboBox,tipoComboBox);
				
			}
		});	
//-----------------------------------------------------------------****SOBRE MENU ITEM CLICK EVENT****----------------------------------------------------------------------------------------				
		
		sobreMenuItem.addActionListener( (e)  -> {
			new IgSobreFinanceiro();
		});	
//-----------------------------------------------------------------****GRAVAR COMO... MENU ITEM CLICK EVENT****----------------------------------------------------------------------------------------				
		gravarComoMenuItem.addActionListener((e) ->{

			gravarComo(database);
		});
		
//-----------------------------------------------------------------****GRAVAR MENU ITEM CLICK EVENT****----------------------------------------------------------------------------------------				

		gravarMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				OrcamentoDAO dao = new OrcamentoDAO(database);
				try {
					//verifica se o orcamento já exista caso exista chama o metodo gravar
					int id =dao.buscaOcamentoPorNome(orcamento.getTitulo());
					if(id!=-1) {
						gravar(id, database, orcamento.getDespesas(), orcamento.getReceitas());
					}else {
						gravarComo(database);
					}

				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(IgPlanejamentoFinanceiro.this, Constantes.MSG_ERROREGISTRO, Constantes.TITULO_ERROREGISTRO, JOptionPane.ERROR_MESSAGE);
				}
				
			}//performed
		});//listener
//-----------------------------------------------------------------****SAIR MENU ITEM CLICK EVENT****----------------------------------------------------------------------------------------				
		
		sairMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finaliza(database);
			}
		});		
		
//-----------------------------------------------------------------****TABLE LISTENER EVENT****----------------------------------------------------------------------------------------				
		
		despesasTable.addContainerListener(new ContainerAdapter() {
			@Override
			public void componentRemoved(ContainerEvent e) {
				
				int linhaAtual = despesasTable.getSelectedRow();
				
				
				//armazena os dados da linha na lista de despesas
				Despesa despesa = validaDespesa(despesasTable.getModel(), linhaAtual,mesComboBox.getSelectedIndex());
				if(despesa!=null) {
					orcamento.adicionarDespesa(despesa);
					atualizaBalanco(mesComboBox.getSelectedIndex());
				
				}else {
					orcamento.removeDespesa(linhaAtual, mesComboBox.getSelectedIndex());
					atualizaBalanco(mesComboBox.getSelectedIndex());
				}		
				
			}
		});
		

		
		
		setResizable(false);
		setVisible(true);
		
	}//construtor
	
	/**
	 * Recebe um valor por String e tenta fazer o parse para Float 
	 * @return Float valor caso efetue o parse<br>
	 * null caso não efetue o parse
	 * @param valorString
	 */
	public Float validarValor(String valorString) {
		try {
		float valor = Float.parseFloat(valorString);
		return valor;	
			
		} catch (Exception e) {
	
			return null;	
		}
		
	}//validaValor
	
	
	/**
	 * Recebe uma String e substitui a ,(virgula) por .(ponto final)
	 * @param texto
	 * @return texto com .(ponto final) no lugar da ,(virgula)
	 */
	public String trocarVirgula(String texto) {
		texto=texto.replace(',', '.');
		return texto;
		
	}//trocar virgula
	
	
	/**
	 * Atualiza os dados do balanço mensal de acordo com o mes pasado como parametro
	 * @param mes, mes que deseja obter receitas e despesas
	 */
	public void atualizaBalanco(int mes) {
		float receita=orcamento.valorMensalReceitas(mes)
				,despesa=orcamento.totalPagoPorMes(mes),
				aPagar=orcamento.totalAPagarPorMes(mes);
		
		totalPagoTextField.setText(String.format("%.2f", despesa));
		totalAPagarTextField.setText(String.format("%.2f", aPagar));
		totalMensaltextField.setText(String.format("%.2f", despesa+aPagar));
		saldoTextField.setText(String.format("%.2f", receita-despesa));
		
	}//atualiza balanco
	
	/**
	 * Verifica se a despesa é valida, ou seja se possui uma data válida,descrição e valor.
	 * @param tabela
	 * @param linha
	 * @param mes
	 * @return Despesa caso seja valida, null se invalida.
	 */
	public Despesa validaDespesa(TableModel tabela,int linha,int mes) {
		String data,descricao,situacao;
		float valor;
			linha = (linha<0?0:linha);
		data=String.valueOf(tabela.getValueAt(linha, 0));
		if(validaData(data)==0) {
			tabela.setValueAt("",linha, 0);
			return null;
		}else if(Integer.parseInt(data.substring(3, 5))!=mes+1) {
		JOptionPane.showMessageDialog(this, Constantes.MSG_ERROMES, Constantes.TITULO_ERROMES, JOptionPane.ERROR_MESSAGE);
			tabela.setValueAt("",linha, 0);
			return null;
		}
		
		descricao=String.valueOf(tabela.getValueAt(linha, 1));
		if(descricao.isBlank()) {
			return null;
		}
		
		try {
			
		String valorString = String.valueOf(tabela.getValueAt(linha, 2));
		valorString=trocarVirgula(valorString);
		valor = Float.parseFloat(valorString);
		tabela.setValueAt(valorString, linha, 2);
			
		} catch (Exception e) {
			tabela.setValueAt("",linha, 2);
			return null;
		}
		
		situacao=(tabela.getValueAt(linha, 3)==null ||((String) tabela.getValueAt(linha, 3)).isBlank()?null:tabela.getValueAt(linha, 3).toString());


		Despesa despesa = new Despesa(linha, 1, mes, descricao, data, valor, situacao);
		return despesa;
		
		
		
	}//valida Despesa
	
	/**
	 * verifica se a data é valida
	 * @param data 
	 * @return 1 data valida, 0 data invalida
	 */
	public int validaData(String data){
	
		int dia,mes,ano,cont=0,bisexto=0;
		
		if(data.length()!=10) {
			return 0;
		}
		
		String dataAux=data.substring(6);
		
		for(int i=0;i<data.length();i++) {
		//verifica se existe caracter além de numeros ou "/"	
			if(!Character.isDigit(data.charAt(i)) && data.charAt(i)!='/') {
				return 0;}
					
		}
		
		for(int i=0;i<data.length();i++) {
			//verifica se existe mais de duas "/" na string
			if(data.charAt(i)=='/') {
				cont++;}
					
		}
		if(cont!=2) {
			return 0;
			
		}
		
		if(data.charAt(2)!='/' && data.charAt(5)!='/') {
			return 0;
		}
	
			ano=Integer.parseInt(dataAux);
			if(ano<1900 || ano>2100) {
				//verica se o ano é valido
				return 0;
			}
			//verifica se o ano é bisexto
			if(((ano % 4 == 0) &&(ano % 100 !=0))|| (ano%400 ==0)) {
				bisexto=1;
			}
			
			
			 dataAux=data.substring(3,5);
			mes=Integer.parseInt(dataAux);
			
			if(mes<1 || mes>12) {
				// verifica se o mes é valido
				return 0;
			}
			
			 dataAux=data.substring(0,2);
			 dia=Integer.parseInt(dataAux);
			 //verifica se o dia é valido de acordo com o mes	
			 switch(mes) {
			 	case 1:
			 	case 3:
			 	case 5:
			 	case 7:
			 	case 8:
			 	case 10:
			 	case 12:
			 	if(dia<1 || dia>31) {
			 		return 0;
			 	}
			 	break;
			 	case 4:
			 	case 6:
			 	case 9:
			 	case 11:
			 	if(dia<1 || dia>30) {
				return 0;
				 	}
			 	break;
			 	case 2:
			 		if(bisexto==1) {
			 			
			 			if(dia<1 || dia>29) {
			 				return 0;
			 			}
			 		}else {
			 			if(dia<1 || dia>28) {
			 				return 0;
			 			}
			 		}
			 	}
			return 1;
	
	}//valida data
	
	/**
	 * Atualiza a tabela de despesas com os valores da lista passada por paramentro.
	 * @param lista
	 * @param tabela
	 */
	public void atualizaTabela(List<Despesa> lista,JTable tabela) {
		
		DefaultTableModel defaultTableModel = new DefaultTableModel(Constantes.COLUNAS, Constantes.NUMEROLINHAS);

		int i=0;
		//modifica os dados da lista na posição selecionada
		for (Despesa d:lista) {
			d.setId(i);
			defaultTableModel.setValueAt(d.getData(), i, 0);
			defaultTableModel.setValueAt(d.getDescricao(), i, 1);
			defaultTableModel.setValueAt(d.getValor(), i, 2);
			defaultTableModel.setValueAt(d.getSituacao(), i, 3);
			i++;
		}
		
		
		tabela.setModel(defaultTableModel);
		tabela.getColumnModel().getColumn(0).setPreferredWidth(50);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(100);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(300);
		tabela.getColumnModel().getColumn(3).setPreferredWidth(100);
	}//atualiza tabela 
	
	/**
	 * Cria um novo orcamento vazio
	 * @param tabela
	 */
	public void novoOrcamento(JTable tabela) {
		orcamento=new Orcamento();
		atualizaTabela(orcamento.despesasPorMes(0), tabela);
	}//novo Orcamento
	
	
	/**
	 * Solicita um nome de um orçamento para ser salvo caso o orçamento já exista chama o método de {@link #gravar(int, Database, List, List)}
	 * @param database
	 */
	public void gravarComo(Database database) {
		
			try {
				String titulo =JOptionPane.showInputDialog(this, "Digite o nome do orcamento:", "Salvar Orcamento", JOptionPane.QUESTION_MESSAGE);
				
				int verificar = new OrcamentoDAO(database).buscaOcamentoPorNome(titulo);
				//verifica se o orçamento já existe no bd 
				if(verificar != -1) {
				int s_n =JOptionPane.showConfirmDialog(IgPlanejamentoFinanceiro.this, "Já existe um orcamento com esse nome\nDeseja sobrescrever? ", "Orçamento já existente",JOptionPane.YES_NO_OPTION ,JOptionPane.QUESTION_MESSAGE) ;
					
					if(s_n == 0) {
						gravar(verificar, database, orcamento.getDespesas(), orcamento.getReceitas());
						return;
					}else {
						JOptionPane.showMessageDialog(this, "Atenção!!!\nNada foi gravado", "Informações não gravadas", JOptionPane.WARNING_MESSAGE);
						return;
					}
					
				}
				if(titulo!=null) {
					
					int id = proximoValorSequencia("seqorcamento",database);
					if(id!=-1) {
						orcamento.setTitulo(titulo);
						orcamento.setId(id);
						OrcamentoDAO dao= new OrcamentoDAO(database);
						dao.inserirRegistro(orcamento);
						orcamento.defineIdOrcamento();
						adiconaListasBd(orcamento.getDespesas(),orcamento.getReceitas(),database);
						JOptionPane.showMessageDialog(this, Constantes.MSG_DADOSSUCESSO, Constantes.TITULO_DADOSSUCESSO, JOptionPane.INFORMATION_MESSAGE);

					}else {
						JOptionPane.showMessageDialog(this, "Erro ao obter ID", "Erro banco de dados", JOptionPane.ERROR_MESSAGE);

					}
				
				}//titulo null
				
				
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, Constantes.MSG_ERROREGISTRO, Constantes.TITULO_ERROREGISTRO, JOptionPane.ERROR_MESSAGE);

			}
		
		
	}//gravar Como
	
	/**
	 * Sobrescreve um orçamento já existente no bd
	 * @param id
	 * @param database
	 * @param despesas
	 * @param receitas
	 */
	public void gravar(int id, Database database,List<Despesa> despesas,List<Receita> receitas) {
		
		try {
			orcamento.setId(id);
			orcamento.defineIdOrcamento();
			
			DespesaDAO dDAO = new DespesaDAO(database);
			ReceitaDAO rDAO = new ReceitaDAO(database);
			
			dDAO.removeTodosPorIdOrcamento(id);
			rDAO.removeTodosporIdOrcamento(id);
				
			adiconaListasBd(despesas, receitas, database);
			JOptionPane.showMessageDialog(this, Constantes.MSG_DADOSSUCESSO, Constantes.TITULO_DADOSSUCESSO, JOptionPane.INFORMATION_MESSAGE);

				
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, Constantes.MSG_ERROREGISTRO, Constantes.TITULO_ERROREGISTRO, JOptionPane.ERROR_MESSAGE);
		}
		
	}//gravar
	
	/**
	 * Solicita ao usuario o nome de um orçamento no banco de dados verifica se ele existe exibe na tela principal
	 * 
	 * @param database
	 * @param tabela, a tabela utilizada
	 * @param mesCB, comboBox com o mes
	 * @param tipoCB, comboBox com o titulo
	 */
	public void abrir(Database database, JTable tabela, JComboBox<String> mesCB, JComboBox<String> tipoCB) {

			
		try {
			

				String[] orcamentos = new OrcamentoDAO(database).listaOrcamentos();
				
				if(orcamentos==null) {
					JOptionPane.showMessageDialog(this, "Nenhum orcamento encontrado", "Não existem orcamentos salvos", JOptionPane.ERROR_MESSAGE);
					return;
				}
				//salva o titulo do orçamento para o caso de a listagem retornar vazia
				String guardaTitulo = orcamento.getTitulo();
				
				new IgListaOrcamentos(orcamentos, orcamento);
			if(!orcamento.getTitulo().isBlank()) {
			
				OrcamentoDAO dao = new OrcamentoDAO(database);
				int id = dao.buscaOcamentoPorNome(orcamento.getTitulo());
				if(id!=-1) {
					
					DespesaDAO dDAO = new DespesaDAO(database);
					ReceitaDAO rDAO = new ReceitaDAO(database);
					
					orcamento.setDespesas(dDAO.getListaPorIdOrcamento(id));
					orcamento.setReceitas(rDAO.getListaPorIdOrcamento(id));
					
					orcamento.setId(id);
					mesCB.setSelectedIndex(0);
					tipoCB.setSelectedIndex(0);
					receita = new Receita(mesCB.getSelectedIndex(),Receita.TIPOS[0],0f);
					if(orcamento.valorMensalPorTipo(receita.getMes(), receita.getTipo())>0) { 
					valorTextField.setText(trocarVirgula(String.format("%.2f", orcamento.valorMensalPorTipo(receita.getMes(),receita.getTipo()))));
					}else {
						valorTextField.setText("");
					}
					atualizaTabela(orcamento.despesasPorMes(mesCB.getSelectedIndex()), tabela);
					atualizaBalanco(mesCB.getSelectedIndex());
					
				}
				
			}else {
				orcamento.setTitulo(guardaTitulo);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, Constantes.MSG_ERROABRIRORCAMENTO, Constantes.TITULO_ERROABRIRORCAMENTO, JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	/**
	 * Obtem o proximo valor de uma sequencia 
	 */
	public int proximoValorSequencia(String nomeSequencia,Database database) {
		try { // Obtém o próximo valor numérico da sequência por meio do resultado da consulta SQL.
				 ResultSet resultSet = database.select(String.format("SELECT nextval('%s');", nomeSequencia));

			     /*Verifica se há uma linha na tabela que possui o resultado da consulta, ou seja, se há uma linha no objeto ResultSet.
			      * Se tiver obtém o próximo valor da sequência acessando a primeira e única coluna da tabela resultante da consulta. 
			      */
			     return resultSet.next() ? resultSet.getInt(1) : -1;
				

		} catch (SQLException e) {
			return -1;
		}
	}//proximo valor sequencia
	
	/**
	 *Adiciona as listas de despesa e receitas na suas respectivas tabelas.
	 *@param listaDespesa,listaReceita,database
	 */
	public void adiconaListasBd(List<Despesa> despesas,List<Receita> receitas,Database database) {
		try {
			DespesaDAO dDAO= new DespesaDAO(database); 
			for(Despesa d: despesas) {
				
				dDAO.inserirRegistro(d);
				
			}//for despesa
			
			ReceitaDAO rDAO = new ReceitaDAO(database);
			for(Receita r : receitas) {
				
				rDAO.inserirRegistro(r);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, Constantes.MSG_ERROREGISTRO, Constantes.TITULO_ERROREGISTRO, JOptionPane.ERROR_MESSAGE);

		}
		
	}//adicionaListBd
	
	
	/**
	 * Verifica se o usuario deseja salvar o arquivo atual antes de finalizar.
	 * @param database
	 */
	public void finaliza(Database database) {
		int s_n =JOptionPane.showConfirmDialog(IgPlanejamentoFinanceiro.this, Constantes.MSG_SAIR, Constantes.TITULO_SAIR,JOptionPane.YES_NO_OPTION ,JOptionPane.QUESTION_MESSAGE) ;
		if(s_n==0) {
			gravarComo(database);
			System.exit(0);
			
		}else {
			System.exit(0);
		}
	}
	
}//class igPlanejamentoFinanceiro