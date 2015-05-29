package next.jdbc.mysql.sql.analyze.info;

import java.lang.reflect.Field;

import next.jdbc.mysql.annotation.Column;
import next.jdbc.mysql.constants.Constants;

public class FieldInfo {

	private String columnName;
	private TableInfo info;

	public FieldInfo(TableInfo info, Field field) {
		setColumnName(field);
		columnName = info.getColumnPrefix() + columnName + info.getColumnSuffix();
		this.info = info;
	}

	private void setColumnName(Field field) {
		columnName = field.getName();
		if (!field.isAnnotationPresent(Column.class))
			return;
		Column column = field.getAnnotation(Column.class);
		if ("".equals(column.value()))
			return;
		columnName = column.value();
	}

	public String getColumnName() {
		return info.getTableName() + Constants.DOT + Constants.wrapped(columnName);
	}

}
