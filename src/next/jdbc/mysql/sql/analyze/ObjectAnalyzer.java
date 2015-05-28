package next.jdbc.mysql.sql.analyze;

import java.lang.reflect.Field;

import next.jdbc.mysql.annotation.Exclude;
import next.jdbc.mysql.annotation.Key;
import next.jdbc.mysql.sql.analyze.bind.FieldObject;
import next.jdbc.mysql.sql.analyze.info.TableInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectAnalyzer extends TypeAnalyzer {

	private final static Logger logger = LoggerFactory.getLogger(ObjectAnalyzer.class);

	public ObjectAnalyzer(Object record) {
		super();
		tableInfo = new TableInfo(record.getClass());
		Class<?> type = record.getClass();
		Field[] fields = type.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			fieldDefine(record, fields[i]);
		}
	}

	private void fieldDefine(Object record, Field field) {
		if (field.isAnnotationPresent(Exclude.class))
			return;
		try {
			if (field.isAnnotationPresent(Key.class)) {
				field.setAccessible(true);
				Object param = field.get(record);
				keyFields.add(new FieldObject(param, field, tableInfo));
				return;
			}
			field.setAccessible(true);
			Object param = field.get(record);
			fields.add(new FieldObject(param, field, tableInfo));
			return;
		} catch (IllegalAccessException | IllegalArgumentException | SecurityException e) {
			logger.warn(e.getMessage());
			return;
		}
	}

}
