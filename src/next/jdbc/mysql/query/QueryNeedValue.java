package next.jdbc.mysql.query;

import next.jdbc.mysql.sql.analyze.info.FieldInfo;

public class QueryNeedValue {

	private WhereQuery query;
	private FieldInfo fieldInfo;
	private static final String EQ = "=?";
	private static final String LIKE = " LIKE ";

	public QueryNeedValue(String fieldName, WhereQuery query) {
		this.query = query;
		this.fieldInfo = query.typeAnalyzer.get(fieldName);
	}

	public QueryNeedCondition like(Object value) {
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
		return new QueryNeedCondition(query);
	}

	public QueryNeedCondition equal(Object value) {
		query.makeWhere();
		query.where.append(fieldInfo.getColumnName());
		query.where.append(EQ);
		query.where.addParameter(value);
		return new QueryNeedCondition(query);
	}

	@Override
	public String toString() {
		return query.toString();
	}

}
