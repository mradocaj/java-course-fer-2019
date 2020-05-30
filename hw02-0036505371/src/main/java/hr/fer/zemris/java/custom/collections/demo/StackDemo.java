package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Program za postfiksno računanje. Program se pokreće uz točno 1 argument koji
 * treba biti jedan string, a znakovi i operatori moraju biti odvojeni barem
 * jednim razmakom. Argument su unijeti unutar navodnika (npr. "8 2 /"). Tijekom
 * izvođenja provjerava se je li argument ispravan postfiks izraz.
 * 
 * @author Maja Radočaj
 *
 */
public class StackDemo {

	/**
	 * Glavna metoda od koje kreće program.
	 * 
	 * @param args argumenti naredbenog retka
	 */
	public static void main(String[] args) {

		if(args.length != 1) {
			System.out.println("There should only be one String argument. Try again.");
			return;
		}

		ObjectStack stack = new ObjectStack();
		String[] elements = args[0].split("\\s+"); // 1 ili više razmaka

		for(String element : elements) {
			try {
				int number = Integer.parseInt(element); // ako odmah prođe kao broj, pushaj na stog
				stack.push(number);
			} catch(NumberFormatException ex) {
				try {
					int numberOne = (Integer) stack.pop(); // sad popamo dva broja - ako nema dva broja,
															// greška
					int numberTwo = (Integer) stack.pop();

					if(element.equals("+")) {
						stack.push(numberOne + numberTwo);

					} else if(element.equals("-")) {
						stack.push(numberTwo - numberOne);

					} else if(element.equals("*")) {
						stack.push(numberTwo * numberOne);

					} else if(element.equals("/")) {
						stack.push(numberTwo / numberOne);

					} else if(element.equals("%")) {
						stack.push(numberTwo % numberOne);

					} else {
						System.out.println("Only operations allowed: +, -, *, /, %. Try again.");
						return;
					}
					
				} catch(EmptyStackException e) {
					System.out.println("Invalid expression. Try again.");
					return;
				} catch(ArithmeticException exception) {
					System.out.println("Dividing by zero not possible. Try again.");
					return;
				}

			}
		}

		if(stack.size() != 1) {
			System.out.println("Invalid expression. Try again.");
			return;
		} else {
			System.out.println("Expression evaluates to " + stack.pop() + ".");
		}

	}
}
