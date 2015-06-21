package next.jdbc.mysql.query.support;

public class Limit {

	private static final String LIMIT = " LIMIT ";
	private int begin;
	private int end;

	public Limit(int begin, int end) {
		this.begin = begin;
		this.end = end;
	}

	public void limit(StringBuilder sql) {
		sql.append(LIMIT);
		sql.append(begin);
		sql.append(',');
		sql.append(end);
	}

}
