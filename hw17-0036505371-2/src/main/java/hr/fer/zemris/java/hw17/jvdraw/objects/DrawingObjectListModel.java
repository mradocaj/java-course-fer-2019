package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModelListener;

/**
 * Razred koji modelira model liste objekata za crtanje.
 * 
 * @author Maja Radočaj
 *
 */
public class DrawingObjectListModel implements ListModel<GeometricalObject>, DrawingModelListener {

	/**
	 * Model za crtanje.
	 */
	private DrawingModel model;
	/**
	 * Slušači liste.
	 */
	private List<ListDataListener> listeners;
	
	/**
	 * Javni konstruktor.
	 * 
	 * @param model model
	 */
	public DrawingObjectListModel(DrawingModel model) {
		this.model = model;
		this.listeners = new ArrayList<>();
		model.addDrawingModelListener(this);
	}

	@Override
	public int getSize() {
		return model.getSize();
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return model.getObject(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(Objects.requireNonNull(l));
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		listeners.forEach(l -> l.intervalAdded(new ListDataEvent(this
				, ListDataEvent.INTERVAL_ADDED
				, index0, index1)));
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		listeners.forEach(l -> l.intervalAdded(new ListDataEvent(this
				, ListDataEvent.INTERVAL_REMOVED
				, index0, index1)));
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		listeners.forEach(l -> l.intervalAdded(new ListDataEvent(this
				, ListDataEvent.CONTENTS_CHANGED
				, index0, index1)));
	}

}
