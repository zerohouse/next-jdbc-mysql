package next.jdbc.mysql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import next.jdbc.mysql.query.DeleteQuery;
import next.jdbc.mysql.query.InsertQuery;
import next.jdbc.mysql.query.SelectQuery;
import next.jdbc.mysql.query.UpdateQuery;
import next.jdbc.mysql.sql.analyze.bind.ModelMaker;

/**
 * Database Access 작업을 수행합니다.<br>
 * SQL을 실행하여 실행결과를 Boolean, 맵, List로 반환합니다.<br>
 * 
 */
public class DAOQuery extends DAORaw {

	public DAOQuery() {
	};

	public DAOQuery(Transaction tran) {
		super(tran);
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
		return ModelMaker.getByMap(type, record);
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
	public <T> List<T> getList(Class<T> type, String sql, Object... parameters) {
		List<Map<String, Object>> records = getRecords(sql, parameters);
		List<T> result = new ArrayList<T>();
		if (records == null)
			return null;
		records.forEach(record -> {
			result.add(ModelMaker.getByMap(type, record));
		});
		return result;
	}

	/**
	 * 테이블에 해당하는 Select Query를 얻습니다.
	 * <p>
	 * 
	 * @param <T>
	 *            클래스 타입
	 * @param type
	 *            클래스 타입
	 * @return SelectQuery Query
	 */
	public <T> SelectQuery<T> getSelectQuery(Class<T> type) {
		return new SelectQuery<T>(this, type);
	}

	/**
	 * 테이블에 해당하는 Insert Query를 얻습니다.
	 * <p>
	 * 
	 * @param <T>
	 *            클래스 타입
	 * @param type
	 *            클래스 타입
	 * @return InsertQuery Query
	 */
	public InsertQuery getInsertQuery(Class<?> type) {
		return new InsertQuery(this, type);
	}
	
	/**
	 * 테이블에 해당하는 Update Query를 얻습니다.
	 * <p>
	 * 
	 * @param <T>
	 *            클래스 타입
	 * @param type
	 *            클래스 타입
	 * @return UpdateQuery Query
	 */
	public UpdateQuery getUpdateQuery(Class<?> type) {
		return new UpdateQuery(this, type);
	}

	/**
	 * 테이블에 해당하는 Delete Query를 얻습니다.
	 * <p>
	 * 
	 * @param <T>
	 *            클래스 타입
	 * @param type
	 *            클래스 타입
	 * @return DeleteQuery Query
	 */
	public DeleteQuery getDeleteQuery(Class<?> type) {
		return new DeleteQuery(this, type);
	}

}
