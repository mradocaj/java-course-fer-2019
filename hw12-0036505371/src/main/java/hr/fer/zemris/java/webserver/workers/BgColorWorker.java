package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class BgColorWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String color = context.getParameter("bgcolor");
		if(color != null && color.length() == 6) {
			try {
				Long.parseLong(color, 16);	//bacit će se iznimka ako nisu hex znamenke
				context.setPersistentParameter("bgcolor", color);
				context.setTemporaryParameter("message", "Color is updated.");
				context.getDispatcher().dispatchRequest("/index2.html");
				return;
			} catch(NumberFormatException ex) {
				
			}
		}
		
		context.setTemporaryParameter("message", "Color is not updated.");
		context.getDispatcher().dispatchRequest("/index2.html");
	}

}
