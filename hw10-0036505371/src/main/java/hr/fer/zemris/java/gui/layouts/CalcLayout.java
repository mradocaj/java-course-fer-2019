package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * Upravljač razmještaja za kalkulator implementiran u razredu
 * {@link Calculator}.
 * 
 * @author Maja Radočaj
 *
 */
public class CalcLayout implements LayoutManager2 {

	/**
	 * Razmak između redaka i stupaca.
	 */
	private int spacing;
	/**
	 * Broj redaka.
	 */
	private static final int ROWS = 5;
	/**
	 * Broj stupaca.
	 */
	private static final int COLUMNS = 7;
	/**
	 * Minimalna širina.
	 */
	private int minWidth;
	/**
	 * Minimalna visina.
	 */
	private int minHeight;
	/**
	 * Maksimalna širina.
	 */
	private int maxWidth;
	/**
	 * Maksimalna visina.
	 */
	private int maxHeight;
	/**
	 * Preferirana širina.
	 */
	private int preferredWidth;
	/**
	 * Preferirana visina.
	 */
	private int preferredHeight;
	/**
	 * Nepoznata veličina.
	 */
	private boolean sizeUnknown = true;
	/**
	 * Mapa komponenti.
	 */
	private Map<RCPosition, Component> components;

	/**
	 * Javni konstruktor koji inicijalizira razmak među stupcima i retcima.
	 * 
	 * @param spacing razmak među stupcima i retcima
	 */
	public CalcLayout(int spacing) {
		components = new HashMap<>();
		this.spacing = spacing;
	}

	/**
	 * Konstruktor koji postavlja razmak među stupcima i retcima na nulu.
	 */
	public CalcLayout() {
		this(0);
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException("Operation not supported.");
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		RCPosition key = null;

		for(Map.Entry<RCPosition, Component> entry : components.entrySet()) {
			if(entry.getValue().equals(comp)) {
				key = entry.getKey();
				break;
			}
		}

		if(key != null) {
			components.remove(key);
		}
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return setDimension("preferred", parent);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return setDimension("minimum", parent);
	}

	@Override
	public void layoutContainer(Container parent) {
		Dimension containerDim = parent.getSize();
		Insets insets = parent.getInsets();
		int parentWidth = containerDim.width - insets.right - insets.left;
		int parentHeight = containerDim.height - insets.bottom - insets.top;

		double widthOfColumns = (parentWidth * 1.0 - (COLUMNS - 1) * spacing) / COLUMNS;
		double heightOfRows = (parentHeight * 1.0 - (ROWS - 1) * spacing) / ROWS;

		if(sizeUnknown) {
			setSizes(parent);
		}

		int[] widths = new int[7]; // polje u koje se pohranjuju širine komponenti
		int[] heights = new int[5]; // polje u koje se pohranjuju visine komponenti
		int[] startingX = new int[7]; // polje u koje se pohranjuju x-evi početaka komponenti
		int[] startingY = new int[5]; // polje u koje se pohranjuju y-ovu početaka komponenti

		Arrays.fill(widths, (int) Math.floor(widthOfColumns));
		Arrays.fill(heights, (int) heightOfRows);
		int leftWidth = (parentWidth - (COLUMNS - 1) * spacing) % 7;

		for(int i = 0; i < leftWidth * 2; i = i + 2) {
			if(i >= 7) {
				widths[(i % (COLUMNS - 1)) + 1]++;
			} else {
				widths[i % COLUMNS]++;
			}
		}

		int leftHeight = (parentHeight - (ROWS - 1) * spacing) % 5;
		for(int i = 0; i < leftHeight; i = i + 2) {
			if(i >= 7) {
				heights[(i % (ROWS - 1)) + 1]++;
			} else {
				heights[i % ROWS]++;
			}
		}

		startingX[0] = insets.left;
		startingY[0] = insets.top;
		for(int i = 1; i < 7; i++) {
			startingX[i] = startingX[i - 1] + widths[i - 1] + spacing;
			if(i < 5) {
				startingY[i] = startingY[i - 1] + heights[i - 1] + spacing;
			}
		}

		for(Map.Entry<RCPosition, Component> component : components.entrySet()) {
			Component comp = component.getValue();
			int row = component.getKey().getRow();
			int column = component.getKey().getColumn();

			if(row == 1 && column == 1) {
				comp.setBounds(insets.left, insets.top, startingX[5] - spacing, heights[0]);
				continue;
			}
			// postavljanje pozicije komponente
			comp.setBounds(startingX[column - 1], startingY[row - 1], 
					widths[column - 1], heights[row - 1]);
		}
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		Objects.requireNonNull(comp);
		Objects.requireNonNull(constraints);
		RCPosition constraint;

		if(constraints instanceof String) {
			constraint = parse((String) constraints);
		} else if(constraints instanceof RCPosition) {
			constraint = (RCPosition) constraints;
		} else {
			throw new UnsupportedOperationException("Constraint must be of type RCposition.");
		}

		checkConstraint(comp, constraint);

		components.put(constraint, comp);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return setDimension("maximum", target);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
	}

	/**
	 * Pomoćna metoda koja parsira dani String u primjerak razreda
	 * {@link RCPosition}.
	 * 
	 * @param constraints String reprezentacija RCPositiona
	 * @return parsirani RCPosition
	 * @throws IndexOutOfBoundsException ako je nepravilno zadan String
	 * @throws NumberFormatException     ako se String ne može parsirati
	 */
	private static RCPosition parse(String constraints) {
		try {
			String[] parts = constraints.split(",");
			int row = Integer.parseInt(parts[0]);
			int column = Integer.parseInt(parts[1]);
			return new RCPosition(row, column);
		} catch(IndexOutOfBoundsException | NumberFormatException ex) {
			throw new UnsupportedOperationException("Invalid String representation of RCposition.");
		}
	}

	/**
	 * Pomoćna metoda koja provjerava jesu li vrijednosti za danu poziciju
	 * dozvoljene.
	 * 
	 * @param comp       komponenta
	 * @param constraint pozicija
	 */
	private void checkConstraint(Component comp, RCPosition constraint) {
		int row = constraint.getRow();
		int column = constraint.getColumn();
		boolean invalid = false;

		if(row < 1 || row > ROWS || column < 1 || column > COLUMNS)
			invalid = true;
		if(row == 1 && column > 1 && column < COLUMNS - 1)
			invalid = true;
		if(components.containsKey(constraint) && components.get(constraint) != comp)
			invalid = true;

		if(invalid) {
			throw new CalcLayoutException("Illegal constraint for component: " 
		+ "(" + row + "," + column + ")");
		}
	}

	/**
	 * Pomoćna metoda koja postavlja minimalne i maksimalne širine i visine.
	 * 
	 * @param parent container
	 */
	private void setSizes(Container parent) {
		minWidth = 0;
		minHeight = 0;
		maxWidth = 0;
		maxHeight = 0;
		preferredWidth = 0;
		preferredHeight = 0;

		for(Map.Entry<RCPosition, Component> comp : components.entrySet()) {
			Component component = comp.getValue();
			int row = comp.getKey().getRow();
			int column = comp.getKey().getColumn();
			if(!component.isVisible())
				continue;

			if(column != 1 || row != 1) {
				minWidth = Math.max(component.getMinimumSize().width, minWidth);
				maxWidth = Math.min(component.getMaximumSize().width, maxWidth);
			}
			minHeight = Math.max(component.getMinimumSize().height, minHeight);
			maxHeight = Math.min(component.getMaximumSize().height, maxHeight);
		}

		preferredWidth = minWidth * COLUMNS + spacing * (COLUMNS - 1);
		preferredHeight = minHeight * ROWS + spacing * (ROWS - 1);
	}

	/**
	 * Pomoćna metoda za postavljanje dimenzije.
	 * 
	 * @param mode   način rada za dimenziju
	 * @param parent kontejner
	 * @return nova dimenzija
	 */
	private Dimension setDimension(String mode, Container parent) {
		Dimension dim = new Dimension(0, 0);

		setSizes(parent);
		Insets insets = parent.getInsets();

		switch(mode) {
		case "minimum":
		case "preferred":
			dim.width = preferredWidth + insets.left + insets.right;
			dim.height = preferredHeight + insets.top + insets.bottom;
			break;
		case "maximum":
			dim.width = maxWidth * COLUMNS + spacing * (COLUMNS - 1) + insets.left + insets.right;
			dim.height = maxHeight * ROWS + spacing * (ROWS - 1) + insets.top + insets.bottom;
			break;
		}

		sizeUnknown = false;
		return new Dimension(dim);
	}
}
