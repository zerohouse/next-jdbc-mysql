package next.jdbc.mysql.query.analyze;

import java.lang.reflect.Field;

import next.jdbc.mysql.annotation.Exclude;
import next.jdbc.mysql.annotation.Key;
import next.jdbc.mysql.query.analyze.bind.FieldObject;
import next.jdbc.mysql.query.analyze.bind.FieldsMap;
import next.jdbc.mysql.query.analyze.info.TableInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectAnalyzer {

	private final static Logger logger = LoggerFactory.getLogger(ObjectAnalyzer.class);

	private FieldsMap fieldMap;
	private TableInfo tableInfo;

	public ObjectAnalyzer(Object record) {
		Class<?> type = record.getClass();
		Field[] fields = type.getDeclaredFields();
		tableInfo = new TableInfo(record.getClass());
		fieldMap = new FieldsMap(tableInfo.getPrefix(), tableInfo.getSuffix());
		for (int i = 0; i < fields.length; i++) {
			fieldDefine(record, fields[i]);
		}
	}

	private void fieldDefine(Object record, Field field) {
		if (field.isAnnotationPresent(Exclude.class))
			return;
		if (field.isAnnotationPresent(Key.class))
			try {
				field.setAccessible(true);
				Object param = field.get(record);
				if (param == null) {
					fieldMap.addNullKeyField(field);
					return;
				}
				fieldMap.addKeyField(new FieldObject(param, tableInfo.getPrefix(), tableInfo.getPrefix(), field));
				return;
			} catch (IllegalAccessException | IllegalArgumentException | SecurityException e) {
				logger.warn(e.getMessage());
				return;
			}
		try {
			field.setAccessible(true);
			Object param = field.get(record);
			if (param == null) {
				fieldMap.addNullField(field);
				return;
			}
			fieldMap.addField(new FieldObject(param, tableInfo.getPrefix(), tableInfo.getPrefix(), field));
			return;
		} catch (IllegalAccessException | IllegalArgumentException | SecurityException e) {
			logger.warn(e.getMessage());
			return;
		}
	}

	public TableInfo getTableInfo() {
		return tableInfo;
	}

	public FieldsMap getFieldMap() {
		return fieldMap;
	}

}
