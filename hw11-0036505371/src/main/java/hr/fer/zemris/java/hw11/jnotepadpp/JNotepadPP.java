package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.*;
import java.awt.event.*;
import java.nio.file.*;
import java.text.Collator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

import hr.fer.zemris.java.hw11.jnotepadpp.local.*;
import hr.fer.zemris.java.hw11.jnotepadpp.models.*;

/**
 * Aplikacija za uređivanje teksta.
 * <p>
 * Uređivač teksta JNotepad++ posjeduje sve osnovne značajke za uređivanje
 * teksta poput stvaranja novih dokumenata, njihovo uređivanje i spremanje.
 * <p>
 * Ova verzija JNotepad++ podržava tri jezika - hrvatski, engleski i njemački.
 * 
 * @author Maja Radočaj
 *
 */
public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;
	/**
	 * Adresa trenutno otvorenog dokumenta.
	 */
	private Path openedFilePath;
	/**
	 * Model koji podržava otvaranje i rad sa više dokumenata.
	 */
	private DefaultMultipleDocumentModel model;
	/**
	 * Međuspremnik pri kopiranju, izrezivanju i lijepljenju teksta.
	 */
	private String clipboard = "";
	/**
	 * Zastavica koja označava je li tražen izlazak iz aplikacije.
	 */
	private boolean stopRequested;
	/**
	 * Panel u koji spremamo TabbedPane.
	 */
	private JPanel panel;
	/**
	 * Labela koja prikazuje trenutnu duljinu dokumenta.
	 */
	private LJLengthLabel lengthLabel;
	/**
	 * Labela koja prikazuje trenutne informacije o dokumentu.
	 */
	private LJInfoLabel infoLabel;
	/**
	 * Statusna traka.
	 */
	private JPanel statusbar;
	/**
	 * Tekstualni prikaz trenutnog dokumenta.
	 */
	private JTextArea textArea;
	/**
	 * Slušač za caret.
	 */
	private ChangeListener caretListener;
	/**
	 * Objekt za lokalizaciju.
	 */
	private FormLocalizationProvider flp = new FormLocalizationProvider(
			LocalizationProvider.getInstance(), this);

	/**
	 * Javni konstruktor.
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(900, 700);
		initGUI();

		setLocationRelativeTo(null); // stavljanje prozora u centar
	}

	/**
	 * Pomoćna metoda koja inicijalizira grafičko sučelje.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		model = new DefaultMultipleDocumentModel();
		model.createNewDocument();
		setTitle("(unnamed) - JNotepad++");
		model.addMultipleDocumentListener(new MultipleDocumentListener() {

			@Override
			public void documentRemoved(SingleDocumentModel model) {
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, 
					SingleDocumentModel currentModel) {
				
				String title = currentModel.getFilePath() == null ? 
						flp.getString("unnamed") + " - JNotepad++"
						: currentModel.getFilePath() + " - JNotepad++";
				setTitle(title);
				setStats();
				previousModel.getTextComponent().getCaret().removeChangeListener(caretListener);
				textArea = currentModel.getTextComponent();
				addCaretListener(textArea);
			}
		});

		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(model, BorderLayout.CENTER);
		cp.add(panel, BorderLayout.CENTER);

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				if(!closeWindows())
					return;
				dispose();
			}
		});

		configureActions();
		createMenus();
		createToolbar();
		createStatusbar();
	}

	/**
	 * Pomoćna metoda koja konfigurira akcije.
	 */
	private void configureActions() {
		newDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		newDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);

		openDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);

		saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

		saveAsDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
		saveAsDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);

		closeDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		closeDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);

		documentInfo.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		documentInfo.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);

		cutSelectedPart.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutSelectedPart.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		cutSelectedPart.setEnabled(false);

		copySelectedPart.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copySelectedPart.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copySelectedPart.setEnabled(false);

		pasteSelectedPart.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteSelectedPart.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);

		invertCase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control K"));
		invertCase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_K);
		invertCase.setEnabled(false);

		toUpperCase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		toUpperCase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		toUpperCase.setEnabled(false);

		toLowerCase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		toLowerCase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		toLowerCase.setEnabled(false);

		ascending.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt A"));
		ascending.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		ascending.setEnabled(false);

		descending.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt D"));
		descending.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		descending.setEnabled(false);

		unique.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt U"));
		unique.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		unique.setEnabled(false);

		exitApplication.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		exitApplication.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);

		textArea = model.getCurrentDocument().getTextComponent();
		addCaretListener(textArea);
	}

	/**
	 * Pomoćna metoda za stvaranje izborničke trake i izbornika.
	 */
	private void createMenus() {
		JMenuBar mb = new JMenuBar();

		JMenu file = new LJMenu("file", flp);
		mb.add(file);
		file.add(new JMenuItem(newDocument));
		file.add(new JMenuItem(openDocument));
		file.add(new JMenuItem(saveDocument));
		file.add(new JMenuItem(saveAsDocument));
		file.add(new JMenuItem(closeDocument));
		file.add(new JMenuItem(documentInfo));
		file.addSeparator();
		file.add(new JMenuItem(exitApplication));

		JMenu edit = new LJMenu("edit", flp);
		mb.add(edit);
		edit.add(new JMenuItem(cutSelectedPart));
		edit.add(new JMenuItem(copySelectedPart));
		edit.add(new JMenuItem(pasteSelectedPart));

		JMenu languages = new LJMenu("languages", flp);
		mb.add(languages);

		JMenuItem hr = new JMenuItem("hr");
		hr.addActionListener(e -> {
			LocalizationProvider.getInstance().setLanguage("hr");
			if(model.getCurrentDocument().getFilePath() == null) {
				setTitle(flp.getString("unnamed") + " - JNotepad++");
			}
		});
		languages.add(hr);
		JMenuItem en = new JMenuItem("en");
		en.addActionListener(e -> {
			LocalizationProvider.getInstance().setLanguage("en");
			if(model.getCurrentDocument().getFilePath() == null) {
				setTitle(flp.getString("unnamed") + " - JNotepad++");
			}
		});
		languages.add(en);
		JMenuItem de = new JMenuItem("de");
		de.addActionListener(e -> {
			LocalizationProvider.getInstance().setLanguage("de");
			if(model.getCurrentDocument().getFilePath() == null) {
				setTitle(flp.getString("unnamed") + " - JNotepad++");
			}
		});
		languages.add(de);

		JMenu tools = new LJMenu("tools", flp);
		mb.add(tools);
		JMenu changeCase = new LJMenu("change_case", flp);
		tools.add(changeCase);
		changeCase.add(new JMenuItem(invertCase));
		changeCase.add(new JMenuItem(toUpperCase));
		changeCase.add(new JMenuItem(toLowerCase));

		JMenu sort = new LJMenu("sort", flp);
		tools.add(sort);
		sort.add(new JMenuItem(ascending));
		sort.add(new JMenuItem(descending));
		tools.addSeparator();
		tools.add(new JMenuItem(unique));

		setJMenuBar(mb);

	}

	/**
	 * Pomoćna metoda za stvaranje alatne trake.
	 */
	private void createToolbar() {
		JToolBar tb = new JToolBar();
		tb.setFloatable(true);

		tb.add(new JButton(newDocument));
		tb.add(new JButton(openDocument));
		tb.add(new JButton(saveDocument));
		tb.add(new JButton(saveAsDocument));
		tb.add(new JButton(closeDocument));
		tb.add(new JButton(documentInfo));
		tb.add(new JButton(exitApplication));
		tb.addSeparator();
		tb.add(new JButton(cutSelectedPart));
		tb.add(new JButton(copySelectedPart));
		tb.add(new JButton(pasteSelectedPart));

		// dodajem u panel zato da toolbar uvijek bude floatable
		panel.add(tb, BorderLayout.PAGE_START);
	}

	/**
	 * Pomoćna metoda za stvaranje statusne trake.
	 */
	private void createStatusbar() {
		statusbar = new JPanel();
		statusbar.setLayout(new GridLayout(1, 4, 0, 0));
		statusbar.setBorder(BorderFactory.createEtchedBorder());

		lengthLabel = new LJLengthLabel("length", flp);
		lengthLabel.setBorder(BorderFactory.createEtchedBorder());

		infoLabel = new LJInfoLabel("ln", "col", "sel", flp);
		infoLabel.setBorder(BorderFactory.createEtchedBorder());

		JLabel emptyLabel = new JLabel("");
		emptyLabel.setBorder(BorderFactory.createEtchedBorder());

		JLabel timeLabel = new JLabel();
		timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		timeLabel.setBorder(BorderFactory.createEtchedBorder());

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		Thread t = new Thread(() -> {
			while(true) {
				try {
					Thread.sleep(500);
				} catch(Exception ex) {

				}
				if(stopRequested)
					break;
				SwingUtilities.invokeLater(() -> {
					timeLabel.setText(formatter.format(LocalDateTime.now()));
				});
			}
		});
		t.setDaemon(true);
		t.start();

		statusbar.add(lengthLabel);
		statusbar.add(infoLabel);
		statusbar.add(emptyLabel);
		statusbar.add(timeLabel);
		this.add(statusbar, BorderLayout.PAGE_END);

		setStats();
	}

	/**
	 * Akcija za stvaranje novog dokumenta.
	 */
	private final Action newDocument = new LocalizableAction("new", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			model.createNewDocument();
		}
	};

	/**
	 * Akcija za otvaranje postojećeg dokumenta.
	 */
	private final Action openDocument = new LocalizableAction("open", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Open file");
			if(jfc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}

			openedFilePath = jfc.getSelectedFile().toPath();

			try {
				model.loadDocument(openedFilePath);
			} catch(RuntimeException ex) {
				JOptionPane.showMessageDialog(JNotepadPP.this, 
						flp.getString("error_message"), 
						flp.getString("error"),
						JOptionPane.ERROR_MESSAGE);
			}
		}
	};

	/**
	 * Akcija za spremanje dokumenta.
	 */
	private final Action saveDocument = new LocalizableAction("save", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Path path = model.getCurrentDocument().getFilePath();

			if(path == null) {
				path = getPath();
				if(path == null || !overwrite(path)) {
					return;
				}
				;
			}
			save(path);
		}
	};

	/**
	 * Akcija za spremanje dokumenta kao novog dokumenta.
	 */
	private final Action saveAsDocument = new LocalizableAction("saveAs", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Path path = getPath();

			if(path == null || !overwrite(path)) {
				return;
			}
			save(path);
		}
	};

	/**
	 * Akcija za zatvaranje dokumenta.
	 */
	private final Action closeDocument = new LocalizableAction("close", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(model.getCurrentDocument().isModified() 
					&& !saveBeforeClosing(model.getCurrentDocument())) {
				
				return;
			}
			model.closeDocument(model.getCurrentDocument());
		}
	};

	/**
	 * Akcija za obrtanje veličine slova.
	 */
	private final Action invertCase = new LocalizableAction("invert_case", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = model.getCurrentDocument().getTextComponent().getDocument();
			changeText(doc, "invert case");
		}
	};

	/**
	 * Akcija za postavljanje velikih slova u odabranom dijelu.
	 */
	private final Action toUpperCase = new LocalizableAction("to_uppercase", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = model.getCurrentDocument().getTextComponent().getDocument();
			changeText(doc, "to uppercase");
		}
	};

	/**
	 * Akcija za postavljanje malih slova u odabranom dijelu.
	 */
	private final Action toLowerCase = new LocalizableAction("to_lowercase", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = model.getCurrentDocument().getTextComponent().getDocument();
			changeText(doc, "to lowercase");
		}
	};

	/**
	 * Akcija za izrezivanje odabranog dijela teksta.
	 */
	private final Action cutSelectedPart = new LocalizableAction("cut", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = model.getCurrentDocument().getTextComponent().getDocument();

			Caret caret = model.getCurrentDocument().getTextComponent().getCaret();
			int start = Math.min(caret.getDot(), caret.getMark());
			int len = Math.abs(caret.getDot() - caret.getMark());

			if(len == 0)
				return;

			try {
				clipboard = doc.getText(start, len);
				doc.remove(start, len);
			} catch(BadLocationException ignorable) {

			}
		}
	};

	/**
	 * Akcija za kopiranje odabranog dijela teksta.
	 */
	private final Action copySelectedPart = new LocalizableAction("copy", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = model.getCurrentDocument().getTextComponent().getDocument();

			Caret caret = model.getCurrentDocument().getTextComponent().getCaret();
			int start = Math.min(caret.getDot(), caret.getMark());
			int len = Math.abs(caret.getDot() - caret.getMark());

			if(len == 0)
				return;

			try {
				clipboard = doc.getText(start, len);
			} catch(BadLocationException ignorable) {

			}
		}
	};

	/**
	 * Akcija za lijepljenje teksta.
	 */
	private final Action pasteSelectedPart = new LocalizableAction("paste", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = model.getCurrentDocument().getTextComponent().getDocument();

			Caret caret = model.getCurrentDocument().getTextComponent().getCaret();
			int start = Math.min(caret.getDot(), caret.getMark());
			int len = Math.abs(caret.getDot() - caret.getMark());

			try {
				doc.remove(start, len);
				doc.insertString(start, clipboard, null);
			} catch(BadLocationException ignorable) {

			}
		}
	};

	/**
	 * Akcija koja vraća korisniku informacije o trenutnom dokumentu.
	 */
	private final Action documentInfo = new LocalizableAction("info", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea textArea = model.getCurrentDocument().getTextComponent();
			int length = textArea.getText().length();
			String character = length == 1 ? flp.getString("character") : flp.getString("characters");
			int nonBlanks = textArea.getText().replaceAll("\\s+", "").length();
			String nonBlank = nonBlanks == 1 ? flp.getString("character") : flp.getString("characters");
			int lines = textArea.getLineCount();
			String line = lines == 1 ? flp.getString("line") : flp.getString("lines");

			JOptionPane.showMessageDialog(JNotepadPP.this,
					String.format("%s %d %s, %d %s %s %s %d %s.", 
							flp.getString("doc_has"), length, character, nonBlanks, 
							flp.getString("non_blank"), nonBlank, 
							flp.getString("and"), lines, line),
					flp.getString("statistical_info"), 
					JOptionPane.INFORMATION_MESSAGE);
		}
	};

	/**
	 * Akcija koja sortira odabrani dio teksta po uzlaznom poretku.
	 */
	private final Action ascending = new LocalizableAction("ascending", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			getTextToSort("ascending");
		}

	};

	/**
	 * Akcija koja sortira odabrani dio teksta po silaznom poretku.
	 */
	private final Action descending = new LocalizableAction("descending", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			getTextToSort("descending");
		}

	};

	/**
	 * Akcija koja iz odabranog dijela teksta uklanja duple linije.
	 */
	private final Action unique = new LocalizableAction("unique", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea textComponent = model.getCurrentDocument().getTextComponent();
			Document doc = textComponent.getDocument();
			Element root = doc.getDefaultRootElement();

			Caret caret = textComponent.getCaret();
			int start = Math.min(caret.getDot(), caret.getMark());
			int len = Math.abs(caret.getDot() - caret.getMark());
			int firstLine = root.getElementIndex(start);
			int lastLine = root.getElementIndex(start + len);

			try {
				start = textComponent.getLineStartOffset(firstLine);
				len = textComponent.getLineEndOffset(lastLine) - start;
				String text = doc.getText(start, len);
				doc.remove(start, len);
				doc.insertString(start, removeDoubles(text), null);
			} catch(BadLocationException ignorable) {

			}
		}

	};

	/**
	 * Akcija koja izlazi iz aplikacije.
	 */
	private final Action exitApplication = new LocalizableAction("exit", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!closeWindows())
				return;
			dispose();
		}
	};

	/**
	 * Pomoćna metoda koja provjerava smije li se dokument prepisati.
	 * 
	 * @param path putanja do dokumenta
	 * @return <code>true</code> ako smije, <code>false</code> ako ne
	 */
	private boolean overwrite(Path path) {
		if(Files.exists(path)) {
			String[] options = { flp.getString("yes"), flp.getString("no") };
			int result = JOptionPane.showOptionDialog(null,
					flp.getString("file") + " \"" + path.toString() + "\" " 
							+ flp.getString("already_exists") + ". "
							+ flp.getString("overwrite") + "?",
					flp.getString("overwrite"), 
					JOptionPane.YES_NO_OPTION, 
					JOptionPane.QUESTION_MESSAGE, 
					null, 
					options,
					options[1]);
			if(result == 1) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Pomoćna metoda provjerava smije li zatvoriti prozore.
	 * 
	 * @return <code>true</code> ako smije, <code>false</code> ako ne
	 */
	private boolean closeWindows() {
		Iterator<SingleDocumentModel> it = model.iterator();
		int i = 0;

		while(it.hasNext()) {
			SingleDocumentModel current = it.next();
			model.setSelectedIndex(i);
			i++;

			if(current.isModified()) {
				if(!saveBeforeClosing(current)) {
					return false;
				}
			}
		}

		stopRequested = true;
		return true;
	}

	/**
	 * Pomoćna metoda koja pita treba li spremiti neki dokument prije zatvaranja.
	 * 
	 * @param current trenutni dokument
	 * @return <code>true</code> ako treba ili ne treba, <code>false</code> ako se
	 *         otkaže zahtjev
	 */
	private boolean saveBeforeClosing(SingleDocumentModel current) {
		String filename = current.getFilePath() == null ? "(unnamed)" : 
			current.getFilePath().getFileName().toString();
		String[] options = { flp.getString("yes"), flp.getString("no"), flp.getString("cancel") };
		
		int result = JOptionPane.showOptionDialog(null, 
				flp.getString("save_file_question") + " \"" + filename + "\"?",
				flp.getString("save_file"), 
				JOptionPane.YES_NO_CANCEL_OPTION, 
				JOptionPane.QUESTION_MESSAGE, null,
				options, 
				options[2]);
		
		switch(result) {
			case 0: // yes
				saveDocument.actionPerformed(null);
				break;
			case 1: // no
				break;
			case 2: // cancel
				return false;
		}

		return true;
	}

	/**
	 * Pomoćna metoda koja vraća path koji korisnik odabere.
	 * 
	 * @return path
	 */
	private Path getPath() {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle(flp.getString("save_file"));
		
		if(jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		return jfc.getSelectedFile().toPath();
	}

	/**
	 * Pomoćna metoda koja sprema trenutni dokument na path dan argumentom.
	 * 
	 * @param path putanja gdje treba spremiti dokument
	 */
	private void save(Path path) {
		try {
			model.saveDocument(model.getCurrentDocument(), path);
		} catch(RuntimeException ex) {
			JOptionPane.showMessageDialog(JNotepadPP.this, 
					ex.getMessage(), 
					flp.getString("error"),
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Pomoćna metoda koja postavlja informacije na statusnoj traci.
	 */
	private void setStats() {
		JTextArea textArea = model.getCurrentDocument().getTextComponent();
		int length = textArea.getText().length();

		Caret caret = textArea.getCaret();
		int sel = Math.abs(caret.getDot() - caret.getMark());

		try {
			int caretPosition = textArea.getCaretPosition();
			int line = textArea.getLineOfOffset(caretPosition) + 1;
			int col = caretPosition - textArea.getLineStartOffset(line - 1);
			lengthLabel.labelChange(length);
			infoLabel.labelChange(line, col, sel);
		} catch(BadLocationException ex) {

		}
	}

	/**
	 * Pomoćna metoda koja na dani textArea postavlja slušača za caret.
	 * 
	 * @param textArea textArea gdje želimo postaviti slušača
	 */
	private void addCaretListener(JTextArea textArea) {
		caretListener = new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JTextArea textArea = model.getCurrentDocument().getTextComponent();
				boolean hasSelection = textArea.getCaret().getDot() != textArea.getCaret().getMark();

				cutSelectedPart.setEnabled(hasSelection); // onemogućujem ove akcije sve dok nema selekcije
				copySelectedPart.setEnabled(hasSelection);
				invertCase.setEnabled(hasSelection);
				toUpperCase.setEnabled(hasSelection);
				toLowerCase.setEnabled(hasSelection);
				ascending.setEnabled(hasSelection);
				descending.setEnabled(hasSelection);
				unique.setEnabled(hasSelection);
				setStats();
			}
		};

		textArea.getCaret().addChangeListener(caretListener);
	}

	/**
	 * Metoda koja vraća polje koje sadrži početak i dužinu odabira caretom.
	 * 
	 * @return početak i dužinu odabira
	 */
	private int[] startAndLen() {
		Caret caret = model.getCurrentDocument().getTextComponent().getCaret();
		int start = Math.min(caret.getDot(), caret.getMark());
		int len = Math.abs(caret.getDot() - caret.getMark());
		return new int[] { start, len };
	}

	/**
	 * Metoda koja mijenja tekst za akcije toUppercase, toLowercase i inverCase
	 * 
	 * @param doc    dokument
	 * @param action akcija za koju treba promijeniti tekst dokumenta
	 */
	private void changeText(Document doc, String action) {
		doc = model.getCurrentDocument().getTextComponent().getDocument();
		int[] startAndLen = startAndLen();

		if(startAndLen[1] == 0)
			return;

		try {
			String text = doc.getText(startAndLen[0], startAndLen[1]);
			switch(action) {
				case ("to uppercase"):
					text = text.toUpperCase();
					break;
				case ("to lowercase"):
					text = text.toLowerCase();
					break;
				case ("invert case"):
					text = toggleText(text);
			}
			doc.remove(startAndLen[0], startAndLen[1]);
			doc.insertString(startAndLen[0], text, null);
		} catch(BadLocationException ignorable) {

		}
	}

	/**
	 * Pomoćna metoda koja korigira tekst (velika slova idu u mala, mala u velika).
	 * 
	 * @param text tekst
	 * @return korigirani tekst
	 */
	private String toggleText(String text) {
		char[] chars = text.toCharArray();
		for(int i = 0; i < chars.length; i++) {
			char c = chars[i];
			
			if(Character.isUpperCase(c)) {
				chars[i] = Character.toLowerCase(c);
			} else if(Character.isLowerCase(c)) {
				chars[i] = Character.toUpperCase(c);
			}
		}

		return new String(chars);
	}

	/**
	 * Pomoćna metoda koja iz trenutnog dokumenta traži odabir i uzima linije koje
	 * treba sortirati.
	 * 
	 * @param key način sortiranja
	 */
	private void getTextToSort(String key) {
		JTextArea textComponent = model.getCurrentDocument().getTextComponent();
		Document doc = textComponent.getDocument();
		Element root = doc.getDefaultRootElement();

		Caret caret = textComponent.getCaret();
		int start = Math.min(caret.getDot(), caret.getMark());
		int len = Math.abs(caret.getDot() - caret.getMark());
		int firstLine = root.getElementIndex(start);
		int lastLine = root.getElementIndex(start + len);

		try {
			start = textComponent.getLineStartOffset(firstLine);
			len = textComponent.getLineEndOffset(lastLine) - start;
			
			String text = doc.getText(start, len);
			String sortedText = sort(text, key);
			
			doc.remove(start, len);
			doc.insertString(start, sortedText, null);
		} catch(BadLocationException ignorable) {

		}
	}

	/**
	 * Pomoćna metoda za sortiranje teksta.
	 * 
	 * @param text tekst
	 * @param key  način sortiranja
	 * @return sortirani tekst
	 */
	private String sort(String text, String key) {
		Locale locale = new Locale(flp.getCurrentLanguage());
		Collator collator = Collator.getInstance(locale);
		String[] lines = text.split("\\r?\\n");
		List<String> lineList = Arrays.asList(lines);
		lineList.sort(new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				if(key.equals("ascending")) {
					return collator.compare(o1, o2);
				} else {
					return collator.compare(o2, o1);
				}
			}
		});

		StringBuilder sb = new StringBuilder();
		lineList.forEach(l -> sb.append(l + "\n"));
		return sb.toString();
	}

	/**
	 * Metoda koja uklanja duple linije.
	 * 
	 * @param text tekst
	 * @return tekst bez duplih linija
	 */
	private String removeDoubles(String text) {
		String[] lines = text.split("\\r?\\n");
		List<String> lineList = Arrays.asList(lines);
		List<String> listNoDuplicates = new ArrayList<>();
		lineList.forEach(l -> {
			if(!listNoDuplicates.contains(l)) {
				listNoDuplicates.add(l);
			}
		});

		StringBuilder sb = new StringBuilder();
		listNoDuplicates.forEach(l -> sb.append(l + "\n"));
		return sb.toString();
	}

	/**
	 * Glavni program.
	 * 
	 * @param args argumenti naredbenog retka
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}
}