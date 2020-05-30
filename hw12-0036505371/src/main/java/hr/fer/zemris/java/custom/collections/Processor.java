package hr.fer.zemris.java.custom.collections;

/**
 * Sučelje procesor opisuje objekt koji može obavljati neku radnju nad danim objektom.
 * Ta radnja bit će opisana u metodi process koju će implementirati specijalizirani procesori koji implementiraju 
 * ovo sučelje.
 * 
 * @author Maja Radočaj
 *
 */
public interface Processor {

	/**
	 * Metoda koja prima objekt i nad njime vrši neki posao.
	 * 
	 * @param value objekt nad kojim treba obaviti radnju
	 */
	void process(Object value);
}
