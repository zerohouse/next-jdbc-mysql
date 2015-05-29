package next.jdbc.mysql.sql.analyze;

import next.jdbc.mysql.join.Join;

public class JoinAnalyzer extends JoinTypeAnalyzer {

	@SuppressWarnings("rawtypes")
	public JoinAnalyzer(Join join) {
		super();
		leftType = join.getLeft().getClass();
		rightType = join.getRight().getClass();
		left = analyze(join.getLeft());
		right = analyze(join.getRight());
		this.join = join;
	}

	@SuppressWarnings("rawtypes")
	private Analyzer analyze(Object object) {
		Analyzer result;
		if (Join.class.isAssignableFrom(object.getClass()))
			result = new JoinAnalyzer((Join) object);
		else {
			result = new ObjectAnalyzer(object);
			result.getAllFields().forEach(field -> {
				field.setJoin(true);
			});
		}
		return result;
	}

}
