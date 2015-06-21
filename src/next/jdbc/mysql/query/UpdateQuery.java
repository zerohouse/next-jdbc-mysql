package next.jdbc.mysql.query;

import java.util.HashMap;
import java.util.Map;

import next.jdbc.mysql.DAORaw;
import next.jdbc.mysql.query.support.Delimiter;
import next.jdbc.mysql.sql.Sql;
import next.jdbc.mysql.sql.analyze.info.FieldInfo;

/**
 * Update Query입니다.
 * <p>
 *
 * field("fieldName")를 통해 조건을 설정합니다.
 */
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

	/**
	 * 필드에 밸류를 설정합니다.
	 * <p>
	 *
	 * @param fieldName
	 *            필드네임입니다. [fieldName] or [className].[fieldName]
	 * @param value
	 *            설정할 값입니다.
	 * @return UpdateQuery
	 */
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
			FieldInfo info = typeAnalyzer.get(key);
			info.regexMatchedCheck(value);
			fields.append(limiter.get());
			fields.append(info.getColumnName());
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

	@Override
	public String toString() {
		return getSql().getQueryString();
	}

}
