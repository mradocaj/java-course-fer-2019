package hr.fer.zemris.lsystems.impl.demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Razred koji služi za demonstraciju rada razreda {@link LSystemBuilderImpl}.
 * 
 * @author Maja Radočaj
 *
 */
public class Glavni2 {

	/**
	 * Glavni program. 
	 * 
	 * @param args argumenti naredbenog retka
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(createKochCurve2(LSystemBuilderImpl::new));
	}

	/**
	 * Metoda koja stvara novu Kochovu krivulju. Naredbe za stvaranje sustava konfigurirane su iz teksta.
	 * 
	 * @param provider objekt koji zna stvarati objekte za konfiguriranje Lindermayerovih sustava
	 * @return novi Lindermayerov sustav
	 */
	private static LSystem createKochCurve2(LSystemBuilderProvider provider) {
		String[] data = new String[] {
			"origin 0.05 0.4",
			"angle 0",
			"unitLength 0.9",
			"unitLengthDegreeScaler 1.0 / 3.0",
			"",
			"command F draw 1",
			"command + rotate 60",
			"command - rotate -60",
			"",
			"axiom F",
			"",
			"production F F+F--F+F"
			};
		
		return provider.createLSystemBuilder().configureFromText(data).build();
	}
}
