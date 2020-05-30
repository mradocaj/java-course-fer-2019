package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Razred koji modelira prozor za prikaz lista prostih brojeva.
 * 
 * @author Maja Radočaj
 *
 */
public class PrimDemo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor.
	 */
	public PrimDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(200, 300);
		setLocation(20, 20);
		initGUI();
	}

	/**
	 * Metoda za inicijalizaciju grafičkog sučelja.
	 */
	private void initGUI() {
		setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		PrimListModel listModel = new PrimListModel();
		JList<Integer> list1 = new JList<>(listModel);
		JList<Integer> list2 = new JList<>(listModel);
		JScrollPane scrollPane1 = new JScrollPane(list1);
		JScrollPane scrollPane2 = new JScrollPane(list2);
		panel.add(scrollPane1);
		panel.add(scrollPane2);
		
		add(panel, BorderLayout.CENTER);
		JButton btnNext = new JButton("sljedeći");
		btnNext.addActionListener(e -> {
			listModel.next();
		});
		add(btnNext, BorderLayout.PAGE_END);
	}
	
	/**
	 * Glavni program.
	 * 
	 * @param args argumenti naredbenog retka
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			PrimDemo prim = new PrimDemo();
			prim.setVisible(true);
		});
	}
	
}
