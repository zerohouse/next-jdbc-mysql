package next.jdbc.mysql.query;

import java.util.List;

import next.jdbc.mysql.DAOQuery;
import next.jdbc.mysql.query.support.Typer;
import next.jdbc.mysql.query.support.Limit;
import next.jdbc.mysql.query.support.OrderBy;
import next.jdbc.mysql.sql.Sql;

public class SelectQuery<T> {

	Sql where;
	Typer typeAnalyzer;
	Limit limit;
	OrderBy orderBy;
	Class<T> type;
	DAOQuery dao;

	public SelectQuery(DAOQuery dao, Class<T> type) {
		this.type = type;
		orderBy = new OrderBy();
		typeAnalyzer = new Typer(type);
		this.dao = dao;
	}

	@Override
	public String toString() {
		return getSql().getQueryString();
	}

	Sql getSql() {
		Sql result = new Sql(String.format(FORMAT, ASTAR, typeAnalyzer.getTableName()));
		if (where != null) {
			result.append(WHERE);
			result.concat(where);
		}
		if (!orderBy.isEmpty()) {
			orderBy.orderBy(result.getQuery());
		}
		if (limit != null) {
			limit.limit(result.getQuery());
		}
		return result;
	}

	private static final String FORMAT = "SELECT %s from %s";
	private static final String ASTAR = "*";
	private static final String WHERE = " WHERE ";

	public T find() {
		Sql sql = getSql();
		return (T) dao.get(type, sql.getQueryString(), sql.getParameterArray());
	}

	public List<T> findList() {
		Sql sql = getSql();
		return (List<T>) dao.getList(type, sql.getQueryString(), sql.getParameterArray());
	}

	public SelectQuery<T> limit(int begin, int end) {
		limit = new Limit(begin, end);
		return this;
	}

	public SelectQuery<T> orderBy(String fieldName) {
		return orderBy(fieldName, false);
	}

	public SelectQuery<T> orderBy(String fieldName, boolean desc) {
		orderBy.order(typeAnalyzer.get(fieldName).getColumnName(), desc);
		return this;
	}

	public QueryNeedValueForSelect<T> field(String fieldName) {
		makeWhere();
		return new QueryNeedValueForSelect<T>(fieldName, this);
	}

	void makeWhere() {
		if (where == null)
			where = new Sql();
	}
}
