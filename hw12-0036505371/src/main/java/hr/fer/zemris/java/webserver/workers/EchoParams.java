package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Razred koji implementira sučelje {@link IWebWorker}.
 * <p>Pošiljatelju zahtjeva u tablici ispisuje poslane parametre.
 * 
 * @author Maja Radočaj
 *
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		context.setMimeType("text/html");
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head><title>EchoParams</title></head>"
				+ "<body><table border=\"2\"><tbody>");
		context.getParameters().forEach((k, v) -> 
			sb.append("<tr><td>" + k + "</td><td>" + v + "</td></tr>"));
		sb.append("</tbody></table></body></html>");
		
		context.write(sb.toString().getBytes());
	}

}
