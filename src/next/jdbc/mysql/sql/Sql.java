package next.jdbc.mysql.sql;

import java.util.ArrayList;
import java.util.List;

public class Sql {

	private StringBuilder query;
	private List<Object> parameters;

	public Sql() {
		query = new StringBuilder();
		parameters = new ArrayList<Object>();
	}

	public Sql(String query) {
		this.query = new StringBuilder(query);
		parameters = new ArrayList<Object>();
	}

	public Sql addParameter(Object parameter) {
		parameters.add(parameter);
		return this;
	}

	public Sql append(String string) {
		query.append(string);
		return this;
	}

	public int length() {
		return query.length();
	}

	public Sql delete(int start, int end) {
		query.delete(start, end);
		return this;
	}

	public String getQueryString() {
		return query.toString();
	}

	public Sql setQuery(String query) {
		this.query = new StringBuilder(query);
		return this;
	}

	@Override
	public String toString() {
		return "Query [query=" + query + ", parameters=" + parameters + "]";
	}

	public int size() {
		return parameters.size();
	}

	public Sql concatParameters(Sql query) {
		this.parameters.addAll(query.parameters);
		return this;
	}

	public Object[] getParameterArray() {
		return parameters.toArray();
	}

	public void concat(Sql sql) {
		query.append(sql.query);
		parameters.addAll(sql.parameters);
	}

	public StringBuilder getQuery() {
		return query;
	}

}
