package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Razred koji se registrira kao slušač na dani prozor.
 * 
 * @author Maja Radočaj
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Javni konstruktor.
	 * 
	 * @param parent dobavljač prijevoda
	 * @param frame prozor
	 */
	public FormLocalizationProvider(ILocalizationProvider parent, JFrame frame) {
		super(parent);
		frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
		});
	}

}
