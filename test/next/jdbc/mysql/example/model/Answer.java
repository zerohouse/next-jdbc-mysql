package next.jdbc.mysql.example.model;

import next.jdbc.mysql.annotation.Column;
import next.jdbc.mysql.annotation.Key;
import next.jdbc.mysql.annotation.Table;

@Table
public class Answer {

	public Answer(String userEmail, String test, String answers) {
		this.userEmail = userEmail;
		this.test = test;
		this.answers = answers;
	}

	@Override
	public String toString() {
		return "Answer [userEmail=" + userEmail + ", test=" + test + ", answers=" + answers + "]";
	}

	@Key
	private String userEmail;
	@Key
	private String test;
	@Column(DATA_TYPE = "TEXT", hasDefaultValue = false)
	private String answers;

}
