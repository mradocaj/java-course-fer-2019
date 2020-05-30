package hr.fer.zemris.math;

import static java.lang.Math.*;

import java.util.Objects;

/**
 * Razred koji modelira neizmjenjivi trokomponentni vektor u prostoru.
 * 
 * @author Maja Radočaj
 *
 */
public class Vector3 {

	/**
	 * X komponenta vektora.
	 */
	private double x;
	/**
	 * Y komponenta vektora.
	 */
	private double y;
	/**
	 * Z komponenta vektora.
	 */
	private double z;

	/**
	 * Javni konstruktor.
	 * 
	 * @param x x komponenta vektora
	 * @param y y komponenta vektora
	 * @param z z komponenta vektora
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Metoda koja vraća normu vektora.
	 * 
	 * @return norma vektora
	 */
	public double norm() {
		return sqrt(x * x + y * y + z * z);
	}

	/**
	 * Metoda koja vraća novi vektor koji predstavlja trenutni normalizirani vektor.
	 * 
	 * @return normalizirani trenutni vektor
	 */
	public Vector3 normalized() {
		double length = norm();
		return new Vector3(x / length, y / length, z / length);
	}

	/**
	 * Metoda koja vraća novi vektor kao zbroj trenutnog i predanog vektora.
	 * 
	 * @param other predani vektor
	 * @return zbroj trenutnog i predanog vektora
	 * @throws NullPointerException ako je predani vektor <code>null</code>
	 */
	public Vector3 add(Vector3 other) {
		Objects.requireNonNull(other);
		return new Vector3(x + other.x, y + other.y, z + other.z);
	}

	/**
	 * Metoda koja vraća novi vektor kao razliku trenutnog i predanog vektora.
	 * 
	 * @param other predani vektor
	 * @return razlika trenutnog i predanog vektora
	 * @throws NullPointerException ako je predani vektor <code>null</code>
	 */
	public Vector3 sub(Vector3 other) {
		Objects.requireNonNull(other);
		return new Vector3(x - other.x, y - other.y, z - other.z);
	}

	/**
	 * Metoda koja vraća novi vektor kao skalarni produkt trenutnog i predanog
	 * vektora.
	 * 
	 * @param other predani vektor
	 * @return skalarni produkt trenutnog i predanog vektora
	 * @throws NullPointerException ako je predani vektor <code>null</code>
	 */
	public double dot(Vector3 other) {
		Objects.requireNonNull(other);
		return x * other.x + y * other.y + z * other.z;
	}

	/**
	 * Metoda koja vraća novi vektor kao vektorski produkt trenutnog i predanog
	 * vektora.
	 * 
	 * @param other predani vektor
	 * @return vektorski produkt trenutnog i predanog vektora
	 * @throws NullPointerException ako je predani vektor <code>null</code>
	 */
	public Vector3 cross(Vector3 other) {
		Objects.requireNonNull(other);
		double newX = y * other.z - z * other.y;
		double newY = z * other.x - x * other.z;
		double newZ = x * other.y - y * other.x;
		return new Vector3(newX, newY, newZ);
	}

	/**
	 * Metoda koja vraća novi vektor kao trenutni vektor skaliran zadanim faktorom
	 * <code>s</code>.
	 * 
	 * @param s faktor skaliranja
	 * @return novi, skalirani vektor
	 */
	public Vector3 scale(double s) {
		return new Vector3(x * s, y * s, z * s);
	}

	/**
	 * Metoda koja vraća kosinus kuta između trenutnog i predanog vektora.
	 * 
	 * @param other predani vektor
	 * @return kosinus kuta između trenutnog i predanog vektora
	 * @throws NullPointerException ako je predani vektor <code>null</code>
	 */
	public double cosAngle(Vector3 other) {
		return this.dot(other) / (this.norm() * other.norm());
	}

	/**
	 * Getter koji vraća x komponentu vektora.
	 * 
	 * @return x komponenta vektora
	 */
	public double getX() {
		return x;
	}

	/**
	 * Getter koji vraća y komponentu vektora.
	 * 
	 * @return y komponenta vektora
	 */
	public double getY() {
		return y;
	}

	/**
	 * Getter koji vraća z komponentu vektora.
	 * 
	 * @return z komponenta vektora
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Metoda koja vraća komponente vektora u obliku polja doubleova.
	 * 
	 * @return komponente vektora u obliku polja
	 */
	public double[] toArray() {
		return new double[] { x, y, z };
	}

	@Override
	public String toString() {
		return String.format("(%f, %f, %f)", x, y, z);
	}

}
