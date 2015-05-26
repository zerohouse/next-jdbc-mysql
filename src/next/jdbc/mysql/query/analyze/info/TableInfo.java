package next.jdbc.mysql.query.analyze.info;

import next.jdbc.mysql.annotation.Table;

public class TableInfo {

	private static final String TABLE = "$table";
	private static final String Q = "`";

	public TableInfo(Class<?> type) {
		Table table = type.getAnnotation(Table.class);
		tableName = table.value().equals("") ? type.getSimpleName() : table.value();
		prefix = table.columnPrefix().replace(TABLE, tableName);
		suffix = table.columnSuffix().replace(TABLE, tableName);
		createQuery = table.createQuery();
		neverDrop = table.neverDrop();
	}

	private String tableName;
	private String prefix;
	private String suffix;
	private Boolean neverDrop;
	private String createQuery;

	public String getWrappedTableName() {
		return Q + tableName + Q;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public Boolean getNeverDrop() {
		return neverDrop;
	}

	public String getCreateQuery() {
		return createQuery;
	}

}
