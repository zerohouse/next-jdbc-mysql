package next.jdbc.mysql.query;

public class QueryNeedFieldForSelect<T> {

	private SelectQuery<T> query;

	public QueryNeedFieldForSelect(SelectQuery<T> query) {
		this.query = query;
	}

	/**
	 * 필드를 선택합니다.
	 * <p>
	 *
	 * @param fieldName
	 *            찾을 필드의 이름입니다. [fieldName] or [className].[fieldName]
	 * @return QueryNeedValueForSelect
	 */
	public QueryNeedValueForSelect<T> field(String fieldName) {
		query.makeWhere();
		return new QueryNeedValueForSelect<T>(fieldName, query);
	}

	@Override
	public String toString() {
		return query.toString();
	}

}
