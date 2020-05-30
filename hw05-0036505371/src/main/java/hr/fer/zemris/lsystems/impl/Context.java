package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Razred čiji primjerci omogućavaju izvođenje postupka prikazivanja fraktala.
 * 
 * @author Maja Radočaj
 *
 */
public class Context {

	/**
	 * Stog za dohvaćanje stanja kornjače. Trenutno aktivno stanje kornjače nalazi
	 * se na vrhu stoga.
	 */
	private ObjectStack<TurtleState> stack;

	/**
	 * Konstruktor koji inicijalizira člansku varijablu.
	 */
	public Context() {
		stack = new ObjectStack<>();
	}

	/**
	 * Metoda koja vraća stanje s vrha stoga bez uklanjanja.
	 * 
	 * @return stanje s vrha stoga
	 */
	public TurtleState getCurrentState() {
		return stack.peek();
	}

	/**
	 * Metoda koja na vrh stoga gura predano stanje. Na stog se ne smije dodati
	 * <code>null</code>.
	 * 
	 * @param state stanje koje treba pohraniti na vrh stoga
	 * @throws NullPointerException ako se pokuša dodati <code>null</code>.
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}

	/**
	 * Metoda koja briše jedno stanje s vrha stoga.
	 */
	public void popState() {
		stack.pop();
	}
}
