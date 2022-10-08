package nasc.financeiro.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;


import nasc.financeiro.constantes.Constantes;

public class  IgPesquisaDespesa extends JDialog {
	private JTextField itemPesquisaTextField;
	private int botaoSelecionado=1,proximaRow=-1;
	/**
	 * Cria e exibe a gui
	 */
	public IgPesquisaDespesa(JTable tabela) {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		setTitle("Pesquisar Despesa");
		 final int WHIDTH=450,HEIGHT=210;
		setBounds(100, 100, WHIDTH, HEIGHT);
		
	
		
		JPanel pesquisaPanel = new JPanel();
		pesquisaPanel.setBorder(new TitledBorder(null, "Pesquisa", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		FlowLayout flowLayout = (FlowLayout) pesquisaPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		getContentPane().add(pesquisaPanel, BorderLayout.NORTH);
		
		JLabel itemDespesaLabel = new JLabel("Item de despesa:");
		itemDespesaLabel.setDisplayedMnemonic(KeyEvent.VK_I);
		itemDespesaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		pesquisaPanel.add(itemDespesaLabel);
		
		itemPesquisaTextField = new JTextField();
		itemDespesaLabel.setLabelFor(itemPesquisaTextField);
		itemPesquisaTextField.setToolTipText("Digite o item que voce quer pesquisar de acordo com o tipo escolhido abaixo");
		pesquisaPanel.add(itemPesquisaTextField);
		itemPesquisaTextField.setColumns(25);
		
		JPanel buttonsPanel = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) buttonsPanel.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
		
		JButton proximaDespesaButton = new JButton("Proxima Despesa");

		proximaDespesaButton.setToolTipText("Clique para buscar uma nova despesa com os dados acima");
		proximaDespesaButton.setMnemonic(KeyEvent.VK_P);
		buttonsPanel.add(proximaDespesaButton);
		
		JButton cancelaButton = new JButton("Cancelar");
		cancelaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelaButton.setToolTipText("Clique para fechar a janela e cancelar a busca");
		buttonsPanel.add(cancelaButton);
		
		JPanel pesquisaPorPanel = new JPanel();
		pesquisaPorPanel.setBorder(new TitledBorder(null, "Pesquisa por", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(pesquisaPorPanel, BorderLayout.CENTER);
		
		JRadioButton dataRadioButton = new JRadioButton("Data:");
		dataRadioButton.setMnemonic(KeyEvent.VK_D);
		dataRadioButton.setToolTipText("Marque para efetuar a pesquisa por Data");
		pesquisaPorPanel.add(dataRadioButton);
		
		JRadioButton descricaoRadioButton = new JRadioButton("Descrição:");

		descricaoRadioButton.setSelected(true);
		descricaoRadioButton.setMnemonic(KeyEvent.VK_E);
		descricaoRadioButton.setToolTipText("Marque para efetuar a pesquisa por Descrição");
		pesquisaPorPanel.add(descricaoRadioButton);
		
		JRadioButton valorRadioButton = new JRadioButton("Valor:");
		valorRadioButton.setMnemonic(KeyEvent.VK_V);
		valorRadioButton.setToolTipText("Marque para efetuar a pesquisa por Valor");
		pesquisaPorPanel.add(valorRadioButton);
		
		getRootPane().setDefaultButton(proximaDespesaButton);
		
		descricaoRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dataRadioButton.setSelected(false);
				valorRadioButton.setSelected(false);
				botaoSelecionado=1;
				proximaRow=-1;
			}
		});
		
		dataRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				descricaoRadioButton.setSelected(false);
				valorRadioButton.setSelected(false);
				botaoSelecionado=0;
				proximaRow=-1;
			}
		});
		
		valorRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dataRadioButton.setSelected(false);
				descricaoRadioButton.setSelected(false);
				botaoSelecionado=2;
				proximaRow=-1;
			}
		});
		
		proximaDespesaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String pesquisa = itemPesquisaTextField.getText();
				
				if(pesquisa.isBlank()) {
					//msg de erro 
				}else {
					
					 efetuaPesquisa(pesquisa,tabela);
					
				}
				
			}//performed
		});//listener
		
		setResizable(false);
		setModal(true);
		setVisible(true);

	}//construtor
	
	/**
	 * efetua a pesquisa e seleciona a row correspondendte na tabela
	 * @param pesquisa 
	 * @param tabela
	 */
	public void efetuaPesquisa(String pesquisa,JTable tabela) {
		
		switch (botaoSelecionado) {
		case 0:
		case 1:
			
			//for que itera a cada linha da tabela
			for(proximaRow++;proximaRow<tabela.getRowCount();proximaRow++) {
				String data =String.valueOf(tabela.getValueAt(proximaRow, botaoSelecionado)) ;
				pesquisa=pesquisa.trim();
				data = data.trim();
				if(data.equalsIgnoreCase(pesquisa)) {
					tabela.setRowSelectionInterval(proximaRow, proximaRow);
					return;
				}
				
			}
				
				proximaRow=-1;
				tabela.setRowSelectionInterval(0, 0);
				tabela.removeRowSelectionInterval(0, 0);
				JOptionPane.showMessageDialog(this, Constantes.MSG_ERROPESQUISA, Constantes.TITULO_ERROPESQUISA, JOptionPane.ERROR_MESSAGE);
			
			break;

		case 2:
			pesquisa=pesquisa.replace(',', '.');
			for(proximaRow++;proximaRow<tabela.getRowCount();proximaRow++) {
				
				String valorString =String.valueOf(tabela.getValueAt(proximaRow, botaoSelecionado)) ;
				
				try {
					float valor = Float.parseFloat(valorString),valorPesquisa = Float.parseFloat(pesquisa);
				
					if(valor == valorPesquisa) {
						tabela.setRowSelectionInterval(proximaRow, (proximaRow+1 == tabela.getRowCount()?proximaRow:proximaRow) );
						return;
						
					}
					
					
					
				} catch (Exception e) {
					proximaRow=-1;
					tabela.setRowSelectionInterval(0, 0);
					tabela.removeRowSelectionInterval(0, 0);
					JOptionPane.showMessageDialog(this, Constantes.MSG_ERROPESQUISA, Constantes.TITULO_ERROPESQUISA, JOptionPane.ERROR_MESSAGE);
					return;
				}
				
			}
				proximaRow=-1;
				tabela.setRowSelectionInterval(0, 0);
				tabela.removeRowSelectionInterval(0, 0);
				JOptionPane.showMessageDialog(this, Constantes.MSG_ERROPESQUISA, Constantes.TITULO_ERROPESQUISA, JOptionPane.ERROR_MESSAGE);
			
			break;
		
		}//switch
		
		
		
		
	}//efetuaPesquisa

}//classe