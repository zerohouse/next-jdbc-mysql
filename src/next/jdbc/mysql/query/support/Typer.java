package next.jdbc.mysql.query.support;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

import next.jdbc.mysql.join.Join;
import next.jdbc.mysql.query.support.exception.FieldNotFoundException;
import next.jdbc.mysql.sql.analyze.info.FieldInfo;
import next.jdbc.mysql.sql.analyze.info.TableInfo;

public class Typer {

	Map<String, FieldInfo> map;
	private TableNamer namer;

	public Typer(Class<?> type) {
		map = new HashMap<String, FieldInfo>();
		namer = TableNamer.get(type, this);
		object(type);
	}

	private void join(Class<?> type) {
		object((Class<?>) ((ParameterizedType) type.getGenericSuperclass()).getActualTypeArguments()[0]);
		object((Class<?>) ((ParameterizedType) type.getGenericSuperclass()).getActualTypeArguments()[1]);
	}

	private static final String DOT = ".";

	private void object(Class<?> type) {
		if (Join.class.isAssignableFrom(type)) {
			join(type);
			return;
		}
		Field[] fields = type.getDeclaredFields();
		TableInfo info = new TableInfo(type);
		for (int i = 0; i < fields.length; i++) {
			map.put(fields[i].getName(), new FieldInfo(info, fields[i]));
			map.put(type.getSimpleName() + DOT + fields[i].getName(), new FieldInfo(info, fields[i]));
		}
	}

	public FieldInfo get(String fieldName) {
		FieldInfo info = map.get(fieldName);
		if (info == null)
			throw new FieldNotFoundException(fieldName);
		return info;
	}

	public String getTableName() {
		return namer.getName();
	}

}
