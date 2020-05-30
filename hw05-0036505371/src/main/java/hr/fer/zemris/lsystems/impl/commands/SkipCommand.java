package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Razred koji implementira radnju pomicanja kornjače za dani pomak.
 * 
 * @author Maja Radočaj
 *
 */
public class SkipCommand implements Command {

	/**
	 * Pomak kornjače.
	 */
	double step;

	/**
	 * Konstruktor koji inicijalizira pomak kornjače <code>step</code>.
	 * 
	 * @param step
	 */
	public SkipCommand(double step) {
		this.step = step;
	}

	/**
	 * {@inheritDoc} Ova konkretna radnja računa gdje kornjača mora otići za dani
	 * pomak te pohranjuje novu poziciju kornjače u trenutno stanje.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		Vector2D newPosition = currentState.getCurrentPosition()
				.translated(currentState.getDirection().scaled(currentState.getEffectiveLength() * step));
		ctx.getCurrentState().setCurrentPosition(newPosition);
	}
}
