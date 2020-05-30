package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program koji omogućava računanje fakortijela brojeva. Brojevi koji se mogu
 * izračunati su u rasponu od 3 do 20 uključivo.
 * 
 * @author Maja Radočaj
 */
public class Factorial {

	/**
	 * Donja granica za izračun faktorijela.
	 */
	private static final int LOWER_LIMIT = 3;
	/**
	 * Gornja granica za izračun faktorijela.
	 */
	private static final int UPPER_LIMIT = 20;
	/**
	 * Linija koja označava kraj rada.
	 */
	private static final String END = "kraj";

	/**
	 * Glavna metoda od kojeg kreće računanje. Petlja se zaustavlja ključnom riječi
	 * "kraj".
	 * 
	 * @param args argumenti naredbenog retka (program se pokreće bez njih)
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		while(true) {
			System.out.print("Unesite broj > ");

			if(sc.hasNextInt()) {
				int number = sc.nextInt();
				if(number < LOWER_LIMIT || number > UPPER_LIMIT) {
					System.out.printf("'%d' nije broj u dozvoljenom rasponu.%n", number);
					continue;
				}
				long result = calculateFactorial(number);
				System.out.printf("%d! = %d%n", number, result);

			} else {
				String line = sc.next();
				if(line.equals(END)) {
					System.out.println("Doviđenja.");
					sc.close();
					return;
				} else {
					System.out.printf("'%s' nije cijeli broj.%n", line);
				}
			}
		}

	}

	/**
	 * Metoda za računanje faktorijela.
	 * 
	 * @param number broj čiji faktorijel treba izračunati
	 * @return rezultat
	 * @throws IllegalArgumentException ukoliko metoda primi argument koji je
	 *                                  negativan ili veći od 20
	 */
	public static long calculateFactorial(int number) {
		long result = 1;
		if(number > 20 || number < 0) {
			throw new IllegalArgumentException();
		}

		for(int i = 1; i <= number; i++) {
			result *= i;
		}
		
		return result;
	}

}
