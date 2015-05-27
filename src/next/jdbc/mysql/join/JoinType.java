package next.jdbc.mysql.join;

public enum JoinType {
	INNER("INNER"), LEFT("LEFT"), RIGHT("RIGHT");

	private String type;

	private JoinType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
