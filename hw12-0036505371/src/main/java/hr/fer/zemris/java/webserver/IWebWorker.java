package hr.fer.zemris.java.webserver;

/**
 * Sučelje koje modelira objekt koji može obraditi zahtjev.
 * 
 * @author Maja Radočaj
 *
 */
public interface IWebWorker {
	/**
	 * Metoda za obradu zahtjeva.
	 * <p>Na temelju konteksta trenutnog zahtjeva (<code>context</code>) obrađuje se 
	 * zahtjev i obavlja neki posao.
	 * 
	 * @param context trenutni zahtjev
	 * @throws Exception ako dođe do greške pri obradi zahtjeva
	 */
	public void processRequest(RequestContext context) throws Exception;
}