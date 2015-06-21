package next.jdbc.mysql.query.support;

import next.jdbc.mysql.annotation.Table;
import next.jdbc.mysql.constants.Constants;

public class Namer implements TableNamer {

	private String name;

	public Namer(Class<?> type) {
		Table table = type.getAnnotation(Table.class);
		name = table.value().equals("") ? type.getSimpleName() : table.value();
	}

	@Override
	public String getName() {
		return Constants.wrapped(name);
	}

}
