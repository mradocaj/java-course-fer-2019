package hr.fer.zemris.hw16.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Razred koji nudi statičke metode za stvaranje objekata slika iz opisnika.
 * 
 * @author Maja Radočaj
 *
 */
@WebListener
public class ImageProvider implements ServletContextListener {

	/**
	 * Slike.
	 */
	private static Image[] images;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		images = initImages(sce.getServletContext());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

	/**
	 * Pomoćna metoda koja inicijalizira slike pri pokretanju servera.
	 * 
	 * @param context kontekst servera
	 * @return slike
	 */
	private static Image[] initImages(ServletContext context) {
		List<String> lines = null;
		
		try {
			lines = Files.readAllLines(Paths.get(context.getRealPath("/WEB-INF/opisnik.txt"))); 
		} catch(IOException ex) {
			throw new RuntimeException("Couldn't read file.");
		}
		
		if(lines.size() % 3 != 0 || lines.size() == 0) {
			throw new RuntimeException("Invalid file format.");
		}
		
		Image[] images = new Image[lines.size() / 3];
		
		for(int i = 0; i < lines.size(); i = i + 3) {
			images[i / 3] = new Image(lines.get(i), lines.get(i + 1), 
					Arrays.asList(lines.get(i + 2).split(", ")));
		}
		
		return images;
	}
	
	/**
	 * Metoda koja vraća slike.
	 * 
	 * @return polje slika
	 */
	public static Image[] getImages() {
		return images;
	}
	
	/**
	 * Metoda koja vraća listu slika za određenu oznaku.
	 * 
	 * @param tag oznaka
	 * @return lista slika koje imaju oznaku tag
	 */
	public static List<Image> getImagesForTag(String tag) {
		Image[] allImages = getImages();
		List<Image> taggedImages = new ArrayList<>();
		
		for(Image image : allImages) {
			if(image.getTags().contains(tag)) {
				taggedImages.add(image);
			}
		}
		
		taggedImages.sort((i1, i2) -> i1.getFileName().compareTo(i2.getFileName()));
		return taggedImages;
	}
	
	/**
	 * Metoda koja vraća sliku sa imenom <code>fileName</code>.
	 * 
	 * @param fileName ime slike
	 * @return slika sa tim imenom
	 */
	public static Image getImageForFileName(String fileName) {
		Image[] allImages = getImages();
		
		for(Image image : allImages) {
			if(image.getFileName().equals(fileName)) {
				return image;
			}
		}
		
		return null;
	}
	
	/**
	 * Metoda koja vraća listu svih oznaka.
	 * 
	 * @return lista oznaki
	 */
	public static List<String> getTags() {
		Image[] allImages = getImages();
		List<String> tags = new ArrayList<>();
		
		for(Image image : allImages) {
			List<String> currentTags = image.getTags();
			currentTags.forEach(t -> {
				if(!tags.contains(t)) {
					tags.add(t);
				}
			});
		}
		
		tags.sort((t1, t2) -> t1.compareTo(t2));
		return tags;
	}
}
