package next.jdbc.mysql.sql.analyze;

import java.lang.reflect.ParameterizedType;

import next.jdbc.mysql.join.Join;
import next.jdbc.mysql.sql.analyze.bind.Fields;
import next.jdbc.mysql.sql.analyze.bind.ModelMaker;

public class JoinTypeAnalyzer implements Analyzer {

	protected Analyzer left;
	protected Analyzer right;
	@SuppressWarnings("rawtypes")
	protected Join join;

	public Analyzer getLeftAnalyzer() {
		return left;
	}

	public Analyzer getRightAnalyzer() {
		return right;
	}

	@SuppressWarnings("rawtypes")
	public JoinTypeAnalyzer(Class<?> type) {
		defineGenerics(type);
		left = Analyzer.getAnalyzer(leftType);
		right = Analyzer.getAnalyzer(rightType);
		join = (Join) ModelMaker.newInstance(type);
	}

	public JoinTypeAnalyzer() {
	}

	private void defineGenerics(Class<?> type) {
		this.leftType = (Class<?>) ((ParameterizedType) type.getGenericSuperclass()).getActualTypeArguments()[0];
		this.rightType = (Class<?>) ((ParameterizedType) type.getGenericSuperclass()).getActualTypeArguments()[1];
	}

	protected Class<?> leftType;
	protected Class<?> rightType;

	@Override
	public Fields getNotNullKeyFields() {
		Fields fields = new Fields();
		fields.concat(left.getNotNullKeyFields());
		fields.concat(right.getNotNullKeyFields());
		return fields;
	}

	@Override
	public Fields getNotNullAllFields() {
		Fields fields = new Fields();
		fields.concat(left.getNotNullAllFields());
		fields.concat(right.getNotNullAllFields());
		return fields;
	}

	@Override
	public Fields getNotNullFields() {
		Fields fields = new Fields();
		fields.concat(left.getNotNullFields());
		fields.concat(right.getNotNullFields());
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

	public Class<?> getLeftType() {
		return leftType;
	}

	public Class<?> getRightType() {
		return rightType;
	}

	@SuppressWarnings("rawtypes")
	public Join getJoin() {
		return join;
	}

	@Override
	public void setKeyParameters(Object[] parameters) {
		for (int i = 0; i < parameters.length; i++)
			getNotNullKeyFields().get(i).setObject(parameters[i]);
	}

}
