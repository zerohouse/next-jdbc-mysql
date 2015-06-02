package next.jdbc.mysql.query;

import next.jdbc.mysql.DAORaw;
import next.jdbc.mysql.query.support.Typer;
import next.jdbc.mysql.sql.Sql;

public abstract class WhereQuery extends Query {

	protected static final String WHERE = " WHERE ";
	protected static final String COMMA = ", ";
	protected static final String EQ = "=?";

	protected Sql where;
	protected Typer typeAnalyzer;

	public WhereQuery(DAORaw dao, Class<?> type) {
		super(dao);
		typeAnalyzer = new Typer(type);
	}

	/**
	 * field 이름으로 field를 선택합니다.<br>
	 * 포맷은 [fieldname] 혹은 [classname].[fieldname]입니다.
	 * <p>
	 *
	 */
	public QueryNeedValue field(String fieldName) {
		makeWhere();
		return new QueryNeedValue(fieldName, this);
	}

	void makeWhere() {
		if (where == null)
			where = new Sql();
	}

	abstract Sql getSql();

}
