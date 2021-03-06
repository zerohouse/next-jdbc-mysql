package next.jdbc.mysql.sql.analyze.bind;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import next.jdbc.mysql.sql.Sql;

public class Fields {

	private List<FieldObject> fields;

	public Fields() {
		fields = new ArrayList<FieldObject>();
	}

	public Fields(List<FieldObject> fields) {
		this.fields = fields;
	}

	public Sql getQuery(String suffix, String delimiter) {
		Sql query = new Sql();
		if (fields.size() == 0)
			return query;
		fields.forEach(param -> {
			query.append(param.getColumnName());
			query.append(suffix);
			query.append(delimiter);
			query.addParameter(param.getObject());
		});
		query.delete(query.length() - delimiter.length(), query.length());
		return query;
	}

	public String getQueryString(String suffix, String delimiter) {
		StringBuilder query = new StringBuilder();
		if (fields.size() == 0)
			return query.toString();
		fields.forEach(param -> {
			query.append(param.getColumnName());
			query.append(suffix);
			query.append(delimiter);
		});
		query.delete(query.length() - delimiter.length(), query.length());
		return query.toString();
	}

	public FieldObject findByFieldName(String fieldName) {
		if (!fieldName.contains("."))
			return fields.stream().filter(field -> field.getField().getName().equals(fieldName)).findFirst().get();
		String[] name = fieldName.split("\\.");
		return fields
				.stream()
				.filter(field -> field.getField().getName().equals(name[name.length - 1])
						&& field.getField().getDeclaringClass().getSimpleName().equals(name[name.length - 2])).findFirst().get();
	}

	public void add(FieldObject fieldObject) {
		fields.add(fieldObject);
	}

	public void concat(Fields fields) {
		this.fields.addAll(fields.fields);
	}

	public void forEach(Consumer<? super FieldObject> action) {
		fields.forEach(action);
	}

	public Stream<FieldObject> stream() {
		return fields.stream();
	}

	public FieldObject get(int i) {
		return fields.get(i);
	}
}
