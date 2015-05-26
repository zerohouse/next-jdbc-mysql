package next.jdbc.mysql.query;

import next.jdbc.mysql.DAO;

import org.junit.Test;

import test.User;

public class QueryTest {

	@Test
	public void test() {
		QueryMaker maker = new QueryMaker();
		System.out.println(maker.select(User.class));
		System.out.println(maker.delete(User.class));
		User user = new User("abc", "pass");
		user.setId(1);
		System.out.println(maker.select(user));
		System.out.println(maker.insert(user));
		System.out.println(maker.update(user));
		System.out.println(maker.delete(user));
		System.out.println(maker.insertIfExistUpdate(user));
	}

	@Test
	public void teest() {
		DAO dao = new DAO();
		User user = new User("b", "b", "c", "d");
		user.setId(1);
		dao.update(user);
		
		//System.out.println(dao.fill(new User("12")));
	}

	@Test
	public void teeset() {
		StringBuilder b = new StringBuilder();
		b.append(true);
		b.delete(0, 3);
		System.out.println(b);
	}

}
