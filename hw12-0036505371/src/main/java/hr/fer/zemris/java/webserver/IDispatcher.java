package hr.fer.zemris.java.webserver;

/**
 * Sučelje koje ima metodu za obradu i prosljeđivanje zahtjeva.
 * 
 * @author Maja Radočaj
 *
 */
public interface IDispatcher {
	/**
	 * Metoda za prosljeđivanje zahtjeva.
	 * 
	 * @param urlPath putanja
	 * @throws Exception ako dođe do greške
	 */
	void dispatchRequest(String urlPath) throws Exception;
}