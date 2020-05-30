package searching.algorithms;


/**
 * Razred koji predstavlja uređeni par novog stanja i cijene potrebne da se dođe u to stanje.
 * 
 * @author Maja Radočaj
 *
 * @param <S> tip stanja
 */
public class Transition<S> {

	/**
	 * Stanje.
	 */
	private S state;
	/**
	 * Cijena.
	 */
	private double cost;
	
	/**
	 * Javni konstruktor
	 * 
	 * @param state stanje
	 * @param cost cijena
	 */
	public Transition(S state, double cost) {
		this.state = state;
		this.cost = cost;
	}

	/**
	 * 
	 * @return stanje
	 */
	public S getState() {
		return state;
	}

	/**
	 * 
	 * @return cijena
	 */
	public double getCost() {
		return cost;
	}
	
	
}
