package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Razred koji implementira radnju skidanja stanja s vrha stoga.
 * 
 * @author Maja Radočaj
 *
 */
public class PopCommand implements Command {

	/**
	 * {@inheritDoc} Ova konkretna radnja skida jedno stanje s vrha stoga.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}
}
