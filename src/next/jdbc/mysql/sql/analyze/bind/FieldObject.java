package next.jdbc.mysql.sql.analyze.bind;

import java.lang.reflect.Field;

import next.jdbc.mysql.sql.analyze.info.FieldInfo;
import next.jdbc.mysql.sql.analyze.info.TableInfo;

public class FieldObject {

	private Object object;
	private FieldInfo fieldInfo;

	public FieldObject(Object object, Field field, TableInfo info) {
		this.fieldInfo = new FieldInfo(info, field);
		fieldInfo.regexMatchedCheck(object);
		this.object = object;
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
		return fieldInfo.getField();
	}

	public void setObject(Object object) {
		fieldInfo.regexMatchedCheck(object);
		this.object = object;
	}

}
