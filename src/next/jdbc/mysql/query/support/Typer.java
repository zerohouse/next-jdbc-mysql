package next.jdbc.mysql.query.support;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import next.jdbc.mysql.annotation.Table;
import next.jdbc.mysql.constants.Constants;
import next.jdbc.mysql.query.support.exception.FieldNotFoundException;
import next.jdbc.mysql.sql.analyze.info.FieldInfo;
import next.jdbc.mysql.sql.analyze.info.TableInfo;

public class Typer {

	Map<String, FieldInfo> map;
	String tableName;

	public Typer(Class<?> type) {
		map = new HashMap<String, FieldInfo>();
		tableName = getTableName(type);
		object(type);
	}

	public static String getTableName(Class<?> type) {
		Table table = type.getAnnotation(Table.class);
		String result = table.value().equals("") ? type.getSimpleName() : table.value();
		return Constants.wrapped(result);
	}

	private static final String DOT = ".";

	private void object(Class<?> type) {
		Field[] fields = type.getDeclaredFields();
		TableInfo info = new TableInfo(type);
		for (int i = 0; i < fields.length; i++) {
			map.put(fields[i].getName(), new FieldInfo(info, fields[i]));
			map.put(type.getSimpleName() + DOT + fields[i].getName(), new FieldInfo(info, fields[i]));
		}
	}

	public String getColumnName(String fieldName) {
		FieldInfo info = map.get(fieldName);
		if (info == null)
			return fieldName;
		return info.getColumnName();
	}

	public FieldInfo get(String fieldName) {
		FieldInfo info = map.get(fieldName);
		if (info == null)
			throw new FieldNotFoundException(fieldName);
		return info;
	}

	public String getTableName() {
		return tableName;
	}

	public void concat(Typer typer) {
		this.map.putAll(typer.map);
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
