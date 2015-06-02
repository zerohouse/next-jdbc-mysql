package next.jdbc.mysql.query;

import next.jdbc.mysql.sql.analyze.info.FieldInfo;

public class QueryNeedValueForSelect<T> {

	private SelectQuery<T> query;
	private FieldInfo fieldInfo;
	private static final String EQ = "=?";
	private static final String LIKE = " LIKE ";

	public QueryNeedValueForSelect(String fieldName, SelectQuery<T> selectQuery) {
		this.query = selectQuery;
		this.fieldInfo = query.typeAnalyzer.get(fieldName);
	}

	public QueryNeedConditionForSelect<T> like(Object value) {
		String val = value.toString();
		query.makeWhere();
		query.where.append(fieldInfo.getColumnName());
		query.where.append(LIKE);
		if (val.startsWith("'"))
			query.where.append(val);
		else {
			query.where.append("'");
			if (!val.contains("%"))
				query.where.append("%");
			query.where.append(val);
			if (!val.contains("%"))
				query.where.append("%");
			query.where.append("'");
		}
		return new QueryNeedConditionForSelect<T>(query);
	}

	public QueryNeedConditionForSelect<T> equal(Object value) {
		query.makeWhere();
		query.where.append(fieldInfo.getColumnName());
		query.where.append(EQ);
		query.where.addParameter(value);
		return new QueryNeedConditionForSelect<T>(query);
	}

	@Override
	public String toString() {
		return query.toString();
	}

}
