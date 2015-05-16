package next.database.setting;

public class TableCreate {

	private String table_suffix = "ENGINE = InnoDB DEFAULT CHARACTER SET utf8";
	private Table stringOptions = new Table("VARCHAR(255)", true, true, "");
	private Table integerOptions = new Table("INTEGER", true, true, 0);
	private Table booleanOptions = new Table("TINYINT(1)", true, true, 0);
	private Table dateOptions = new Table("DATETIME", true, true, "CURRENT_TIMESTAMP");
	private Table floatOptions = new Table("FLOAT", true, true, 0);
	private Table longOptions = new Table("BIGINT", true, true, 0);

	public String getTable_suffix() {
		return table_suffix;
	}

	public void setTable_suffix(String table_suffix) {
		this.table_suffix = table_suffix;
	}

	public Table getStringOptions() {
		return stringOptions;
	}

	public void setStringOptions(Table stringOptions) {
		this.stringOptions = stringOptions;
	}

	public Table getIntegerOptions() {
		return integerOptions;
	}

	public void setIntegerOptions(Table integerOptions) {
		this.integerOptions = integerOptions;
	}

	public Table getBooleanOptions() {
		return booleanOptions;
	}

	public void setBooleanOptions(Table booleanOptions) {
		this.booleanOptions = booleanOptions;
	}

	public Table getDateOptions() {
		return dateOptions;
	}

	public void setDateOptions(Table dateOptions) {
		this.dateOptions = dateOptions;
	}

	public Table getFloatOptions() {
		return floatOptions;
	}

	public void setFloatOptions(Table floatOptions) {
		this.floatOptions = floatOptions;
	}

	public Table getLongOptions() {
		return longOptions;
	}

	public void setLongOptions(Table longOptions) {
		this.longOptions = longOptions;
	}

}
