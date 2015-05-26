package next.jdbc.mysql.setting;

import java.io.FileReader;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import com.google.gson.Gson;

public class Setting {

	private static Database database;
	private static Reflections reflections;

	static {
		try {
			database = new Gson().fromJson(new FileReader(Setting.class.getResource("/next-jdbc-mysql.json").getFile()), Database.class);
		} catch (Exception e) {
			database = new Database();
		}
		reflections = new Reflections(database.getBasePackage(), new SubTypesScanner(), new TypeAnnotationsScanner());
	}

	public static TableCreate getCreateOption() {
		return database.getCreateOption();
	}

	public static Reflections getReflections() {
		return reflections;
	}

	public static Database getDatabase() {
		return database;
	}

}
