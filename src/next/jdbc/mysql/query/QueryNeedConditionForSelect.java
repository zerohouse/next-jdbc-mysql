package next.jdbc.mysql.query;

import java.util.List;

import next.jdbc.mysql.query.support.Limit;

public class QueryNeedConditionForSelect<T> {

	private SelectQuery<T> query;

	public QueryNeedConditionForSelect(SelectQuery<T> query) {
		this.query = query;
	}

	private static final String AND = " AND ";
	private static final String OR = " OR ";

	public QueryNeedFieldForSelect<T> and() {
		query.where.append(AND);
		return new QueryNeedFieldForSelect<T>(query);
	}

	public QueryNeedFieldForSelect<T> or() {
		query.where.append(OR);
		return new QueryNeedFieldForSelect<T>(query);
	}

	public QueryNeedConditionForSelect<T> limit(int begin, int end) {
		query.limit = new Limit(begin, end);
		return this;
	}

	public QueryNeedConditionForSelect<T> orderBy(String fieldName) {
		return orderBy(fieldName, false);
	}

	public QueryNeedConditionForSelect<T> orderBy(String fieldName, boolean desc) {
		query.orderBy.order(query.typeAnalyzer.get(fieldName).getColumnName(), desc);
		return this;
	}

	public T find() {
		return query.find();
	}

	public List<T> findList() {
		return query.findList();
	}

	@Override
	public String toString() {
		return query.toString();
	}

}
