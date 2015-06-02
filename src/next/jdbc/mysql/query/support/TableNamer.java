package next.jdbc.mysql.query.support;

import next.jdbc.mysql.join.Join;

public interface TableNamer {

	@SuppressWarnings("unchecked")
	public static TableNamer get(Class<?> type, Typer typeAnalyzer) {
		if (Join.class.isAssignableFrom(type))
			return new JoinNamer((Class<? extends Join<?, ?>>) type, typeAnalyzer);
		return new Namer(type);
	}

	public String getName();

}
