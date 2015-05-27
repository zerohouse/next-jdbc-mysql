package next.jdbc.mysql;

import static org.junit.Assert.*;
import next.jdbc.mysql.example.model.Message;
import next.jdbc.mysql.example.model.User;
import next.jdbc.mysql.example.model.join.UserMessage;

import org.junit.Before;
import org.junit.Test;

public class DAOTest {

	private DAO dao;

	@Before
	public void setup() {
		dao = new DAO();
	}

	@Test
	public void testInsertAndDelete() {
		assertTrue(dao.insertIfExistUpdate(new User("mail@mail.com")));
		assertTrue(dao.delete(new User("mail@mail.com")));
		assertFalse(dao.delete(new User("mail@mail.com")));
		assertTrue(dao.insert(new User("mail@mail.com")));
		assertFalse(dao.insert(new User("mail@mail.com")));
	}

	@Test
	public void testUpdateAndFind() {
		assertTrue(dao.insertIfExistUpdate(new User("mail@mail.com", "password")));
		assertEquals("password", dao.find(new User("mail@mail.com")).getPassword());
		User user = dao.find(new User("mail@mail.com"));
		user.setGender("F");
		assertTrue(dao.update(user));
		assertEquals("F", dao.find(new User("mail@mail.com")).getGender());
		assertEquals(1, dao.findList(new User("mail@mail.com")).size());
	}

	@Test
	public void join() {
		UserMessage m = new UserMessage(new User("mail@mail.com"), new Message());
		assertNotNull(dao.findList(m));
		User user = new User();
		user.setId(1);
		assertEquals(100, dao.findList(new UserMessage(user, new Message())).size());
	}

}
