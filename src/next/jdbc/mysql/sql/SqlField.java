package next.jdbc.mysql.sql;

public interface SqlField {

	String getColumnName();

	boolean check(Object param);

}
