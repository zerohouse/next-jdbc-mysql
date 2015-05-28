package next.jdbc.mysql.sql.analyze.info;

import java.lang.reflect.Field;

import next.jdbc.mysql.annotation.Column;

public class FieldInfo {

	private String columnName;

	public FieldInfo(String prefix, String suffix, Field field) {
		columnName = prefix + field.getName() + suffix;
		if (!field.isAnnotationPresent(Column.class))
			return;
		Column column = field.getAnnotation(Column.class);
		if ("".equals(column.value()))
			return;
		columnName = prefix + column.value() + suffix;
	}

	public String getColumnName() {
		return columnName;
	}

}
