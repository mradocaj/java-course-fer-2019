package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * <p>
 * Razred za prikazivanje fraktala baziranih na Newton-Raphson iteraciji.
 * </p>
 * <p>
 * Program se pokreće bez argumenata naredbenog retka, a korisnik mora unijeti
 * minimalno dva ispravno napisana kompleksna broja koja predstavljaju nultočke
 * polinoma.
 * </p>
 * <p>
 * Program iscrtava fraktal nakon unosa ključne riječi <i>done</i>.
 * 
 * @author Maja Radočaj
 *
 */
public class Newton {

	/**
	 * Ključna riječ za kraj unosa.
	 */
	private static final String DONE = "done";

	/**
	 * Glavni program.
	 * 
	 * @param args argumenti naredbenog retka
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.\n"
				+ "Please enter at least two roots, one root per line. Enter 'done' when done.");

		Scanner sc = new Scanner(System.in);
		List<Complex> roots = new ArrayList<>();
		int i = 1;

		while(true) {
			System.out.print("Root " + i + " > ");
			String line = sc.nextLine();
			i++;
			if(line.trim().equals(DONE))
				break;
			try {
				roots.add(Complex.parse(line));
			} catch(NumberFormatException ex) {
				System.out.println("Invalid root given. " + ex.getMessage() + " Try again.");
				i--;
			}
		}

		sc.close();
		if(roots.size() < 2) {
			System.out.println("Two roots should have been given.");
			return;
		}
		System.out.println("Image of fractal will appear shortly. Thank you.");
		ComplexRootedPolynomial rootedPolynomial = new ComplexRootedPolynomial(Complex.ONE,
				roots.toArray(new Complex[] {}));

		FractalViewer.show(new NewtonFractalProducer(rootedPolynomial));
	}

	/**
	 * Razred koji predstavlja posao koji se obavlja za iscrtavanje jednog područja
	 * slike.
	 * 
	 * @author Maja Radočaj
	 *
	 */
	private static class NewtonJob implements Callable<Void> {
		/**
		 * Najmanji realni dio.
		 */
		private double reMin;
		/**
		 * Najveći realni dio.
		 */
		private double reMax;
		/**
		 * Najmanji imaginarni dio.
		 */
		private double imMin;
		/**
		 * Najveći imaginarni dio.
		 */
		private double imMax;
		/**
		 * Širina slike.
		 */
		private int width;
		/**
		 * Visina slike.
		 */
		private int height;
		/**
		 * Najmanji y.
		 */
		private int yMin;
		/**
		 * Najveći y.
		 */
		private int yMax;
		/**
		 * Maksimalni broj iteracija.
		 */
		private int maxIter;
		/**
		 * Polje za pohranu indeksa kompleksnih brojeva.
		 */
		private short[] data;
		/**
		 * Polinom sa nultočkama.
		 */
		private ComplexRootedPolynomial rootedPolynomial;
		/**
		 * Polinom.
		 */
		private ComplexPolynomial polynom;
		/**
		 * Prag konvergencije.
		 */
		private static final double CONVERGENCE_TRESHOLD = 1E-3;
		/**
		 * Prag za korijene.
		 */
		private static final double ROOT_TRESHOLD = 2E-3;

		/**
		 * Javni konstruktor.
		 * 
		 * @param reMin            najmanji realni dio
		 * @param reMax            najveći realni dio
		 * @param imMin            najmanji imaginarni dio
		 * @param imMax            najveći imaginarni dio
		 * @param width            širina slike
		 * @param height           visina slike
		 * @param yMin             najmanji y
		 * @param yMax             najveći y
		 * @param m                maksimalni broj iteracija
		 * @param data             polje podataka
		 * @param rootedPolynomial polinom sa korijenima
		 * @param polynom          polinom
		 */
		public NewtonJob(double reMin, double reMax, double imMin, double imMax, 
				int width, int height, int yMin,
				int yMax, int m, short[] data, ComplexRootedPolynomial rootedPolynomial, 
				ComplexPolynomial polynom) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.maxIter = m;
			this.data = data;
			this.rootedPolynomial = rootedPolynomial;
			this.polynom = polynom;
		}

		@Override
		public Void call() {
			for(int y = yMin; y <= yMax; y++) {
				for(int x = 0; x < width; x++) {
					Complex zn = mapToComplexPlain(x, y, 0, width, yMin, yMax, reMin, 
							reMax, imMin, imMax);
					int iter = 0;
					Complex znold = null;
					ComplexPolynomial derived = polynom.derive();

					do {
						iter++;
						Complex numerator = polynom.apply(zn);
						Complex denominator = derived.apply(zn);
						znold = zn;
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
					} while(znold.sub(zn).module() > CONVERGENCE_TRESHOLD && iter < maxIter);

					int index = rootedPolynomial.indexOfClosestRootFor(zn, ROOT_TRESHOLD);
					data[x + y * width] = (short) (index + 1);
				}
			}

			return null;
		}

		/**
		 * Metoda koja stvara novi kompleksni broj za slikovni element na koordinatama
		 * <code>(x, y)</code>.
		 * 
		 * @param x     x koordinata
		 * @param y     y koordinata
		 * @param xMin  minimalni x
		 * @param xMax  maksimalni x
		 * @param yMin  minimalni y
		 * @param yMax  maksimalni y
		 * @param reMin najmanji realni dio
		 * @param reMax najveći realni dio
		 * @param imMin najmanji imaginarni dio
		 * @param imMax najveći imaginarni dio
		 * @return novi kompleksni broj
		 */
		private Complex mapToComplexPlain(int x, int y, int xMin, int xMax, int yMin, 
				int yMax, double reMin,
				double reMax, double imMin, double imMax) {

			double re = x / (xMax - 1.0) * (reMax - reMin) + reMin;
			double im = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;
			return new Complex(re, im);
		}
	}

	/**
	 * Pomoćni razred za generiranje podataka za vizualizaciju Newtownovog fraktala.
	 * 
	 * @author Maja Radočaj
	 *
	 */
	private static class NewtonFractalProducer implements IFractalProducer {
		/**
		 * Polinom sa korijenima.
		 */
		private ComplexRootedPolynomial rootedPolynomial;
		/**
		 * Polinom.
		 */
		private ComplexPolynomial polynom;
		/**
		 * Pool.
		 */
		private ExecutorService pool;

		/**
		 * Javni konstruktor.
		 * 
		 * @param rootedPolynomial polinom
		 */
		public NewtonFractalProducer(ComplexRootedPolynomial rootedPolynomial) {
			this.rootedPolynomial = rootedPolynomial;
			this.polynom = rootedPolynomial.toComplexPolynom();
			this.pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), w -> {
				Thread worker = new Thread(w);
				worker.setDaemon(true);
				return worker;
			});
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			
			System.out.println("Zapocinjem izracun...");
			int maxIter = 16 * 16;
			short[] data = new short[width * height];
			int numberOfProcessors = Runtime.getRuntime().availableProcessors();
			int tracksNumber = 8 * numberOfProcessors;
			int yNumberByTrack = height / tracksNumber;
			List<Future<Void>> results = new ArrayList<>();

			for(int i = 0; i < tracksNumber; i++) {
				int yMin = yNumberByTrack * i;
				int yMax = (i + 1) * yNumberByTrack - 1;
				if(i == tracksNumber - 1) {
					yMax = height - 1;
				}
				NewtonJob job = new NewtonJob(reMin, reMax, imMin, imMax, width, height, 
						yMin, yMax, maxIter, data, rootedPolynomial, polynom);
				results.add(pool.submit(job));
			}

			for(Future<Void> job : results) {
				try {
					job.get();
				} catch(InterruptedException | ExecutionException e) {
				}
			}

			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short) (polynom.order() + 1), requestNo);
		}
	}
}
