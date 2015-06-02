package next.jdbc.mysql.query;

public class QueryNeedField {

	private WhereQuery query;

	public QueryNeedField(WhereQuery query) {
		this.query = query;
	}

	/**
	 * 필드를 선택합니다.
	 * <p>
	 *
	 * @param fieldName
	 *            찾을 필드의 이름입니다. [fieldName] or [className].[fieldName]
	 */
	public QueryNeedValue field(String fieldName) {
		query.makeWhere();
		return new QueryNeedValue(fieldName, query);
	}

	@Override
	public String toString() {
		return query.toString();
	}

}
