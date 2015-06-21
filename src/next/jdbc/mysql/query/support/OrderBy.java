package next.jdbc.mysql.query.support;

import java.util.LinkedList;
import java.util.Queue;

public class OrderBy {

	private Queue<Order> orders;
	private Delimiter delimiter;
	private static final String ORDER_BY = " ORDER BY ";
	private static final String DESC = " DESC ";
	private static final String COMMA = ",";

	public OrderBy() {
		orders = new LinkedList<Order>();
		delimiter = new Delimiter(COMMA);
	}

	public void orderBy(StringBuilder sql) {
		sql.append(ORDER_BY);
		orders.forEach(order -> {
			sql.append(delimiter.get());
			sql.append(order.getColumnName());
			if (order.getDesc())
				sql.append(DESC);
		});
	}

	public boolean isEmpty() {
		return orders.size() == 0;
	}

	public void order(String columnName, boolean desc) {
		orders.add(new Order(columnName, desc));
	}

}
