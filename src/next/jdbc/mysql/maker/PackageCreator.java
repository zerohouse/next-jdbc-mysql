package next.jdbc.mysql.maker;

import next.jdbc.mysql.DAO;
import next.jdbc.mysql.annotation.Table;
import next.jdbc.mysql.setting.Setting;

public class PackageCreator {
	public static void create() {
		DAO dao = new DAO();
		Setting.getReflections().getTypesAnnotatedWith(Table.class).forEach(table -> {
			TableMaker m = new TableMaker(table, dao);
			m.createTable();
		});
	}

	public static void reset() {
		DAO dao = new DAO();
		Setting.getReflections().getTypesAnnotatedWith(Table.class).forEach(table -> {
			TableMaker m = new TableMaker(table, dao);
			m.reset();
		});
	}

	public static void drop() {
		DAO dao = new DAO();
		Setting.getReflections().getTypesAnnotatedWith(Table.class).forEach(table -> {
			TableMaker m = new TableMaker(table, dao);
			m.dropTable();
		});
	}
}
