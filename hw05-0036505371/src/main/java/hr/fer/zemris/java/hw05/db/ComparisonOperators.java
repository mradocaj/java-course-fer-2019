package hr.fer.zemris.java.hw05.db;

/**
 * Razred koji nudi nekoliko implementacija sučelja {@link IComparisonOperator}
 * u obliku javnih statičkih varijabli.
 * 
 * @author Maja Radočaj
 *
 */
public class ComparisonOperators {

	/**
	 * Operator koji vraća <code>true</code> ako je prvi argument manji od drugog
	 * (na temelju metode <code>compareTo</code>). U svakom drugom slučaju vraća
	 * <code>false</code>.
	 */
	public static final IComparisonOperator LESS = (s1, s2) -> s1.compareTo(s2) < 0;
	/**
	 * Operator koji vraća <code>true</code> ako je prvi argument manji ili jednak
	 * drugome (na temelju metode <code>compareTo</code>). U svakom drugom slučaju
	 * vraća <code>false</code>.
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (s1, s2) -> s1.compareTo(s2) <= 0;
	/**
	 * Operator koji vraća <code>true</code> ako je prvi argument veći od drugog (na
	 * temelju metode <code>compareTo</code>). U svakom drugom slučaju vraća
	 * <code>false</code>.
	 */
	public static final IComparisonOperator GREATER = (s1, s2) -> s1.compareTo(s2) > 0;
	/**
	 * Operator koji vraća <code>true</code> ako je prvi argument veći ili jednak
	 * drugome (na temelju metode <code>compareTo</code>). U svakom drugom slučaju
	 * vraća <code>false</code>.
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (s1, s2) -> s1.compareTo(s2) >= 0;
	/**
	 * Operator koji vraća <code>true</code> ako je prvi argument jednak drugome (na
	 * temelju metode <code>compareTo</code>). U svakom drugom slučaju vraća
	 * <code>false</code>.
	 */
	public static final IComparisonOperator EQUALS = (s1, s2) -> s1.compareTo(s2) == 0;
	/**
	 * Operator koji vraća <code>true</code> ako je prvi argument nije jednak
	 * drugome (na temelju metode <code>compareTo</code>). U svakom drugom slučaju
	 * vraća <code>false</code>.
	 */
	public static final IComparisonOperator NOT_EQUALS = (s1, s2) -> s1.compareTo(s2) != 0;
	/**
	 * Operator koji vraća <code>true</code> ako je prvi argument poput drugog
	 * (String literala). U svakom drugom slučaju vraća <code>false</code>. String
	 * literal može sadržavati wildcard '*' koji predstavlja 0 ili više znakova. Ako
	 * literal sadrži više od jednog znaka '*', baca se
	 * {@link IllegalArgumentException}.
	 */
	public static final IComparisonOperator LIKE = new IComparisonOperator() {

		@Override
		public boolean satisfied(String value1, String value2) {
			if(value2.length() >= value1.length() + 2)
				return false;
			int count = 0;
			for(char c : value2.toCharArray()) {
				if(c == '*')
					count++;
			}
			if(count > 1) {
				throw new IllegalArgumentException("Only one wildcard symbol is allowed.");
			}

			String[] parts = value2.split("\\*");

			if(parts.length == 0) { // ako je literal "*"
				return true;
			} else if(parts.length == 1) {
				if(value2.startsWith("*")) {
					return value1.endsWith(parts[1]);
				}
				if(value2.endsWith("*")) {
					return value1.startsWith(parts[0]);
				}
				return value1.equals(value2);
			} else {
				return value1.startsWith(parts[0]) && value1.endsWith(parts[1]);
			}
		}
	};
}
