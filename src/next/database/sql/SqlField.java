package next.database.sql;

public interface SqlField {

	String getColumnName();

	boolean check(Object param);

}
