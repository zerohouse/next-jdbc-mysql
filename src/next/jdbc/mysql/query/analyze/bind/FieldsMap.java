package next.jdbc.mysql.query.analyze.bind;

import java.lang.reflect.Field;

import next.jdbc.mysql.query.analyze.info.TableInfo;

public class FieldsMap {

	private Fields nullFields;
	private Fields nullKeyFields;
	private Fields fields;
	private Fields keyFields;
	private TableInfo tableInfo;

	public FieldsMap(TableInfo info) {
		this.tableInfo = info;
		nullFields = new Fields();
		nullKeyFields = new Fields();
		fields = new Fields();
		keyFields = new Fields();
	}

	public void addNullField(Field field) {
		nullFields.add(new FieldObject(null, field, tableInfo));
	}

	public void addField(FieldObject fieldObject) {
		fields.add(fieldObject);
	}

	public void addNullKeyField(Field field) {
		nullFields.add(new FieldObject(null, field, tableInfo));
	}

	public void addKeyField(FieldObject fieldObject) {
		keyFields.add(fieldObject);
	}

	public Fields getNullFields() {
		return nullFields;
	}

	public Fields getNullKeyFields() {
		return nullKeyFields;
	}

	public Fields getFields() {
		return fields;
	}

	public Fields getKeyFields() {
		return keyFields;
	}

	public Fields getNotNullFields() {
		Fields result = new Fields();
		result.concat(fields);
		result.concat(keyFields);
		return result;
	}

	public Fields getAllFields() {
		Fields result = new Fields();
		result.concat(fields);
		result.concat(keyFields);
		result.concat(nullFields);
		result.concat(nullKeyFields);
		return result;
	}

}
