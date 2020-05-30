package hr.fer.zemris.java.hw05.db;

import java.util.List;
import java.util.Objects;

/**
 * Razred koji implementira sučelje {@link IFilter}. Nadjačava metodu
 * <code>accepts</code> tako da vraća <code>true</code> ako zadovoljava sve
 * uvjete iz liste uvjeta primljene preko konstruktora.
 * 
 * @author Maja Radočaj
 *
 */
public class QueryFilter implements IFilter {

	/**
	 * Lista uvjetnih izraza.
	 */
	private List<ConditionalExpression> list;

	/**
	 * Konstruktor koji inicijalizira listu uvjetnih izraza. Lista ne smije biti
	 * <code>null</code>.
	 * 
	 * @param list lista uvjetnih izraza
	 * @throws NullPointerException ako je lista <code>null</code>
	 */
	public QueryFilter(List<ConditionalExpression> list) {
		this.list = Objects.requireNonNull(list);
	}

	/**
	 * {@inheritDoc} U ovoj implementaciji svi uvjeti iz liste uvjeta moraju biti
	 * zadovoljeni da bi zapis bio prihvaćen.
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		for(ConditionalExpression expression : list) {
			if(!expression.getComparisonOperator().satisfied(expression.getFieldGetter().get(record),
					expression.getStringLiteral())) {
				return false;
			}
		}
		return true;
	}
}
