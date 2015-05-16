package next.jdbc.mysql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import next.jdbc.mysql.setting.Parser;
import next.jdbc.mysql.setting.Setting;
import next.jdbc.mysql.sql.KeyParams;
import next.jdbc.mysql.sql.NullableParams;

/**
 * 정의한 클래스의 테이블에 연관되는 작업을 수행합니다.<br>
 * 기본 테이블명은 [클래스명]<br>
 * 기본 칼럼명은 [클래스명]_[칼럼명]으로 정의되어 있으며,<br>
 * 키 칼럼이 필요합니다.<br>
 * <br>
 * 아래의 어노테이션으로 클래스를 정의합니다.<br>
 * 
 * &#064;Table, &#064;Key, &#064;Column, &#064;Exclude, &#064;OtherTable,
 * &#064;RequiredRegex
 * 
 */

public class GDAO<T> extends DAORaw {

	private Class<T> type;

	public GDAO(Class<T> type) {
		this.type = type;
	}

	public GDAO(Class<T> type, Transaction tran) {
		super(tran);
		this.type = type;
	}

	/**
	 * SQL에 해당하는 Object를 리턴합니다.
	 * <p>
	 *
	 * @param sql
	 *            SQL 실행문
	 * @param parameters
	 *            SQL 파라미터
	 * @return T
	 */

	public T get(String sql, Object... parameters) {
		Map<String, Object> record = getRecord(sql, parameters);
		T result = Parser.getObject(type, record);
		return result;
	}

	/**
	 * 클래스에 해당하는 SQL을 생성하여 Object를 찾습니다.<br>
	 * 
	 * &#064;Key, &#064;Column 이 필요합니다.
	 * <p>
	 *
	 *
	 * @param parameters
	 *            Key에 해당하는 파라미터
	 * @return T
	 */
	public T find(Object... parameters) {
		KeyParams sp = Setting.getSqlSupports().getKeyParams(type);
		Map<String, Object> record = getRecord(String.format("SELECT * FROM %s WHERE %s", sp.getTableName(), sp.getKeyFieldNames(EQ, and)),
				parameters);
		T result = Parser.getObject(type, record);
		return result;
	}

	/**
	 * SQL에 해당하는 Object를 리스트로 만들어 리턴합니다.
	 * <p>
	 *
	 * @param sql
	 *            sql문
	 * @param parameters
	 *            ?에 파싱할 파라미터
	 * @return T List
	 */
	public List<T> getList(String sql, Object... parameters) {
		List<Map<String, Object>> records = getRecords(sql, parameters);
		List<T> result = new ArrayList<T>();
		if (records == null)
			return null;
		records.forEach(record -> {
			result.add(Parser.getObject(type, record));
		});
		return result;
	}

	/**
	 * Object와 같은 조건의 리스트를 반환합니다.<br>
	 * <br>
	 * User { name : null, age : 10, email : null, gender : null }를<br>
	 * 파라미터로 넣으면, age=10인 유저의 리스트를 반환합니다.
	 * <p>
	 *
	 * @param object
	 *            조건 오브젝트
	 * @return T List
	 */
	public List<T> findList(T object) {
		KeyParams kp = new NullableParams(Setting.getSqlSupports(), object);
		return getList(String.format("SELECT * FROM %s WHERE %s", kp.getTableName(), kp.getKeyFieldNames(EQ, and)), kp.getKeyParams().toArray());
	}

	/**
	 * Object와 같은 조건의 리스트를 반환합니다.<br>
	 * <br>
	 * User { name : null, age : 10, email : null, gender : null }를<br>
	 * 파라미터로 넣으면, age=10인 유저의 리스트를 반환합니다.
	 * <p>
	 *
	 * @param object
	 *            조건 오브젝트
	 * @param additionalCondition
	 *            page, limit, orderBy 등의 조건을 정의합니다.
	 *            ex)"order by User_id limit 0,3"
	 * @return T List
	 */
	public List<T> findList(T object, String additionalCondition) {
		KeyParams kp = new NullableParams(Setting.getSqlSupports(), object);
		return getList(String.format("SELECT * FROM %s WHERE %s %s", kp.getTableName(), kp.getKeyFieldNames(EQ, and), additionalCondition), kp
				.getKeyParams().toArray());
	}

	public static final String EQ = "=?";
	public static final String and = " and ";
	public static final String comma = ", ";

	/**
	 * Object를 조건이 맞는 DB의 데이터로 채웁니다.<br>
	 * 만약 조건에 맞는 Object가 결과가 없다면 null이 리턴됩니다.<br>
	 * User { name : null, age : 10, email : mail@mail.com, gender : null }를<br>
	 * 파라미터로 넣으면, age=10, mail=mail@mail.com인 유저의 오브젝트가 리턴됩니다.<br>
	 * 
	 *
	 * @param object
	 *            채울 object
	 * @return boolean 실행결과
	 */
	public T fill(T object) {
		KeyParams kp = new NullableParams(Setting.getSqlSupports(), object);
		Map<String, Object> recordMap = getRecord(String.format("SELECT * FROM %s WHERE %s", kp.getTableName(), kp.getKeyFieldNames(EQ, and)), kp
				.getKeyParams().toArray());
		return Parser.setObject(object, recordMap);
	}

	private final static String INSERT = "INSERT %s SET %s";

	/**
	 * Object를 DB에 삽입합니다.
	 * <p>
	 *
	 * @param object
	 *            삽입할 object
	 * @return boolean 실행결과
	 */
	public boolean insert(T object) {
		KeyParams sap = Setting.getSqlSupports().getKeyParams(object);
		if (sap.isEmpty())
			return false;
		String fieldsNames = sap.getIntegratedFieldNames(EQ, comma);
		String sql = String.format(INSERT, sap.getTableName(), fieldsNames);
		return execute(sql, sap.getIntegratedParams());
	}

	private final static String INSERT_IFEXISTS_UPDATE = "INSERT INTO %s (%s) VALUES (%s) ON DUPLICATE KEY UPDATE %s";

	/**
	 * Object를 DB에 삽입합니다. 같은 키를 가진 레코드가 있으면, 업데이트합니다.
	 * <p>
	 *
	 * @param object
	 *            삽입 / 업데이트할 object
	 * @return boolean 실행결과
	 */
	public boolean insertIfExistUpdate(T object) {
		KeyParams sap = Setting.getSqlSupports().getKeyParams(object);
		if (sap.isEmpty())
			return false;
		String fieldsNames = sap.getIntegratedFieldNames("", comma);
		int size = sap.getParams().size() + sap.getKeyParams().size();
		String questions = "";
		for (int i = 0; i < size; i++) {
			questions += "?,";
		}
		questions = questions.substring(0, questions.length() - 1);

		String sql = String.format(INSERT_IFEXISTS_UPDATE, sap.getTableName(), fieldsNames, questions, sap.getFieldNames(EQ, comma));
		List<Object> params = new ArrayList<Object>();
		params.addAll(sap.getParams());
		params.addAll(sap.getKeyParams());
		params.addAll(sap.getParams());
		return execute(sql, params.toArray());
	}

	private final static String UPDATE = "UPDATE %s SET %s WHERE %s";

	/**
	 * Object를 업데이트합니다. 같은 키를 가진 레코드를 업데이트합니다.
	 * <p>
	 *
	 * @param object
	 *            업데이트할 object
	 * @return boolean 실행결과
	 */
	public boolean update(T object) {
		KeyParams sap = Setting.getSqlSupports().getKeyParams(object);
		if (!sap.hasKeyParams())
			return false;
		if (!sap.hasParams())
			return false;
		String sql = String.format(UPDATE, sap.getTableName(), sap.getFieldNames(EQ, comma), sap.getKeyFieldNames(EQ, and));
		List<Object> params = new ArrayList<Object>();
		params.addAll(sap.getParams());
		params.addAll(sap.getKeyParams());
		return execute(sql, params.toArray());
	}

	private final static String DELETE = "DELETE FROM %s WHERE %s";

	/**
	 * Object를 삭제합니다. 같은 키를 가진 레코드를 삭제합니다.
	 * <p>
	 *
	 * @param object
	 *            삭제할 object
	 * @return boolean 실행결과
	 */
	public boolean delete(T object) {
		KeyParams sap = Setting.getSqlSupports().getKeyParams(object);
		if (!sap.hasKeyParams())
			return false;
		return execute(String.format(DELETE, sap.getTableName(), sap.getKeyFieldNames(EQ, and)), sap.getKeyParams().toArray());
	}

}
