package next.jdbc.mysql;

import java.util.List;
import java.util.Map;

import next.jdbc.mysql.join.Join;
import next.jdbc.mysql.sql.Sql;
import next.jdbc.mysql.sql.SqlMaker;
import next.jdbc.mysql.sql.analyze.Analyzer;
import next.jdbc.mysql.sql.analyze.ObjectAnalyzer;
import next.jdbc.mysql.sql.analyze.bind.ModelMaker;

/**
 * Database Access 작업을 수행합니다.<br>
 * <br>
 * 클래스에 아래의 어노테이션을 사용 가능 합니다.<br>
 * 
 * &#064;Table, &#064;Key, &#064;Column, &#064;Exclude, &#064;OtherTable,
 * &#064;RequiredRegex
 * 
 */

public class DAO extends DAOQuery {

	private SqlMaker maker;

	public DAO() {
		maker = new SqlMaker();
	}

	public DAO(Transaction tran) {
		super(tran);
		maker = new SqlMaker();
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
		Analyzer analyzer = Analyzer.getAnalyzer(type);
		analyzer.setKeyParameters(parameters);
		Sql query = maker.select(analyzer);
		Map<String, Object> record = getRecord(query.getQueryString(), query.getParameterArray());
		if (record == null)
			return null;
		return ModelMaker.getObjectByMap(type, record, analyzer);
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
	@SuppressWarnings("unchecked")
	public <T> T find(T object) {
		Analyzer analyzer = Analyzer.getObjectAnalyzer(object);
		Sql query = maker.select(analyzer);
		Map<String, Object> recordMap = getRecord(query.getQueryString(), query.getParameterArray());
		if (recordMap == null)
			return null;
		return (T) ModelMaker.setByMap(object.getClass(), object, recordMap, analyzer);
	}

	/**
	 * Object를 찾습니다. 키필드를 지정합니다.
	 * <p>
	 *
	 * @param object
	 *            찾을 object
	 * @param keyFieldNames
	 *            오브젝트의 keyField
	 * @return object 실행결과
	 */
	@SuppressWarnings("unchecked")
	public <T> T find(T object, String... keyFieldNames) {
		Analyzer analyzer = Analyzer.getObjectAnalyzer(object);
		Sql query = maker.select(analyzer, keyFieldNames);
		Map<String, Object> recordMap = getRecord(query.getQueryString(), query.getParameterArray());
		if (recordMap == null)
			return null;
		return (T) ModelMaker.setByMap(object.getClass(), object, recordMap, analyzer);
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
		Analyzer analyzer = Analyzer.getObjectAnalyzer(object);
		Sql query = maker.select(analyzer);
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
		Analyzer analyzer = Analyzer.getObjectAnalyzer(object);
		Sql query = maker.select(analyzer);
		query.append(" ");
		query.append(additionalCondition);
		return (List<T>) getList(object.getClass(), query.getQueryString(), query.getParameterArray());
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
		if (Join.class.isAssignableFrom(object.getClass())) {
			@SuppressWarnings("rawtypes")
			Join join = (Join) object;
			return insert(join.getLeft()) && insert(join.getRight());
		}
		Sql query = maker.insert(new ObjectAnalyzer(object));
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
		if (Join.class.isAssignableFrom(object.getClass())) {
			@SuppressWarnings("rawtypes")
			Join join = (Join) object;
			return insertIfExistUpdate(join.getLeft()) && insertIfExistUpdate(join.getRight());
		}
		Sql query = maker.insertIfExistUpdate(new ObjectAnalyzer(object));
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
		if (Join.class.isAssignableFrom(object.getClass())) {
			@SuppressWarnings("rawtypes")
			Join join = (Join) object;
			return update(join.getLeft()) && update(join.getRight());
		}
		Sql query = maker.update(new ObjectAnalyzer(object));
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
		if (Join.class.isAssignableFrom(object.getClass())) {
			@SuppressWarnings("rawtypes")
			Join join = (Join) object;
			return update(join.getLeft(), keyFieldNames) && update(join.getRight(), keyFieldNames);
		}
		Sql query = maker.update(new ObjectAnalyzer(object), keyFieldNames);
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
		if (Join.class.isAssignableFrom(object.getClass())) {
			@SuppressWarnings("rawtypes")
			Join join = (Join) object;
			return delete(join.getLeft()) && delete(join.getRight());
		}
		Sql query = maker.delete(new ObjectAnalyzer(object));
		return execute(query);
	}

	private boolean execute(Sql query) {
		return execute(query.getQueryString(), query.getParameterArray());
	}

}
