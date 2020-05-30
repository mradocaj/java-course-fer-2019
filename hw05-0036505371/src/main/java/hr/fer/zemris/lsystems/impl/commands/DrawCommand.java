package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Razred koji implementira radnju crtanja linije za dani pomak.
 * 
 * @author Maja Radočaj
 *
 */
public class DrawCommand implements Command {

	/**
	 * Pomak kornjače.
	 */
	double step;

	/**
	 * Javni konstruktor koji inicijalizira pomak kornjače <code>step</code>.
	 * 
	 * @param step
	 */
	public DrawCommand(double step) {
		this.step = step;
	}

	/**
	 * {@inheritDoc} Ova konkretna radnja računa gdje kornjača mora otići za dani
	 * pomak, crta liniju te pohranjuje novu poziciju kornjače u trenutno stanje.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		Vector2D oldPosition = currentState.getCurrentPosition();
		Vector2D newPosition = oldPosition
				.translated(currentState.getDirection().scaled(currentState.getEffectiveLength() * step));

		painter.drawLine(oldPosition.getX(), oldPosition.getY(), newPosition.getX(), newPosition.getY(),
				currentState.getColor(), 1f);

		ctx.getCurrentState().setCurrentPosition(newPosition);
	}

}
