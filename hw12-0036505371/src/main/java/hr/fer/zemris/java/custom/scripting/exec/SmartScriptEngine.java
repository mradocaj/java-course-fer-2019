package hr.fer.zemris.java.custom.scripting.exec;

import static java.lang.Math.sin;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.demo.SmartScriptEngineDemo;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Razred koji služi za izvođenje dokumenta čije stablo parsiranja dobije preko konstruktora.
 * <p>Demonstraciju rada pogledati u razredu {@link SmartScriptEngineDemo}.
 * 
 * @author Maja Radočaj
 *
 */
public class SmartScriptEngine {

	/**
	 * Čvor dokumenta.
	 */
	private DocumentNode documentNode;
	/**
	 * Zahtjev.
	 */
	private RequestContext requestContext;
	/**
	 * Stog.
	 */
	private ObjectMultistack multistack = new ObjectMultistack();
	/**
	 * Visitor za izvođenje dokumenta.
	 */
	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {						
			try{
				requestContext.write(node.getText());
			} catch(IOException ex) {
				System.out.println("Couldn't write text node. " + ex.getMessage());
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String var = node.getVariable().getName();
			String endValue = node.getEndExpression().asText();
			String step = node.getStepExpression().asText();
			multistack.push(var, new ValueWrapper(node.getStartExpression().asText()));
			
			while(multistack.peek(var).numCompare(endValue) <= 0) {
				for(int i = 0; i < node.numberOfChildren(); i++) {
					node.getChild(i).accept(this);
				}
				ValueWrapper wrapper = multistack.pop(var);
				wrapper.add(step);
				multistack.push(var, wrapper);
			}
			
			multistack.pop(var);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<Object> temporaryStack = new Stack<>();
			Element[] tokens = node.getElements();
			
			for(Element token : tokens) {
				if(token instanceof ElementConstantDouble) {
					temporaryStack.push(((ElementConstantDouble) token).getValue());
					
				} else if(token instanceof ElementConstantInteger) {
					temporaryStack.push(((ElementConstantInteger) token).getValue());
					
				} else if(token instanceof ElementString) {
					temporaryStack.push(((ElementString) token).getValue());
					
				} else if(token instanceof ElementVariable) {
					String name = ((ElementVariable) token).getName();
					temporaryStack.push(multistack.peek(name).getValue());
					
				} else if(token instanceof ElementOperator) {
					String symbol = ((ElementOperator) token).getSymbol();
					ValueWrapper result = operation(symbol, temporaryStack);
					temporaryStack.push(result.getValue());
					
 				} else if(token instanceof ElementFunction) {
 					String funct = ((ElementFunction) token).getName();
 					doFunction(funct, temporaryStack);
 				}
			}
			
			if(!temporaryStack.isEmpty()) {
				emptyStack(temporaryStack);
			}
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for(int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
		}
		
	};

	/**
	 * Javni konstruktor. Predani argumenti ne smiju biti <code>null</code>.
	 * 
	 * @param documentNode čvor dokumenta 
	 * @param requestContext zahtjev
	 * @throws NullPointerException ako je ijedan od argumenata <code>null</code>
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = Objects.requireNonNull(documentNode);
		this.requestContext = Objects.requireNonNull(requestContext);
	}

	/**
	 * Metoda koja započinje izvođenje skripte.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}

	/**
	 * Pomoćna metoda koja sa stoga skida i vraća ime i vrijednost neke varijable.
	 * 
	 * @param temporaryStack privremeni stog
	 * @return ime i vrijednost varijable
	 */
	private String[] nameAndValue(Stack<Object> temporaryStack) {
		String name = (String) temporaryStack.pop();
		String value = null;
		Object z = temporaryStack.pop();
		
		if(z instanceof Integer) {
			value = Integer.toString((Integer) z);
		} else if(z instanceof Double) {
			value = Double.toString((Double) z);
		} else if(z instanceof String) {
			value = (String) z;
		}
		return new String[] {name, value};
	}
	
	/**
	 * Pomoćna metoda za izvođenje funkcije.
	 * 
	 * @param funct funkcija
	 * @param temporaryStack privremeni stog
	 */
	private void doFunction(String funct, Stack<Object> temporaryStack) {
		switch(funct) {
			case "sin":
				Object x = temporaryStack.pop();
				if(x instanceof Integer) {
					temporaryStack.push(sin(Math.toRadians((Integer) x)));
				} else if(x instanceof Double) {
					temporaryStack.push(sin(Math.toRadians((Double) x)));
				} else if(x instanceof String) {
					Double value = Double.parseDouble((String) x);
					temporaryStack.push(sin(Math.toRadians(value)));
				}
				break;
			case "decfmt":
				DecimalFormat format = new DecimalFormat((String) temporaryStack.pop());
				Object y = temporaryStack.pop();
				temporaryStack.push(format.format(y));
				break;
			case "setMimeType":
				String s = (String) temporaryStack.pop();
				requestContext.setMimeType(s);
				break;
			case "dup":
				Object value = temporaryStack.pop();
				temporaryStack.push(value);
				temporaryStack.push(value);
				break;
			case "swap":
				Object first = temporaryStack.pop();
				Object second = temporaryStack.pop();
				temporaryStack.push(first);
				temporaryStack.push(second);
				break;
			case "paramGet":
				Object defValue = temporaryStack.pop();
				String name = (String) temporaryStack.pop();
				String val = requestContext.getParameter(name);
				temporaryStack.push(val == null ? defValue : val);
				break;
			case "pparamGet":
				Object defVal = temporaryStack.pop();
				String n = (String) temporaryStack.pop();
				String v = requestContext.getPersistentParameter(n);
				temporaryStack.push(v == null ? defVal : v);
				break;
			case "pparamSet":
				String[] nameAndValue = nameAndValue(temporaryStack);
				requestContext.setPersistentParameter(nameAndValue[0], nameAndValue[1]);
				break;
			case "pparamDel":
				String namePer = (String) temporaryStack.pop();
				requestContext.removePersistentParameter(namePer);
				break;
			case "tparamGet":
				Object defValT = temporaryStack.pop();
				String nT = (String) temporaryStack.pop();
				String vT = requestContext.getTemporaryParameter(nT);
				temporaryStack.push(vT == null ? defValT : vT);
				break;
			case "tparamSet":
				String[] nameAndVal = nameAndValue(temporaryStack);
				requestContext.setTemporaryParameter(nameAndVal[0], nameAndVal[1]);
				break;
			case "tparamDel":
				String nameTemp = (String) temporaryStack.pop();
				requestContext.removeTemporaryParameter(nameTemp);
				break;
		}
	}
	
	/**
	 * Pomoćna metoda koja prima simbol operacije i sa predanog stoga skida argumente.
	 * Nakon toga izvodi operaciju nad argumentima.
	 * 
	 * @param symbol simbol operacije
	 * @param temporaryStack privremeni stog
	 * @return rezultat operacije
	 */
	private ValueWrapper operation(String symbol, Stack<Object> temporaryStack) {
		ValueWrapper result = new ValueWrapper(temporaryStack.pop());
		Object second = temporaryStack.pop();
		
		switch(symbol) {
			case "+":
				result.add(second);
				break;
			case "-":
				result.subtract(second);
				break;
			case "*":
				result.multiply(second);
				break;
			case "/":
				result.divide(second);
				break;
		}
		
		return result;
	}
	
	/**
	 * Pomoćna metoda koja obrađuje elemente koji su ostali na stogu.
	 * 
	 * @param temporaryStack privremeni stog
	 */
	private void emptyStack(Stack<Object> temporaryStack) {
		Stack<Object> newStack = new Stack<>();
		int size = temporaryStack.size();
		
		for(int i = 0; i < size; i++) {
			newStack.push(temporaryStack.pop());
		}
		
		for(int i = 0; i < size; i++) {
			Object elem = newStack.pop();
			String result = null;
			if(elem instanceof String) {
				result = (String) elem;
			} else if(elem instanceof Double) {
				result = Double.toString((Double) elem);
			} else if(elem instanceof Integer) {
				result = Integer.toString((Integer) elem);
			}
			
			try{
				requestContext.write(result);	
			} catch(IOException ex) {
				System.out.println("Couldn't write.");
			}
		}
	}
}
