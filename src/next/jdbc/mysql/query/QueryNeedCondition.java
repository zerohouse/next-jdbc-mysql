package next.jdbc.mysql.query;

import next.jdbc.mysql.sql.Sql;

public class QueryNeedCondition {

	private WhereQuery query;

	public QueryNeedCondition(WhereQuery query) {
		this.query = query;
	}

	private static final String AND = " AND ";
	private static final String OR = " OR ";

	/**
	 * and조건의 다른 필드를 선택합니다.
	 *
	 * @return QueryNeedField
	 */
	public QueryNeedField and() {
		query.where.append(AND);
		return new QueryNeedField(query);
	}

	/**
	 * or조건의 다른 필드를 선택합니다.
	 *
	 * @return QueryNeedField
	 */
	public QueryNeedField or() {
		query.where.append(OR);
		return new QueryNeedField(query);
	}

	@Override
	public String toString() {
		return query.toString();
	}

	/**
	 * 쿼리를 실행합니다.
	 *
	 * @return boolean 실행결과가 리턴됩니다.
	 */
	public Boolean execute() {
		Sql sql = query.getSql();
		return query.dao.execute(sql.getQueryString(), sql.getParameterArray());
	}

}
