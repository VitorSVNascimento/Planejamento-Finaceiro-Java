package nasc.financeiro.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import nasc.financeiro.modelo.Orcamento;

public class IgListaOrcamentos extends JDialog {
	/**
	 * Cria e exibe a gui.
	 */
	public IgListaOrcamentos(String[] orcamentos,Orcamento orcamento) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				fechar(orcamento);
			}
		});
		setBounds(130, 130, 292, 284);
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		JButton abrirButton = new JButton("Abrir");

		abrirButton.setMnemonic(KeyEvent.VK_A);
		panel.add(abrirButton);
		
		JButton cancelarButton = new JButton("Cancelar");
		cancelarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fechar(orcamento);
			}
		});
		cancelarButton.setMnemonic(KeyEvent.VK_C);
		panel.add(cancelarButton);
		
		getRootPane().setDefaultButton(abrirButton);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JList<String> orcamentoList = new JList<>();
		orcamentoList.setModel(new AbstractListModel<String>() {
			String[] values =orcamentos;
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		orcamentoList.setSelectedIndex(0);
		
		scrollPane.setViewportView(orcamentoList);
		
		abrirButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				orcamento.setTitulo(orcamentoList.getSelectedValue());
				dispose();

			}
		});
		
		setResizable(false);
		setModal(true);
		setVisible(true);

	}
	
	public void fechar(Orcamento orcamento) {
		orcamento.setTitulo("");
		dispose();
		
	}
	

}
