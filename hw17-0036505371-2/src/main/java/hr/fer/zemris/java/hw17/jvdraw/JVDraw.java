package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw17.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.components.JColorLabel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModelImpl;
import hr.fer.zemris.java.hw17.jvdraw.drawing.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObjectSaver;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;
import hr.fer.zemris.java.hw17.jvdraw.objects.DrawingObjectListModel;
import hr.fer.zemris.java.hw17.jvdraw.tools.CircleTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.FilledCircleTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.LineTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.ToolSupplier;

/**
 * Desktop aplikacija za crtanje objekata poput linije, kružnice i ispunjene kružnice.
 * <p>Korisnik može uređivati objekte, spremati slike i exportati ih u različitim formatima.
 * 
 * @author Maja Radočaj
 *
 */
public class JVDraw extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Veličina toggle buttona.
	 */
	private static final int size = 18;
	/**
	 * Riječ za spremanje linija.
	 */
	private static final String LINE = "LINE";
	/**
	 * Riječ za spremanje krugova.
	 */
	private static final String CIRCLE = "CIRCLE";
	/**
	 * Riječ za spremanje ispunjenih krugova.
	 */
	private static final String FCIRCLE = "FCIRCLE";
	/**
	 * Supplier za trenutni alat.
	 */
	private ToolSupplier supplier = new ToolSupplier();
	/**
	 * Komponenta za boju linija.
	 */
	private JColorArea fgc = new JColorArea(Color.BLUE);
	/**
	 * Komponenta za boju ispune.
	 */
	private JColorArea bgc = new JColorArea(Color.RED);
	/**
	 * Toolbar.
	 */
	private JToolBar tb = new JToolBar();
	/**
	 * Model za crtanje.
	 */
	private DrawingModelImpl model = new DrawingModelImpl();
	/**
	 * Komponenta za crtanje.
	 */
	private JDrawingCanvas canvas;
	/**
	 * Alat za linije.
	 */
	private LineTool lineTool;
	/**
	 * Alat za kružnice.
	 */
	private CircleTool circleTool;
	/**
	 * Alat za krugove.
	 */
	private FilledCircleTool filledCircleTool;
	/**
	 * Lista objekata.
	 */
	private JList<GeometricalObject> list;
	/**
	 * Putanja na kojoj je pohranjen trenutni crtež.
	 */
	private Path path;
	
	/**
	 * Javni konstruktor.
	 */
	public JVDraw() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);			
		setSize(900, 700);
		
		canvas = new JDrawingCanvas(supplier, model);
		lineTool = new LineTool(model, fgc, canvas);
		circleTool = new CircleTool(model, fgc, canvas);
		filledCircleTool = new FilledCircleTool(model, fgc, bgc, canvas);
		supplier.setTool(lineTool);
		
		try {
			initGUI();
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		
		setLocationRelativeTo(null);	//stavljanje prozora u centar
	}

	/**
	 * Metoda za inicijalizaciju grafičkog sučelja.
	 * 
	 * @throws IOException u slučaju greške
	 */
	private void initGUI() throws IOException {
		java.awt.Container cp =  this.getContentPane();
		cp.validate();
		cp.setLayout(new BorderLayout());
		setTitle("JVDraw");
		
		JColorLabel label = new JColorLabel(fgc, bgc);
		
		this.addComponentListener(new ComponentAdapter() {
			
			@Override
			public void componentResized(ComponentEvent e) {
				canvas.setSize((int) (getWidth() * 0.75) - 10, canvas.getHeight());
				list.setFixedCellWidth((int) (getWidth() * 0.25) - 10);
			}
		});
		
		configureActions();
		createMenus();
		createToolbar();
		createList();
		
		cp.add(tb, BorderLayout.PAGE_START);
		cp.add(label, BorderLayout.PAGE_END);
		cp.add(canvas, BorderLayout.CENTER);
		
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				exitAction.actionPerformed(null);
			}
		});
	}
	
	/**
	 * Pomoćna metoda za stvaranje menija.
	 */
	private void createMenus() {
		JMenuBar mb = new JMenuBar();
		
		JMenu file = new JMenu("File");
		mb.add(file);
		
		file.add(saveAction);
		file.add(saveAsAction);
		file.add(openAction);
		file.add(exportAction);
		file.addSeparator();
		file.add(exitAction);
		
		setJMenuBar(mb);
	}
	
	/**
	 * Pomoćna metoda za stvaranje toolbara.
	 */
	private void createToolbar() {
		tb.add(fgc);
		tb.addSeparator(new Dimension(5, 15));
		tb.add(bgc);
		
		ButtonGroup buttons = new ButtonGroup();
		JToggleButton line = new JToggleButton(lineAction);
		line.setIcon(createImageIcon("icons/line.png"));
		line.setSelected(true);
		
		JToggleButton circle = new JToggleButton(circleAction);
		circle.setIcon(createImageIcon("icons/circle.png"));
		
		JToggleButton filledCircle = new JToggleButton(filledCircleAction);
		filledCircle.setIcon(createImageIcon("icons/filledCircle.png"));
		
		buttons.add(line);
		buttons.add(circle);
		buttons.add(filledCircle);
		
		tb.addSeparator();
		tb.add(line);
		tb.add(circle);
		tb.add(filledCircle);
	}
	
	/**
	 * Pomoćna metoda za konfiguriranje akcija.
	 */
	private void configureActions() {
		saveAction.putValue(Action.NAME, "Save");
		saveAction.putValue(Action.SHORT_DESCRIPTION, "Save current painting.");
		saveAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		
		saveAsAction.putValue(Action.NAME, "Save As");
		saveAsAction.putValue(Action.SHORT_DESCRIPTION, "Save current painting as another file.");
		saveAsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
		saveAsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		
		openAction.putValue(Action.NAME, "Open");
		openAction.putValue(Action.SHORT_DESCRIPTION, "Open .jsv file from disk.");
		openAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		
		exportAction.putValue(Action.NAME, "Export");
		exportAction.putValue(Action.SHORT_DESCRIPTION, "Export image in JPG, PNG or GIF format.");
		exportAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		exportAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		
		lineAction.putValue(Action.SHORT_DESCRIPTION, "Tool for drawing lines.");
		lineAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		lineAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		
		circleAction.putValue(Action.SHORT_DESCRIPTION, "Tool for drawing circles.");
		circleAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		circleAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		
		filledCircleAction.putValue(Action.SHORT_DESCRIPTION, "Tool for drawing filled circles.");
		filledCircleAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F"));
		filledCircleAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_F);
		
		exitAction.putValue(Action.NAME, "Exit");
		exitAction.putValue(Action.SHORT_DESCRIPTION, "Exit JVDraw application.");
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
	}

	/**
	 * Pomoćna metoda za stvaranje liste objekata.
	 */
	private void createList() {
		DrawingObjectListModel listModel = new DrawingObjectListModel(model);
		
		list = new JList<>(listModel);
		JScrollPane scrollPane = new JScrollPane(list);
		list.setVisible(true);
		list.setBackground(new Color(245, 245, 239));
		list.addMouseListener(new MouseAdapter() {
			
			@Override
			@SuppressWarnings("unchecked")
			public void mouseClicked(MouseEvent e) {
				JList<GeometricalObject> list = (JList<GeometricalObject>) e.getSource();
				if(e.getClickCount() == 2) {
					int index = list.locationToIndex(e.getPoint());
					GeometricalObject clicked = model.getObject(index);
					GeometricalObjectEditor editor = clicked.createGeometricalObjectEditor();

					if(JOptionPane.showConfirmDialog(JVDraw.this, editor, "Edit object",
							JOptionPane.OK_CANCEL_OPTION) == 0) {

						try {
							editor.checkEditing();	
							editor.acceptEditing();
							model.setModifiedTrue();
							canvas.repaint();
						} catch(Exception ex) {
							JOptionPane.showMessageDialog(JVDraw.this, ex.getMessage(), "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		
		list.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				int index = list.getSelectedIndex();
				if(index <= -1 || index >= model.getSize()) return;
				
				GeometricalObject object = model.getObject(list.getSelectedIndex());
				
				int keyCode = e.getKeyCode();
				
				if(keyCode == KeyEvent.VK_ADD || keyCode == KeyEvent.VK_PLUS) {
					model.changeOrder(object, 1);
					list.setSelectedIndex(index + 1);
				} else if(keyCode == KeyEvent.VK_SUBTRACT || keyCode == KeyEvent.VK_MINUS) {
					model.changeOrder(object, -1);
					list.setSelectedIndex(index - 1);
				} else if(keyCode == KeyEvent.VK_DELETE) {
					model.remove(object);
				} else {
					return;
				}
			}
		});
		
		this.getContentPane().add(scrollPane, BorderLayout.EAST);
	}
	
	/**
	 * Akcija za dodavanje linije kao trenutnog alata.
	 */
	private final Action lineAction = new AbstractAction() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			supplier.setTool(lineTool);
		}
	}; 
	
	/**
	 * Akcija za dodavanje kružnice kao trenutnog alata.
	 */
	private final Action circleAction = new AbstractAction() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			supplier.setTool(circleTool);
		}
	};
	
	/**
	 * Akcija za dodavanje kruga kao trenutnog alata.
	 */
	private final Action filledCircleAction = new AbstractAction() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			supplier.setTool(filledCircleTool);
		}
	};
	
	/**
	 * Akcija za spremanje crteža.
	 */
	private final Action saveAction = new AbstractAction() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(path == null) {
				saveAsAction.actionPerformed(e);
				return;
			}
			
			try {
				save();
			} catch(IOException ex) {
				JOptionPane.showMessageDialog(JVDraw.this, ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			
		}
	};
	
	/**
	 * Akcija za spremanje crteža pod drugim imenom.
	 */
	private final Action saveAsAction = new AbstractAction() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			path = getPath("Save file");
			if(path == null) return;
			
			if(!path.toString().substring(path.toString().length() - 4).equals(".jvd")) {
				JOptionPane.showMessageDialog(JVDraw.this, "File extension must be \".jvd\".", "Error",
						JOptionPane.ERROR_MESSAGE);
				path = null;
				return;
			}
			
			try {
				save();
			} catch(IOException ex) {
				JOptionPane.showMessageDialog(JVDraw.this, ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	};
	
	/**
	 * Akcija za učitavanje <code>.jvd</code> dokumenta sa diska i prikaz crteža.
	 */
	private final Action openAction = new AbstractAction() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(model.isModified()) {
				if(!closeImage()) {
					return;
				}
			}
			
			path = getPath("Open file");
			
			if(!path.toString().substring(path.toString().length() - 3).equals("jvd")) {
				JOptionPane.showMessageDialog(JVDraw.this, "File extension must be \".jvd\".", "Error",
						JOptionPane.ERROR_MESSAGE);
				path = null;
				return;
			}
			
			try {
				List<String> lines = Files.readAllLines(path);
				List<GeometricalObject> objects = new ArrayList<>();
				
				for(String line : lines) {
					String[] parts = line.split(" ");
					
					if(parts[0].equals(LINE)) {
						if(parts.length != 8) {
							throw new RuntimeException();
						}
						objects.add(new Line(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), 
								Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), 
								new Color(Integer.parseInt(parts[5]), Integer.parseInt(parts[6]), 
										Integer.parseInt(parts[7]))));
						
					} else if(parts[0].equals(CIRCLE)) {
						if(parts.length != 7) {
							throw new RuntimeException();
						}
						objects.add(new Circle(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), 
								Integer.parseInt(parts[3]), new Color(Integer.parseInt(parts[4]),
										Integer.parseInt(parts[5]), Integer.parseInt(parts[6]))));
						
					} else if(parts[0].equals(FCIRCLE)) {
						if(parts.length != 10) {
							throw new RuntimeException();
						}
						objects.add(new FilledCircle(Integer.parseInt(parts[1]), 
								Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), 
								new Color(Integer.parseInt(parts[4]),
										Integer.parseInt(parts[5]), Integer.parseInt(parts[6])),
								new Color(Integer.parseInt(parts[7]),
										Integer.parseInt(parts[8]), Integer.parseInt(parts[9]))));
					}
				}
				
				model.clear();
				objects.forEach(o -> model.add(o));
				model.clearModifiedFlag();
				
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(JVDraw.this, "Invalid file format.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			
		}
	};

	/**
	 * Akcija za exportanje trenutno otvorenog crteža u formi JPG, PNG ili GIF.
	 */
	private final Action exportAction = new AbstractAction() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();

			FileNameExtensionFilter jpgFilter = new FileNameExtensionFilter("JPG file", "jpg", "jpeg");
			FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("PNG file", "png");
			FileNameExtensionFilter gifFilter = new FileNameExtensionFilter("GIF file", "gif");
			
			jfc.setAcceptAllFileFilterUsed(false);
			jfc.addChoosableFileFilter(jpgFilter);
			jfc.addChoosableFileFilter(pngFilter);
			jfc.addChoosableFileFilter(gifFilter);
	        
			jfc.setDialogTitle("Export file");
			
			if(jfc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			
			File file = jfc.getSelectedFile();
			if(!jpgFilter.accept(file) && !pngFilter.accept(file) && !gifFilter.accept(file)) {
				
				JOptionPane.showMessageDialog(JVDraw.this, "Invalid export file format.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
			for(int i = 0; i < model.getSize(); i++) {
				model.getObject(i).accept(bbcalc);
			}
			
			Rectangle box = bbcalc.getBoundingBox();
			BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D g = image.createGraphics();
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, image.getWidth(), image.getHeight());
			
			g.translate(-box.x, -box.y);
			GeometricalObjectPainter painter = new GeometricalObjectPainter(g);
			for(int i = 0; i < model.getSize(); i++) {
				model.getObject(i).accept(painter);
			}
			
			g.dispose();
			
			try {
				if(jpgFilter.accept(file)) {
					ImageIO.write(image, "jpg", file);
				} else if(pngFilter.accept(file)) {
					ImageIO.write(image, "png", file);
				} else {
					ImageIO.write(image, "gif", file);
				}
				
				JOptionPane.showMessageDialog(JVDraw.this, "Image exported successfully.", 
						"Export success", JOptionPane.INFORMATION_MESSAGE);
				
			} catch(IOException ex) {
				JOptionPane.showMessageDialog(JVDraw.this, "Could not export image.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}; 
	
	/**
	 * Akcija za izlaz iz aplikacije.
	 */
	private final Action exitAction = new AbstractAction() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(closeImage()) {
				dispose();
			}
		}
	};
	
	/**
	 * Pomoćna metoda za dohvat putanje dokumenta pomoću FIleChoosera.
	 * 
	 * @param title naslov prozora
	 * @return putanja dokumenta
	 */
	private Path getPath(String title) {
		JFileChooser jfc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JVDraw", "jvd");
	    jfc.setFileFilter(filter);
		jfc.setDialogTitle(title);
		jfc.setAcceptAllFileFilterUsed(false);
		
		if(title.equals("Save file")) {
			jfc.setSelectedFile(new File("new.jvd"));
			if(jfc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				return null;
			}
		} else {
			if(jfc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				return null;
			}
		}
		
		return jfc.getSelectedFile().toPath();
	}

	/**
	 * Metoda za spremanje trenutnog crteža pomoću visitora.
	 * 
	 * @throws IOException u slučaju greške
	 */
	private void save() throws IOException {
		GeometricalObjectSaver saver = new GeometricalObjectSaver();
		for(int i = 0; i < model.getSize(); i++) {
			model.getObject(i).accept(saver);
		}
		Files.write(path, saver.getFileText().getBytes(StandardCharsets.UTF_8));
		model.clearModifiedFlag();
	}

	/**
	 * Metoda za stvaranje ikone.
	 * 
	 * @param path putanja do slike
	 * @return nova ikona
	 * @throws RuntimeException ako dođe do greške pri učitavanju ikone
	 */
	private ImageIcon createImageIcon(String path) {
		InputStream is = JVDraw.class.getResourceAsStream(path);
		if(is == null) {
			throw new RuntimeException("Cannot get picture file.");
		}
		
		byte[] bytes;
		try{
			bytes = is.readAllBytes();
			is.close();
			ImageIcon icon = new ImageIcon(bytes);
			Image image = icon.getImage();
			Image newImage = image.getScaledInstance(size, size, Image.SCALE_SMOOTH);
			return new ImageIcon(newImage);
		} catch(IOException ex) {
			throw new RuntimeException("Error while loading icon.");
		}
	}

	/**
	 * Metoda koja se poziva pri izlasku iz aplikacije ili otvaranju 
	 * nove slike bez spremanja prethodne.
	 * 
	 * @return <code>true</code> ako se izlazi iz aplikacije, <code>false</code> ako ne
	 */
	private boolean closeImage() {
		if(model.isModified()) {
			int result = JOptionPane.showConfirmDialog(JVDraw.this, 
					"Image has unsaved chages. Save image?", 
					"Save image", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			switch(result) {
				case 0:		//yes
					saveAction.actionPerformed(null);
					break;
				case 1:		//no
					break;
				case 2:		//cancel
					return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Glavni program.
	 * 
	 * @param args argumenti naredbenog retka
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JVDraw().setVisible(true);
		});
	}
}
