package hr.fer.zemris.java.custom.scripting.exec;
import static hr.fer.zemris.java.custom.scripting.exec.util.ArgumentsUtil.*;

/**
 * Razred koji služi za omoravanje neke vrijednosti te omogućuje izvođenje nekih aritmetičkih operacija
 * nad njome.
 * 
 * @author Maja Radočaj
 *
 */
public class ValueWrapper {

	/**
	 * Vrijednost koju omotavamo.
	 */
	private Object value;
	/**
	 * Broj decimalnih mjesta koja se uzimaju u obzir pri računanju jednakosti decimalnih brojeva.
	 */
	private static final double LIMIT = 1E-6;
	
	/**
	 * Konstruktor koji inicijalizira vrijednost.
	 * 
	 * @param value vrijednost koju omotavamo
	 */
	public ValueWrapper(Object value) {
		this.value = value;
//		System.out.println(value);
	}

	/**
	 * Getter koji vraća vrijednost.
	 * 
	 * @return vrijednost
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Setter koji postavlja vrijednost na danu novu vrijednost.
	 * 
	 * @param value nova vrijednost
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Metoda koja zbraja trenutnu vrijednost sa predanom vrijednosti.
	 * Obje vrijednosti u trenutnku poziva moraju biti <code>null</code>, <code>String</code>, <code>Integer</code>
	 * ili <code>Double</code>. Ako nisu, baca se {@link RuntimeException}.
	 * Također, iznimka se baca ako je jedna od vrijednosti <code>String</code> koji se ne može parsirati u
	 * <code>Double</code> ili <code>Integer</code>.
	 * Ako je ijedan od argumenata <code>Double</code>, onda se rezultat pohranjuje u trenutnoj vrijednosti u 
	 * obliku <code>Double</code>. U suprotnome, pohranjuje se <code>Integer</code> rezultat.
	 * 
	 * @param incValue vrijednost s kojom zbrajamo trenutnu vrijednost
	 * @throws RuntimeException ako predana ili trenutna vrijednost nisu validne
	 */
	public void add(Object incValue) {
//		System.out.println(value);
//		System.out.println(incValue);
		Object first = getArgument(value, incValue);
		Object second = getArgument(incValue, value);
//		System.out.println(first.toString());
//		System.out.println(second.toString());
		if(first instanceof Double) {	//ako je prvi Double, parsiranjem argumenata je osigurano da je i drugi Double
			value = (Double) first + (Double) second;
		} else {
			value = (Integer) first + (Integer) second;
		}
	}

	/**
	 * Metoda koja oduzima predanu vrijednost od trenutne vrijednosti.
	 * Obje vrijednosti u trenutnku poziva moraju biti <code>null</code>, <code>String</code>, <code>Integer</code>
	 * ili <code>Double</code>. Ako nisu, baca se {@link RuntimeException}.
	 * Također se iznimka baca ako je jedna od vrijednosti <code>String</code> koji se ne može parsirati u
	 * <code>Double</code> ili <code>Integer</code>.
	 * Ako je ijedan od argumenata <code>Double</code>, onda se rezultat pohranjuje u trenutnoj vrijednosti u 
	 * obliku <code>Double</code>. U suprotnome, pohranjuje se <code>Integer</code> rezultat.
	 * 
	 * @param decValue vrijednost koju oduzimamo od trenutne vrijednosti
	 * @throws RuntimeException ako predana ili trenutna vrijednost nisu validne
	 */
	public void subtract(Object decValue) {
		Object first = getArgument(value, decValue);
		Object second = getArgument(decValue, value);
		if(first instanceof Double) {
			value = (Double) first - (Double) second;
		} else {
			value = (Integer) first - (Integer) second;
		}
	}

	/**
	 * Metoda koja množi trenutnu vrijednost sa predanom vrijednosti.
	 * Obje vrijednosti u trenutnku poziva moraju biti <code>null</code>, <code>String</code>, <code>Integer</code>
	 * ili <code>Double</code>. Ako nisu, baca se {@link RuntimeException}.
	 * Također se iznimka baca ako je jedna od vrijednosti <code>String</code> koji se ne može parsirati u
	 * <code>Double</code> ili <code>Integer</code>.
	 * Ako je ijedan od argumenata <code>Double</code>, onda se rezultat pohranjuje u trenutnoj vrijednosti u 
	 * obliku <code>Double</code>. U suprotnome, pohranjuje se <code>Integer</code> rezultat.
	 * 
	 * @param mulValue vrijednost s kojom množimo trenutnu vrijednost
	 * @throws RuntimeException ako predana ili trenutna vrijednost nisu validne
	 */
	public void multiply(Object mulValue) {
		Object first = getArgument(value, mulValue);
		Object second = getArgument(mulValue, value);
		if(first instanceof Double) {
			value = (Double) first * (Double) second;
		} else {
			value = (Integer) first * (Integer) second;
		}
	}

	/**
	 * Metoda koja dijeli trenutnu vrijednost sa predanom vrijednosti.
	 * Obje vrijednosti u trenutnku poziva moraju biti <code>null</code>, <code>String</code>, <code>Integer</code>
	 * ili <code>Double</code>. Ako nisu, baca se {@link RuntimeException}.
	 * Također se iznimka baca ako je jedna od vrijednosti <code>String</code> koji se ne može parsirati u
	 * <code>Double</code> ili <code>Integer</code>.
	 * Ako je ijedan od argumenata <code>Double</code>, onda se rezultat pohranjuje u trenutnoj vrijednosti u 
	 * obliku <code>Double</code>. U suprotnome, pohranjuje se <code>Integer</code> rezultat.
	 * 
	 * @param divValue vrijednost s kojom dijelimo trenutnu vrijednost
	 * @throws RuntimeException ako predana ili trenutna vrijednost nisu validne
	 */
	public void divide(Object divValue) {
		Object first = getArgument(value, divValue);
		Object second = getArgument(divValue, value);
		if(first instanceof Double) {
			value = (Double) first / (Double) second;
		} else {
			value = (Integer) first / (Integer) second;
		}
	}

	/**
	 * Metoda koja uspoređuje trenutnu vrijednost sa predanom vrijednosti.
	 * Obje vrijednosti u trenutnku poziva moraju biti <code>null</code>, <code>String</code>, <code>Integer</code>
	 * ili <code>Double</code>. Ako nisu, baca se {@link RuntimeException}.
	 * Također se iznimka baca ako je jedna od vrijednosti <code>String</code> koji se ne može parsirati u
	 * <code>Double</code> ili <code>Integer</code>.
	 * Metoda vraća 0 ako su vrijednosti jednake, broj veći od 0 ako je trenutna vrijednost veća od predane, 
	 * a broj manji od 0 ako je trenutna vrijednost manja od predane.
	 * 
	 * @param withValue vrijednost s kojom uspoređujemo trenutnu vrijednost
	 * @throws RuntimeException ako predana ili trenutna vrijednost nisu validne
	 */
	public int numCompare(Object withValue) {
		Object first = getArgument(value, withValue);
		Object second = getArgument(withValue, value);
		
		if(first instanceof Double) {
			if(Math.abs((Double) first - (Double) second) < LIMIT) return 0;
			return Double.compare((Double) first, (Double) second);
		}
		return Integer.compare((Integer) first, (Integer) second);
	}
}