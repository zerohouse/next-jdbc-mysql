package next.jdbc.mysql.query;

import java.util.HashMap;
import java.util.Map;

import next.jdbc.mysql.DAORaw;
import next.jdbc.mysql.query.support.Delimiter;
import next.jdbc.mysql.query.support.Typer;
import next.jdbc.mysql.sql.Sql;
import next.jdbc.mysql.sql.analyze.info.FieldInfo;

/**
 * Insert Query입니다.
 * <p>
 *
 * set("fieldName", value)를 통해 값을 설정합니다.
 */
public class InsertQuery extends Query {

	Map<String, Object> values;
	Typer typeAnalyzer;
	Class<?> type;
	Delimiter delimiter;

	public InsertQuery(DAORaw dao, Class<?> type) {
		super(dao);
		this.type = type;
		typeAnalyzer = new Typer(type);
		this.values = new HashMap<String, Object>();
		delimiter = new Delimiter(", ");
	}

	/**
	 * 필드에 밸류를 설정합니다.
	 * <p>
	 *
	 * @param fieldName
	 *            필드네임입니다. [fieldName] or [className].[fieldName]
	 * @param value
	 *            설정할 값입니다.
	 * @return InsertQuery
	 */
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
			FieldInfo info = typeAnalyzer.get(key);
			info.regexMatchedCheck(value);
			fields.append(limiter.get());
			fields.append(info.getColumnName());
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
		Sql sql = getSql();
		return dao.execute(sql.getQueryString(), sql.getParameterArray());
	}

}
