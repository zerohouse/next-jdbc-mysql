package next.database.sql;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import next.database.annotation.Exclude;
import next.database.annotation.Key;
import next.database.annotation.OtherTable;

public class KeyParams {
	protected static final String GET = "get";

	protected List<FieldObject> params;
	protected List<FieldObject> keyParams;
	protected String tableName;

	public KeyParams() {
	}

	public KeyParams(Class<?> cLass, SqlSupports sqlSupports, String tableName) {
		this.tableName = tableName;
		Field[] fields = cLass.getDeclaredFields();
		params = new ArrayList<FieldObject>();
		keyParams = new ArrayList<FieldObject>();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isAnnotationPresent(Exclude.class))
				continue;
			if (fields[i].isAnnotationPresent(OtherTable.class))
				continue;
			if (fields[i].isAnnotationPresent(Key.class)) {
				keyParams.add(new FieldObject(null, sqlSupports.getSqlField(fields[i])));
				continue;
			}
			params.add(new FieldObject(null, sqlSupports.getSqlField(fields[i])));
		}
	}

	public KeyParams(Object record, SqlSupports sqlSupports) {
		Class<?> cLass = record.getClass();
		this.tableName = sqlSupports.getTableName(cLass);
		Field[] fields = cLass.getDeclaredFields();
		params = new ArrayList<FieldObject>();
		keyParams = new ArrayList<FieldObject>();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isAnnotationPresent(Exclude.class))
				continue;
			if (fields[i].isAnnotationPresent(OtherTable.class))
				continue;
			try {
				fields[i].setAccessible(true);
				Object param = fields[i].get(record);
				if (param == null)
					continue;
				if (fields[i].isAnnotationPresent(Key.class)) {
					keyParams.add(new FieldObject(param, sqlSupports.getSqlField(fields[i])));
					continue;
				}
				params.add(new FieldObject(param, sqlSupports.getSqlField(fields[i])));
			} catch (IllegalAccessException | IllegalArgumentException | SecurityException e) {
				e.printStackTrace();
			}
		}
	}

	public String getFieldNames(String suffix, String deter) {
		String result = new String();
		if (!hasParams())
			return result;
		for (int i = 0; i < params.size(); i++)
			result += params.get(i).getColumnName() + suffix + deter;
		result = result.substring(0, result.length() - deter.length());
		return result;
	}

	public boolean hasParams() {
		return params.size() != 0;
	}

	public String getKeyFieldNames(String suffix, String deter) {
		String result = new String();
		if (!hasKeyParams())
			return result;
		for (int i = 0; i < keyParams.size(); i++)
			result += keyParams.get(i).getColumnName() + suffix + deter;
		result = result.substring(0, result.length() - deter.length());
		return result;
	}

	public boolean hasKeyParams() {
		return keyParams.size() != 0;
	}

	public boolean isEmpty() {
		return !hasKeyParams() && !hasParams();
	}

	public List<Object> getParams() {
		List<Object> result = new ArrayList<Object>();
		params.forEach(param -> {
			result.add(param.getParam());
		});
		return result;
	}

	public List<Object> getKeyParams() {
		List<Object> result = new ArrayList<Object>();
		keyParams.forEach(param -> {
			result.add(param.getParam());
		});
		return result;
	}

	public String getTableName() {
		return tableName;
	}

	public String getIntegratedFieldNames(String suffix, String deter) {
		if (isEmpty())
			return "";
		if (keyParams.isEmpty())
			return getFieldNames(suffix, deter);
		if (params.isEmpty())
			return getKeyFieldNames(suffix, deter);
		return getFieldNames(suffix, deter) + deter + getKeyFieldNames(suffix, deter);
	}

	public Object[] getIntegratedParams() {
		if (isEmpty())
			return null;
		if (keyParams.isEmpty())
			return getParams().toArray();
		if (params.isEmpty())
			return getKeyParams().toArray();
		List<Object> result = new ArrayList<Object>();
		result.addAll(getParams());
		result.addAll(getKeyParams());
		return result.toArray();
	}
}
