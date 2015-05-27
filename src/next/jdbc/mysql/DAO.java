package next.jdbc.mysql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import next.jdbc.mysql.join.Join;
import next.jdbc.mysql.join.JoinSet;
import next.jdbc.mysql.query.Query;
import next.jdbc.mysql.query.QueryMaker;
import next.jdbc.mysql.query.analyze.Analyzer;
import next.jdbc.mysql.query.analyze.JoinAnalyzer;
import next.jdbc.mysql.query.analyze.ObjectAnalyzer;
import next.jdbc.mysql.query.analyze.TypeAnalyzer;
import next.jdbc.mysql.query.analyze.bind.ModelMaker;

/**
 * Database Access 작업을 수행합니다.<br>
 * <br>
 * 클래스에 아래의 어노테이션을 사용 가능 합니다.<br>
 * 
 * &#064;Table, &#064;Key, &#064;Column, &#064;Exclude, &#064;OtherTable,
 * &#064;RequiredRegex
 * 
 */

public class DAO extends DAORaw {

	private QueryMaker maker;

	public DAO() {
		maker = new QueryMaker();
	}

	public DAO(Transaction tran) {
		super(tran);
		maker = new QueryMaker();
	}

	/**
	 * 클래스에 해당하는 SQL을 생성하여 Object를 찾습니다.<br>
	 * 해당 클래스의 키 어노테이션 지정이 필요합니다.
	 * <p>
	 *
	 *
	 * @param <T>
	 *            클래스 타입
	 * @param type
	 *            클래스 타입
	 * @param parameters
	 *            Key에 해당하는 파라미터
	 * @return T
	 */
	public <T> T get(Class<T> type, Object... parameters) {
		Analyzer analyzer = new TypeAnalyzer(type);
		Query query = maker.select(analyzer);
		Map<String, Object> record = getRecord(query.getQueryString(), parameters);
		if (record == null)
			return null;
		@SuppressWarnings("unchecked")
		T result = (T) ModelMaker.newInstance(type);
		ModelMaker.setByMap(result, record, analyzer);
		return result;
	}

	/**
	 * SQL에 해당하는 레코드를 Object로 리턴합니다.
	 * <p>
	 *
	 * @param sql
	 *            SQL 실행문
	 * @param <T>
	 *            클래스 타입
	 * @param type
	 *            클래스 타입
	 * @param parameters
	 *            SQL 파라미터
	 * @return T
	 */

	public <T> T get(Class<T> type, String sql, Object... parameters) {
		Map<String, Object> record = getRecord(sql, parameters);
		if (record == null)
			return null;
		@SuppressWarnings("unchecked")
		T result = (T) ModelMaker.newInstance(type);
		ModelMaker.setByMap(result, record);
		return result;
	}

	/**
	 * SQL에 해당하는 Object를 리스트로 만들어 리턴합니다.
	 * <p>
	 *
	 * @param <T>
	 *            클래스 타입
	 * @param type
	 *            클래스 타입
	 * @param sql
	 *            sql문
	 * @param parameters
	 *            ?에 파싱할 파라미터
	 * @return T List
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(Class<T> type, String sql, Object... parameters) {
		List<Map<String, Object>> records = getRecords(sql, parameters);
		List<T> result = new ArrayList<T>();
		if (records == null)
			return null;
		records.forEach(record -> {
			T object = (T) ModelMaker.newInstance(type);
			ModelMaker.setByMap(object, record);
			result.add(object);
		});
		return result;
	}

	/**
	 * Object를 조건이 맞는 레코드를 찾습니다.<br>
	 * 만약 조건에 맞는 결과가 없다면 null이 리턴됩니다.<br>
	 * User { name : null, age : 10, email : mail@mail.com, gender : null }를<br>
	 * 파라미터로 넣으면, age=10, mail=mail@mail.com인 유저의 오브젝트가 리턴됩니다.<br>
	 * <p>
	 * 
	 * @param <T>
	 *            클래스 타입
	 * @param object
	 *            찾을 object
	 * @return Object 해당 Object
	 */
	public <T> T find(T object) {
		Analyzer analyzer = new ObjectAnalyzer(object);
		Query query = maker.select(analyzer);
		Map<String, Object> recordMap = getRecord(query.getQueryString(), query.getParameterArray());
		if (recordMap == null)
			return null;
		ModelMaker.setByMap(object, recordMap, analyzer);
		return object;
	}

	/**
	 * Object와 조건이 맞는 조인 레코드를 찾습니다.<br>
	 * <br>
	 * <p>
	 *
	 * @param <LEFT>
	 *            Type1
	 * @param <RIGHT>
	 *            Type2
	 * @param join
	 *            조건 오브젝트
	 * @return Join join된 left, right
	 */
	public <LEFT, RIGHT> JoinSet<LEFT, RIGHT> find(Join<LEFT, RIGHT> join) {
		JoinAnalyzer analyzer = new JoinAnalyzer(join);
		Query query = maker.select(analyzer);
		Map<String, Object> recordMap = getRecord(query.getQueryString(), query.getParameterArray());
		ModelMaker.setByMap(join.getLeft(), recordMap, analyzer.getLeft());
		ModelMaker.setByMap(join.getRight(), recordMap, analyzer.getRight());
		return new JoinSet<LEFT, RIGHT>(join.getLeft(), join.getRight());
	}

	/**
	 * Object와 같은 조건의 리스트를 반환합니다.<br>
	 * <br>
	 * User { name : null, age : 10, email : null, gender : null }를<br>
	 * 파라미터로 넣으면, age=10인 유저의 리스트를 반환합니다.
	 * <p>
	 *
	 * @param <T>
	 *            Type
	 * @param object
	 *            조건 오브젝트
	 * @return T List
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findList(T object) {
		Query query = maker.select(new ObjectAnalyzer(object));
		return (List<T>) getList(object.getClass(), query.getQueryString(), query.getParameterArray());
	}

	/**
	 * Object와 같은 조건의 리스트를 반환합니다.<br>
	 * <br>
	 * User { name : null, age : 10, email : null, gender : null }를<br>
	 * 파라미터로 넣으면, age=10인 유저의 리스트를 반환합니다.
	 * <p>
	 * 
	 * @param <T>
	 *            Type
	 * @param object
	 *            조건 오브젝트
	 * @param additionalCondition
	 *            page, limit, orderBy 등의 조건을 정의합니다.
	 *            ex)"order by User_id limit 0,3"
	 * @return T List
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findList(T object, String additionalCondition) {
		Query query = maker.select(new ObjectAnalyzer(object));
		query.append(" ");
		query.append(additionalCondition);
		return (List<T>) getList(object.getClass(), query.getQueryString(), query.getParameterArray());
	}

	/**
	 * Object와 조건이 맞는 조인 레코드를 찾습니다.<br>
	 * <br>
	 * <p>
	 * 
	 * @param <LEFT>
	 *            LeftType
	 * @param <RIGHT>
	 *            RightType
	 * @param join
	 *            조건 오브젝트
	 * @return JoinSet join된 left, right
	 */
	@SuppressWarnings("unchecked")
	public <LEFT, RIGHT> List<JoinSet<LEFT, RIGHT>> findList(Join<LEFT, RIGHT> join) {
		JoinAnalyzer analyzer = new JoinAnalyzer(join);
		Query query = maker.select(analyzer);
		List<JoinSet<LEFT, RIGHT>> result = new ArrayList<JoinSet<LEFT, RIGHT>>();
		List<Map<String, Object>> recordMap = getRecords(query.getQueryString(), query.getParameterArray());
		recordMap.forEach(map -> {
			Object left = ModelMaker.newInstance(join.getLeft().getClass());
			Object right = ModelMaker.newInstance(join.getRight().getClass());
			ModelMaker.setByMap(left, map, analyzer.getLeft());
			ModelMaker.setByMap(right, map, analyzer.getRight());
			result.add(new JoinSet<LEFT, RIGHT>((LEFT) left, (RIGHT) right));
		});
		return result;
	}

	/**
	 * Object와 조건이 맞는 조인 레코드를 찾습니다.<br>
	 * <br>
	 * <p>
	 * 
	 * @param <LEFT>
	 *            LeftType
	 * @param <RIGHT>
	 *            RightType
	 * @param join
	 *            조건 오브젝트
	 * @param additionalCondition
	 *            page, limit, orderBy 등의 조건을 정의합니다.
	 *            ex)"order by User_id limit 0,3"
	 * @return JoinSet join된 left, right
	 */
	@SuppressWarnings("unchecked")
	public <LEFT, RIGHT> List<JoinSet<LEFT, RIGHT>> findList(Join<LEFT, RIGHT> join, String additionalCondition) {
		JoinAnalyzer analyzer = new JoinAnalyzer(join);
		Query query = maker.select(analyzer);
		query.append(" ");
		query.append(additionalCondition);
		List<JoinSet<LEFT, RIGHT>> result = new ArrayList<JoinSet<LEFT, RIGHT>>();
		List<Map<String, Object>> recordMap = getRecords(query.getQueryString(), query.getParameterArray());
		recordMap.forEach(map -> {
			Object left = ModelMaker.newInstance(join.getLeft().getClass());
			Object right = ModelMaker.newInstance(join.getRight().getClass());
			ModelMaker.setByMap(left, map, analyzer.getLeft());
			ModelMaker.setByMap(right, map, analyzer.getRight());
			result.add(new JoinSet<LEFT, RIGHT>((LEFT) left, (RIGHT) right));
		});
		return result;
	}

	/**
	 * Object를 DB에 삽입합니다.
	 * <p>
	 *
	 * @param object
	 *            삽입할 object
	 * @return boolean 실행결과
	 */
	public boolean insert(Object object) {
		Query query = maker.insert(new ObjectAnalyzer(object));
		return execute(query);
	}

	/**
	 * Object를 DB에 삽입합니다. 같은 키를 가진 레코드가 있으면, 업데이트합니다.
	 * <p>
	 *
	 * @param object
	 *            삽입 / 업데이트할 object
	 * @return boolean 실행결과
	 */
	public boolean insertIfExistUpdate(Object object) {
		Query query = maker.insertIfExistUpdate(new ObjectAnalyzer(object));
		return execute(query);
	}

	/**
	 * Object를 업데이트합니다. 같은 키를 가진 레코드를 업데이트합니다.
	 * <p>
	 *
	 * @param object
	 *            업데이트할 object
	 * @return boolean 실행결과
	 */
	public boolean update(Object object) {
		Query query = maker.update(new ObjectAnalyzer(object));
		return execute(query);
	}

	/**
	 * Object를 업데이트합니다. 키필드를 지정합니다.
	 * <p>
	 *
	 * @param object
	 *            업데이트할 object
	 * @param keyFieldNames
	 *            업데이트할 오브젝트의 keyField
	 * @return boolean 실행결과
	 */
	public boolean update(Object object, String... keyFieldNames) {
		Query query = maker.update(new ObjectAnalyzer(object), keyFieldNames);
		return execute(query);
	}

	/**
	 * Object를 삭제합니다. 같은 조건을 가진 레코드를 삭제합니다.
	 * <p>
	 *
	 * @param object
	 *            삭제할 object
	 * @return boolean 실행결과
	 */
	public boolean delete(Object object) {
		Query query = maker.delete(new ObjectAnalyzer(object));
		return execute(query);
	}

	private boolean execute(Query query) {
		return execute(query.getQueryString(), query.getParameterArray());
	}

}
