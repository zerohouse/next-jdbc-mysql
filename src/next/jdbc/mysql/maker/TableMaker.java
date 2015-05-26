package next.jdbc.mysql.maker;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import next.jdbc.mysql.DAORaw;
import next.jdbc.mysql.annotation.Column;
import next.jdbc.mysql.annotation.Exclude;
import next.jdbc.mysql.annotation.Key;
import next.jdbc.mysql.annotation.Table;
import next.jdbc.mysql.query.analyze.TypeAnalyzer;

public class TableMaker {

	private DAORaw dao;
	private Class<?> type;
	private TypeAnalyzer analyzer;

	public TableMaker(Class<?> type) {
		this.dao = new DAORaw();
		this.type = type;
		analyzer = new TypeAnalyzer(type);
	}

	private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS %s (%s) %s";

	public void createTable() {
		dao.execute(createQuery());
	}

	public String createQuery() {
		return String.format(CREATE_TABLE, analyzer.getTableInfo().getWrappedTableName(), getColumnString(), analyzer.getTableInfo().getSuffix());
	}

	private static final String DROP_TABLE = "DROP TABLE IF EXISTS %s";

	public void dropTable() {
		if (type.getAnnotation(Table.class).neverDrop())
			return;
		dao.execute(dropQuery());
	}

	public String dropQuery() {
		return String.format(DROP_TABLE, analyzer.getTableInfo().getWrappedTableName());
	}

	public void reset() {
		dropTable();
		createTable();
	}

	private static final String PRIMARY_KEY = "PRIMARY KEY";
	private Map<String, SqlFunction> functions = new HashMap<String, SqlFunction>();

	private String getColumnString() {
		Field[] fields = type.getDeclaredFields();
		String result = "";
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isAnnotationPresent(Exclude.class))
				continue;
			CreateColumn col = new CreateColumn(analyzer.getTableInfo().getPrefix(), analyzer.getTableInfo().getSuffix(), fields[i]);
			result += col.getCreateString() + ", ";
			if (fields[i].isAnnotationPresent(Key.class)) {
				addFunction(col, PRIMARY_KEY);
			}
			if (fields[i].isAnnotationPresent(Column.class)) {
				String[] fs = fields[i].getAnnotation(Column.class).function();
				for (int j = 0; j < fs.length; j++)
					if (!fs[j].equals(""))
						addFunction(col, fs[j].toUpperCase());
			}
		}

		for (Map.Entry<String, SqlFunction> entry : functions.entrySet()) {
			result += entry.getValue().getFunctionString(entry.getKey()) + ", ";
		}
		if (!functions.isEmpty())
			result = result.substring(0, result.length() - 2);
		return result;
	}

	private void addFunction(CreateColumn col, String key) {
		SqlFunction sf = functions.get(key);
		if (sf == null) {
			sf = new SqlFunction();
			functions.put(key, sf);
		}
		sf.add(col);
	}

}
