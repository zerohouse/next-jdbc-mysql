package next.jdbc.mysql.query;

import next.jdbc.mysql.query.analyze.ObjectAnalyzer;
import next.jdbc.mysql.query.analyze.TypeAnalyzer;

public class QueryMaker {

	private static final String ASTAR = "*";
	private static final String EQ = "=?";
	private static final String COMMA = ",";

	private static final String SELECT = "SELECT %s FROM %s WHERE %s";
	private static final String INSERT = "INSERT INTO %s (%s) VALUES (%s)";
	private static final String UPDATE = "UPDATE %s SET %s WHERE %s";
	private static final String DELETE = "DELETE FROM %s WHERE %s";

	public Query select(Class<?> type) {
		TypeAnalyzer typeAnalyzer = new TypeAnalyzer(type);
		Query result = typeAnalyzer.getKeyFields().getQuery(EQ, COMMA);
		return result.setQuery(String.format(SELECT, ASTAR, typeAnalyzer.getTableInfo().getWrappedTableName(), result.getQueryString()));
	}

	public Query delete(Class<?> type) {
		TypeAnalyzer typeAnalyzer = new TypeAnalyzer(type);
		Query result = typeAnalyzer.getKeyFields().getQuery(EQ, COMMA);
		return result.setQuery(String.format(DELETE, typeAnalyzer.getTableInfo().getWrappedTableName(), result.getQueryString()));
	}

	public Query select(Object record) {
		ObjectAnalyzer objectAnalyzer = new ObjectAnalyzer(record);
		Query result = objectAnalyzer.getFieldMap().getNotNullFields().getQuery(EQ, COMMA);
		return result.setQuery(String.format(SELECT, ASTAR, objectAnalyzer.getTableInfo().getWrappedTableName(), result.getQueryString()));
	}

	public Query delete(Object record) {
		ObjectAnalyzer objectAnalyzer = new ObjectAnalyzer(record);
		Query result = objectAnalyzer.getFieldMap().getNotNullFields().getQuery(EQ, COMMA);
		return result.setQuery(String.format(DELETE, objectAnalyzer.getTableInfo().getWrappedTableName(), result.getQueryString()));
	}

	public Query insert(Object record) {
		ObjectAnalyzer objectAnalyzer = new ObjectAnalyzer(record);
		Query result = objectAnalyzer.getFieldMap().getNotNullFields().getQuery("", COMMA);
		return result.setQuery(String.format(INSERT, objectAnalyzer.getTableInfo().getWrappedTableName(), result.getQueryString(),
				makeQ(result.size())));
	}

	public Query update(Object record) {
		ObjectAnalyzer objectAnalyzer = new ObjectAnalyzer(record);
		Query fields = objectAnalyzer.getFieldMap().getFields().getQuery(EQ, COMMA);
		Query keys = objectAnalyzer.getFieldMap().getKeyFields().getQuery(EQ, COMMA);
		Query query = new Query();
		query.concatParameters(fields);
		query.concatParameters(keys);
		return query.setQuery(String.format(UPDATE, objectAnalyzer.getTableInfo().getWrappedTableName(), fields.getQueryString(),
				keys.getQueryString()));
	}

	private static final String QC = "?, ";

	private final static String INSERT_IF_EXISTS_UPDATE = "INSERT INTO %s (%s) VALUES (%s) ON DUPLICATE KEY UPDATE %s";

	public Query insertIfExistUpdate(Object record) {
		ObjectAnalyzer objectAnalyzer = new ObjectAnalyzer(record);
		Query notNullfields = objectAnalyzer.getFieldMap().getNotNullFields().getQuery("", COMMA);
		Query fields = objectAnalyzer.getFieldMap().getFields().getQuery(EQ, COMMA);
		Query query = new Query();
		query.concatParameters(notNullfields);
		query.concatParameters(fields);
		return query.setQuery(String.format(INSERT_IF_EXISTS_UPDATE, objectAnalyzer.getTableInfo().getWrappedTableName(), notNullfields.getQueryString(),
				makeQ(notNullfields.size()), fields.getQueryString()));
	}

	public String makeQ(int length) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < length; i++) {
			result.append(QC);
		}
		result.delete(result.length() - 2, result.length());
		return result.toString();
	}

}
