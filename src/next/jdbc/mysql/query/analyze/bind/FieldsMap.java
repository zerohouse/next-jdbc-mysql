package next.jdbc.mysql.query.analyze.bind;

import java.lang.reflect.Field;

public class FieldsMap {

	private Fields nullFields;
	private Fields nullKeyFields;
	private Fields fields;
	private Fields keyFields;
	private String prefix;
	private String suffix;

	public FieldsMap(String prefix, String suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
		nullFields = new Fields();
		nullKeyFields = new Fields();
		fields = new Fields();
		keyFields = new Fields();
	}

	public void addNullField(Field field) {
		nullFields.add(new FieldObject(null, prefix, suffix, field));
	}

	public void addField(FieldObject fieldObject) {
		fields.add(fieldObject);
	}

	public void addNullKeyField(Field field) {
		nullFields.add(new FieldObject(null, prefix, suffix, field));
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

}
