package next.jdbc.mysql.sql.analyze;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

import next.jdbc.mysql.annotation.Exclude;
import next.jdbc.mysql.annotation.Key;
import next.jdbc.mysql.sql.analyze.bind.FieldObject;
import next.jdbc.mysql.sql.analyze.bind.Fields;
import next.jdbc.mysql.sql.analyze.info.TableInfo;

public class TypeAnalyzer implements Analyzer {

	protected Fields keyFields;
	protected Fields fields;
	protected TableInfo tableInfo;

	protected TypeAnalyzer() {
		this.fields = new Fields();
		keyFields = new Fields();
	}

	public TypeAnalyzer(Class<?> type) {
		tableInfo = new TableInfo(type);
		this.fields = new Fields();
		keyFields = new Fields();
		Field[] fields = type.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isAnnotationPresent(Exclude.class))
				continue;
			if (fields[i].isAnnotationPresent(Key.class)) {
				keyFields.add(new FieldObject(null, fields[i], tableInfo));
				continue;
			}
			this.fields.add(new FieldObject(null, fields[i], tableInfo));
		}
	}

	@Override
	public Fields getNotNullKeyFields() {
		return new Fields(keyFields.stream().filter(field -> field.getObject() != null).collect(Collectors.toList()));
	}

	@Override
	public Fields getNotNullFields() {
		return new Fields(fields.stream().filter(field -> field.getObject() != null).collect(Collectors.toList()));
	}

	@Override
	public Fields getAllFields() {
		Fields result = new Fields();
		result.concat(fields);
		result.concat(keyFields);
		return result;
	}

	@Override
	public String getTableName() {
		return tableInfo.getTableName();
	}

	public String getColumnPrefix() {
		return tableInfo.getColumnPrefix();
	}

	public String getColumnSuffix() {
		return tableInfo.getColumnSuffix();
	}

	@Override
	public Fields getNotNullAllFields() {
		return new Fields(getAllFields().stream().filter(field -> field.getObject() != null).collect(Collectors.toList()));
	}

	@Override
	public void setKeyParameters(Object[] parameters) {
		for (int i = 0; i < parameters.length; i++)
			keyFields.get(i).setObject(parameters[i]);
	}
}
