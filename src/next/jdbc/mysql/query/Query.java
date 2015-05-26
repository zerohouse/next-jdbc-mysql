package next.jdbc.mysql.query;

import java.util.ArrayList;
import java.util.List;

public class Query {

	private StringBuilder query;
	private List<Object> parameters;

	public Query() {
		query = new StringBuilder();
		parameters = new ArrayList<Object>();
	}

	public Query(String query) {
		this.query = new StringBuilder(query);
		parameters = new ArrayList<Object>();
	}

	public Query addParameter(Object parameter) {
		parameters.add(parameter);
		return this;
	}

	public Query append(String string) {
		query.append(string);
		return this;
	}

	public int length() {
		return query.length();
	}

	public Query delete(int start, int end) {
		query.delete(start, end);
		return this;
	}

	public String getQueryString() {
		return query.toString();
	}

	public Query setQuery(String query) {
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

	public Query concatParameters(Query query) {
		this.parameters.addAll(query.parameters);
		return this;
	}

	public Object[] getParameterArray() {
		return parameters.toArray();
	}

}