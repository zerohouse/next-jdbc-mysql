package next.jdbc.mysql.query.analyze.info;

import next.jdbc.mysql.annotation.Table;

public class TableInfo {

	private static final String TABLE = "$table";

	public TableInfo(Class<?> type) {
		Table table = type.getAnnotation(Table.class);
		tableName = table.value().equals("") ? type.getSimpleName() : table.value();
		columnPrefix = table.columnPrefix().replace(TABLE, tableName);
		columnSuffix = table.columnSuffix().replace(TABLE, tableName);
		createQuery = table.createQuery();
		neverDrop = table.neverDrop();
	}

	private String tableName;
	private String columnPrefix;
	private String columnSuffix;
	private Boolean neverDrop;
	private String createQuery;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnPrefix() {
		return columnPrefix;
	}

	public String getColumnSuffix() {
		return columnSuffix;
	}

	public Boolean getNeverDrop() {
		return neverDrop;
	}

	public String getCreateQuery() {
		return createQuery;
	}

}
