package hr.fer.zemris.java.hw17.jvdraw.drawing;

import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;

/**
 * Sušelje koje modelira model za crtanje.
 * <p>
 * Model pohranjuje različite nacrtane objekte.
 * 
 * @author Maja Radočaj
 *
 */
public interface DrawingModel {

	/**
	 * Metoda koja vraća broj objekata u modelu.
	 * 
	 * @return broj objekata u modelu
	 */
	public int getSize();

	/**
	 * Metoda koja vraća objekt na indeksu <code>index</code>.
	 * 
	 * @param index indeks objekta
	 * @return objekt na danom indeksu
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Metoda za dodavanje novog objekta u model.
	 * 
	 * @param object objekt
	 * @throws NullPointerException ako se pokuša dodati <code>null</code>
	 */
	public void add(GeometricalObject object);

	/**
	 * Metoda za uklanjanje objekta iz modela.
	 * 
	 * @param object model kojeg treba ukloniti
	 */
	public void remove(GeometricalObject object);

	/**
	 * Metoda za pomicanje pozicije objekta <code>object</code> za
	 * <code>offset</code> mjesta.
	 * 
	 * @param object objekt koji treba premjestiti
	 * @param offset pomak
	 */
	public void changeOrder(GeometricalObject object, int offset);

	/**
	 * Metoda koja vraća indeks objekta <code>object</code>.
	 * 
	 * @param object objekt
	 * @return indeks objekta
	 */
	public int indexOf(GeometricalObject object);
	
	/**
	 * Metoda za uklanjanje svih objekata iz modela.
	 */
	public void clear();

	/**
	 * Metoda za postavljanje zastavice izmjene na <code>false</code>.
	 */
	public void clearModifiedFlag();

	/**
	 * Metoda koja vraća je li model izmijenjen.
	 * 
	 * @return <code>true</code> ako je model izmijenjen, <code>false</code> ako nije
	 */
	public boolean isModified();

	/**
	 * Metoda za dodavanje slušača modela.
	 * 
	 * @param l slušač modela
	 * @throws NullPointerException ako se pokuša dodati <code>null</code>
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Metoda za uklanjanje slušača modela.
	 * 
	 * @param l slušač modela
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
}