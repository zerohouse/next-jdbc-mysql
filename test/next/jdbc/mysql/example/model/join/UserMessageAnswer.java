package next.jdbc.mysql.example.model.join;

import next.jdbc.mysql.example.model.Answer;
import next.jdbc.mysql.join.Join;
import next.jdbc.mysql.join.JoinType;

public class UserMessageAnswer extends Join<Answer, UserMessage> {

	public UserMessageAnswer(Answer left, UserMessage right) {
		super(left, right);
		this.joinType = JoinType.LEFT; // default INNER
	}

	@Override
	public String getLeftOnFieldName() {
		return "test"; // Message field from;
	}

	@Override
	public String getRightOnFieldName() {
		return "User.id"; // User field id;
	}

}
