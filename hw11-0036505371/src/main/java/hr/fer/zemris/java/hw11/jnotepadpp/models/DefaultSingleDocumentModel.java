package hr.fer.zemris.java.hw11.jnotepadpp.models;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Razred koji predstavlja defaultnu implementaciju jednog dokumenta.
 * 
 * @author Maja Radočaj
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

	/**
	 * Putanja do dokumenta.
	 */
	private Path filePath;
	/**
	 * Swing komponenta za uređivanje teksta dokumenta.
	 */
	private JTextArea textComponent;
	/**
	 * Zastavica koja govori je li dokument modificiran.
	 */
	private boolean modified;
	/**
	 * Slušači dokumenta.
	 */
	private List<SingleDocumentListener> listeners;
	
	/**
	 * Javni konstruktor.
	 * 
	 * @param filePath putanja do dokumenta
	 * @param text tekst dokumenta
	 * @throws NullPointerException ako je tekst <code>null</code>
	 */
	public DefaultSingleDocumentModel(Path filePath, String text) {
		this.filePath = filePath;
		this.textComponent = new JTextArea(Objects.requireNonNull(text));
		this.listeners = new ArrayList<>();
		
		textComponent.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				setModified(true);
			}
		});
	}
	
	@Override
	public JTextArea getTextComponent() {
		return textComponent;
	}

	@Override
	public Path getFilePath() {
		return filePath;
	}

	/**
	 * {@inheritDoc}
	 * @throws NullPointerException ako se pokuša postaviti <code>null</code> kao path
	 */
	@Override
	public void setFilePath(Path path) {
		this.filePath = Objects.requireNonNull(path);
		for(SingleDocumentListener listener : listeners) {
			listener.documentFilePathUpdated(this);
		}
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		notifyListeners();
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		if(l == null) return;
		listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}

	/**
	 * Pomoćna metoda koja obaviještava slušače da je dokument modificiran.
	 */
	private void notifyListeners() {
		for(SingleDocumentListener listener : listeners) {
			listener.documentModifyStatusUpdated(this);
		}
	}
}
