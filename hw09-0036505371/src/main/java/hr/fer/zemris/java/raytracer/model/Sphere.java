package hr.fer.zemris.java.raytracer.model;
import static java.lang.Math.*;

/**
 * <p>Razred koji modelira sferu kao grafički objekt.
 * <p>Sfera je definirana svojim središtem i radijusom.
 * 
 * @author Maja Radočaj
 *
 */
public class Sphere extends GraphicalObject {
	/**
	 * Središte sfere.
	 */
	private Point3D center;
	/**
	 * Radijus sfere.
	 */
	private double radius;
	/**
	 * Komponenta difuzije za crvenu boju.
	 */
	private double kdr;
	/**
	 * Komponenta difuzije za zelenu boju.
	 */
	private double kdg;
	/**
	 * Komponenta difuzije za plavu boju.
	 */
	private double kdb;
	/**
	 * Komponenta refleksije za crvenu boju.
	 */
	private double krr;
	/**
	 * Komponenta refleksije za zelenu boju.
	 */
	private double krg;
	/**
	 * Komponenta refleksije za plavu boju.
	 */
	private double krb;
	/**
	 * Koeficijent n za refleksiju.
	 */
	private double krn;
	
	/**
	 * Javni konstruktor.
	 * 
	 * @param center centar sfere
	 * @param radius radijus sfere
	 * @param kdr komponenta difuzije za crvenu boju
	 * @param kdg komponenta difuzije za zelenu boju
	 * @param kdb komponenta difuzije za plavu boju
	 * @param krr komponenta refleksije za crvenu boju.
	 * @param krg komponenta refleksije za zelenu boju.
	 * @param krb komponenta refleksije za plavu boju.
	 * @param krn koeficijent n za refleksiju
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		Point3D viewpoint = ray.start;
		Point3D direction = ray.direction;
		
		double b = direction.scalarMultiply(2).scalarProduct(viewpoint.sub(center));
		double c = viewpoint.sub(center).scalarProduct(viewpoint.sub(center)) - radius * radius;
		double discriminant = b * b - 4 * c;
		if(discriminant < 0) {
			return null;
		}
		
		boolean outer = true;
		double distance1 = (-b - sqrt(b * b - 4 * c)) / 2;
		double distance2 = (-b + sqrt(b * b - 4 * c)) / 2;
		double minDistance = distance1 > 0 ? distance1 : distance2;
		if(distance1 < 0) outer = false;
		if(minDistance < 0) {
			return null;
		}
		
		Point3D intersectionPoint = viewpoint.add(direction.scalarMultiply(minDistance));
 		
		return new RayIntersection(intersectionPoint, minDistance, outer) {
			
			@Override
			public Point3D getNormal() {
				return intersectionPoint.sub(center).normalize();
			}
			
			@Override
			public double getKrr() {
				return krr;
			}
			
			@Override
			public double getKrn() {
				return krn;
			}
			
			@Override
			public double getKrg() {
				return krg;
			}
			
			@Override
			public double getKrb() {
				return krb;
			}
			
			@Override
			public double getKdr() {
				return kdr;
			}
			
			@Override
			public double getKdg() {
				return kdg;
			}
			
			@Override
			public double getKdb() {
				return kdb;
			}
		};
	}

}
