package hr.fer.zemris.java.hw17.jvdraw.drawing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObjectListener;

/**
 * Implementacija modela za crtanje.
 * 
 * @author Maja Radočaj
 *
 */
public class DrawingModelImpl implements DrawingModel, GeometricalObjectListener {

	/**
	 * Lista objekata u modelu.
	 */
	private List<GeometricalObject> objects = new ArrayList<>();
	/**
	 * Lista slučača.
	 */
	private List<DrawingModelListener> listeners = new ArrayList<>();
	/**
	 * Zastavica izmjene.
	 */
	private boolean modified;

	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		objects.add(Objects.requireNonNull(object));
		int index = objects.indexOf(object);
		object.addGeometricalObjectListener(this);

		listeners.forEach(l -> l.objectsAdded(this, index, index));
		modified = true;
	}

	@Override
	public void remove(GeometricalObject object) {
		int index = objects.indexOf(object);
		objects.remove(object);
		object.removeGeometricalObjectListener(this);

		listeners.forEach(l -> l.objectsRemoved(this, index, index));
		modified = true;
	}

	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		int index = objects.indexOf(object);
		if(index + offset < 0 || index + offset >= objects.size()) {
			return;
		}
		Collections.swap(objects, index, index + offset);

		listeners.forEach(l -> l.objectsChanged(this, index, index + offset));
		modified = true;
	}

	@Override
	public int indexOf(GeometricalObject object) {
		return objects.indexOf(object);
	}

	@Override
	public void clear() {
		if(objects.size() != 0)
			modified = true;
		objects.clear();
	}

	@Override
	public void clearModifiedFlag() {
		modified = false;
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(Objects.requireNonNull(l));
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}

	@Override
	public void geometricalObjectChanged(GeometricalObject o) { 
		listeners.forEach(l -> l.objectsChanged(this, objects.indexOf(o), objects.indexOf(o)));
		modified = true;
	}

	/**
	 * Metoda koja postavlja zastavicu izmjene na <code>true</code>.
	 */
	public void setModifiedTrue() {
		modified = true;
	}
}
