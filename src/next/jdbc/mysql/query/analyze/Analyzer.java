package next.jdbc.mysql.query.analyze;

import next.jdbc.mysql.query.analyze.bind.Fields;

public interface Analyzer {

	Fields getNotNullFields();

	String getTableName();

	Fields getFields();

	Fields getKeyFields();

	Fields getAllFields();

}
