package next.jdbc.mysql.example.dblauncher;

import java.util.Date;

import next.jdbc.mysql.DAO;
import next.jdbc.mysql.example.model.Answer;
import next.jdbc.mysql.example.model.Message;
import next.jdbc.mysql.example.model.User;
import next.jdbc.mysql.maker.PackageCreator;

public class DBLauncher {

	public static void main(String[] args) {
		PackageCreator.reset();
		insertTestData();
	}

	private static void insertTestData() {
		DAO dao = new DAO();
		for (int i = 0; i < 100; i++)
			dao.insert(new User("testmail" + i + "" + "@test.com", "name", "password", "m"));
		for (int i = 0; i < 100; i++)
			dao.insert(new Message(1, 1, false, "testmessage" + i, "message", new Date()));
		for (int i = 0; i < 100; i++)
			dao.insert(new Answer("mail@mail.com" + i, "test", "answer"));
	}

}
