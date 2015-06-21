package next.jdbc.mysql.query;

import next.jdbc.mysql.DAORaw;

public class Query {
	protected DAORaw dao;

	public Query(DAORaw dao) {
		this.dao = dao;
	}

}
