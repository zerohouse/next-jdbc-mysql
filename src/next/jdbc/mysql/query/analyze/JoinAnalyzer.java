package next.jdbc.mysql.query.analyze;

import next.jdbc.mysql.join.Join;
import next.jdbc.mysql.query.analyze.bind.Fields;

public class JoinAnalyzer implements Analyzer {

	private Analyzer left;
	private Analyzer right;
	@SuppressWarnings("rawtypes")
	private Join join;

	public Analyzer getLeftAnalyzer() {
		return left;
	}

	public Analyzer getRightAnalyzer() {
		return right;
	}

	@SuppressWarnings("rawtypes")
	public JoinAnalyzer(Join join) {
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

	@Override
	public Fields getKeyFields() {
		Fields fields = new Fields();
		fields.concat(left.getKeyFields());
		fields.concat(right.getKeyFields());
		return fields;
	}

	@Override
	public Fields getNotNullFields() {
		Fields fields = new Fields();
		fields.concat(left.getNotNullFields());
		fields.concat(right.getNotNullFields());
		return fields;
	}

	@Override
	public Fields getFields() {
		Fields fields = new Fields();
		fields.concat(left.getFields());
		fields.concat(right.getFields());
		return fields;
	}

	private final static String JOIN_NAME = "%s %s JOIN %s ON %s = %s";

	@Override
	public String getTableName() {
		return String.format(JOIN_NAME, left.getTableName(), join.getJoinType().getType(), right.getTableName(),
				left.getAllFields().findByFieldName(join.getLeftOnFieldName()).getColumnName(),
				right.getAllFields().findByFieldName(join.getRightOnFieldName()).getColumnName());
	}

	@Override
	public Fields getAllFields() {
		Fields fields = new Fields();
		fields.concat(left.getAllFields());
		fields.concat(right.getAllFields());
		return fields;
	}

	public Object getLeft() {
		return join.getLeft();
	}

	public Object getRight() {
		return join.getRight();
	}

}
