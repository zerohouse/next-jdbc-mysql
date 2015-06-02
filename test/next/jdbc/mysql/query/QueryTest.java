package next.jdbc.mysql.query;

import next.jdbc.mysql.DAO;
import next.jdbc.mysql.example.model.User;
import next.jdbc.mysql.maker.TableMaker;

import org.junit.Test;

public class QueryTest {

	@Test
	public void test() {
		TableMaker maker = new TableMaker(User.class);
		maker.createTable(); //CREATE TABLE IF NOT EXISTS `User` (`id` INTEGER AUTO_INCREMENT NOT NULL, `email` VARCHAR(255) NOT NULL DEFAULT '', `auth` VARCHAR(255) NOT NULL DEFAULT '', `name` VARCHAR(255) NOT NULL DEFAULT '', `password` VARCHAR(255) NOT NULL DEFAULT '', `gender` CHAR(1) NOT NULL DEFAULT '', `message` VARCHAR(255) NOT NULL DEFAULT '', `photo` VARCHAR(255) NOT NULL DEFAULT '', `cover` VARCHAR(255) NOT NULL DEFAULT '', PRIMARY KEY(`id`), UNIQUE(`email`), INDEX(`email`)) ENGINE = InnoDB DEFAULT CHARACTER SET utf8
		DAO dao = new DAO();
		dao.get(User.class, 1); // SELECT * FROM `User` WHERE `User`.`id`=?, Parameters : [1]
		dao.insert(new User("parksungho86@gmail.com", "newPassword")); //INSERT INTO `User` (`User`.`email`, `User`.`password`) VALUES (?, ?), Parameters : [parksungho86@gmail.com, newPassword]
		dao.update(new User("parksungho86@gmail.com", "newPassword"), "email"); //UPDATE `User` SET `User`.`password`=? WHERE `User`.`email`=?, Parameters : [newPassword, parksungho86@gmail.com]
		dao.find(new User("parksungho86@gmail.com", "newPassword")); //SELECT * FROM `User` WHERE `User`.`email`=? AND `User`.`password`=?, Parameters : [parksungho86@gmail.com, newPassword]
		dao.findList(new User("parksungho86@gmail.com", "newPassword")); //SELECT * FROM `User` WHERE `User`.`email`=? AND `User`.`password`=?, Parameters : [parksungho86@gmail.com, newPassword]
		dao.delete(new User("parksungho86@gmail.com", "newPassword")); //DELETE FROM `User` WHERE `User`.`email`=? AND `User`.`password`=?, Parameters : [parksungho86@gmail.com, newPassword]
		// dao.getRecord(sql, parameters);
		// dao.getRecords(sql, parameters)
		dao.getSelectQuery(User.class).field("email").like("pa").or().field("password").equal("3").limit(3, 4).orderBy("id").find(); //SELECT * from `User` WHERE `User`.`email` LIKE '%pa%' OR `User`.`password`=? ORDER BY `User`.`id` LIMIT 3,4, Parameters : [3]
	}
}
