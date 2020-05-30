package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Razred koji implementira sučelje {@link IWebWorker}.
 * <p>Pošljatelju u tablici ispisuje argumente a, b i njihov zbroj. 
 * Argumenti su dobavljeni iz zahtjeva.
 * <p>Ako je zbroj paran, prikazuje se gif Gingerbread Man-a iz Shreka.
 * Ako je neparan, prikazuje se slika Shreka, Fione i Magareta.
 * 
 * @author Maja Radočaj
 *
 */
public class SumWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String first = context.getParameter("a");
		String second = context.getParameter("b");
		int a = 1;
		int b = 2;
		
		try {
			a = Integer.parseInt(first);
		} catch(NumberFormatException | NullPointerException ex) {
		}
		try {
			b = Integer.parseInt(second);
		} catch(NumberFormatException | NullPointerException ex) {
		}
		
		int sum = a + b;
		String zbroj = Integer.toString(sum);
		context.setTemporaryParameter("zbroj", zbroj);
		context.setTemporaryParameter("varA", Integer.toString(a));
		context.setTemporaryParameter("varB", Integer.toString(b));
		String image = sum % 2 == 0 ? "images/gingerbreadMan.gif" : "images/shrek.jpg";
		context.setTemporaryParameter("imgName", image);
		
		context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");
	}

}
