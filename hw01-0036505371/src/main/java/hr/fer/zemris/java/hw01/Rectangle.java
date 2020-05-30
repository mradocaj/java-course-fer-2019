package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program za računanje površine i opsega pravokutnika.
 * 
 * @author Maja Radočaj
 *
 */
public class Rectangle {

	/**
	 * Glavni program. Korisnik mora unijeti ili nijedan ili dva argumenta preko
	 * naredbenog retka. Ako nema argumenata, zahtjeva se unos sve dok se ne unesu
	 * ispravne vrijednosti za visinu i širinu.
	 * 
	 * @param args argumenti uneseni preko naredbenog retka (visina i širina)
	 */
	public static void main(String[] args) {
		double height, width;

		if(args.length == 0) {
			Scanner sc = new Scanner(System.in);
			width = input("širinu", sc);
			height = input("visinu", sc);
			sc.close();

		} else if(args.length == 2) {
			try {
				width = Double.parseDouble(args[0]);
				height = Double.parseDouble(args[1]);
			} catch(NumberFormatException ex) {
				System.out.println("Oba argumenta moraju biti double vrijednosti.");
				return;
			}

		} else {
			System.out.println("Treba biti ili 0 ili 2 argumenta pri pokretanju programa.");
			return;
		}

		calculate(width, height);
	}

	/**
	 * Metoda koja računa i ispisuje površinu i opseg pravokutnika.
	 * 
	 * @param width  širina pravokunika
	 * @param height visina pravokutnika
	 */
	private static void calculate(double width, double height) {
		System.out.println("Pravokutnik širine " + width + " i visine " + height + " ima površinu " + width * height
				+ " te opseg " + 2 * (width + height) + ".");

	}

	/**
	 * Metoda koja zahtjeva da se unese ispravan tip za visinu ili širinu (double).
	 * Metoda se zaustavlja tek kad se unesu ispravne vrijednosti.
	 * 
	 * @param type širina ili visina (potrebno pri ispisu)
	 * @param sc   Scanner kojeg koristimo
	 * @return ispravna double vrijednost
	 */
	private static double input(String type, Scanner sc) {
		double num;
		while(true) {
			System.out.printf("Unesite %s > ", type);

			if(sc.hasNextDouble()) {
				num = sc.nextDouble();
				if(num < 0) {
					System.out.println("Unijeli ste negativnu vrijednost.");
					continue;
				} else {
					break;
				}
			} else {
				System.out.printf("'%s' se ne može protumačiti kao broj.%n", sc.next());
			}

		}

		return num;
	}

}
