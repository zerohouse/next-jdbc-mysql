package next.jdbc.mysql.query.support;

import java.lang.reflect.ParameterizedType;

import next.jdbc.mysql.join.Join;
import next.jdbc.mysql.sql.analyze.bind.ModelMaker;

public class JoinNamer implements TableNamer {

	@SuppressWarnings("rawtypes")
	private Join join;
	private TableNamer left;
	private TableNamer right;
	private Typer typeAnalyzer;

	public JoinNamer(Class<? extends Join<?, ?>> type, Typer typeAnalyzer) {
		join = ModelMaker.newInstance(type);
		left = TableNamer.get((Class<?>) ((ParameterizedType) type.getGenericSuperclass()).getActualTypeArguments()[0], typeAnalyzer);
		right = TableNamer.get((Class<?>) ((ParameterizedType) type.getGenericSuperclass()).getActualTypeArguments()[1], typeAnalyzer);
		this.typeAnalyzer = typeAnalyzer;
	}

	private final static String JOIN = "(%s) %s JOIN (%s) ON %s = %s";

	@Override
	public String getName() {
		return String.format(JOIN, left.getName(), join.getJoinType().getType(), right.getName(), typeAnalyzer.getColumnName(join.getLeftOnFieldName()),
				typeAnalyzer.getColumnName(join.getLeftOnFieldName()));
	}

}
