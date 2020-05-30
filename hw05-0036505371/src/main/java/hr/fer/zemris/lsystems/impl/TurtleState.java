package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.util.Objects;

import hr.fer.zemris.math.Vector2D;

/**
 * Razred koji pamti trenutnu poziciju, trenutni smjer, boju kojom kornjača crta
 * te efektivnu duljinu pomaka kornjače pri crtanju krivulje. Trenutna pozicija
 * definirana je u odnosu na ishodište, a smjer je u obliku jediničnog vektora.
 *
 * @author Maja Radočaj
 *
 */
public class TurtleState {

	/**
	 * Trenutna pozicija kornjače.
	 */
	private Vector2D currentPosition;
	/**
	 * Trenutni smjer kornjače.
	 */
	private Vector2D direction;
	/**
	 * Boja kojom kornjača crta.
	 */
	private Color color;
	/**
	 * Efektivna duljina pomaka kornjače.
	 */
	private double effectiveLength;

	/**
	 * Javni konstruktor koji inicijalizira stanje kornjače. Ukoliko se na mjesto
	 * nekih od referenci preda <code>null</code>, baca se
	 * {@link NullPointerException}.
	 * 
	 * @param currentPosition pozicija kornjače
	 * @param direction       smjer kornjače
	 * @param color           boja kojom kornjača crta
	 * @param effectiveLength efektivna duljina pomaka kornjače
	 * @throws NullPointerException ako je pozicija, smjer ili boja
	 *                              <code>null</code>
	 */
	public TurtleState(Vector2D currentPosition, Vector2D direction, Color color, 
			double effectiveLength) {

		this.currentPosition = Objects.requireNonNull(currentPosition);
		this.direction = Objects.requireNonNull(direction);
		this.color = Objects.requireNonNull(color);
		this.effectiveLength = effectiveLength;
	}

	/**
	 * Metoda koja vraća kopiju trenutnog stanja kornjače.
	 * 
	 * @return kopija trenutnog stanja kornjače
	 */
	public TurtleState copy() {
		return new TurtleState(currentPosition.copy(), direction.copy(), 
				new Color(color.getRGB()), effectiveLength);

	}

	/**
	 * Setter koji postavlja trenutnu poziciju kornjače na predanu vrijednost.
	 * Ukoliko je predana pozicija <code>null</code>, baca se
	 * {@link NullPointerException}.
	 * 
	 * @param currentPosition predana pozicija
	 * @throws NullPointerException ako je predana pozicija <code>null</code>
	 */
	public void setCurrentPosition(Vector2D currentPosition) {
		this.currentPosition = Objects.requireNonNull(currentPosition);
	}

	/**
	 * Setter koji postavlja trenutni smjer kornjače na predanu vrijednost. Ukoliko
	 * je predani smjer <code>null</code>, baca se {@link NullPointerException}.
	 * 
	 * @param currentPosition predani smjer
	 * @throws NullPointerException ako je predani smjer <code>null</code>
	 */
	public void setDirection(Vector2D direction) {
		this.direction = Objects.requireNonNull(direction);
	}

	/**
	 * Setter koji postavlja trenutnu boju kornjače na predanu vrijednost. Ukoliko
	 * je predana boja <code>null</code>, baca se {@link NullPointerException}.
	 * 
	 * @param currentPosition predana boja
	 * @throws NullPointerException ako je predana boja <code>null</code>
	 */
	public void setColor(Color color) {
		this.color = Objects.requireNonNull(color);
	}

	/**
	 * Setter koji postavlja trenutnu efektivnu duljinu koraka kornjače na predanu
	 * vrijednost.
	 * 
	 * @param currentPosition predana boja
	 */
	public void setEffectiveLength(double effectiveLength) {
		this.effectiveLength = effectiveLength;
	}

	/**
	 * Getter koji vraća trenutnu poziciju kornjače.
	 * 
	 * @return trenutna pozicija kornjače
	 */
	public Vector2D getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * Getter koji vraća trenutni smjer kornjače.
	 * 
	 * @return trenutni smjer kornjače
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * Getter koji vraća trenutnu boju kornjače.
	 * 
	 * @return trenutna boja kornjače
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Getter koji vraća efektivnu duljinu koraka kornjače.
	 * 
	 * @return efektivnu duljinu koraka kornjače
	 */
	public double getEffectiveLength() {
		return effectiveLength;
	}

}
