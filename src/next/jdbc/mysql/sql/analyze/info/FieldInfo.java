package next.jdbc.mysql.sql.analyze.info;

import java.lang.reflect.Field;

import next.jdbc.mysql.annotation.Column;
import next.jdbc.mysql.annotation.RegexFormat;
import next.jdbc.mysql.constants.Constants;
import next.jdbc.mysql.exception.RegexNotMatchedException;

public class FieldInfo {

	private static final String $FIELD = "$field";
	private String columnName;
	private RegexFormat regex;
	private TableInfo info;
	private Field field;

	public FieldInfo(TableInfo info, Field field) {
		setColumnName(field);
		columnName = info.getColumnPrefix() + columnName + info.getColumnSuffix();
		this.info = info;
		this.field = field;
		if (!field.isAnnotationPresent(RegexFormat.class))
			return;
		regex = field.getAnnotation(RegexFormat.class);
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

	public Field getField() {
		return field;
	}

	public void regexMatchedCheck(Object object) {
		if (object == null)
			return;
		if (regex == null)
			return;
		if (object.toString().matches(regex.value()))
			return;
		throw new RegexNotMatchedException(regex.errorMessage().replace($FIELD, field.getName()));
	}
}
