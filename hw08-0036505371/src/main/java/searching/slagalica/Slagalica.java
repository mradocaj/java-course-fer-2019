package searching.slagalica;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import searching.algorithms.Transition;

/**
 * Razred koji predstavlja implementaciju naše slagalice.
 * 
 * @author Maja Radočaj
 *
 */
public class Slagalica implements Supplier<KonfiguracijaSlagalice>,  
	Function<KonfiguracijaSlagalice,List<Transition<KonfiguracijaSlagalice>>>, Predicate<KonfiguracijaSlagalice> {

	/**
	 * Početna konfiguracija slagalice.
	 */
	private KonfiguracijaSlagalice konfig;
	
	/**
	 * Javni konstruktor.
	 * 
	 * @param konfiguracija konfiguracija slagalice
	 * @throws NullPointerException ako se za konfiguraciju pokuša dati <code>null</code>
	 */
	public Slagalica(KonfiguracijaSlagalice konfiguracija) {
		this.konfig = Objects.requireNonNull(konfiguracija);
	}
	
	@Override
	public boolean test(KonfiguracijaSlagalice konfSlagalice) {
		Objects.requireNonNull(konfSlagalice);
		int[] polje = konfSlagalice.getPolje();
		int k = polje.length;
		
		for(int i = 0; i < k; i++) {
			if(i == 8 && polje[i] == 0) return true;
			if(polje[i] != i + 1) return false; 
		}
		return true;
	}

	@Override
	public KonfiguracijaSlagalice get() {
		return konfig;
	}

	@Override
	public List<Transition<KonfiguracijaSlagalice>> apply(KonfiguracijaSlagalice konfSlagalice) {
		Objects.requireNonNull(konfSlagalice);
		List<Transition<KonfiguracijaSlagalice>> rezultat = new LinkedList<>();
		
		int indeks = konfSlagalice.indexOfSpace();
		int[] polje = konfSlagalice.getPolje();
		
		if(indeks % 3 != 2) {	//rubni slučaj, desni rub tablice
			rezultat.add(new Transition<>(new KonfiguracijaSlagalice(swapped(indeks, indeks + 1, polje)), 1));
		}
		if(indeks % 3 != 0) {	//rubni slučaj, lijevi rub tablice
			rezultat.add(new Transition<>(new KonfiguracijaSlagalice(swapped(indeks, indeks - 1, polje)), 1));
		}
		if(indeks - 3 >= 0) {	
			rezultat.add(new Transition<>(new KonfiguracijaSlagalice(swapped(indeks, indeks - 3, polje)), 1));
		}
		if(indeks + 3 < polje.length) {
			rezultat.add(new Transition<>(new KonfiguracijaSlagalice(swapped(indeks, indeks + 3, polje)), 1));
		}
		
		return rezultat;
	}

	/**
	 * Pomoćna metoda za stvaranje novog niza kojem su dva elementa zamijenjena.
	 * 
	 * @param firstIndex indeks prvog elementa
	 * @param secondIndex indeks drugog elementa
	 * @param polje polje u kojem treba zamijeniti elemente
	 * @return kopija polja sa zamijenjenim elementima
	 */
	private int[] swapped(int firstIndex, int secondIndex, int[] polje) {
		int novoPolje[] = Arrays.copyOf(polje, polje.length);
		int help = novoPolje[firstIndex];
		novoPolje[firstIndex] = polje[secondIndex];
		novoPolje[secondIndex] = help;
		return novoPolje;
	}
}
