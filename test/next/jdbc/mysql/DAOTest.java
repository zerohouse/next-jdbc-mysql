package next.jdbc.mysql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import next.jdbc.mysql.example.model.Message;
import next.jdbc.mysql.example.model.User;
import next.jdbc.mysql.example.model.join.UserMessage;
import next.jdbc.mysql.example.model.join.UserMessageAnswer;

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
		assertTrue(dao.update(user, "email"));
		assertEquals("F", dao.find(new User("mail@mail.com")).getGender());
		assertEquals(1, dao.findList(new User("mail@mail.com")).size());
	}

	@Test
	public void join() {
		User user = new User();
		user.setId(3);
		user.setName("testttttt");
		Message message = new Message();
		message.setId(3);
		message.setHead("testtttt");
		UserMessage m = new UserMessage(user, message);
		System.out.println(dao.update(m));

	}

	@Test
	public void joins() {
		System.out.println(dao.get(UserMessageAnswer.class));
	}

}
