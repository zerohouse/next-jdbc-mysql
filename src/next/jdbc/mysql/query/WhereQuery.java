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

	void makeWhere() {
		if (where == null)
			where = new Sql();
	}

	abstract Sql getSql();

}
