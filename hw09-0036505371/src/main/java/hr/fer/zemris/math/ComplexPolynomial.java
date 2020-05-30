package hr.fer.zemris.math;

import java.util.Arrays;
import java.util.Objects;

/**
 * Razred koji modelira polinom nad kompleksim brojevima oblika
 * <code>f(z) =  zn*z^n+zn-1*z^(n-1)+...+z2*z^2+z1*z+z0</code>, gdje su
 * <code>z0</code> do <code>zn</code> koeficijenti koji pišu uz odgovarajuće
 * potencije od <code>z</code> (i zadaje ih korisnik kroz konstruktor).
 * 
 * @author Maja Radočaj
 *
 */
public class ComplexPolynomial {

	/**
	 * Kompleksni faktori polinoma.
	 */
	Complex[] factors;

	/**
	 * Javni konstruktor.
	 * 
	 * @param factors faktori
	 * @throws NullPointerException ako se preda <code>null</code>
	 */
	public ComplexPolynomial(Complex... factors) {
		this.factors = Objects.requireNonNull(factors);
	}

	/**
	 * Metoda koja vraća red polinoma (najveću potenciju uz z).
	 * 
	 * @return red polinoma
	 */
	public short order() {
		return (short) (factors.length - 1);
	}

	/**
	 * Metoda koja vraća novi polinom kao rezultat množenja trenutnog sa predanim
	 * polinomom.
	 * 
	 * @param p predani polinom
	 * @return umnožak polinoma
	 * @throws NullPointerException ako je predani polinom <code>null</code>
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Objects.requireNonNull(p);
		Complex[] result = new Complex[factors.length + p.order()];

		Arrays.fill(result, Complex.ZERO);
		Complex[] otherFactors = p.getFactors();

		for(int i = 0; i < factors.length; i++) {
			for(int j = 0; j < otherFactors.length; j++) {
				result[i + j] = result[i + j].add(factors[i].multiply(otherFactors[j]));
			}
		}

		return new ComplexPolynomial(result);
	}

	/**
	 * Metoda koja vraća novi polinom kao prvu derivaciju trenutnog polinoma.
	 * 
	 * @return prva derivacija polinoma
	 */
	public ComplexPolynomial derive() {
		Complex[] result = new Complex[factors.length - 1];

		for(int i = 0; i < result.length; i++) {
			result[i] = factors[i + 1].multiply(new Complex(i + 1, 0));
		}

		return new ComplexPolynomial(result);
	}

	/**
	 * Metoda koja prima neki konkretan <code>z</code> i računa koju vrijednost ima
	 * polinom u toj točki.
	 * 
	 * @param z kompleksni broj za čiju vrijednost se računa polinom
	 * @return vrijednost polinoma za neki z
	 * @throws NullPointerException ako je predani kompleksni broj <code>null</code>
	 */
	public Complex apply(Complex z) {
		Complex result = new Complex();

		for(int i = 0; i < factors.length; i++) {
			result = result.add(z.power(i).multiply(factors[i]));
		}

		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = factors.length - 1; i >= 0; i--) {
			sb.append(factors[i]);
			if(i != 0) {
				sb.append("*z^" + i + "+");
			}
		}
		return sb.toString();
	}

	public Complex[] getFactors() {
		return factors;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(factors);
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
		ComplexPolynomial other = (ComplexPolynomial) obj;
		if(!Arrays.equals(factors, other.factors))
			return false;
		return true;
	}

}
