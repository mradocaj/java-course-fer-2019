package hr.fer.zemris.math;

import static java.lang.Math.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Razred koji modelira kompleksan broj. Svaki kompleksni broj ima realni i
 * imaginarni dio.
 * 
 * @author Maja Radočaj
 *
 */
public class Complex {

	/**
	 * Realni dio kompleksnog broja.
	 */
	private double real;
	/**
	 * Imaginarni dio kompleksnog broja.
	 */
	private double imaginary;
	/**
	 * Maksimalno odstupanje da bi komponente kompleksnih brojeva bile jednake.
	 */
	private static double maxDeviation = 10E-10;
	/**
	 * Prag tolerancije za ispis pozitivnog broja.
	 */
	private static double positive = -0.05;
	/**
	 * Kompleksni broj <code>0 + i0</code>.
	 */
	public static final Complex ZERO = new Complex(0, 0);
	/**
	 * Kompleksni broj <code>1 + i0</code>.
	 */
	public static final Complex ONE = new Complex(1, 0);
	/**
	 * Kompleksni broj <code>-1 + i0</code>.
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);
	/**
	 * Kompleksni broj <code>i</code>.
	 */
	public static final Complex IM = new Complex(0, 1);
	/**
	 * Kompleksni broj <code>-i</code>.
	 */
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Defaultni konstruktor.
	 */
	public Complex() {
		this(0, 0);
	}

	/**
	 * Konstruktor koji inicijalizira realnu i imaginarnu komponentu kompleksnog
	 * broja.
	 * 
	 * @param real      realni dio
	 * @param imaginary imaginarni dio
	 */
	public Complex(double re, double im) {
		this.real = re;
		this.imaginary = im;
	}

	/**
	 * Getter koji vraća realni dio kompleksnog broja.
	 * 
	 * @return realni dio
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Getter koji vraća imaginarni dio kompleksnog broja.
	 * 
	 * @return imaginarni dio
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Metoda koja vraća modul kompleksnog broja.
	 * 
	 * @return modul kompleksnog broja
	 */
	public double module() {
		return sqrt(real * real + imaginary * imaginary);
	}

	/**
	 * Metoda koja vraća novi kompleksni broj kao umnožak trenutnog i broja danog
	 * kao argument metode.
	 * 
	 * @param c dani kompleksni broj
	 * @return umnožak trenutnog i danog
	 */
	public Complex multiply(Complex c) {
		return new Complex(real * c.getReal() - imaginary * c.getImaginary(),
				imaginary * c.getReal() + real * c.getImaginary());
	}

	/**
	 * Metoda koja vraća novi kompleksni broj kao količnik trenutnog i broja danog
	 * kao argument metode.
	 * 
	 * @param c dani kompleksni broj
	 * @return količnik trenutnog i danog
	 */
	public Complex divide(Complex c) {
		return fromMagnitudeAndAngle(module() / c.module(), getAngle() - c.getAngle());
	}

	/**
	 * Metoda koja vraća novi kompleksni broj kao zbroj trenutnog i broja danog kao
	 * argument metode.
	 * 
	 * @param c dani kompleksni broj
	 * @return zbroj trenutnog i drugog
	 */
	public Complex add(Complex c) {
		return new Complex(real + c.getReal(), imaginary + c.getImaginary());
	}

	/**
	 * Metoda koja vraća novi kompleksni broj kao razliku trenutnog i broja danog
	 * kao argument metode.
	 * 
	 * @param c dani kompleksni broj
	 * @return razlika trenutnog i danog
	 */
	public Complex sub(Complex c) {
		return new Complex(real - c.getReal(), imaginary - c.getImaginary());
	}

	/**
	 * Metoda koja vraća novi kompleksni broj sa negativnim imaginarnim i
	 * kompleksnim dijelom.
	 * 
	 * @return novi, negativni kompleksni broj
	 */
	public Complex negate() {
		return new Complex(-real, -imaginary);
	}

	/**
	 * Metoda koja vraća novi kompleksni broj kao n-tu potenciju trenutnog
	 * kompleksnog broja. Ako je potencija manja od 0, baca se
	 * IllegalArgumentException.
	 * 
	 * @param n potencija
	 * @return n-ta potencija broja
	 * @throws IllegalArgumentException ako n nije nenegativan cijeli broj
	 */
	public Complex power(int n) {
		if(n < 0) {
			throw new IllegalArgumentException();
		}
		return fromMagnitudeAndAngle(pow(module(), n), getAngle() * n);
	}

	/**
	 * Metoda koja vraća listu kompleksnih brojeva kao n-te korijene trenutnog
	 * kompleksnog broja. Ako je n manji ili jednak 0, baca se
	 * IllegalArgumentException.
	 * 
	 * @param n korijen
	 * @return n-ti korijeni broja
	 * @throws IllegalArgumentException ako je n manji od 0
	 */
	public List<Complex> root(int n) {
		if(n <= 0) {
			throw new IllegalArgumentException();
		}
		List<Complex> roots = new ArrayList<>();
		for(int k = 0; k < n; k++) {
			roots.add(fromMagnitudeAndAngle(pow(module(), 1.0 / n), (getAngle() + 2 * k * Math.PI) / n));
		}
		return roots;
	}

	/**
	 * <p>
	 * Metoda za parsiranje danog stringa u kompleksni broj.
	 * </p>
	 * Stringovi mogu biti potpuno realni ili samo imaginarni brojevi. Oznaka 'i'
	 * mora se nalaziti ispred imaginarnog dijela broja. Nisu dozvoljeni dupli
	 * znakovi, npr. 1+-i2. U slučaju odstupanja od pravila, baca se iznimka
	 * NumberFormatException.
	 * 
	 * @param s string kojeg treba parsirati
	 * @return novi kompleksni broj
	 * @throws NumberFormatException ako se String ne može parsirati u kompleksan
	 *                               broj
	 */
	public static Complex parse(String s) {
		if(s == null || s.trim().length() == 0) {
			throw new NumberFormatException("String mustn't be empty.");
		}
		s = s.replaceAll(" ", "");
		try {
			return new Complex(Double.parseDouble(s), 0); // ako je samo realni dio, ovdje završavamo
		} catch(NumberFormatException ex) {
			checkArgument(s);

			if(s.indexOf("i") == 0 || s.indexOf("i") == 1) {
				String number = s.replace("i", "").isEmpty() ? "1" : s.replace("i", "");
				number = number.replace("-", "").isEmpty() ? "-1" : number.replace("-", "");
				return new Complex(0, Double.parseDouble(number));
			}
			s = s.replace("i", "");

			String prefix = "";
			if(s.startsWith("-")) {
				prefix = "-";
				s = s.replaceFirst("-", "");
			}
			String[] parts;

			if(s.contains("+")) {
				parts = s.split("\\+");
			} else {
				parts = s.split("-");
				parts[1] = "-" + parts[1];
			}

			parts[0] = prefix + parts[0];
			if(parts[1].length() == 0) {
				parts[1] = s.lastIndexOf("-") != 0 && s.lastIndexOf("-") != -1 ? "-1" : "1";
			}
			return new Complex(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");

		sb.append(real > positive ? "" : "-");
		sb.append(String.format("%.1f", Math.abs(real)));

		sb.append(imaginary > positive ? "+" : "-");
		sb.append(String.format("i%.1f", Math.abs(imaginary)));

		sb.append(")");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(imaginary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(real);
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
		Complex other = (Complex) obj;
		if(Math.abs(imaginary - other.imaginary) > maxDeviation) { // odstupanje smije biti u desetoj decimali
			return false;
		}
		if(Math.abs(real - other.real) > maxDeviation) {
			return false;
		}
		return true;
	}

	/**
	 * Getter koji vraća kut kompleksnog broja u trigonometrijskom zapisu. Kut je u
	 * intervalu od 0 do 2*PI (uključeno).
	 * 
	 * @return kut
	 */
	private double getAngle() {
		double angle = Math.atan(imaginary / real);
		if(real < 0) {
			return angle + Math.PI;
		}
		if(real >= 0 && imaginary < 0) {
			return angle + 2 * Math.PI;
		}
		return angle;
	}

	/**
	 * Metoda koja stvara novi komplelsni broj iz trigonometrijskog oblika (danog
	 * kuta i modula).
	 * 
	 * @param magnitude modul
	 * @param angle     kut
	 * @return novi kompleksni broj
	 */
	private static Complex fromMagnitudeAndAngle(double magnitude, double angle) {
		double x = magnitude * Math.cos(angle);
		double y = magnitude * Math.sin(angle);
		return new Complex(x, y);
	}

	/**
	 * Pomoćna metoda koja provjerava je li predani argument pri parsiranju validno
	 * napisani kompleksni broj.
	 * 
	 * @param s predani String
	 * @throws NumberFormatException ako argument nije validna String reprezentacija
	 *                               kompleksnog broja
	 */
	private static void checkArgument(String s) {
		if(!s.contains("i") || s.indexOf("i") != s.lastIndexOf("i")) {
			throw new NumberFormatException("Number must have one real and one imaginary component.");
		}
		if(!s.startsWith("i") && s.indexOf("+") != s.indexOf("i") - 1 && s.lastIndexOf("-") != s.indexOf("i") - 1) {
			throw new NumberFormatException("Wrong position of 'i'.");
		}

		String s1 = s.replace("i", "");
		if(s1.indexOf("+") != s1.lastIndexOf("+") || s1.contains("+") && s1.contains("-") && !s1.startsWith("-")) {
			throw new NumberFormatException("Invalid number of '+' or '-'.");
		}
	}
}
