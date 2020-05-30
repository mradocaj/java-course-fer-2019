package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Razred koji implementira radnju rotiranja trenutnog smjera gledanja kornjače
 * za dani kut.
 * 
 * @author Maja Radočaj
 *
 */
public class RotateCommand implements Command {

	/**
	 * Kut rotacije kornjače.
	 */
	private double angle;

	/**
	 * Konstruktor koji prima kut rotacije u stupnjevima.
	 * 
	 * @param angle kut rotacije (u stupnjevima)
	 */
	public RotateCommand(double angle) {
		this.angle = Math.toRadians(angle);
	}

	/**
	 * s {@inheritDoc} Ova konkretna radnja rotira trenutni smjer gledanja kornjače
	 * za dani kut.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().getDirection().rotate(angle);
	}

}
