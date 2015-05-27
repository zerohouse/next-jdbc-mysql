package next.jdbc.mysql.query.analyze;

import next.jdbc.mysql.join.Join;
import next.jdbc.mysql.query.analyze.bind.Fields;

public class JoinAnalyzer implements Analyzer {

	private Analyzer left;
	private Analyzer right;
	@SuppressWarnings("rawtypes")
	private Join join;

	public Analyzer getLeft() {
		return left;
	}

	public Analyzer getRight() {
		return right;
	}

	@SuppressWarnings("rawtypes")
	public JoinAnalyzer(Join join) {
		left = new ObjectAnalyzer(join.getLeft());
		left.getNotNullFields().forEach(field -> {
			field.setJoin(true);
		});
		right = new ObjectAnalyzer(join.getRight());
		right.getNotNullFields().forEach(field -> {
			field.setJoin(true);
		});
		this.join = join;
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

	private final static String JOIN_NAME = "%s %s JOIN %s ON %s.%s = %s.%s";

	@Override
	public String getTableName() {
		return String.format(JOIN_NAME, left.getTableName(), join.getJoinType().getType(), right.getTableName(), left.getTableName(), left
				.getAllFields().findByFieldName(join.getLeftOnFieldName()).getColumnName(), right.getTableName(), right.getAllFields()
				.findByFieldName(join.getRightOnFieldName()).getColumnName());
	}

	@Override
	public Fields getAllFields() {
		Fields fields = new Fields();
		fields.concat(left.getAllFields());
		fields.concat(right.getAllFields());
		return fields;
	}

}
