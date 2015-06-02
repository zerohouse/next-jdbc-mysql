package next.jdbc.mysql.query;

import next.jdbc.mysql.DAORaw;
import next.jdbc.mysql.sql.Sql;

public class DeleteQuery extends WhereQuery {

	Class<?> type;

	public DeleteQuery(DAORaw dao, Class<?> type) {
		super(dao, type);
		this.type = type;
	}

	private static final String FORMAT = "DELETE FROM %s";

	protected Sql getSql() {
		Sql result = new Sql(String.format(FORMAT, typeAnalyzer.getTableName()));
		if (where != null) {
			result.append(WHERE);
			result.concat(where);
		}
		return result;
	}

	public QueryNeedValue field(String fieldName) {
		makeWhere();
		return new QueryNeedValue(fieldName, this);
	}

	@Override
	public String toString() {
		return getSql().getQueryString();
	}

}
