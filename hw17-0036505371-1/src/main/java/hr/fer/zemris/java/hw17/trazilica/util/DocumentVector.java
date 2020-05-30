package hr.fer.zemris.java.hw17.trazilica.util;

/**
 * Razred za pohranu vektora pojedinih dokumenata.
 * 
 * @author Maja Radočaj
 *
 */
public class DocumentVector {

	/**
	 * putanja dokumenta.
	 */
	private String path;
	/**
	 * TfIdf vektor dokumenta.
	 */
	private double[] tfIdf;
	/**
	 * Tf vektor dokumenta.
	 */
	private int[] tf;
	
	/**
	 * Javni konstruktor.
	 * 
	 * @param path putanja
	 * @param size veličina vokabulara
	 */
	public DocumentVector(String path, int size) {
		this.path = path;
		tfIdf = new double[size];
		tf = new int[size];
	}

	/**
	 * Setter za tf na određenoj poziciji.
	 * 
	 * @param position pozicija
	 * @param value vrijednost vektora na danoj poziciji
	 */
	public void setTf(int position, Integer value) {
		tf[position] = value;
	}
	
	/**
	 * Setter za vektor tf-a.
	 * 
	 * @param tf vektor
	 */
	public void setTf(int[] tf) {
		this.tf = tf;
	}
	
	/**
	 * Getter koji vraća vrijednost tf-a na danoj poziciji.
	 * 
	 * @param position pozicija
	 * @return vrijednost vektora na danoj poziciji
	 */
	public Integer getTf(int position) {
		return tf[position];
	}
	
	/**
	 * Metoda koja vraća path dokumenta.
	 * 
	 * @return path dokumenta
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * Metoda koja računa tfIdf vektor dokumenta za predani idf.
	 * 
	 * @param idf idf vektor
	 */
	public void computeTfIdf(double[] idf) { 
		for(int i = 0; i < tf.length; i++) {
			tfIdf[i] = tf[i] * idf[i];
		}
	}
	
	/**
	 * Metoda koja vraža tfIdf vektor dokumenta.
	 * 
	 * @return tfIdf vektor dokumenta
	 */
	public double[] getTfIdf() {
		return tfIdf;
	}
}
