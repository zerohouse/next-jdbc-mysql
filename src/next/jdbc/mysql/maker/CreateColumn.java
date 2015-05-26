package next.jdbc.mysql.maker;

import java.lang.reflect.Field;
import java.util.Date;

import next.jdbc.mysql.annotation.Column;
import next.jdbc.mysql.annotation.Key;
import next.jdbc.mysql.setting.Setting;
import next.jdbc.mysql.setting.TableCreate;
import next.jdbc.mysql.setting.TableOptions;

public class CreateColumn {

	private static final String AUTO_INCREMENT = "AUTO_INCREMENT";
	private static final String DEFAULT = "DEFAULT";
	private static final String NOT = "NOT";
	private static final String NULL = "NULL";
	private static final String Q = "`";
	private final static String SPACE = " ";

	private String columnName;
	private Field field;
	private String defaultValue;
	private String nullType;
	private String type;

	public CreateColumn(String prefix, String suffix, Field field) {
		this.field = field;
		setCondition(Setting.getCreateOption());
		columnName = prefix + field.getName() + suffix;
		if (!field.isAnnotationPresent(Column.class))
			return;
		Column column = field.getAnnotation(Column.class);
		if ("".equals(column.value()))
			return;
		columnName = prefix + column.value() + suffix;
	}

	public String getColumnName() {
		return Q + columnName + Q;
	}

	private void setCondition(TableCreate options) {
		Class<?> t = field.getType();
		if (t.equals(Integer.class) || t.equals(int.class)) {
			setSettings(options.getIntegerOptions());
		} else if (t.equals(String.class)) {
			TableOptions to = options.getStringOptions();
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

	private void setSettings(TableOptions options) {
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

	public String getCreateString() {
		String result = "";
		columnName = field.getName();
		if (!field.isAnnotationPresent(Column.class)) {
			result += getColumnName() + SPACE + type + SPACE;
			if (field.isAnnotationPresent(Key.class) && field.getAnnotation(Key.class).AUTO_INCREMENT()) {
				result += AUTO_INCREMENT + SPACE + NOT + SPACE + NULL;
				return result;
			}
			result += nullType;
			if (field.isAnnotationPresent(Key.class))
				return result;
			result += SPACE + defaultValue;
			return result;
		}
		Column column = field.getAnnotation(Column.class);
		if (!column.value().equals(""))
			columnName = column.value();
		result += getColumnName() + SPACE;

		if (column.DATA_TYPE().equals(""))
			result += type + SPACE;
		else
			result += column.DATA_TYPE() + SPACE;

		if (field.isAnnotationPresent(Key.class) && field.getAnnotation(Key.class).AUTO_INCREMENT()) {
			result += AUTO_INCREMENT + SPACE;
			return result;
		}

		if (column.NULL())
			result += NULL + SPACE;
		else
			result += NOT + SPACE + NULL + SPACE;

		if (field.isAnnotationPresent(Key.class))
			return result;
		if (!column.hasDefaultValue())
			return result;
		if (!column.DEFAULT().equals(""))
			result += DEFAULT + SPACE + column.DEFAULT();
		else
			result += defaultValue;
		return result;

	}

}
