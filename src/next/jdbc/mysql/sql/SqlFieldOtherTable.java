package next.jdbc.mysql.sql;

import java.lang.reflect.Field;

import next.jdbc.mysql.annotation.OtherTable;

public class SqlFieldOtherTable implements SqlField {

	private String columnName;

	public SqlFieldOtherTable(Field field) {
		columnName = field.getName();
		OtherTable othertable = field.getAnnotation(OtherTable.class);
		if (!othertable.COLUMN_NAME().equals(""))
			columnName = othertable.COLUMN_NAME();
	}

	@Override
	public String getColumnName() {
		return columnName;
	}

	@Override
	public boolean check(Object param) {
		return true;
	}

}
