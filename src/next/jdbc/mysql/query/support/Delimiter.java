package next.jdbc.mysql.query.support;

public class Delimiter {

	private boolean use;
	private String delimiter;
	private static final String EMPTY = "";

	public Delimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public String get() {
		if (use)
			return delimiter;
		use = true;
		return EMPTY;
	}

}
