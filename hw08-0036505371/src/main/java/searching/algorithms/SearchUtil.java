package searching.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import searching.algorithms.Node;
import searching.algorithms.Transition;

/**
 * Razred koji nudi statičke metode za pretraživanje.
 * 
 * @author Maja Radočaj
 *
 */
public class SearchUtil {

	/**
	 * Algoritam za obilazak bfs.
	 * 
	 * @param s0 početno stanje
	 * @param succ funkcija koja obavlja posao nad stanjem s
	 * @param goal predikat koji javlja jesmo li došli do rezultata
	 * @return rezultatni čvor
	 */
	public static <S> Node<S> bfs(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal) {
		List<Node<S>> toCheck = new LinkedList<>();
		toCheck.add(new Node<S>(null, s0.get(), 0));
		
		while(!toCheck.isEmpty()) {
			Node<S> node = toCheck.remove(0);
			if(goal.test(node.getState())) return node;
			List<Transition<S>> result = succ.apply(node.getState());
			result.forEach(t -> toCheck.add(new Node<>(node, t.getState(), node.getCost() + t.getCost())));
		}
		
		return null;
	}
	
	/**
	 * Algoritam za obilazak bfsv.
	 * Ovo je optimiziranija verzija algoritma za prolazak.
	 * 
	 * @param s0 početno stanje
	 * @param succ funkcija koja obavlja posao nad stanjem s
	 * @param goal predikat koji javlja jesmo li došli do rezultata
	 * @return rezultatni čvor
	 */
	public static <S> Node<S> bfsv(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal) {
		List<Node<S>> toCheck = new LinkedList<>();
		Set<S> visited = new HashSet<>();
		toCheck.add(new Node<S>(null, s0.get(), 0));
		visited.add(s0.get());
		
		while(!toCheck.isEmpty()) {
			Node<S> node = toCheck.remove(0);
			if(goal.test(node.getState())) {
				return node;
			}
			List<Transition<S>> result = succ.apply(node.getState());
			List<Transition<S>> resultCopy = new LinkedList<>(result);
			resultCopy.forEach(t -> {
				if(visited.contains(t.getState())) {
					result.remove(t);
				}
			});
			result.forEach(t -> toCheck.add(new Node<>(node, t.getState(), node.getCost() + t.getCost())));
			result.forEach(t -> visited.add(t.getState()));
		}
		
		return null;
	}
	
}
