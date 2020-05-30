package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Razred koji modelira listu prostih brojeva.
 * 
 * @author Maja Radočaj
 *
 */
public class PrimListModel implements ListModel<Integer> {

	/**
	 * Lista prostih brojeva.
	 */
	List<Integer> primeNumbers;
	/**
	 * Slušači promjena.
	 */
	List<ListDataListener> listeners;
	
	/**
	 * Konstruktor.
	 */
	public PrimListModel() {
		primeNumbers = new ArrayList<>();
		primeNumbers.add(1);
		listeners = new ArrayList<>();
	}
	
	/**
	 * Metoda koja dodaje sljedeći prosti broj u listu i obavještava slušače o promjeni.
	 */
	public void next() {
		int current = primeNumbers.get(primeNumbers.size() - 1) + 1;
		while(true) {
			boolean isPrime = true;
			for(int i = 2; i <= Math.sqrt(current); i++) {
				if(current % i == 0) {
					current++;
					isPrime = false;
					break;
				}
			}
			if(isPrime) {
				primeNumbers.add(current);
				break;
			}
		}
		
		listeners.forEach(l -> l.intervalAdded(new ListDataEvent(this
				, ListDataEvent.INTERVAL_ADDED
				, primeNumbers.size() - 1
				, primeNumbers.size() - 1)));
	}
	@Override
	public int getSize() {
		return primeNumbers.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return primeNumbers.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}

}
