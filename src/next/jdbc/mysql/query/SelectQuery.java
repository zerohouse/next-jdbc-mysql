package next.jdbc.mysql.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import next.jdbc.mysql.DAOQuery;
import next.jdbc.mysql.query.support.Delimiter;
import next.jdbc.mysql.query.support.Typer;
import next.jdbc.mysql.query.support.Limit;
import next.jdbc.mysql.query.support.OrderBy;
import next.jdbc.mysql.sql.Sql;

public class SelectQuery<T> {

	Sql where;
	List<String> select;
	Typer typeAnalyzer;
	Limit limit;
	OrderBy orderBy;
	Class<T> type;
	DAOQuery dao;

	public SelectQuery(DAOQuery dao, Class<T> type) {
		this.type = type;
		orderBy = new OrderBy();
		typeAnalyzer = new Typer(type);
		this.dao = dao;
	}

	@Override
	public String toString() {
		return getSql().getQueryString();
	}

	Sql getSql() {
		String fields = ASTAR;
		if (select != null) {
			StringBuilder builder = new StringBuilder();
			Delimiter limiter = new Delimiter(COMMA);
			select.forEach(name -> {
				builder.append(limiter.get());
				builder.append(typeAnalyzer.get(name).getColumnName());
			});
			fields = builder.toString();
		}
		Sql result = new Sql(String.format(FORMAT, fields, typeAnalyzer.getTableName()));
		if (where != null) {
			result.append(WHERE);
			result.concat(where);
		}
		if (!orderBy.isEmpty()) {
			orderBy.orderBy(result.getQuery());
		}
		if (limit != null) {
			limit.limit(result.getQuery());
		}
		return result;
	}

	private static final String FORMAT = "SELECT %s from %s";
	private static final String ASTAR = "*";
	private static final String COMMA = ",";
	private static final String WHERE = " WHERE ";

	/**
	 * 쿼리 조건에 맞는 첫번쨰 레코드를 반환합니다.
	 *
	 * @return T 결과
	 */
	public T find() {
		Sql sql = getSql();
		return (T) dao.get(type, sql.getQueryString(), sql.getParameterArray());
	}

	/**
	 * 쿼리 조건에 맞는 레코드를 리스트로 반환합니다.
	 * <p>
	 *
	 * @return List 결과
	 */
	public List<T> findList() {
		Sql sql = getSql();
		return (List<T>) dao.getList(type, sql.getQueryString(), sql.getParameterArray());
	}

	/**
	 * 쿼리 조건에 맞는 첫번쨰 레코드를 맵으로 반환합니다.
	 *
	 * @return T 결과
	 */
	public Map<String, Object> asMap() {
		Sql sql = getSql();
		return dao.getRecord(sql.getQueryString(), sql.getParameterArray());
	}

	/**
	 * 쿼리 조건에 맞는 레코드를 맵의 리스트로 반환합니다.
	 * <p>
	 *
	 * @return List 결과
	 */
	public List<Map<String, Object>> asMapList() {
		Sql sql = getSql();
		return dao.getRecords(sql.getQueryString(), sql.getParameterArray());
	}

	/**
	 * limit 조건을 부여합니다.
	 * <p>
	 *
	 * @param begin
	 *            시작값
	 * @param end
	 *            범위
	 * @return QueryNeedFieldForSelect
	 */
	public SelectQuery<T> limit(int begin, int end) {
		limit = new Limit(begin, end);
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

	public SelectQuery<T> orderBy(String fieldName) {
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

	public SelectQuery<T> orderBy(String fieldName, boolean desc) {
		orderBy.order(typeAnalyzer.get(fieldName).getColumnName(), desc);
		return this;
	}

	/**
	 * 
	 * 필드 조건을 추가합니다.
	 * 
	 * @param fieldName
	 *            조건을 줄 필드
	 *
	 * @return QueryNeedValueForSelect
	 */
	public QueryNeedValueForSelect<T> whereField(String fieldName) {
		makeWhere();
		return new QueryNeedValueForSelect<T>(fieldName, this);
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
	public SelectQuery<T> select(String... fieldNames) {
		makeSelect();
		for (String field : fieldNames)
			select.add(field);
		return this;
	}

	void makeSelect() {
		if (select == null)
			select = new ArrayList<String>();
	}

	void makeWhere() {
		if (where == null)
			where = new Sql();
	}
}
