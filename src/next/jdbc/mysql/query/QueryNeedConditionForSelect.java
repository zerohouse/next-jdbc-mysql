package next.jdbc.mysql.query;

import java.util.List;

import next.jdbc.mysql.query.support.Limit;

public class QueryNeedConditionForSelect<T> {

	private SelectQuery<T> query;

	public QueryNeedConditionForSelect(SelectQuery<T> query) {
		this.query = query;
	}

	private static final String AND = " AND ";
	private static final String OR = " OR ";

	/**
	 * and조건의 다른 필드를 선택합니다.
	 * <p>
	 *
	 */
	public QueryNeedFieldForSelect<T> and() {
		query.where.append(AND);
		return new QueryNeedFieldForSelect<T>(query);
	}

	/**
	 * or조건의 다른 필드를 선택합니다.
	 * <p>
	 *
	 */
	public QueryNeedFieldForSelect<T> or() {
		query.where.append(OR);
		return new QueryNeedFieldForSelect<T>(query);
	}

	/**
	 * limit 조건을 부여합니다.
	 * <p>
	 *
	 * @param begin
	 *            시작값
	 * @param end
	 *            범위
	 */
	public QueryNeedConditionForSelect<T> limit(int begin, int end) {
		query.limit = new Limit(begin, end);
		return this;
	}

	/**
	 * 정렬 조건을 부여합니다.
	 * <p>
	 *
	 * @param fieldName
	 *            기준 필드 이름
	 */
	public QueryNeedConditionForSelect<T> orderBy(String fieldName) {
		return orderBy(fieldName, false);
	}

	/**
	 * 정렬 조건을 부여합니다.
	 * <p>
	 *
	 * @param fieldName
	 *            기준 필드 이름
	 * @param desc
	 *            역순 여부
	 */
	public QueryNeedConditionForSelect<T> orderBy(String fieldName, boolean desc) {
		query.orderBy.order(query.typeAnalyzer.get(fieldName).getColumnName(), desc);
		return this;
	}

	/**
	 * 쿼리 조건에 맞는 첫번쨰 레코드를 반환합니다.
	 * <p>
	 *
	 * @return T 결과
	 */
	public T find() {
		return query.find();
	}

	/**
	 * 쿼리 조건에 맞는 레코드를 리스트로 반환합니다.
	 * <p>
	 *
	 * @return List<T> 결과
	 */
	public List<T> findList() {
		return query.findList();
	}

	@Override
	public String toString() {
		return query.toString();
	}

}
