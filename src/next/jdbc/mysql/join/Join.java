package next.jdbc.mysql.join;

public abstract class Join<LEFT, RIGHT> {

	private LEFT left;
	private RIGHT right;

	public Join(LEFT left, RIGHT right) {
		this.left = left;
		this.right = right;
	}

	public LEFT getLeft() {
		return left;
	}

	public RIGHT getRight() {
		return right;
	}

	public void setRight(RIGHT right) {
		this.right = right;
	}

	public void setLeft(LEFT left) {
		this.left = left;
	}

	public abstract JoinType getJoinType();

	public abstract String getLeftOnFieldName();

	public abstract String getRightOnFieldName();

	@Override
	public String toString() {
		return "Join [left=" + left + ", right=" + right + "]";
	}

}
