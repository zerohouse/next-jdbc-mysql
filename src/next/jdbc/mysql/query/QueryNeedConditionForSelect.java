package next.jdbc.mysql.query;

import java.util.List;
import java.util.Map;

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
	 * 
	 * @return QueryNeedFieldForSelect
	 */
	public QueryNeedFieldForSelect<T> and() {
		query.where.append(AND);
		return new QueryNeedFieldForSelect<T>(query);
	}

	/**
	 * or조건의 다른 필드를 선택합니다.
	 *
	 * @return QueryNeedFieldForSelect
	 */
	public QueryNeedFieldForSelect<T> or() {
		query.where.append(OR);
		return new QueryNeedFieldForSelect<T>(query);
	}

	/**
	 * limit 조건을 부여합니다.
	 * <p>
	 *
	 * @param start
	 *            시작값
	 * @param size
	 *            범위
	 * @return QueryNeedFieldForSelect
	 */
	public QueryNeedConditionForSelect<T> limit(int start, int size) {
		query.limit = new Limit(start, size);
		return this;
	}

	/**
	 * 정렬 조건을 부여합니다.
	 * <p>
	 *
	 * @param fieldName
	 *            기준 필드 이름
	 * @return QueryNeedFieldForSelect
	 */
	public QueryNeedConditionForSelect<T> orderBy(String fieldName) {
		return orderBy(fieldName, false);
	}

	/**
	 * 정렬 조건을 부여합니다.
	 *
	 * @param fieldName
	 *            기준 필드 이름
	 * @param desc
	 *            역순 여부
	 * @return QueryNeedFieldForSelect
	 */
	public QueryNeedConditionForSelect<T> orderBy(String fieldName, boolean desc) {
		query.orderBy.order(query.typeAnalyzer.get(fieldName).getColumnName(), desc);
		return this;
	}

	/**
	 * 쿼리 조건에 맞는 첫번쨰 레코드를 반환합니다.
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
	 * @return List 결과
	 */
	public List<T> findList() {
		return query.findList();
	}

	/**
	 * 쿼리 조건에 맞는 첫번쨰 레코드를 맵으로 반환합니다.
	 *
	 * @return T 결과
	 */
	public Map<String, Object> asMap() {
		return query.asMap();
	}

	/**
	 * 쿼리 조건에 맞는 레코드를 맵의 리스트로 반환합니다.
	 * <p>
	 *
	 * @return List 결과
	 */
	public List<Map<String, Object>> asMapList() {
		return query.asMapList();
	}

	/**
	 * 
	 * 선택할 필드를 지정합니다. 지정하지 않으면 모두 선택합니다.
	 * 
	 * @param fieldNames
	 *            선택한 필드
	 *
	 * @return QueryNeedConditionForSelect
	 */
	public QueryNeedConditionForSelect<T> select(String... fieldNames) {
		query.select(fieldNames);
		return this;
	}

	@Override
	public String toString() {
		return query.toString();
	}

	/**
	 * 
	 * 중복되지 않게 선택합니다.
	 * 
	 * @return QueryNeedConditionForSelect
	 */

	public QueryNeedConditionForSelect<T> distinct() {
		query.distinct();
		return this;
	}

}
