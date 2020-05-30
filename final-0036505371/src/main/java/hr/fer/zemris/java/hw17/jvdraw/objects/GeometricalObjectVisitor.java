package hr.fer.zemris.java.hw17.jvdraw.objects;

/**
 * Sučelje koje modelira visitora nad grafičkim objektima.
 * 
 * @author Maja Radočaj
 *
 */
public interface GeometricalObjectVisitor {
	
	/**
	 * Metoda koja se poziva pri posjetu linije.
	 * 
	 * @param line grafički objekt linija
	 */
	public abstract void visit(Line line);

	/**
	 * Metoda koja se poziva pri posjetu kružnice.
	 * 
	 * @param circle grafički objekt kružnica
	 */
	public abstract void visit(Circle circle);

	/**
	 * Metoda koja se poziva pri posjetu ispunjenog kruga.
	 * 
	 * @param filledCircle grafički objekt krug
	 */
	public abstract void visit(FilledCircle filledCircle);
	
	public abstract void visit(FilledTriangle triangle);
}
