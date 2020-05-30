package searching.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;
import searching.slagalica.gui.SlagalicaViewer;

/**
 * Razred za demonstraciju rada slagalice.
 * Program prima jedan argument - konfiguraciju slagalice.
 * 
 * @author Maja Radočaj
 *
 */
public class SlagalicaMain {

	/**
	 * Glavni program.
	 * 
	 * @param args argumenti naredbenog retka
	 */
	public static void main(String[] args) {
		if(args.length == 0) {
			System.out.println("Očekivan argument.");
			return;
		}
		String konfiguracija = args[0].trim();
		if(!ispravnaKonfig(konfiguracija)) {
			System.out.println("Očekivana konfiguracija sadrži 9 brojeva od 0 do 8.");
			return;
		}
		int polje[] = new int[9];
		char[] znakovi = konfiguracija.toCharArray();
		for(int i = 0; i < 9; i++) {
			polje[i] = Integer.parseInt("" + znakovi[i]);
		}
		
		Slagalica slagalica = new Slagalica(new KonfiguracijaSlagalice(polje));
		Node<KonfiguracijaSlagalice> rješenje = SearchUtil.bfsv(slagalica, slagalica, slagalica);
		if (rješenje == null) {
			System.out.println("Nisam uspio pronaći rješenje.");
		} else {
			System.out.println("Imam rješenje. Broj poteza je: " + rješenje.getCost());
			List<KonfiguracijaSlagalice> lista = new ArrayList<>();
			Node<KonfiguracijaSlagalice> trenutni = rješenje;
			while (trenutni != null) {
				lista.add(trenutni.getState());
				trenutni = trenutni.getParent();
			}
			Collections.reverse(lista);
			lista.stream().forEach(k -> {
				System.out.println(k);
				System.out.println();
			});
			SlagalicaViewer.display(rješenje);
		}
	}
	
	/**
	 * Pomoćna metoda koja provjerava je li konfiguracija validna.
	 * 
	 * @param konfig konfiguracija
	 * @return <code>true</code> ako je, <code>false</code> ako nije
	 */
	private static boolean ispravnaKonfig(String konfig) {
		if(konfig.length() != 9) return false;
		if(!konfig.contains("0")) return false;
		if(!konfig.contains("1")) return false;
		if(!konfig.contains("2")) return false;
		if(!konfig.contains("3")) return false;
		if(!konfig.contains("4")) return false;
		if(!konfig.contains("5")) return false;
		if(!konfig.contains("6")) return false;
		if(!konfig.contains("7")) return false;
		if(!konfig.contains("8")) return false;
		return true;
	}
}
