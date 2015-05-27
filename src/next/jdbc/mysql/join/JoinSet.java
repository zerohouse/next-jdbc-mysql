package next.jdbc.mysql.join;

public class JoinSet<LEFT, RIGHT> {

	private LEFT left;
	private RIGHT right;

	public JoinSet(LEFT left, RIGHT right) {
		this.left = left;
		this.right = right;
	}

	public LEFT getLeft() {
		return left;
	}

	public void setLeft(LEFT left) {
		this.left = left;
	}

	public RIGHT getRight() {
		return right;
	}

	public void setRight(RIGHT right) {
		this.right = right;
	}

	@Override
	public String toString() {
		return "JoinSet [left=" + left + ", right=" + right + "]";
	}

}
