package hr.fer.zemris.math;

import static java.lang.Math.*;

import java.util.Objects;

/**
 * Razred koji modelira 2D vektor čije su komponente realni brojevi
 * <code>x</code> i <code>y</code>. Također omogućava jednostavne operacije sa
 * vektorima.
 * 
 * @author Maja Radočaj
 *
 */
public class Vector2D {

	/**
	 * <code>x</code> komponenta vektora.
	 */
	private double x;
	/**
	 * <code>y</code> komponenta vektora.
	 */
	private double y;
	/**
	 * Maksimalno odstupanje pri računanju jednakosti dva realna broja.
	 */
	private static final double MAX_DEVIATION = 1E-10;

	/**
	 * Konstruktor koji inicijalizira <code>x</code> i <code>y</code> komponentu
	 * vektora.
	 * 
	 * @param x prva komponenta vektora
	 * @param y druga komponenta vektora
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter koji vraća <code>x</code> komponentu vektora.
	 * 
	 * @return <code>x</code> komponentu vektora
	 */
	public double getX() {
		return x;
	}

	/**
	 * Getter koji vraća <code>y</code> komponentu vektora.
	 * 
	 * @return <code>y</code> komponentu vektora
	 */
	public double getY() {
		return y;
	}

	/**
	 * Metoda koja prima jedan vektor te pomoću njega translatira trenutni vektor.
	 * Ukoliko je predani vektor <code>null</code>, baca se
	 * {@link NullPointerException}.
	 * 
	 * @param offset vektor pomoću kojeg vršimo translataciju
	 * @throws NullPointerException ako je predani <code>offset</code> vrijednosti
	 *                              <code>null</code>
	 */
	public void translate(Vector2D offset) {
		Objects.requireNonNull(offset);
		x += offset.getX();
		y += offset.getY();
	}

	/**
	 * Metoda koja prima jedan vektor te pomoću stvara novi vektor koji je nastao
	 * kao rezultat translatacije trenutnog za dani. Ukoliko je predani vektor
	 * <code>null</code>, baca se {@link NullPointerException}.
	 * 
	 * @param offset vektor pomoću kojeg vršimo translataciju
	 * @throws NullPointerException ako je predani <code>offset</code> vrijednosti
	 *                              <code>null</code>
	 */
	public Vector2D translated(Vector2D offset) {
		Objects.requireNonNull(offset);
		return new Vector2D(x + offset.getX(), y + offset.getY());
	}

	/**
	 * Metoda koja prima kut u radijanima te rotira trenutni vektor za dani kut.
	 * 
	 * @param angle kut rotacije
	 */
	public void rotate(double angle) {
		double oldX = x;
		x = cos(angle) * x - sin(angle) * y;
		y = sin(angle) * oldX + cos(angle) * y;
	}

	/**
	 * Metoda koja prima kut u radijanima te stvara novi vektor kao rezultat
	 * rotiracije trenutnog vektor za dani kut.
	 * 
	 * @param angle kut rotacije
	 */
	public Vector2D rotated(double angle) {
		return new Vector2D(cos(angle) * x - sin(angle) * y, sin(angle) * x + cos(angle) * y);
	}

	/**
	 * Metoda za skaliranje trenutnog vektora za određeni koeficijent
	 * <code>scaler</code>.
	 * 
	 * @param scaler koeficijent skaliranja
	 */
	public void scale(double scaler) {
		x *= scaler;
		y *= scaler;
	}

	/**
	 * Metoda koja vraća novi vektor nastao skaliranjem trenutnog vektora za
	 * određeni koeficijent <code>scaler</code>.
	 * 
	 * @param scaler koeficijent skaliranja
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(x * scaler, y * scaler);
	}

	/**
	 * Metoda koja vraća novi vektor kao kopiju trenutnog.
	 * 
	 * @return kopiju trenutnog vektora
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		Vector2D other = (Vector2D) obj;
		if(abs(x - other.getX()) > MAX_DEVIATION)
			return false;
		if(abs(y - other.getY()) > MAX_DEVIATION)
			return false;
		return true;
	}

}
