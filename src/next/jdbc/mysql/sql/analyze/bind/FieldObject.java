package next.jdbc.mysql.sql.analyze.bind;

import java.lang.reflect.Field;

import next.jdbc.mysql.annotation.RegularExpression;
import next.jdbc.mysql.exception.RegexNotMatchedException;
import next.jdbc.mysql.sql.analyze.info.FieldInfo;
import next.jdbc.mysql.sql.analyze.info.TableInfo;

public class FieldObject {

	private Object object;
	private FieldInfo fieldInfo;
	private Field field;

	public FieldObject(Object object, Field field, TableInfo info) {
		this.field = field;
		this.object = object;
		this.fieldInfo = new FieldInfo(info, field);
		regexCheck(object);
	}

	private void regexCheck(Object object) {
		if (object == null)
			return;
		if (field.isAnnotationPresent(RegularExpression.class)) {
			RegularExpression regex = field.getDeclaredAnnotation(RegularExpression.class);
			if (!object.toString().matches(regex.value()))
				throw new RegexNotMatchedException(regex.errorMessage().replace("$field", field.getName()));
		}
	}

	public Object getObject() {
		return object;
	}

	public FieldInfo getFieldInfo() {
		return fieldInfo;
	}

	public String getColumnName() {
		return fieldInfo.getColumnName();
	}

	public Field getField() {
		return field;
	}

	public void setObject(Object object) {
		regexCheck(object);
		this.object = object;
	}

}
