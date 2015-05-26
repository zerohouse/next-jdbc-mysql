package next.jdbc.mysql.setting;

public class TableCreate {

	private String table_suffix = "ENGINE = InnoDB DEFAULT CHARACTER SET utf8";
	private TableOptions stringOptions = new TableOptions("VARCHAR(255)", true, true, "");
	private TableOptions integerOptions = new TableOptions("INTEGER", true, true, 0);
	private TableOptions booleanOptions = new TableOptions("TINYINT(1)", true, true, 0);
	private TableOptions dateOptions = new TableOptions("DATETIME", true, true, "CURRENT_TIMESTAMP");
	private TableOptions floatOptions = new TableOptions("FLOAT", true, true, 0);
	private TableOptions longOptions = new TableOptions("BIGINT", true, true, 0);

	public String getTable_suffix() {
		return table_suffix;
	}

	public void setTable_suffix(String table_suffix) {
		this.table_suffix = table_suffix;
	}

	public TableOptions getStringOptions() {
		return stringOptions;
	}

	public void setStringOptions(TableOptions stringOptions) {
		this.stringOptions = stringOptions;
	}

	public TableOptions getIntegerOptions() {
		return integerOptions;
	}

	public void setIntegerOptions(TableOptions integerOptions) {
		this.integerOptions = integerOptions;
	}

	public TableOptions getBooleanOptions() {
		return booleanOptions;
	}

	public void setBooleanOptions(TableOptions booleanOptions) {
		this.booleanOptions = booleanOptions;
	}

	public TableOptions getDateOptions() {
		return dateOptions;
	}

	public void setDateOptions(TableOptions dateOptions) {
		this.dateOptions = dateOptions;
	}

	public TableOptions getFloatOptions() {
		return floatOptions;
	}

	public void setFloatOptions(TableOptions floatOptions) {
		this.floatOptions = floatOptions;
	}

	public TableOptions getLongOptions() {
		return longOptions;
	}

	public void setLongOptions(TableOptions longOptions) {
		this.longOptions = longOptions;
	}

}
