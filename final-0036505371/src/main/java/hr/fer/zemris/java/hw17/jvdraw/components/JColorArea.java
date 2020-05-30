package hr.fer.zemris.java.hw17.jvdraw.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.border.BevelBorder;

/**
 * Razred koji modelira komponentu za odabir boje.
 * <p>Komponenta na klik otvara izbornik za odabir boja, a na svojoj površini prikazuje
 * odabranu boju.
 * @author Maja Radočaj
 *
 */
public class JColorArea extends JComponent implements IColorProvider {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Širina komponente.
	 */
	private static final int WIDTH = 15;
	/**
	 * Visina komponente.
	 */
	private static final int HEIGHT = 15;
	/**
	 * Odabrana boja.
	 */
	private Color selectedColor;
	/**
	 * Slušači nad promjenom boje.
	 */
	private List<ColorChangeListener> listeners;
	
	/**
	 * Javni konstruktor
	 * 
	 * @param selectedColor odabrana boja
	 * @throws NullPointerException ako je predana boja <code>null</code>
	 */
	public JColorArea(Color selectedColor) {
		this.selectedColor = Objects.requireNonNull(selectedColor);
		listeners = new ArrayList<>();
		
		this.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				Color color = JColorChooser.showDialog(JColorArea.this, 
						"Select color", selectedColor);
				
				if(color == null || selectedColor.equals(color)) {
					return;
				} else {
					Color oldColor = selectedColor;
					JColorArea.this.selectedColor = color;
					listeners.forEach(l -> l.newColorSelected(JColorArea.this, oldColor, color));
					repaint();
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {	//ovo je malo za ukras hehe
				setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				repaint();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				setBorder(BorderFactory.createEmptyBorder());
				repaint();
			}
		});
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}
	
	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
	
	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(selectedColor);
		setBackground(selectedColor);
		Insets ins = this.getInsets();
		g.fillRect(ins.left, ins.top, this.getWidth(), this.getHeight());
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		listeners.add(Objects.requireNonNull(l));
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		listeners.remove(l);
	}
}
