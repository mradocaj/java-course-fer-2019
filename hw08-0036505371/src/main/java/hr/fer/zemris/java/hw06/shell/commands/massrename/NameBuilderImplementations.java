package hr.fer.zemris.java.hw06.shell.commands.massrename;

import java.util.List;

/**
 * Razred koji u obliu statičkih metoda sadrži nekoliko implementacija {@link NameBuilder}-a.
 * 
 * @author Maja Radočaj
 *
 */
public class NameBuilderImplementations {

	/**
	 * NameBuilder koji prima tekst koji neposredno nadovezuje na StringBuilder.
	 * 
	 * @param t tekst koji treba nadovezati
	 * @return novi {@link NameBuilder}
	 */
	public static NameBuilder text(String t) { return (f, s) -> s.append(t); }
	
	/**
	 * NameBuilder koji prima indeks grupe koju neposredno nadovezuje na StringBuilder.
	 * 
	 * @param index indeks grupe koju treba nadovezati
	 * @return novi {@link NameBuilder}
	 */
	public static NameBuilder group(int index) { return (f, s) -> s.append(f.group(index)); }
	
	/**
	 * NameBuilder koji prima indeks grupe koju neposredno nadovezuje na StringBuilder.
	 * Uz indeks grupe prima se i znak kojim se popunjava niz, kao i minimalna širina ispisa.
	 * 
	 * @param index indeks grupe
	 * @param padding znak popunjavanja
	 * @param minWidth minimalna širina
	 * @return novi {@link NameBuilder}
	 */
	public static NameBuilder group(int index, char padding, int minWidth) { return
	(f, s) -> {
		if(minWidth == 0) return;
		s.append(String.format("%" + minWidth + "s", f.group(index)).replace(' ', padding)); 
		};
	}
	
	/**
	 * Metoda koja vraća kompozitni NameBuilder.
	 * Ona prima listu {@link NameBuilder}-a. Njegova execute naredba sastoji se od ulančanih poziva 
	 * execute naredbi svih članova liste.
	 * 
	 * @param list lista NameBuilder-a
	 * @return novi NameBuilder
	 */
	public static NameBuilder composit(List<NameBuilder> list) {
		return new NameBuilder() {

			@Override
			public void execute(FilterResult result, StringBuilder sb) {
				for(NameBuilder builder : list) {
					builder.execute(result, sb);
				}
			}
			
		};
	}
	
}
