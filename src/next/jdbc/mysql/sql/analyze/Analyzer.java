package next.jdbc.mysql.sql.analyze;

import next.jdbc.mysql.join.Join;
import next.jdbc.mysql.sql.analyze.bind.Fields;

public interface Analyzer {

	public static <T> Analyzer getAnalyzer(Class<T> type) {
		if (Join.class.isAssignableFrom(type))
			return new JoinTypeAnalyzer(type);
		return new TypeAnalyzer(type);
	}

	@SuppressWarnings("rawtypes")
	public static Analyzer getObjectAnalyzer(Object object) {
		if (Join.class.isAssignableFrom(object.getClass()))
			return new JoinAnalyzer((Join) object);
		return new ObjectAnalyzer(object);
	}

	Fields getNotNullAllFields();

	Fields getNotNullFields();

	Fields getNotNullKeyFields();

	Fields getAllFields();

	String getTableName();

	void setKeyParameters(Object[] parameters);

}
