package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Razred koji implementira radnju ažuriranja trenutne boje crtanja kornjače.
 * 
 * @author Maja Radočaj
 *
 */
public class ColorCommand implements Command {

	/**
	 * Boja kojom kornjača crta.
	 */
	Color color;

	/**
	 * Konstruktor koji prima novu boju crtanja <code>color</code>.
	 * 
	 * @param color boja kojom kornjača crta
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}

	/**
	 * {@inheritDoc} Ova konkretna radnja u trenutnom stanju kornjače zapisuje
	 * predanu boju.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setColor(color);
	}

}
