package hr.fer.zemris.java.hw17.jvdraw.components;

import java.awt.Color;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 * Komponenta za prikaz informacija o bojama.
 * 
 * @author Maja Radočaj
 *
 */
public class JColorLabel extends JLabel implements ColorChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Komponenta za boju linija.
	 */
	private IColorProvider fgc;
	/**
	 * Komponenta za boju ispune.
	 */
	private IColorProvider bgc;
	
	/**
	 * Javni konstruktor.
	 * 
	 * @param fgc dohvat boje linija
	 * @param bgc dohvat boje ispune
	 * @throws NullPointerException u slučaju greške
	 */
	public JColorLabel(IColorProvider fgc, IColorProvider bgc) {
		this.fgc = Objects.requireNonNull(fgc);
		this.bgc = Objects.requireNonNull(bgc);
		
		fgc.addColorChangeListener(this);
		bgc.addColorChangeListener(this);
		setBorder(BorderFactory.createLoweredSoftBevelBorder());
		updateText();
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		updateText();
	}

	/**
	 * Pomoćna metoda za ispis teksta komponente na temelju boja.
	 */
	private void updateText() {
		Color fgcol = fgc.getCurrentColor();
		Color bgcol = bgc.getCurrentColor();
		
		setText(String.format("Foreground color: (%d, %d, %d), background color: (%d, %d, %d).", 
				fgcol.getRed(), fgcol.getGreen(), fgcol.getBlue(),
				bgcol.getRed(), bgcol.getGreen(), bgcol.getBlue()));
	}
}
