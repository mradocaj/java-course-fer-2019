package hr.fer.zemris.math;

import java.util.Arrays;
import java.util.Objects;

/**
 * Razred koji modelira polinom zapisan u obliku sa svojim korijenima. Radi se o
 * polinomu <code>f(z)</code> oblika <code>z0*(z-z1)*(z-z2)*...*(z-zn)</code>,
 * gdje su <code>z1</code> do <code>zn</code> njegove nultočke, a
 * <code>z0</code> konstanta (sve njih zadaje korisnik kroz konstruktor).
 * 
 * @author Maja Radočaj
 *
 */
public class ComplexRootedPolynomial {

	/**
	 * Konstanta polinoma.
	 */
	private Complex constant;
	/**
	 * Nultočke polinoma.
	 */
	private Complex[] roots;

	/**
	 * Javni konstruktor.
	 * 
	 * @param constant kontanta polinoma.
	 * @param roots    nultočke polinoma.
	 * @throws NullPointerException ako je neki od predanih argumenata
	 *                              <code>null</code>.
	 */
	public ComplexRootedPolynomial(Complex constant, Complex... roots) {
		this.constant = Objects.requireNonNull(constant);
		this.roots = Objects.requireNonNull(roots);
	}

	/**
	 * Metoda koja vraća vrijednost polinoma u danoj točki <code>z</code>.
	 * 
	 * @param z točka u kojoj se računa vrijednost polinoma
	 * @return izračunata vrijednost polinoma
	 * @throws NullPointerException ako je predana vrijednost <code>null</code>
	 */
	public Complex apply(Complex z) {
		Objects.requireNonNull(z);
		Complex result = constant;
		for(int i = 0; i < roots.length; i++) {
			result = result.multiply(z.sub(roots[i]));
		}
		return result;
	}

	/**
	 * Metoda koja vraća trenutni polinom u obliku zapisa sa potencijama.
	 * 
	 * @return polinom u obliku zapisa sa potencijama
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial result = new ComplexPolynomial(Complex.ONE);
		result = result.multiply(new ComplexPolynomial(constant));

		for(int i = 0; i < roots.length; i++) {
			result = result.multiply(new ComplexPolynomial(roots[i].negate(), Complex.ONE));
		}

		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(constant.toString());
		for(int i = 0; i < roots.length; i++) {
			sb.append("*(z-" + roots[i].toString() + ")");
		}
		return sb.toString();
	}

	/**
	 * <p>
	 * Metoda koja vraća indeks nultočke koja je najmanje udaljenosti od predanog
	 * kompleksnog broja <code>z</code>, a da je unutar raspona
	 * <code>treshold</code>. Ako takav indeks ne postoji, vraća se -1.
	 * </p>
	 * <p>
	 * Indeksiranje kreće od indeksa 0.
	 * </p>
	 * 
	 * @param z        broj od kojeg se računa udaljenost
	 * @param treshold raspon dozvoljene udaljenosti
	 * @return indeks najmanje udaljene nultočke
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int result = -1;
		double min = z.sub(roots[0]).module();
		for(int i = 0; i < roots.length; i++) {
			if(z.sub(roots[i]).module() < treshold && z.sub(roots[i]).module() <= min) {
				result = i;
				min = z.sub(roots[i]).module();
			}
		}
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((constant == null) ? 0 : constant.hashCode());
		result = prime * result + Arrays.hashCode(roots);
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
		ComplexRootedPolynomial other = (ComplexRootedPolynomial) obj;
		if(constant == null) {
			if(other.constant != null)
				return false;
		} else if(!constant.equals(other.constant))
			return false;
		if(!Arrays.equals(roots, other.roots))
			return false;
		return true;
	}

}
