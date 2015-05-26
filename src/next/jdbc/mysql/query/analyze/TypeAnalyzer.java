package next.jdbc.mysql.query.analyze;

import java.lang.reflect.Field;

import next.jdbc.mysql.annotation.Exclude;
import next.jdbc.mysql.annotation.Key;
import next.jdbc.mysql.query.analyze.bind.FieldObject;
import next.jdbc.mysql.query.analyze.bind.Fields;
import next.jdbc.mysql.query.analyze.info.TableInfo;

public class TypeAnalyzer {

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
				keyFields.add(new FieldObject(null, tableInfo.getPrefix(), tableInfo.getSuffix(), fields[i]));
				continue;
			}
			this.fields.add(new FieldObject(null, tableInfo.getPrefix(), tableInfo.getSuffix(), fields[i]));
		}
	}

	public Fields getKeyFields() {
		return keyFields;
	}

	public Fields getFields() {
		return fields;
	}
	
	public Fields getAllFields() {
		Fields result = new Fields();
		result.concat(fields);
		result.concat(keyFields);
		return result;
	}

	public TableInfo getTableInfo() {
		return tableInfo;
	}

}
