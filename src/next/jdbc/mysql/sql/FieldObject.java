package next.jdbc.mysql.sql;

import next.jdbc.mysql.exception.RegexNotMatchedException;

public class FieldObject {

	private Object param;
	private SqlField field;

	public FieldObject(Object param, SqlField field) {
		this.param = param;
		this.field = field;
		if (param == null)
			return;
		if (!field.check(param))
			throw new RegexNotMatchedException();
	}

	public Object getParam() {
		return param;
	}

	public String getColumnName() {
		return field.getColumnName();
	}

}
