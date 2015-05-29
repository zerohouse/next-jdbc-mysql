package next.jdbc.mysql.constants;

public class Constants {

	private final static String Q = "`";
	public final static String DOT = ".";

	public static String wrapped(String str) {
		return Q + str + Q;
	}

}
