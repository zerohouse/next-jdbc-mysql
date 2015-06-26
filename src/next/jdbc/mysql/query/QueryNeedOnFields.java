package next.jdbc.mysql.query;

import next.jdbc.mysql.query.support.Typer;

public class QueryNeedOnFields<T> {

	public QueryNeedOnFields(SelectQuery<T> query, Class<?> joinType) {
		this.query = query;
		this.joinType = joinType;
	}

	private Class<?> joinType;
	private SelectQuery<T> query;

	private static final String ON = "(%s) JOIN (%s) ON %s = %s";

	public SelectQuery<T> on(String fieldName, String fieldName2) {
		Typer typer = new Typer(joinType);
		query.typeAnalyzer.setTableName(String.format(ON, query.typeAnalyzer.getTableName(), Typer.getTableName(joinType),
				query.typeAnalyzer.getColumnName(fieldName), typer.getColumnName(fieldName2)));
		query.typeAnalyzer.concat(typer);
		return query;
	}
}
