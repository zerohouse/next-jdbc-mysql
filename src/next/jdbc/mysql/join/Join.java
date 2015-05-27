package next.jdbc.mysql.join;

public abstract class Join<LEFT, RIGHT> {

	protected LEFT left;
	protected RIGHT right;
	protected JoinType joinType;

	public Join(LEFT left, RIGHT right) {
		this.left = left;
		this.right = right;
		this.joinType = JoinType.INNER;
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

	public JoinType getJoinType() {
		return joinType;
	}

	public void setJoinType(JoinType joinType) {
		this.joinType = joinType;
	}

	public abstract String getLeftOnFieldName();

	public abstract String getRightOnFieldName();

	@Override
	public String toString() {
		return "Join [left=" + left + ", right=" + right + "]";
	}

}
