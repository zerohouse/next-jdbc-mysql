package next.jdbc.mysql.query.support;

import java.util.LinkedList;
import java.util.Queue;

public class GroupBy {

	private Queue<String> groups;
	private Delimiter delimiter;
	private static final String GROUP_BY = " GROUP BY ";
	private static final String COMMA = ",";

	public GroupBy() {
		groups = new LinkedList<String>();
		delimiter = new Delimiter(COMMA);
	}

	public void groupBy(StringBuilder sql) {
		sql.append(GROUP_BY);
		groups.forEach(order -> {
			sql.append(delimiter.get());
			sql.append(order);
		});
	}

	public boolean isEmpty() {
		return groups.size() == 0;
	}

	public void group(String columnName) {
		groups.add(columnName);
	}

}
