package next.jdbc.mysql.query;

import next.jdbc.mysql.sql.Sql;

public class QueryNeedCondition {

	private WhereQuery query;

	public QueryNeedCondition(WhereQuery query) {
		this.query = query;
	}

	private static final String AND = " AND ";
	private static final String OR = " OR ";

	public QueryNeedField and() {
		query.where.append(AND);
		return new QueryNeedField(query);
	}

	public QueryNeedField or() {
		query.where.append(OR);
		return new QueryNeedField(query);
	}

	@Override
	public String toString() {
		return query.toString();
	}

	public Boolean execute() {
		Sql sql = query.getSql();
		return query.dao.execute(sql.getQueryString(), sql.getParameterArray());
	}

}
