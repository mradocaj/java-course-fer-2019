package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Razred koji implementira radnju stavljanja stanja na stog.
 * 
 * @author Maja Radočaj
 *
 */
public class PushCommand implements Command {

	/**
	 * {@inheritDoc} Ova kokretna radnja kopira stanje s vrha stoga i kopiju stavlja
	 * na stog.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.pushState(ctx.getCurrentState().copy());
	}
}
