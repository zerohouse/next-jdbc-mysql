package next.jdbc.mysql.sql.analyze;

import next.jdbc.mysql.sql.analyze.bind.Fields;

public interface Analyzer {

	public static <T> Analyzer getAnalyzer(Class<T> type) {
		return new TypeAnalyzer(type);
	}

	public static Analyzer getObjectAnalyzer(Object object) {
		return new ObjectAnalyzer(object);
	}

	Fields getNotNullAllFields();

	Fields getNotNullFields();

	Fields getNotNullKeyFields();

	Fields getAllFields();

	String getTableName();

	void setKeyParameters(Object[] parameters);

}
