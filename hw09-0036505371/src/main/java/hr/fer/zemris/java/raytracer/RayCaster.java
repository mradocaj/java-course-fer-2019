package hr.fer.zemris.java.raytracer;

import static java.lang.Math.*;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Program za prikazivanje grafičkih objekata u definiranoj sceni. Program se
 * pokreće bez argumenata.
 * 
 * @author Maja Radočaj
 *
 */
public class RayCaster {

	/**
	 * Glavni program.
	 * 
	 * @param args argumenti naredbenog retka
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * Metoda koja vraća objekt koji može stvoriti sliku scene na temelju
	 * iscrtavanja zraka.
	 * 
	 * @return objekt koji implementira {@link IRayTracerProducer}
	 */
	private static IRayTracerProducer getIRayTracerProducer() {

		return new IRayTracerProducer() {
			/**
			 * Tolerancija pri uspoređivanju udaljenosti.
			 */
			private static final double TRESHOLD = 0.01;

			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, 
					double vertical, int width, int height, long requestNo, 
					IRayTracerResultObserver observer, AtomicBoolean cancel) {

				System.out.println("Započinjem izračune...");

				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D vu = viewUp.normalize();
				Point3D og = view.sub(eye).normalize();

//				Point3D zAxis = ...;
				Point3D yAxis = vu.sub(og.scalarMultiply(og.scalarProduct(vu))).normalize();
				Point3D xAxis = og.vectorProduct(yAxis).normalize();

				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2))
						.add(yAxis.scalarMultiply(vertical / 2));

				Scene scene = RayTracerViewer.createPredefinedScene();

				short[] rgb = new short[3];
				int offset = 0;
				for(int y = 0; y < height; y++) {
					for(int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner.add(
								xAxis.scalarMultiply(horizontal * x * 1.0 / (width - 1)))
								.sub(yAxis.scalarMultiply(vertical * y * 1.0 / (height - 1))); 
						// pointx,y u uputi
						Ray ray = Ray.fromPoints(eye, screenPoint);
						tracer(scene, ray, rgb);
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++;
					}
				}
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}

			/**
			 * Metoda koja iscrtava i boja objekte u sceni.
			 * 
			 * @param scene dana scena
			 * @param ray   zraka za koju se iscrtava
			 * @param rgb   polje boja
			 */
			private void tracer(Scene scene, Ray ray, short[] rgb) {

				Arrays.fill(rgb, (short) 0); // default - crna boja
				RayIntersection closestIntersection = findClosestIntersection(scene, ray);

				if(closestIntersection == null) {
					return;
				}

				short[] result = determineColorFor(closestIntersection, scene, ray);
				rgb[0] = result[0];
				rgb[1] = result[1];
				rgb[2] = result[2];
			}

			/**
			 * Metoda koja određuje boju za zadanu točku presjeka i zadanu zraku.
			 * 
			 * @param closestIntersection presjek
			 * @param scene               scena
			 * @param ray                 zraka
			 * @return polje sa rgb vrijednostima koje definiraju boju
			 */
			private short[] determineColorFor(RayIntersection closestIntersection, Scene scene, Ray ray) {
				short rgb[] = new short[] { 15, 15, 15 }; // default je ambijentalna boja

				for(LightSource ls : scene.getLights()) {
					Ray newRay = new Ray(ls.getPoint(),
							closestIntersection.getPoint().sub(ls.getPoint()).modifyNormalize());
					RayIntersection intersection = findClosestIntersection(scene, newRay);

					if(intersection != null 
							&& ls.getPoint().sub(intersection.getPoint()).norm() + TRESHOLD < 
							ls.getPoint().sub(closestIntersection.getPoint()).norm()) {
						
						continue;
					}

					Point3D l = ls.getPoint().sub(closestIntersection.getPoint());
					Point3D n = closestIntersection.getNormal();
					double ln = l.normalize().scalarProduct(n);
					ln = ln >= 0 ? ln : 0;

					double diffuseComponentR = ls.getR() * closestIntersection.getKdr() * ln;
					double diffuseComponentG = ls.getG() * closestIntersection.getKdg() * ln;
					double diffuseComponentB = ls.getB() * closestIntersection.getKdb() * ln;

					double nExponent = closestIntersection.getKrn();
					Point3D lN = n.scalarMultiply(n.scalarProduct(l));
					Point3D v = ray.start.sub(closestIntersection.getPoint());
					Point3D r = lN.sub(l.sub(lN));
					double cos = r.normalize().scalarProduct(v.normalize());

					double reflComponentR = cos > 0 ? 
							ls.getR() * pow(cos, nExponent) * closestIntersection.getKrr() : 0;
					double reflComponentG = cos > 0 ? 
							ls.getG() * pow(cos, nExponent) * closestIntersection.getKrg() : 0;
					double reflComponentB = cos > 0 ? 
							ls.getB() * pow(cos, nExponent) * closestIntersection.getKrb() : 0;

					rgb[0] += diffuseComponentR + reflComponentR;
					rgb[1] += diffuseComponentG + reflComponentG;
					rgb[2] += diffuseComponentB + reflComponentB;
				}

				return rgb;
			}

			/**
			 * Metoda za pronalaženje najbližeg presjeka. Ako takav ne postoji, vraća se
			 * null.
			 * 
			 * @param scene scena
			 * @param ray   zraka
			 * @return najbliži presjek
			 */
			private RayIntersection findClosestIntersection(Scene scene, Ray ray) {
				double minDistance = 0;
				RayIntersection result = null;

				for(GraphicalObject object : scene.getObjects()) {
					RayIntersection current = object.findClosestRayIntersection(ray);
					if(current != null) {
						if(result == null || current.getDistance() < minDistance) {
							result = current;
							minDistance = result.getDistance();
						}
					}
				}

				return result;
			}
		};
	}

}
