package coloring.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Razred koji nudi statičke metode za različite obilaske.
 * 
 * @author Maja Radočaj
 *
 */
public class SubspaceExploreUtil {

	/**
	 * Prolazak površine, tzv "Breadth-First Search".
	 * Algoritam kojim se ispunjava površina određenom bojom.
	 * 
	 * @param s0 početno stanje
	 * @param process funkcija koja obavlja posao nad stanjem
	 * @param succ funkcija koja vraća skup susjednih stanja
	 * @param acceptable ispitni predikat koji nam govori trebamo li nastaviti provjeravati susjedne elemente
	 */
	public static <S> void bfs(Supplier<S> s0, Consumer<S> process, Function<S,List<S>> succ, 
			Predicate<S> acceptable) {
		List<S> toCheck = new LinkedList<>();
		toCheck.add(s0.get());
		
		while(!toCheck.isEmpty()) {
			S element = toCheck.remove(0);
			if(acceptable.test(element)) {
				process.accept(element);
				toCheck.addAll(succ.apply(element));
			}
		}
	}
	
	
	/**
	 * Prolazak površine, tzv "Depth-First Search".
	 * Algoritam kojim se ispunjava površina određenom bojom.
	 * 
	 * @param s0 početno stanje
	 * @param process funkcija koja obavlja posao nad stanjem
	 * @param succ funkcija koja vraća skup susjednih stanja
	 * @param acceptable ispitni predikat koji nam govori trebamo li nastaviti provjeravati susjedne elemente
	 */
	public static <S> void dfs(Supplier<S> s0, Consumer<S> process, Function<S,List<S>> succ, 
			Predicate<S> acceptable) {
		List<S> toCheck = new LinkedList<>(succ.apply(s0.get()));
		toCheck.add(s0.get());
		
		while(!toCheck.isEmpty()) {
			S element = toCheck.remove(0);
			if(acceptable.test(element)) {
				process.accept(element);
				toCheck.addAll(0, succ.apply(element));
			}
		}
	}
	
	/**
	 * Prolazak površine na učinkovitiji i brži način.
	 * Algoritam kojim se ispunjava površina određenom bojom.
	 * 
	 * @param s0 početno stanje
	 * @param process funkcija koja obavlja posao nad stanjem
	 * @param succ funkcija koja vraća skup susjednih stanja
	 * @param acceptable ispitni predikat koji nam govori trebamo li nastaviti provjeravati susjedne elemente
	 */
	public static <S> void bfsv(Supplier<S> s0, Consumer<S> process, Function<S,List<S>> succ, 
			Predicate<S> acceptable) {
		List<S> toCheck = new LinkedList<>();
		Set<S> visited = new HashSet<>();
		List<S> children = new LinkedList<>();
		toCheck.add(s0.get());
		visited.add(s0.get());
		S element;
		
		while(!toCheck.isEmpty()) {
			element = toCheck.remove(0);
			if(acceptable.test(element)) {
				process.accept(element);
				children = succ.apply(element);
				children.removeAll(visited);
				toCheck.addAll(children);
				visited.addAll(children);
			}
		}
	}
}
