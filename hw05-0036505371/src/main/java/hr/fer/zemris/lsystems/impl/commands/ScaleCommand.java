package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Razred koji implementira radnju skaliranja trentune efektivne duljine pomaka
 * kornjače za dani faktor.
 * 
 * @author Maja Radočaj
 *
 */
public class ScaleCommand implements Command {

	/**
	 * Faktor skaliranja efektivne duljine koraka kornjače.
	 */
	double factor;

	/**
	 * Konstruktor koji inicijalizira faktor skaliranja <code>factor</code>.
	 * 
	 * @param factor faktor skaliranja efektivne duljine koraka kornjače
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}

	/**
	 * {@inheritDoc} Ova konkretna radnja skalira efektivnu duljinu koraka kornjače
	 * za predani faktor te tu vrijednost pohranjuje kao efektivnu duljinu koraka
	 * trenutnog stanja.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		double oldEffectiveLength = ctx.getCurrentState().getEffectiveLength();
		ctx.getCurrentState().setEffectiveLength(oldEffectiveLength * factor);
	}
}
