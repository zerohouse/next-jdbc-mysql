package next.database.maker;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import next.database.DAO;
import next.database.annotation.Column;
import next.database.annotation.Exclude;
import next.database.annotation.Key;
import next.database.annotation.OtherTable;
import next.database.annotation.Table;
import next.database.sql.SqlFieldNormal;
import next.database.sql.SqlSupports;
import next.database.setting.Setting;

public class TableMaker {

	private DAO dao;
	private Class<?> tableClass;
	private String tableName;
	private String table_suffix;
	private String createQuery;

	public TableMaker(Class<?> tableObj, DAO dao) {
		tableClass = tableObj;
		this.dao = dao;
		tableName = tableClass.getSimpleName();
		table_suffix = Setting.getCreateOption().getTable_suffix();
		if (!tableClass.isAnnotationPresent(Table.class))
			return;
		Table table = tableClass.getAnnotation(Table.class);
		if (!table.createQuery().equals(""))
			createQuery = table.createQuery();
		if (!table.value().equals(""))
			tableName = table.value();
		if (!table.table_suffix().equals(""))
			table_suffix = table.table_suffix();
	}

	private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `%s` %s %s";

	public void createTable() {
		String sql = createQuery;
		if (sql == null)
			sql = String.format(CREATE_TABLE, tableName, getColumnString(), table_suffix);
		dao.execute(sql);
	}

	private static final String DROP_TABLE = "DROP TABLE IF EXISTS `%s`";

	public void dropTable() {
		if (tableClass.getAnnotation(Table.class).neverDrop())
			return;
		String sql = String.format(DROP_TABLE, tableName);
		dao.execute(sql);
	}

	public void reset() {
		dropTable();
		createTable();
	}

	private static final String PRIMARY_KEY = "PRIMARY KEY";
	private Map<String, SqlFunction> functions = new HashMap<String, SqlFunction>();

	private String getColumnString() {
		Field[] fields = tableClass.getDeclaredFields();
		String result = "(";

		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isAnnotationPresent(OtherTable.class))
				continue;
			if (fields[i].isAnnotationPresent(Exclude.class))
				continue;
			SqlFieldNormal fm = (SqlFieldNormal) new SqlSupports().getSqlField(fields[i]);
			result += fm.getFieldString() + ", ";
			if (fields[i].isAnnotationPresent(Key.class)) {
				addFunction(fm, PRIMARY_KEY);
			}
			if (fields[i].isAnnotationPresent(Column.class)) {
				String[] fs = fields[i].getAnnotation(Column.class).function();
				for (int j = 0; j < fs.length; j++)
					if (!fs[j].equals(""))
						addFunction(fm, fs[j].toUpperCase());
			}
		}

		for (Map.Entry<String, SqlFunction> entry : functions.entrySet()) {
			result += entry.getValue().getFunctionString(entry.getKey()) + ", ";
		}
		if (!functions.isEmpty())
			result = result.substring(0, result.length() - 2);
		result += ")";
		return result;
	}

	private void addFunction(SqlFieldNormal fm, String key) {
		SqlFunction sf = functions.get(key);
		if (sf == null) {
			sf = new SqlFunction();
			functions.put(key, sf);
		}
		sf.add(fm);
	}

	@Override
	public String toString() {
		return tableName + getColumnString();
	}

}
