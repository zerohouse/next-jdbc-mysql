package next.jdbc.mysql.query;

public class QueryNeedField {

	private WhereQuery query;

	public QueryNeedField(WhereQuery query) {
		this.query = query;
	}

	public QueryNeedValue field(String fieldName) {
		query.makeWhere();
		return new QueryNeedValue(fieldName, query);
	}

	@Override
	public String toString() {
		return query.toString();
	}

}
