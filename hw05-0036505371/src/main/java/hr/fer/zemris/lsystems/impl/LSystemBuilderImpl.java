package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.util.Objects;
import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.*;
import hr.fer.zemris.math.Vector2D;

/**
 * Razred koji omogućava stvaranje i crtanje fraktala. Fraktal se stvara od
 * početnog aksioma primjenom danih produkcija i akcija.
 * 
 * @author Maja Radočaj
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {

	/**
	 * Kolekcija koja pohranjuje registrirane produkcije.
	 */
	private Dictionary<Character, String> productions = new Dictionary<>();
	/**
	 * Kolekcija koja pohranjuje registrirane akcije.
	 */
	private Dictionary<Character, Command> actions = new Dictionary<>();
	/**
	 * Početna duljina.
	 */
	private double unitLength = 0.1;
	/**
	 * Faktor skaliranja.
	 */
	private double unitLengthDegreeScaler = 1;
	/**
	 * Početna točka.
	 */
	private Vector2D origin = new Vector2D(0, 0);
	/**
	 * Kut smjera kornjače.
	 */
	private double angle;
	/**
	 * Aksiom, početni niz pri generiranju niza.
	 */
	private String axiom = "";

	/**
	 * Metoda koja vraća novi primjerak razreda koji implementira sučelje
	 * <code>LSystem</code>. Razred implementira metodu za crtanje <code>draw</code>
	 * te metodu <code>generate</code> za generiranje znakovnog niza za danu dubinu.
	 * 
	 * @return primjerak razreda koji implementira LSystem sučelje
	 */
	@Override
	public LSystem build() {
		class MyLSystem implements LSystem {

			/**
			 * Metoda koja prima dubinu za koju treba generirati niz. Nakon što generira
			 * niz, metoda iscrtava novi fraktal.
			 * 
			 * @param depth   predana dubina
			 * @param painter objekt za crtanje
			 */
			@Override
			public void draw(int depth, Painter painter) {
				Context context = new Context();
				Vector2D direction = new Vector2D(1, 0);
				direction.rotate(Math.toRadians(angle));
				TurtleState state = new TurtleState(origin, direction, Color.black,
						unitLength * Math.pow(unitLengthDegreeScaler, depth));
				context.pushState(state);
				String line = generate(depth);

				for(int i = 0; i < line.length(); i++) {
					if(actions.get(line.charAt(i)) != null) {
						Command command = actions.get(line.charAt(i));
						command.execute(context, painter);
					}
				}
			}

			/**
			 * Metoda koja na temelju aksioma i produkcija generira niz za danu dubinu. Ako
			 * je predana dubina 0, onda vraća samo aksiom. U suprotnome primjenjuje
			 * registrirane produkcije.
			 * 
			 * @param depth predana dubina
			 * @return generirani niz
			 */
			@Override
			public String generate(int depth) {
				if(depth == 0)
					return axiom;
				char[] current = axiom.toCharArray();
				StringBuilder result = new StringBuilder();

				for(int i = 0; i < depth; i++) {
					result = new StringBuilder(); 
					for(char c : current) {
						if(productions.get(c) != null) {
							result.append(productions.get(c));
						} else {
							result.append(c);
						}
					}
					current = result.toString().toCharArray();
				}

				return result.toString();
			}
		}

		return new MyLSystem();
	}

	/**
	 * Metoda koja prima niz linija. Podrazumijeva se da svaka linija sadrži neku
	 * direktivu. Metoda potom ažurira početno stanje prije ispisa, postavlja aksiom
	 * te registrira produkcije i akcije.
	 * 
	 * @param data niz direktiva
	 * @return referenca na trenutni, ažurirani primjerak razreda
	 * @throws IllegalArgumentException ako naredbe nemaju predviđen broj i tip
	 *                                  argumenata
	 */
	@Override
	public LSystemBuilder configureFromText(String[] data) {
		for(String line : data) {
			if(line.trim().equals(""))
				continue;
			String[] parts = line.split("\\s+");
			try {

				switch(parts[0]) {
				case "origin":
					checkArguments(3, parts.length);
					setOrigin(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
					break;
				case "angle":
					checkArguments(2, parts.length);
					setAngle(Double.parseDouble(parts[1]));
					break;
				case "unitLength":
					checkArguments(2, parts.length);
					setUnitLength(Double.parseDouble(parts[1]));
					break;
				case "unitLengthDegreeScaler":
					String substring = line.trim().replace("unitLengthDegreeScaler", "")
						.replaceAll("\\s+", "");
					String[] arguments = substring.split("/");
					
					if(arguments.length == 2) {
						setUnitLengthDegreeScaler(Double.parseDouble(arguments[0]) / 
								Double.parseDouble(arguments[1]));
					} else if(arguments.length == 1) {
						setUnitLengthDegreeScaler(Double.parseDouble(arguments[0]));
					} else {
						throw new IllegalArgumentException();
					}
					break;
				case "axiom":
					checkArguments(2, parts.length);
					setAxiom(parts[1]);
					break;
				case "production":
					checkArguments(3, parts.length);
					checkArguments(1, parts[1].length());
					registerProduction(parts[1].charAt(0), parts[2]);
					break;
				case "command":
					if(parts.length == 4) {
						checkArguments(1, parts[1].length());
						registerCommand(parts[1].charAt(0), parts[2] + " " + parts[3]);
					} else if(parts.length == 3) {
						checkArguments(1, parts[1].length());
						registerCommand(parts[1].charAt(0), parts[2]);
					} else {
						throw new IllegalArgumentException();
					}
					break;
				default:
					throw new IllegalArgumentException();
				}
			} catch(NumberFormatException ex) {
				throw new IllegalArgumentException("Expected double argument.");
			}
		}

		return this;
	}

	/**
	 * Metoda koja za dani ključ, ključnu riječ i argument registrira novu akciju.
	 * Razmaci se preskaču.
	 * 
	 * @param actionKey   ključ akcije
	 * @param commandLine ključna riječ i argument akcije
	 * @return referenca na trenutni, ažurirani primjerak razreda
	 * @throws NullPointerException     ako je predani String <code>null</code>
	 * @throws IllegalArgumentException ako naredbe nemaju zadovoljavajuće argumente
	 */
	@Override
	public LSystemBuilder registerCommand(char actionKey, String commandLine) {
		char key = actionKey;
		Objects.requireNonNull(commandLine);
		String[] command = commandLine.split("\\s+");
		
		try {
			switch(command[0]) {
				case "draw":
					checkArguments(2, command.length);
					actions.put(key, new DrawCommand(Double.parseDouble(command[1])));
					break;
				case "skip":
					checkArguments(2, command.length);
					actions.put(key, new SkipCommand(Double.parseDouble(command[1])));
					break;
				case "scale":
					checkArguments(2, command.length);
					actions.put(key, new ScaleCommand(Double.parseDouble(command[1])));
					break;
				case "rotate":
					checkArguments(2, command.length);
					actions.put(key, new RotateCommand(Double.parseDouble(command[1])));
					break;
				case "push":
					checkArguments(1, command.length);
					actions.put(key, new PushCommand());
					break;
				case "pop":
					checkArguments(1, command.length);
					actions.put(key, new PopCommand());
					break;
				case "color":
					checkArguments(2, command.length);
					actions.put(key, new ColorCommand(Color.decode("#" + command[1].toUpperCase())));
					break;
				default:
					throw new IllegalArgumentException();
			}
		} catch(NumberFormatException ex) {
			throw new IllegalArgumentException();
		}

		return this;
	}

	/**
	 * Metoda koja registrira i pohranjuje novu produkciju. Produkcija ne smije biti
	 * <code>null</code>.
	 * 
	 * @param productionKey ključ produkcije
	 * @param production    produkcija
	 * @return referenca na trenutni, ažurirani primjerak razreda
	 * @throws NullPointerException ako je predana produkcija <code>null</code>.
	 */
	@Override
	public LSystemBuilder registerProduction(char productionKey, String production) {
		productions.put(productionKey, Objects.requireNonNull(production));
		return this;
	}

	/**
	 * Metoda koja postavlja kut na predanu vrijednost.
	 * 
	 * @param predani kut
	 * @return referenca na trenutni, ažurirani primjerak razreda
	 */
	@Override
	public LSystemBuilder setAngle(double newAngle) {
		angle = newAngle;
		return this;
	}

	/**
	 * Metoda koja postavlja aksiom na predanu vrijednost.
	 * 
	 * @param aksiom
	 * @return referenca na trenutni, ažurirani primjerak razreda
	 * @throws NullPointerException ako je predani String <code>null</code>
	 */
	@Override
	public LSystemBuilder setAxiom(String newAxiom) {
		axiom = Objects.requireNonNull(newAxiom);
		return this;
	}

	/**
	 * Metoda koja postavlja početnu točku kornjače.
	 * 
	 * @param x <code>x</code> komponenta vektora
	 * @param y <code>y</code> komponenta vektora
	 * @return referenca na trenutni, ažurirani primjerak razreda
	 */
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		origin = new Vector2D(x, y);
		return this;
	}

	/**
	 * Metoda koja postavlja duljinu na predanu vrijednost.
	 * 
	 * @param length predana vrijednost
	 * @return referenca na trenutni, ažurirani primjerak razreda
	 */
	@Override
	public LSystemBuilder setUnitLength(double length) {
		unitLength = length;
		return this;
	}

	/**
	 * Metoda koja postavlja faktor skaliranja na predanu vrijednost.
	 * 
	 * @param factor faktor skaliranja
	 * @return referenca na trenutni, ažurirani primjerak razreda
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double factor) {
		unitLengthDegreeScaler = factor;
		return this;
	}

	/**
	 * Pomoćna metoda koja baca iznimku ako nema očekivan broj argumenata.
	 * 
	 * @param expected očekivan broj argumenata
	 * @param actual   pravi broj argumenata
	 * @throws IllegalArgumentException ako je očekivan broj argumenata različit od
	 *                                  pravog broja argumenata
	 */
	private static void checkArguments(int expected, int actual) {
		if(expected != actual) {
			throw new IllegalArgumentException();
		}
	}
}
