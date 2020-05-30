package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.awt.Color;

/**
 * Razred koji modelira visitora nad objektima koji za svaki objekt nadodaje
 * tekst koji će služiti za spremanje dokumenta.
 * 
 * @author Maja Radočaj
 *
 */
public class GeometricalObjectSaver implements GeometricalObjectVisitor {

	/**
	 * String builder.
	 */
	private StringBuilder sb = new StringBuilder();
	
	@Override
	public void visit(Line line) {
		Color color = line.getColor();
		sb.append(String.format("LINE %d %d %d %d %d %d %d\n", 
				line.getX1(), line.getY1(), 
				line.getX2(), line.getY2(), 
				color.getRed(), color.getGreen(), color.getBlue()));
	}

	@Override
	public void visit(Circle circle) {
		Color outlineColor = circle.getOutlineColor();
		sb.append(String.format("CIRCLE %d %d %d %d %d %d\n", 
				circle.getX(), circle.getY(), 
				circle.getR(), 
				outlineColor.getRed(), outlineColor.getGreen(), outlineColor.getBlue()));
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		Color outlineColor = filledCircle.getOutlineColor();
		Color fillColor = filledCircle.getFillColor();
		sb.append(String.format("FCIRCLE %d %d %d %d %d %d %d %d %d\n", 
				filledCircle.getX(), filledCircle.getY(), 
				filledCircle.getR(), 
				outlineColor.getRed(), outlineColor.getGreen(), outlineColor.getBlue(),
				fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue()));
	}

	/**
	 * Metoda koja vraća stvoreni tekst dokumenta kojeg treba spremiti.
	 * 
	 * @return tekst dokumenta
	 */
	public String getFileText() {
		if(sb.length() != 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	@Override
	public void visit(FilledTriangle triangle) {
		Color outlineColor = triangle.getOutlineColor();
		Color fillColor = triangle.getFillColor();
		
		sb.append(String.format("FTRIANGLE %d %d %d %d %d %d %d %d %d %d %d %d\n", 
				triangle.getX1(), triangle.getY1(), 
				triangle.getX2(), triangle.getY2(),
				triangle.getX3(), triangle.getY3(),
				outlineColor.getRed(), outlineColor.getGreen(), outlineColor.getBlue(),
				fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue()));
	}

}
