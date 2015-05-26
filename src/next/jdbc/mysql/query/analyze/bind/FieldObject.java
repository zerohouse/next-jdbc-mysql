package next.jdbc.mysql.query.analyze.bind;

import java.lang.reflect.Field;

import next.jdbc.mysql.query.analyze.info.FieldInfo;

public class FieldObject {

	private Object object;
	private FieldInfo fieldInfo;
	private Field field;

	public FieldObject(Object object, String prefix, String suffix, Field field) {
		this.object = object;
		this.field = field;
		this.fieldInfo = new FieldInfo(prefix, suffix, field);
	}

	public String getWrappedColumnName() {
		return fieldInfo.getWrappedColumnName();
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

}
