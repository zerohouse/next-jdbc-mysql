package next.database.sql;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import next.database.annotation.OtherTable;
import next.database.annotation.Table;
import next.database.setting.Setting;

public class SqlSupports {

	private Map<Class<?>, KeyParams> keyParamsMap;
	private Map<Field, SqlField> sqlFieldMap;
	private Map<Class<?>, String> tableNameMap;

	public SqlSupports() {
		keyParamsMap = new ConcurrentHashMap<Class<?>, KeyParams>();
		sqlFieldMap = new ConcurrentHashMap<Field, SqlField>();
		tableNameMap = new ConcurrentHashMap<Class<?>, String>();
		Setting.getReflections().getTypesAnnotatedWith(Table.class).forEach(each -> {
			defineClass(each);
		});
	}

	private void defineClass(Class<?> cLass) {
		String tableName = cLass.getSimpleName();
		if (cLass.isAnnotationPresent(Table.class)) {
			Table table = cLass.getAnnotation(Table.class);
			if (!table.value().equals(""))
				tableName = table.value();
		}
		tableNameMap.put(cLass, tableName);

		// 필드 먼저 생성하고 넘겨줘야댐. KeyParams 생성할때 생성된 Sql필드 사용함.
		Field[] fields = cLass.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isAnnotationPresent(OtherTable.class)) {
				sqlFieldMap.put(fields[i], new SqlFieldOtherTable(fields[i]));
			} else {
				sqlFieldMap.put(fields[i], new SqlFieldNormal(tableName, fields[i]));
			}
		}
		keyParamsMap.put(cLass, new KeyParams(cLass, this, tableName));
	}

	public KeyParams getKeyParams(Class<?> cLass) {
		KeyParams result = keyParamsMap.get(cLass);
		if (result == null) {
			defineClass(cLass);
			result = getKeyParams(cLass);
		}
		return result;
	}

	public SqlField getSqlField(Field field) {
		SqlField result = sqlFieldMap.get(field);
		if (result == null) {
			defineClass(field.getDeclaringClass());
			result = getSqlField(field);
		}
		return result;
	}

	public KeyParams getKeyParams(Object record) {
		return new KeyParams(record, this);
	}

	public String getTableName(Class<?> cLass) {
		return getKeyParams(cLass).getTableName();
	}

}
