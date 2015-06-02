package next.jdbc.mysql.query;

import next.jdbc.mysql.DAORaw;
import next.jdbc.mysql.sql.Sql;

/**
 * Delete Query입니다.
 * <p>
 *
 * field("fieldName")를 통해 조건을 설정합니다.
 */
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


	@Override
	public String toString() {
		return getSql().getQueryString();
	}

}
