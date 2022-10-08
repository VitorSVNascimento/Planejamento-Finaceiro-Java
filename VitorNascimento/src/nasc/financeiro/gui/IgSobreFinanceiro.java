package nasc.financeiro.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import nasc.financeiro.constantes.Constantes;

public class IgSobreFinanceiro extends JDialog {

	/**
	 * Cria e exibe a GUI.
	 */
	public IgSobreFinanceiro() {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
	
		setTitle("Sobre o Planejamento Financeiro");
		setBounds(100, 100, 450, 150);
		
		JPanel sobrePanel = new JPanel();
		getContentPane().add(sobrePanel, BorderLayout.CENTER);
		sobrePanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Programa", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
		panel.setBounds(6, 6, 422, 52);
		sobrePanel.add(panel);
		
		JLabel proInfosLabel = new JLabel(String.format("%s| VERSÃO: %s| ANO %s", Constantes.NOME,Constantes.VERSAO,Constantes.ANOAPLICACAO));
		panel.add(proInfosLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Desenvolvedor", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(6, 59, 422, 52);
		sobrePanel.add(panel_1);
		
		JLabel devLabel = new JLabel(Constantes.DEV);
		panel_1.add(devLabel);
		
		setResizable(false);
		setModal(true);
		setVisible(true);

	}
}
