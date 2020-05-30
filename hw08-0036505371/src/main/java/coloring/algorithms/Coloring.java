package coloring.algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import marcupic.opjj.statespace.coloring.Picture;

/**
 * Razred koji omogućava bojanje jednostavnih likova.
 * Bojanje se odvija sve dok postoji susjednih elemenata označenih početnom bojom.
 * 
 * @author Maja Radočaj
 *
 */
public class Coloring implements Consumer<Pixel>, Function<Pixel,List<Pixel>>, Predicate<Pixel>, Supplier<Pixel> {

	/**
	 * Referentni piksel.
	 */
	private Pixel reference;
	/**
	 * Slika po kojoj bojamo.
	 */
	private Picture picture;
	/**
	 * Boja kojom bojamo.
	 */
	private int fillColor;
	/**
	 * Početna boja.
	 */
	private int refColor;
	
	/**
	 * Javni konstruktor.
	 * 
	 * @param reference početni piksel
	 * @param picture slika 
	 * @param fillColor boja
	 * @throws NullPointerException ako je ijedan od argumenata <code>null</code>
	 */
	public Coloring(Pixel reference, Picture picture, int fillColor) {
		this.reference = Objects.requireNonNull(reference);
		this.picture = Objects.requireNonNull(picture);
		this.fillColor = Objects.requireNonNull(fillColor);
		this.refColor = picture.getPixelColor(reference.x, reference.y);
	}

	@Override
	public Pixel get() {
		return reference;
	}

	@Override
	public boolean test(Pixel pixel) {
		Objects.requireNonNull(pixel);
		return picture.getPixelColor(pixel.x, pixel.y) == refColor;
	}

	@Override
	public List<Pixel> apply(Pixel pixel) {
		Objects.requireNonNull(pixel);
		List<Pixel> neighbours = new LinkedList<>();		
		int x = pixel.x;
		int y = pixel.y;
	
		if(x + 1 < picture.getWidth()) neighbours.add(new Pixel (x + 1, y));	//rubni slučajevi
		if(x - 1  >= 0 ) neighbours.add(new Pixel (x - 1, y));
		if(y + 1 < picture.getHeight()) neighbours.add(new Pixel (x, y + 1));
		if(y - 1  >= 0 ) neighbours.add(new Pixel (x, y - 1));
		
		return neighbours;
	}

	@Override
	public void accept(Pixel pixel) {
		Objects.requireNonNull(pixel);
		picture.setPixelColor(pixel.x, pixel.y, fillColor);
	}

	
}
