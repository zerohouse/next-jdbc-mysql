package next.jdbc.mysql.setting;

public class Table {
	private String dataType;
	private Boolean notNull;
	private Boolean hasDefaultValue;
	private Object defaultValue;

	public Table(String dataType, Boolean notNull, Boolean hasDefaultValue, Object defaultValue) {
		this.dataType = dataType;
		this.notNull = notNull;
		this.hasDefaultValue = hasDefaultValue;
		this.defaultValue = defaultValue;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Boolean getNotNull() {
		return notNull;
	}

	public void setNotNull(Boolean notNull) {
		this.notNull = notNull;
	}

	public Boolean getHasDefaultValue() {
		return hasDefaultValue;
	}

	public void setHasDefaultValue(Boolean hasDefaultValue) {
		this.hasDefaultValue = hasDefaultValue;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

}
