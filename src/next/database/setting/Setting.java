package next.database.setting;

import java.io.FileReader;

import next.database.sql.SqlSupports;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import com.google.gson.Gson;

public class Setting {

	private static Database database;
	private static Reflections reflections;
	private static SqlSupports sqlSupports;

	static {
		try {
			database = new Gson().fromJson(new FileReader(Setting.class.getResource("/next-database.json").getFile()), Database.class);
		} catch (Exception e) {
			database = new Database();
		}
		reflections = new Reflections(database.getBasePackage(), new SubTypesScanner(), new TypeAnnotationsScanner());

		sqlSupports = new SqlSupports();
	}

	public static TableCreate getCreateOption() {
		return database.getCreateOption();
	}

	public static Reflections getReflections() {
		return reflections;
	}

	public static SqlSupports getSqlSupports() {
		return sqlSupports;
	}

	public static Database getDatabase() {
		return database;
	}

}
