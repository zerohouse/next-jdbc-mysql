package next.jdbc.mysql.sql.analyze.bind;

import java.lang.reflect.Field;

import next.jdbc.mysql.sql.analyze.info.FieldInfo;
import next.jdbc.mysql.sql.analyze.info.TableInfo;

public class FieldObject {

	private Object object;
	private FieldInfo fieldInfo;
	private Field field;

	public FieldObject(Object object, Field field, TableInfo info) {
		this.object = object;
		this.field = field;
		this.fieldInfo = new FieldInfo(info, field);
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
		this.object = object;
	}

}
