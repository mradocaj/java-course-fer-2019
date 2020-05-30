package hr.fer.zemris.hw16.rest;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import hr.fer.zemris.hw16.model.Image;
import hr.fer.zemris.hw16.model.ImageProvider;

/**
 * Razred koji omogućava REST. Koristi Gson za rad sa JSON formatom.
 * 
 * @author Maja Radočaj
 *
 */
@Path("/images")
public class ImageJSON {

	/**
	 * Kontekst servleta.
	 */
	@Context
	ServletContext context;
	
	/**
	 * Metoda za dohvat svih oznaki.
	 * 
	 * @return odgovor
	 */
	@GET
	@Produces("application/json")
	public Response getTags() {
		List<String> tags = ImageProvider.getTags();
		
		Gson gson = new Gson();
		String jsonText = gson.toJson(tags);
		
		return Response.status(Status.OK).entity(jsonText).build();
	}
	
	/**
	 * Metoda za vraćanje ikoni sa oznakom <code>tag</code>.
	 * 
	 * @param tag oznaka
	 * @return odgovor
	 * @throws IOException u slučaju greške
	 */
	@Path("{tag}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getThumbnails(@PathParam("tag") String tag) throws IOException {
		java.nio.file.Path dir = Paths.get(context.getRealPath("/WEB-INF/thumbnails"));
		String imagesDir = context.getRealPath("/WEB-INF/slike");
		
		if(!(Files.exists(dir) && Files.isDirectory(dir))) {
			Files.createDirectory(dir);
		} 
		
		List<Image> images = ImageProvider.getImagesForTag(tag);
		
		for(Image img : images) {
			java.nio.file.Path imgPath = Paths.get(context.getRealPath("/WEB-INF/thumbnails")
					, img.getFileName());
			
			if(!Files.exists(imgPath)) {
				createImage(imagesDir, img, imgPath);
			}
		}
		
		Gson gson = new Gson();
		String jsonText = gson.toJson(images);
		
		return Response.status(Status.OK).entity(jsonText).build();
	}
	
	/**
	 * Metoda za dohvat slike u punoj veličini.
	 * 
	 * @param fileName naziv slike
	 * @return odgovor
	 */
	@Path("/image/{fileName}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getImage(@PathParam("fileName") String fileName) {
		Image image = ImageProvider.getImageForFileName(fileName);
		if(image == null) {
			return null;
		}
		
		Gson gson = new Gson();
		String jsonText = gson.toJson(image);
		
		return Response.status(Status.OK).entity(jsonText).build();
	}
	
	/**
	 * Pomoćna metoda za stvaranje nove ikone.
	 * 
	 * @param imagesDir direktorij sa slikama
	 * @param img slika
	 * @param imgPath path do nove slike
	 * @throws IOException u slučaju greške
	 */
	private void createImage(String imagesDir, Image img, java.nio.file.Path imgPath) 
		throws IOException {
		
		java.nio.file.Path fullImgPath = Paths.get(imagesDir, img.getFileName());
		BufferedImage image = ImageIO.read(Files.newInputStream(fullImgPath));
		BufferedImage scaledImage = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g2d = scaledImage.createGraphics();
		java.awt.Image scaled = image.getScaledInstance(150, 150, 
				java.awt.Image.SCALE_SMOOTH);
		g2d.drawImage(scaled, 0, 0, null);		
		
		ImageIO.write(scaledImage, "jpg", Files.newOutputStream(imgPath));	
		g2d.dispose();													
	}
}
