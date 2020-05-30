package searching.slagalica;

import java.util.Arrays;

/**
 * Razred koji pretstavlja konfiguraciju slagalice.
 * Slagalica je definirana nizom brojeva od 0 do 8.
 * 
 * @author Maja Radočaj
 *
 */
public class KonfiguracijaSlagalice {

	/**
	 * Polje za konfiguraciju
	 */
	private int[] polje;

	/**
	 * Javni konstruktor.
	 * 
	 * @param polje polje za konfiguraciju
	 * @throws IllegalArgumentException ako je polje van raspona
	 */
	public KonfiguracijaSlagalice(int[] polje) {
		if(polje.length != 9) {
			throw new IllegalArgumentException("Length of field must be 9.");
		}
		this.polje = polje;
	}

	/**
	 * Metoda koja vraća polje kojim je slagalica konfigurirana.
	 * 
	 * @return polje
	 */
	public int[] getPolje() {
		return Arrays.copyOf(polje, polje.length);
	}
	
	/**
	 * Metoda koja vraća indeks na kojem se nalazi praznina (znak 0).
	 * @return
	 */
	public int indexOfSpace() {
		for(int i = 0; i < 9; i++) {
			if(polje[i] == 0) return i;
		}
		return -1;
	}

	
	@Override
	public String toString() {
		return String.format("%d %d %d\n%d %d %d\n%d %d %d", polje[0], polje[1], polje[2], polje[3], 
				polje[4], polje[5], polje[6], polje[7], polje[8]).replace("0", "*");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(polje);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KonfiguracijaSlagalice other = (KonfiguracijaSlagalice) obj;
		if (!Arrays.equals(polje, other.polje))
			return false;
		return true;
	}
	
}
