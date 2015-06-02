package next.jdbc.mysql.query;

public class QueryNeedFieldForSelect<T> {

	private SelectQuery<T> query;

	public QueryNeedFieldForSelect(SelectQuery<T> query) {
		this.query = query;
	}

	public QueryNeedValueForSelect<T> field(String fieldName) {
		query.makeWhere();
		return new QueryNeedValueForSelect<T>(fieldName, query);
	}

	@Override
	public String toString() {
		return query.toString();
	}

}
