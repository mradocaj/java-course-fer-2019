package hr.fer.zemris.lsystems.impl.demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Razred koji služi za demonstraciju rada razreda {@link LSystemBuilderImpl}.
 * Moguće je pokrenuti više različitih demonstracijskih primjera. 
 * Primjeri su pohranjeni u mapi src/main/resources.
 * 
 * @author Maja Radočaj
 *
 */
public class Glavni3 {

	/**
	 * Glavni program.
	 * 
	 * @param args argumenti naredbenog retka
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);
	}

}
