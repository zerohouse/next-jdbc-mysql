package next.jdbc.mysql.query;

import java.util.HashMap;
import java.util.Map;

import next.jdbc.mysql.DAORaw;
import next.jdbc.mysql.query.support.Delimiter;
import next.jdbc.mysql.query.support.Typer;
import next.jdbc.mysql.sql.Sql;

public class InsertQuery {

	Map<String, Object> values;
	Typer typeAnalyzer;
	Class<?> type;
	Delimiter delimiter;

	public InsertQuery(Class<?> type) {
		this.type = type;
		typeAnalyzer = new Typer(type);
		this.values = new HashMap<String, Object>();
		delimiter = new Delimiter(", ");
	}

	public InsertQuery set(String fieldName, Object value) {
		values.put(fieldName, value);
		return this;
	}

	private static final String FORMAT = "INSERT INTO %s (%s) VALUES (%s)";
	private static final String COMMA = ", ";

	public Sql getSql() {
		Sql result = new Sql();
		StringBuilder fields = new StringBuilder();
		Delimiter limiter = new Delimiter(COMMA);
		values.forEach((key, value) -> {
			fields.append(limiter.get());
			fields.append(typeAnalyzer.get(key).getColumnName());
			result.addParameter(value);
		});
		result.setQuery(String.format(FORMAT, typeAnalyzer.getTableName(), fields.toString(), getQ(values.size())));
		return result;
	}

	private String getQ(int length) {
		Delimiter limiter = new Delimiter(",");
		StringBuilder q = new StringBuilder();
		for (int i = 0; i < length; i++) {
			q.append(limiter.get());
			q.append("?");
		}
		return q.toString();
	}

	public Boolean execute() {
		DAORaw dao = new DAORaw();
		Sql sql = getSql();
		return dao.execute(sql.getQueryString(), sql.getParameterArray());
	}

}
