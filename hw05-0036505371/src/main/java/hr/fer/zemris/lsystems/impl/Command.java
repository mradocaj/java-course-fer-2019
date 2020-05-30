package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Sučelje koje modelira jednu naredbu koja se može izvesti pomoću metode
 * <code>execute</code>.
 * 
 * @author Maja Radočaj
 *
 */
public interface Command {

	/**
	 * Radnja koja se izvodi nad nekim kontekstom i stanjem kornjače. Svaka radnja
	 * na neki način modificira konktekst ili trenutno stanje u kojem se kornjača
	 * nalazi.
	 * 
	 * @param ctx     konktekst
	 * @param painter objekt kojim je moguće crtati linije po površini prozora
	 */
	void execute(Context ctx, Painter painter);
}
