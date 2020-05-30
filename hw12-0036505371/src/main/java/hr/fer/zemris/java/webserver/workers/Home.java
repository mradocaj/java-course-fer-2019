package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Razred koji implementira sučelje {@link IWebWorker}.
 * <p>Pošiljatelju zahtjeva ispisuje html stranicu na kojoj postoji nekoliko linkova na
 * workere, kao i mogućnost računanja zbroja te odabira pozadinske boje.
 * 
 * @author Maja Radočaj
 *
 */
public class Home implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		
		String bcg = context.getPersistentParameter("bgcolor");
		if(bcg != null) {
			context.setTemporaryParameter("background", bcg);
		} else {
			context.setTemporaryParameter("background", "7F7F7F");
		}
		
		context.getDispatcher().dispatchRequest("/private/pages/home.smscr");
	}

}
