package next.jdbc.mysql.query.analyze;

import java.lang.reflect.Field;

import next.jdbc.mysql.annotation.Exclude;
import next.jdbc.mysql.annotation.Key;
import next.jdbc.mysql.query.analyze.bind.FieldObject;
import next.jdbc.mysql.query.analyze.bind.Fields;
import next.jdbc.mysql.query.analyze.info.TableInfo;

public class TypeAnalyzer implements Analyzer {

	private Fields keyFields;
	private Fields fields;
	private TableInfo tableInfo;

	public TypeAnalyzer(Class<?> type) {
		tableInfo = new TableInfo(type);
		Field[] fields = type.getDeclaredFields();
		this.fields = new Fields();
		keyFields = new Fields();
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
	public Fields getKeyFields() {
		return keyFields;
	}

	@Override
	public Fields getFields() {
		return fields;
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
	public Fields getNotNullFields() {
		return new Fields();
	}

}
