package hr.fer.zemris.java.hw11.jnotepadpp.models;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;

/**
 * Razred koji modelira defaultnu implementaciju {@link MultipleDocumentModel}.
 * <p>Dokumenti se prikazuju u tabovima koji se mogu otvoriti i zatvoriti.
 * Ima ih 0 ili više.
 * @author Maja Radočaj
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	private static final long serialVersionUID = 1L;
	/**
	 * Lista dokumenata.
	 */
	private List<SingleDocumentModel> documents;
	/**
	 * Trenutni dokument.
	 */
	private SingleDocumentModel currentDocument;
	/**
	 * Slušači.
	 */
	private List<MultipleDocumentListener> listeners;
	
	/**
	 * Javni konstruktor.
	 */
	public DefaultMultipleDocumentModel() {
		documents = new ArrayList<>();
		listeners = new ArrayList<>();
		
		this.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				SingleDocumentModel previousDocument = currentDocument;
				currentDocument = documents.get(getSelectedIndex());
				notifyListenersDocumentChanged(previousDocument, currentDocument);
			}
		});
	}
	
	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return new Iterator<SingleDocumentModel>() {

			private int index;
			
			@Override
			public boolean hasNext() {
				return index < documents.size(); 
			}

			@Override
			public SingleDocumentModel next() {
				return documents.get(index++);			
			}
		};
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		return addNewDocument(null, "");
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	/**
	 * {@inheritDoc}
	 * @throws RuntimeException ako dođe do greške pri čitanju dokumenta
	 */
	@Override
	public SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path);
		for(int i = 0; i < documents.size(); i++) {
			SingleDocumentModel document = documents.get(i);
			if(document.getFilePath() != null && document.getFilePath().equals(path)) {
				notifyListenersDocumentChanged(currentDocument, document);
				currentDocument = document;
				this.setSelectedIndex(i);
				return document;
			}
		}
		
		byte[] bytes;
		try {
			bytes = Files.readAllBytes(path);
		} catch (Exception ex) {
			throw new RuntimeException("Couldn't read file.");
		}
		
		return addNewDocument(path, new String(bytes, StandardCharsets.UTF_8));
	}

	/**
	 * {@inheritDoc}
	 * @throws RuntimeException ako dođe do greške pri čitanju dokumenta
	 */
	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		for(SingleDocumentModel document : documents) {
			if(document.getFilePath() != null && newPath != null 
					&& document.getFilePath().equals(newPath) && !document.equals(model)) {
				throw new RuntimeException("Specified file is already opened.");
			}
		}
		
		Path path = newPath == null ? model.getFilePath() : newPath;
		
		try{
			Files.write(path, model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8));
			model.setFilePath(path);
			model.setModified(false);
			setIconAt(documents.indexOf(model), createImageIcon("icons/greenDisk.png"));
			notifyListenersDocumentChanged(currentDocument, model);
			currentDocument = model;
		} catch(IOException ex) {
			throw new RuntimeException("Error while saving file.");
		}
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		if(!documents.contains(model)) return;
		if(documents.size() == 1) {
			createNewDocument();
		}
		int index = documents.indexOf(model) - 1;
		documents.remove(model);
		
		this.removeTabAt(index + 1);
		index = index < 0 ? 0 : index;
		this.setSelectedIndex(index);
		notifyListenersDocumentChanged(currentDocument, documents.get(index));
		currentDocument = documents.get(index);

		notifyListenersDocumentRemoved(model);
	}

	/**
	 * {@inheritDoc}
	 * @throws NullPointerException ako se pokuša dodati <code>null</code>
	 */
	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(Objects.requireNonNull(l));
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return documents.get(index);
	}

	/**
	 * Pomoćna metoda koja obaviještava slušače o promjeni trenutnog dokumenta.
	 * 
	 * @param previousModel prijašnji dokument
	 * @param currentModel trenutni dokument
	 */
	private void notifyListenersDocumentChanged(SingleDocumentModel previousModel,
			SingleDocumentModel currentModel) {
		
		for(MultipleDocumentListener listener : listeners) {
			listener.currentDocumentChanged(previousModel, currentModel);
		}
	}
	
	/**
	 * Metoda koja obaviještava slušače o dodavanju novog dokumenta.
	 * 
	 * @param model dodani dokument
	 */
	private void notifyListenersDocumentAdded(SingleDocumentModel model) {
		for(MultipleDocumentListener listener : listeners) {
			listener.documentAdded(model);
		}
	}
	
	/**
	 * Metoda koja obaviještava slušače o uklanjanju dokumenta.
	 * 
	 * @param model dokument koji se uklanja
	 */
	private void notifyListenersDocumentRemoved(SingleDocumentModel model) {
		for(MultipleDocumentListener listener : listeners) {
			listener.documentRemoved(model);
		}
	}

	/**
	 * Pomoćna metoda koja dodaje novi dokument sa putanjom <code>path</code> 
	 * i sadržajem <code>text</code>.
	 * 
	 * @param path putanja do datoteke
	 * @param text tekst datoteke
	 * @return novi dokument
	 */
	private SingleDocumentModel addNewDocument(Path path, String text) {
		SingleDocumentModel document = new DefaultSingleDocumentModel(path, text);
		documents.add(document);
		notifyListenersDocumentAdded(document);
		notifyListenersDocumentChanged(currentDocument, document);
		currentDocument = document;
		document.addSingleDocumentListener(new SingleDocumentListener() {
			
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				int index = documents.indexOf(model);
				if(model.isModified()) {
					DefaultMultipleDocumentModel.this.setIconAt(index, 
							createImageIcon("icons/redDisk.png"));
				} else {
					DefaultMultipleDocumentModel.this.setIconAt(index, 
							createImageIcon("icons/greenDisk.png"));
				}
			}
			
			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				int index = documents.indexOf(model);
				DefaultMultipleDocumentModel.this.setTitleAt(index, 
						model.getFilePath().getFileName().toString());
				DefaultMultipleDocumentModel.this.setToolTipTextAt(index,
						model.getFilePath().toString());
			}
		});
		
		createNewTab(path, document.getTextComponent());
		this.setSelectedIndex(documents.size() - 1);
		return document;
	}
	
	/**
	 * Metoda koja stvara novi tab.
	 * 
	 * @param path putanja datoteke
	 * @param textComponent tekstualna komponenta novog dokumenta
	 */
	private void createNewTab(Path path, JTextArea textComponent) {
		JScrollPane scrollPane = new JScrollPane(textComponent);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(scrollPane, BorderLayout.CENTER);
		ImageIcon icon = createImageIcon("icons/greenDisk.png");
		if(path != null) {
			this.addTab(path.getFileName().toString(), icon, panel, path.toString());
		} else {
			this.addTab("(unnamed)", icon, panel, "(unnamed)");
		}
	}
	
	/**
	 * Metoda za stvaranje ikone.
	 * 
	 * @param path putanja do slike
	 * @return nova ikona
	 * @throws RuntimeException ako dođe do greške pri učitavanju ikone
	 */
	private ImageIcon createImageIcon(String path) {
		InputStream is = JNotepadPP.class.getResourceAsStream(path);
		if(is == null) {
			throw new RuntimeException("Cannot get picture file.");
		}
		
		byte[] bytes;
		try{
			bytes = is.readAllBytes();
			is.close();
			ImageIcon icon = new ImageIcon(bytes);
			Image image = icon.getImage();
			Image newImage = image.getScaledInstance(13, 13, Image.SCALE_SMOOTH);
			return new ImageIcon(newImage);
		} catch(IOException ex) {
			throw new RuntimeException("Error while loading icon.");
		}
	}
	
}
