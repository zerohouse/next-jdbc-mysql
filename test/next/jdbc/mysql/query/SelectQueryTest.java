package next.jdbc.mysql.query;

import next.jdbc.mysql.example.model.User;

import org.junit.Test;

public class SelectQueryTest {

	@Test
	public void test() {
		InsertQuery query = new InsertQuery(User.class);
		System.out.println(query);
		System.out.println(query.set("email", "3").set("email", 3).set("name", "3"));
		SelectQuery<User> aa = new SelectQuery<User>(null, User.class);
		System.out.println(aa.field("email").like("3").and().field("email").equal("3"));
	}

}
