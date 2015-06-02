package next.jdbc.mysql.query;

import java.util.HashMap;
import java.util.Map;

import next.jdbc.mysql.DAORaw;
import next.jdbc.mysql.query.support.Delimiter;
import next.jdbc.mysql.sql.Sql;

public class UpdateQuery extends WhereQuery {

	Map<String, Object> values;
	Class<?> type;
	Delimiter delimiter;

	public UpdateQuery(DAORaw dao, Class<?> type) {
		super(dao, type);
		this.type = type;
		this.values = new HashMap<String, Object>();
		delimiter = new Delimiter(", ");
	}

	public UpdateQuery set(String fieldName, Object value) {
		values.put(fieldName, value);
		return this;
	}

	private static final String FORMAT = "UPDATE %s SET %s";

	protected Sql getSql() {
		Sql result = new Sql();
		StringBuilder fields = new StringBuilder();
		Delimiter limiter = new Delimiter(COMMA);
		values.forEach((key, value) -> {
			fields.append(limiter.get());
			fields.append(typeAnalyzer.get(key).getColumnName());
			fields.append(EQ);
			result.addParameter(value);
		});
		result.setQuery(String.format(FORMAT, typeAnalyzer.getTableName(), fields.toString()));
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
