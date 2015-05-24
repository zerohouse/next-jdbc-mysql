package next.jdbc.mysql.sql;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.regex.Pattern;

import next.jdbc.mysql.annotation.Column;
import next.jdbc.mysql.annotation.Key;
import next.jdbc.mysql.annotation.RequiredRegex;
import next.jdbc.mysql.setting.Setting;
import next.jdbc.mysql.setting.Table;
import next.jdbc.mysql.setting.TableCreate;

public class SqlFieldNormal implements SqlField {

	private static final String AUTO_INCREMENT = "AUTO_INCREMENT";
	private static final String DEFAULT = "DEFAULT";
	private static final String NOT = "NOT";
	private static final String NULL = "NULL";
	private static final String Q = "`";
	private final static String SPACE = " ";

	SqlFieldNormal(String prefix, String tableName, Field field) {
		this.field = field;
		this.prefix = prefix.replace("$table", tableName);
		setCondition(Setting.getCreateOption());
		setFieldString();
		if (!field.isAnnotationPresent(RequiredRegex.class))
			return;
		String regex = field.getAnnotation(RequiredRegex.class).value();
		pattern = Pattern.compile(regex);
	}

	public boolean check(Object param) {
		if (pattern == null)
			return true;
		if (pattern.matcher(param.toString()).matches()) {
			return true;
		}
		return false;
	}

	private String prefix;
	private Pattern pattern;
	private Field field;
	private String columnName;

	private String fieldString;

	@Override
	public String getColumnName() {
		return prefix + columnName;
	}

	public String getWrappedColumnName() {
		return Q + getColumnName() + Q;
	}

	public String getFieldString() {
		return fieldString;
	}

	private void setCondition(TableCreate options) {
		Class<?> t = field.getType();
		if (t.equals(Integer.class) || t.equals(int.class)) {
			setSettings(options.getIntegerOptions());
		} else if (t.equals(String.class)) {
			Table to = options.getStringOptions();
			if (to.getDefaultValue().equals(""))
				to.setDefaultValue("''");
			setSettings(to);
		} else if (t.equals(Date.class)) {
			setSettings(options.getDateOptions());
		} else if (t.equals(long.class) || t.equals(Long.class)) {
			setSettings(options.getLongOptions());
		} else if (t.equals(float.class) || t.equals(Float.class)) {
			setSettings(options.getFloatOptions());
		} else if (t.equals(boolean.class) || t.equals(Boolean.class)) {
			setSettings(options.getBooleanOptions());
		}
	}

	private String defaultValue;
	private String nullType;
	private String type;

	private void setSettings(Table options) {
		defaultValue = "";
		nullType = NULL;
		this.type = options.getDataType();
		if (!options.getNotNull())
			return;
		nullType = NOT + SPACE + nullType;
		if (!options.getHasDefaultValue())
			return;
		String defaultvalue = options.getDefaultValue().toString();
		defaultValue += DEFAULT + SPACE + defaultvalue;
	}

	private void setFieldString() {
		fieldString = "";
		columnName = field.getName();
		if (!field.isAnnotationPresent(Column.class)) {
			fieldString += getWrappedColumnName() + SPACE + type + SPACE;
			if (field.isAnnotationPresent(Key.class) && field.getAnnotation(Key.class).AUTO_INCREMENT()) {
				fieldString += AUTO_INCREMENT + SPACE + NOT + SPACE + NULL;
				return;
			}
			fieldString += nullType;
			if (field.isAnnotationPresent(Key.class))
				return;
			fieldString += SPACE + defaultValue;
			return;
		}
		Column column = field.getAnnotation(Column.class);
		if (!column.value().equals(""))
			columnName = column.value();
		fieldString += getWrappedColumnName() + SPACE;

		if (column.DATA_TYPE().equals(""))
			fieldString += type + SPACE;
		else
			fieldString += column.DATA_TYPE() + SPACE;

		if (field.isAnnotationPresent(Key.class) && field.getAnnotation(Key.class).AUTO_INCREMENT()) {
			fieldString += AUTO_INCREMENT + SPACE;
			return;
		}

		if (column.NULL())
			fieldString += NULL + SPACE;
		else
			fieldString += NOT + SPACE + NULL + SPACE;

		if (field.isAnnotationPresent(Key.class))
			return;
		if (!column.hasDefaultValue())
			return;
		if (!column.DEFAULT().equals(""))
			fieldString += DEFAULT + SPACE + column.DEFAULT();
		else
			fieldString += defaultValue;

	}

}