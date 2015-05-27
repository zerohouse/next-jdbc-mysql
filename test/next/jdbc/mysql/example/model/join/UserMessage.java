package next.jdbc.mysql.example.model.join;

import next.jdbc.mysql.example.model.Message;
import next.jdbc.mysql.example.model.User;
import next.jdbc.mysql.join.Join;
import next.jdbc.mysql.join.JoinType;

public class UserMessage extends Join<User, Message> {

	public UserMessage(User left, Message right) {
		super(left, right);
		this.joinType = JoinType.LEFT; // default INNER
	}

	@Override
	public String getLeftOnFieldName() {
		return "id"; // User field id;
	}

	@Override
	public String getRightOnFieldName() {
		return "from"; // Message field from;
	}

}
