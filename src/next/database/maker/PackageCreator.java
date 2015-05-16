package next.database.maker;

import next.database.DAO;
import next.database.annotation.Table;
import next.database.setting.Setting;

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
