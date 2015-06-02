package next.jdbc.mysql.query.support;

public class Order {
	private Boolean desc;
	private String columnName;

	public Order(String columnName, Boolean desc) {
		this.desc = desc;
		this.columnName = columnName;
	}

	public Boolean getDesc() {
		return desc;
	}

	public String getColumnName() {
		return columnName;
	}

}
