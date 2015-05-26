package next.jdbc.mysql;

import static org.junit.Assert.*;

import org.junit.Test;

import test.model.User;

public class DAOTest {

	@Test
	public void test() {
		DAO dao = new DAO();
		User user = new User("email");
		user.setId(1);
		assertTrue(dao.insertIfExistUpdate(user));
	}
}
