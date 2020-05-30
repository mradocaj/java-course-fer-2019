package searching.algorithms;

/**
 * Razred koji modelira čvor stabla pretraživanja.
 * Svaki čvor ima stanje, roditeljski čvor i cijenu.
 * 
 * @author Maja Radočaj
 *
 * @param <S> tip stanja
 */
public class Node <S> {

	/**
	 * Stanje u kojem se nalazimo.
	 */
	private S state;
	/**
	 * Roditeljski čvor.
	 */
	private Node<S> parent;
	/**
	 * Cijena.
	 */
	private double cost;
	
	/**
	 * Javni konstruktor.
	 * 
	 * @param parent roditeljski čvor
	 * @param state stanje
	 * @param cost cijena
	 */
	public Node(Node<S> parent, S state, double cost) {
		this.state = state;
		this.parent = parent;
		this.cost = cost;
	}

	/**
	 * Getter koji vraća stanje.
	 * 
	 * @return stanje
	 */
	public S getState() {
		return state;
	}

	/**
	 * Getter koji vraća roditeljski čvor.
	 * 
	 * @return roditeljski čvor
	 */
	public Node<S> getParent() {
		return parent;
	}

	/**
	 * Getter koji vraća cijenu.
	 * 
	 * @return cijena
	 */
	public double getCost() {
		return cost;
	}
	
	
}
