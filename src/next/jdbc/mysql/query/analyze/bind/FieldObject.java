package next.jdbc.mysql.query.analyze.bind;

import java.lang.reflect.Field;

import next.jdbc.mysql.query.analyze.info.FieldInfo;
import next.jdbc.mysql.query.analyze.info.TableInfo;

public class FieldObject {

	private Object object;
	private FieldInfo fieldInfo;
	private Field field;
	private boolean join;
	private String tableName;

	public FieldObject(Object object, Field field, TableInfo info) {
		this.object = object;
		this.field = field;
		this.tableName = info.getTableName();
		this.fieldInfo = new FieldInfo(info.getColumnPrefix(), info.getColumnSuffix(), field);
	}

	public Object getObject() {
		return object;
	}

	public FieldInfo getFieldInfo() {
		return fieldInfo;
	}

	private final static String Q = "`";
	private final static String DOT = ".";

	public String getColumnName() {
		return fieldInfo.getColumnName();
	}

	public String getWrappedColumnName() {
		if (join)
			return tableName + DOT + Q + fieldInfo.getColumnName() + Q;
		return Q + fieldInfo.getColumnName() + Q;
	}

	public Field getField() {
		return field;
	}

	public void setJoin(boolean join) {
		this.join = join;
	}

}
