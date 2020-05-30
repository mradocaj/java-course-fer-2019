package hr.fer.zemris.java.hw02;

/**
 * Klasa koja modelira kompleksan broj i osnovne operacije sa kompleksnim
 * brojevima. Svaki kompleksni broj ima dvije članske varijable, realni i
 * imaginarni dio.
 * 
 * @author Maja Radočaj
 *
 */
public class ComplexNumber {

	/**
	 * Realni dio kompleksnog broja.
	 */
	double real;
	/**
	 * Imaginarni dio kompleksnog broja.
	 */
	double imaginary;
	/**
	 * Maksimalno odstupanje da bi komponente kompleksnih brojeva bile jednake.
	 */
	private static double maxDeviation = 10E-10;

	/**
	 * Konstruktor koji inicijalizira realnu i imaginarnu komponentu kompleksnog
	 * broja.
	 * 
	 * @param real      realni dio
	 * @param imaginary imaginarni dio
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Metoda koja stvara novi kompleksni broj koji ima samo realni dio.
	 * 
	 * @param real realni dio
	 * @return novi kompleksni broj
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Metoda koja stvara novi kompleksni broj koji ima samo imaginarni dio.
	 * 
	 * @param imaginary imaginarni dio
	 * @return novi kompleksni broj
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Metoda koja stvara novi komplelsni broj iz trigonometrijskog oblika (danog
	 * kuta i modula).
	 * 
	 * @param magnitude modul
	 * @param angle     kut
	 * @return novi kompleksni broj
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		double x = magnitude * Math.cos(angle);
		double y = magnitude * Math.sin(angle);
		return new ComplexNumber(x, y);
	}

	/**
	 * Metoda za parsiranje danog stringa u kompleksni broj. Stringovi mogu biti
	 * potpuno realni ili samo imaginarni brojevi. Oznaka 'i' mora se nalaziti na
	 * kraju broja. Nisu dozvoljeni dupli znakovi, npr. 1+-2i. Dozvoljeni su početni
	 * predznaci, npr. +2+8i. U slučaju odstupanja od pravila, baca se iznimka
	 * NumberFormatException.
	 * 
	 * @param s string kojeg treba parsirati
	 * @return novi kompleksni broj
	 * @throws NumberFormatException
	 */
	public static ComplexNumber parse(String s) {
		double x = 0;
		double y = 0;
		try {
			return fromReal(Double.parseDouble(s)); // ako je samo realni dio, ovdje završavamo
		} catch(NumberFormatException ex) {
			if(!s.endsWith("i")) {
				throw new NumberFormatException(); // ako nema 'i' na kraju, greška
			}
			String helpString = s.substring(0, s.length() - 1); // mičemo 'i' s kraja

			try {
				return fromImaginary(Double.parseDouble(helpString)); // provjeravamo je li
																		// samo imaginarni dio
			} catch(NumberFormatException myEx) {
				int index = 0;
				int countPlusAndMinus = 0;
				int countDots = 0;

				for(int i = 0; i < helpString.length(); i++) {
					char element = helpString.charAt(i);
					if(element == '+' || element == '-') {
						countPlusAndMinus++;
						if(countPlusAndMinus > 2) {
							throw new NumberFormatException(); // baca se iznimka za slučaj npr. 1+1+1i
						}
						if(i != 0 && helpString.charAt(i - 1) != '+' && helpString.charAt(i - 1) != '-') {
							index = i;
						} else if(i != 0) {
							throw new NumberFormatException(); // baca se iznimka za slučaj npr. 2+-3i
						}
					}
					if(element == '.') {
						countDots++;
						if(countDots > 2) {
							throw new NumberFormatException(); // baca se iznimka za slučaj npr. 1..0+3i
						}
					}
				}

				// dobivamo realni dio, baca se iznimka NumberFormatException ako je realni dio
				// npr. 8ž
				x = Double.parseDouble(s.substring(0, index));

				// dobivamo imaginarni dio, baca se iznimka NumberFormatException ako je
				// imaginarni dio npr. 8ž
				y = Double.parseDouble(s.substring(index, helpString.length()));
				return new ComplexNumber(x, y);
			}

		}
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
	 * Getter koji vraća modul kompleksnog broja.
	 * 
	 * @return modul kompleksnog broja
	 */
	public double getMagnitude() {
		return Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
	}

	/**
	 * Getter koji vraća kut kompleksnog broja u trigonometrijskom zapisu. Kut je u
	 * intervalu od 0 do 2*PI (uključeno).
	 * 
	 * @return kut
	 */
	public double getAngle() {
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
	 * Metoda koja vraća novi kompleksni broj kao zbroj trenutnog i broja danog kao
	 * argument metode.
	 * 
	 * @param c dani kompleksni broj
	 * @return zbroj trenutnog i drugog
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(real + c.getReal(), imaginary + c.getImaginary());
	}

	/**
	 * Metoda koja vraća novi kompleksni broj kao razliku trenutnog i broja danog
	 * kao argument metode.
	 * 
	 * @param c dani kompleksni broj
	 * @return razlika trenutnog i danog
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(real - c.getReal(), imaginary - c.getImaginary());
	}

	/**
	 * Metoda koja vraća novi kompleksni broj kao umnožak trenutnog i broja danog
	 * kao argument metode.
	 * 
	 * @param c dani kompleksni broj
	 * @return umnožak trenutnog i danog
	 */
	public ComplexNumber mul(ComplexNumber c) {
		return new ComplexNumber(real * c.getReal() - imaginary * c.getImaginary(),
				imaginary * c.getReal() + real * c.getImaginary());
	}

	/**
	 * Metoda koja vraća novi kompleksni broj kao količnik trenutnog i broja danog
	 * kao argument metode.
	 * 
	 * @param c dani kompleksni broj
	 * @return količnik trenutnog i danog
	 */
	public ComplexNumber div(ComplexNumber c) {
		return fromMagnitudeAndAngle(getMagnitude() / c.getMagnitude(), getAngle() - c.getAngle());
	}

	/**
	 * Metoda koja vraća novi kompleksni broj kao n-tu potenciju trenutnog
	 * kompleksnog broja. Ako je potencija manja od 0, baca se
	 * IllegalArgumentException.
	 * 
	 * @param n potencija
	 * @return n-ta potencija broja
	 * @throws IllegalArgumentException ako je predani argument manji od 0
	 */
	public ComplexNumber power(int n) {
		if(n < 0) {
			throw new IllegalArgumentException();
		}
		return fromMagnitudeAndAngle(Math.pow(getMagnitude(), n), getAngle() * n);
	}

	/**
	 * Metoda koja vraća polje kompleksnih brojeva kao n-te korijene trenutnog
	 * kompleksnog broja. Ako je n manji ili jednak 0, baca se
	 * IllegalArgumentException.
	 * 
	 * @param n korijen
	 * @return n-ti korijeni broja
	 * @throws IllegalArgumentException ako je n manji ili jednak 0
	 */
	public ComplexNumber[] root(int n) {
		if(n <= 0) {
			throw new IllegalArgumentException();
		}
		ComplexNumber[] roots = new ComplexNumber[n];
		for(int k = 0; k < n; k++) {
			roots[k] = fromMagnitudeAndAngle(Math.pow(getMagnitude(), 1.0 / n)
					, (getAngle() + 2 * k * Math.PI) / n);
		}
		return roots;
	}

	/**
	 * Metoda koja vraća String reprezentaciju kompleksnog broja.
	 * 
	 * @return string reprezentacija kompleksnog broja
	 */
	@Override
	public String toString() {
		if(real == 0 && imaginary == 0) {
			return "0";
		}
		if(real == 0) {
			return String.format("%.4i", imaginary);
		}
		if(imaginary == 0) {
			return String.format("%.4f", real);
		}
		if(imaginary > 0) {
			return String.format("%.4f+%.4fi", real, imaginary);
		}
		return String.format("%.4f-%.4fi", real, Math.abs(imaginary));
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

	/**
	 * Nadjačavanje metode equals. Brojevi su jednaki ako se podudaraju do
	 * maxDeviation decimale.
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(getClass() != obj.getClass()) {
			return false;
		}
		ComplexNumber other = (ComplexNumber) obj;
		// odstupanje smije biti u desetoj decimali
		if(Math.abs(imaginary - other.imaginary) > maxDeviation) { 
			return false;
		}
		if(Math.abs(real - other.real) > maxDeviation) {
			return false;
		}
		return true;
	}

}
