package next.jdbc.mysql.maker;

import next.jdbc.mysql.annotation.Table;
import next.jdbc.mysql.setting.Setting;

public class PackageCreator {
	public static void create() {
		Setting.getReflections().getTypesAnnotatedWith(Table.class).forEach(table -> {
			TableMaker m = new TableMaker(table);
			m.createTable();
		});
	}

	public static void reset() {
		Setting.getReflections().getTypesAnnotatedWith(Table.class).forEach(table -> {
			TableMaker m = new TableMaker(table);
			m.reset();
		});
	}

	public static void drop() {
		Setting.getReflections().getTypesAnnotatedWith(Table.class).forEach(table -> {
			TableMaker m = new TableMaker(table);
			m.dropTable();
		});
	}
}
