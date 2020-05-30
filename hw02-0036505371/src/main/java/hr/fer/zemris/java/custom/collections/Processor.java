package hr.fer.zemris.java.custom.collections;

/**
 * Razred procesor modelira objekt koji može obavljati neku radnju nad danim objektom.
 * <p>Ta radnja bit će opisana u metodi process koju će implementirati specijalizirani procesori 
 * koji nasljeđuju ovaj razred.
 * 
 * @author Maja Radočaj
 *
 */
public class Processor {

	/**
	 * Metoda koja prima objekt i nad njime vrši neki posao.
	 * Ovdje je implementirana kao prazna metoda, ostavljena je razredima koji nasljeđuju ovaj razred 
	 * da ju nadjačaju.
	 * 
	 * @param value objekt nad kojim treba obaviti radnju
	 */
	public void process(Object value) {
		
	}
}
