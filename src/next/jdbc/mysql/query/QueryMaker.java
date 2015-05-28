package next.jdbc.mysql.query;

import java.util.Arrays;
import java.util.List;

import next.jdbc.mysql.query.analyze.Analyzer;
import next.jdbc.mysql.query.analyze.bind.Fields;

public class QueryMaker {

	private static final String ASTAR = "*";
	private static final String EQ = "=?";
	private static final String COMMA = ", ";
	private static final String AND = " AND ";

	private static final String SELECT = "SELECT %s FROM %s WHERE %s";
	private static final String INSERT = "INSERT INTO %s (%s) VALUES (%s)";
	private static final String UPDATE = "UPDATE %s SET %s WHERE %s";
	private static final String DELETE = "DELETE FROM %s WHERE %s";

	public Query select(Analyzer analyzer) {
		Query result = analyzer.getNotNullAllFields().getQuery(EQ, AND);
		return result.setQuery(String.format(SELECT, ASTAR, analyzer.getTableName(), result.getQueryString()));
	}

	public Query insert(Analyzer analyzer) {
		Query result = analyzer.getNotNullAllFields().getQuery("", COMMA);
		return result.setQuery(String.format(INSERT, analyzer.getTableName(), result.getQueryString(), makeQ(result.size())));
	}

	public Query update(Analyzer analyzer) {
		Query fields = analyzer.getNotNullFields().getQuery(EQ, COMMA);
		Query keys = analyzer.getNotNullKeyFields().getQuery(EQ, AND);
		Query query = new Query();
		query.concatParameters(fields);
		query.concatParameters(keys);
		return query.setQuery(String.format(UPDATE, analyzer.getTableName(), fields.getQueryString(), keys.getQueryString()));
	}

	public Query update(Analyzer analyzer, String... keyFieldName) {
		Fields update = new Fields();
		Fields key = new Fields();
		List<String> keyFields = Arrays.asList(keyFieldName);
		analyzer.getNotNullAllFields().forEach(field -> {
			if (keyFields.contains(field.getField().getName())) {
				key.add(field);
				return;
			}
			update.add(field);
		});
		Query query = new Query();
		Query fields = update.getQuery(EQ, COMMA);
		Query keys = key.getQuery(EQ, AND);
		query.concatParameters(fields);
		query.concatParameters(keys);
		return query.setQuery(String.format(UPDATE, analyzer.getTableName(), fields.getQueryString(), keys.getQueryString()));
	}

	public Query delete(Analyzer analyzer) {
		Query result = analyzer.getNotNullAllFields().getQuery(EQ, AND);
		return result.setQuery(String.format(DELETE, analyzer.getTableName(), result.getQueryString()));
	}

	private static final String QC = "?, ";

	private final static String INSERT_IF_EXISTS_UPDATE = "INSERT INTO %s (%s) VALUES (%s) ON DUPLICATE KEY UPDATE %s";

	public Query insertIfExistUpdate(Analyzer analyzer) {
		Query notNullfields = analyzer.getNotNullAllFields().getQuery("", COMMA);
		Query fields = analyzer.getNotNullFields().getQuery(EQ, COMMA);
		Query query = new Query();
		query.concatParameters(notNullfields);
		query.concatParameters(fields);
		return query.setQuery(String.format(INSERT_IF_EXISTS_UPDATE, analyzer.getTableName(), notNullfields.getQueryString(),
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
