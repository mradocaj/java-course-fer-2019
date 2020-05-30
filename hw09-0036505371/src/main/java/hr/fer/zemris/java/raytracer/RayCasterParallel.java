package hr.fer.zemris.java.raytracer;

import static java.lang.Math.pow;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
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
 * pokreće bez argumenata. Rad se paralelizira.
 * 
 * @author Maja Radočaj
 *
 */
public class RayCasterParallel {
	/**
	 * Tolerancija pri uspoređivanju udaljenosti.
	 */
	private static final double TRESHOLD = 0.01;

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

			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, 
					double vertical, int width, int height, long requestNo, 
					IRayTracerResultObserver observer, AtomicBoolean cancel) {

				System.out.println("Zapocinjem izracun...");
				int maxIter = 16 * 16;
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

				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new RayTracerJob(0, height - 1, width, height, vertical, horizontal, 
						xAxis, yAxis, scene, red, green, blue, screenCorner, maxIter, eye));
				pool.shutdown();

				System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
				observer.acceptResult(red, green, blue, requestNo);
			}
		};
	}

	/**
	 * Razred koji modelira posao iscrtavanja scene.
	 * 
	 * @author Maja Radočaj
	 *
	 */
	private static class RayTracerJob extends RecursiveAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/**
		 * Minimalni y.
		 */
		private int yMin;
		/**
		 * Maksimalni y.
		 */
		private int yMax;
		/**
		 * Širina slike.
		 */
		private int width;
		/**
		 * Visina slike.
		 */
		private int height;
		/**
		 * Vertikala.
		 */
		private double vertical;
		/**
		 * Horizontala.
		 */
		private double horizontal;
		/**
		 * X os.
		 */
		private Point3D xAxis;
		/**
		 * Y os.
		 */
		private Point3D yAxis;
		/**
		 * Scena.
		 */
		private Scene scene;
		/**
		 * Polje vrijednosti za crvenu boju.
		 */
		private short[] red;
		/**
		 * Polje vrijednosti za zelenu boju.
		 */
		private short[] green;
		/**
		 * Polje vrijednosti za plavu boju.
		 */
		private short[] blue;
		/**
		 * Kut slike.
		 */
		private Point3D screenCorner;
		/**
		 * Granica.
		 */
		private int maxIter;
		/**
		 * Gledište.
		 */
		private Point3D eye;

		/**
		 * Javni konstruktor.
		 * 
		 * @param yMin         minimalni y
		 * @param yMax         maksimalni y
		 * @param width        širina slike
		 * @param height       visina slike
		 * @param vertical     vertikala
		 * @param horizontal   horizontala
		 * @param xAxis        x os
		 * @param yAxis        y os
		 * @param scene        scena
		 * @param red          polje vrijednosti za crvenu boju
		 * @param green        polje vrijednosti za zelenu boju
		 * @param blue         polje vrijednosti za plavu boju
		 * @param screenCorner kut ekrana
		 * @param maxIter      granica
		 * @param eye          gledište
		 */
		public RayTracerJob(int yMin, int yMax, int width, int height, double vertical, double horizontal,
				Point3D xAxis, Point3D yAxis, Scene scene, short[] red, short[] green, short[] blue,
				Point3D screenCorner, int maxIter, Point3D eye) {
			super();
			this.yMin = yMin;
			this.yMax = yMax;
			this.width = width;
			this.height = height;
			this.vertical = vertical;
			this.horizontal = horizontal;
			this.xAxis = xAxis;
			this.yAxis = yAxis;
			this.scene = scene;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.screenCorner = screenCorner;
			this.maxIter = maxIter;
			this.eye = eye;
		}

		@Override
		protected void compute() {
			if(yMax - yMin + 1 <= maxIter) {
				computeDirect();
				return;
			}

			invokeAll(
					new RayTracerJob(yMin, yMin + (yMax - yMin) / 2, width, height, vertical, 
							horizontal, xAxis, yAxis,
							scene, red, green, blue, screenCorner, maxIter, eye),
					new RayTracerJob(yMin + (yMax - yMin) / 2 + 1, yMax, width, height, vertical, 
							horizontal, xAxis,
							yAxis, scene, red, green, blue, screenCorner, maxIter, eye));
		}

		public void computeDirect() {
			short[] rgb = new short[3];
			for(int y = yMin; y <= yMax; y++) {
				for(int x = 0; x < width; x++) {
					Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(
							horizontal * x * 1.0 / (width - 1)))
							.sub(yAxis.scalarMultiply(vertical * y * 1.0 / (height - 1)));
					Ray ray = Ray.fromPoints(eye, screenPoint);
					tracer(scene, ray, rgb);
					red[x + y * width] = rgb[0] > 255 ? 255 : rgb[0];
					green[x + y * width] = rgb[1] > 255 ? 255 : rgb[1];
					blue[x + y * width] = rgb[2] > 255 ? 255 : rgb[2];
				}
			}
		}
	}

	/**
	 * Metoda koja iscrtava i boja objekte u sceni.
	 * 
	 * @param scene dana scena
	 * @param ray   zraka za koju se iscrtava
	 * @param rgb   polje boja
	 */
	private static void tracer(Scene scene, Ray ray, short[] rgb) {

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
	private static short[] determineColorFor(RayIntersection closestIntersection, Scene scene, Ray ray) {
		short rgb[] = new short[] { 15, 15, 15 };

		for(LightSource ls : scene.getLights()) {
			Ray newRay = new Ray(ls.getPoint(), closestIntersection.getPoint().sub(ls.getPoint())
					.modifyNormalize());
			RayIntersection intersection = findClosestIntersection(scene, newRay);

			if(intersection != null && ls.getPoint().sub(
					intersection.getPoint()).norm() + TRESHOLD < ls.getPoint()
					.sub(closestIntersection.getPoint()).norm()) {
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
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
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

}
