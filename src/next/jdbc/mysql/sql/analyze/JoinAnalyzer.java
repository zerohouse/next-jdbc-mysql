package next.jdbc.mysql.sql.analyze;

import next.jdbc.mysql.join.Join;

public class JoinAnalyzer extends JoinTypeAnalyzer {

	@SuppressWarnings("rawtypes")
	public JoinAnalyzer(Join join) {
		super();
		leftType = join.getLeft().getClass();
		rightType = join.getRight().getClass();
		left = Analyzer.getObjectAnalyzer(join.getLeft());
		right = Analyzer.getObjectAnalyzer(join.getRight());
		this.join = join;
	}

}
