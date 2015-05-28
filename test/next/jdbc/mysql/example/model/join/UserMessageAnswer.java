package next.jdbc.mysql.example.model.join;

import next.jdbc.mysql.example.model.Answer;
import next.jdbc.mysql.join.Join;
import next.jdbc.mysql.join.JoinType;

public class UserMessageAnswer extends Join<UserMessage, Answer> {

	public UserMessageAnswer(UserMessage left, Answer right) {
		super(left, right);
		this.joinType = JoinType.LEFT; // default INNER
	}

	@Override
	public String getLeftOnFieldName() {
		return "User.id"; // User field id;
	}

	@Override
	public String getRightOnFieldName() {
		return "test"; // Message field from;
	}

}
