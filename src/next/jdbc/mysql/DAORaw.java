package next.jdbc.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Database Access 작업을 수행합니다.<br>
 * SQL을 실행하여 실행결과를 Boolean, 맵, List로 반환합니다.<br>
 * 
 */
public class DAORaw {

	private static final Logger logger = LoggerFactory.getLogger(DAORaw.class);

	private ConnectionManager cm;

	public DAORaw() {
	}

	public DAORaw(Transaction tran) {
		this.cm = tran;
	}

	public ConnectionManager getConnectionManager() {
		if (cm == null)
			return new Autocommit();
		return cm;
	}

	/**
	 * 하나의 레코드(Row)에 해당하는 데이터를 Map으로 리턴합니다.
	 * <p>
	 *
	 * @param sql
	 *            SQL 실행문
	 * @param parameters
	 *            ?에 해당하는 파라미터
	 * @return Map 레코드(Row) 맵
	 */

	public Map<String, Object> getRecord(String sql, Object... parameters) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ConnectionManager cm = getConnectionManager();
		try {
			pstmt = getPSTMT(cm, sql, parameters);
			rs = pstmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			if (!rs.next())
				return null;
			Map<String, Object> record = new HashMap<String, Object>();
			for (int i = 1; i <= columnCount; i++) {
				record.put(metaData.getColumnLabel(i), rs.getObject(i));
			}
			return record;
		} catch (SQLException e) {
			return null;
		} finally {
			close(pstmt);
			close(rs);
			cm.closeConnection();
		}
	}

	/**
	 * 여러 개의 레코드(Row)에 해당하는 데이터를 Map리스트로 리턴합니다.
	 * <p>
	 *
	 * @param sql
	 *            SQL 실행문
	 * @param parameters
	 *            ?에 해당하는 파라미터
	 * @return Map의 리스트
	 */

	public List<Map<String, Object>> getRecords(String sql, Object... parameters) {
		List<Map<String, Object>> result = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ConnectionManager cm = getConnectionManager();
		try {
			pstmt = getPSTMT(cm, sql, parameters);
			rs = pstmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				if (result == null)
					result = new ArrayList<Map<String, Object>>();
				HashMap<String, Object> columns = new HashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					columns.put(metaData.getColumnLabel(i), rs.getObject(i));
				}
				result.add(columns);
			}

		} catch (SQLException e) {
			logger.warn(String.format("SQL:%s, Parameters:%s 실행중 %s 에러가 발생했습니다.", sql, parameters, e.getMessage()));
		} finally {
			close(pstmt);
			close(rs);
			cm.closeConnection();
		}
		return result;
	}

	/**
	 * 하나의 레코드(Row)에 해당하는 데이터를 List로 리턴합니다.
	 * <p>
	 *
	 * @param sql
	 *            SQL 실행문
	 * @param parameters
	 *            ?에 해당하는 파라미터
	 * @return List 레코드(Row) 맵
	 */

	public List<Object> getRecordAsList(String sql, Object... parameters) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ConnectionManager cm = getConnectionManager();
		try {
			pstmt = getPSTMT(cm, sql, parameters);
			rs = pstmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			if (!rs.next())
				return null;
			List<Object> record = new ArrayList<Object>();
			for (int i = 1; i <= columnCount; i++) {
				record.add(rs.getObject(i));
			}
			return record;
		} catch (SQLException e) {
			return null;
		} finally {
			close(pstmt);
			close(rs);
			cm.closeConnection();
		}
	}

	/**
	 * 여러 개의 레코드(Row)에 해당하는 데이터를 리스트로 리턴합니다.
	 * <p>
	 *
	 * @param sql
	 *            SQL 실행문
	 * @param parameters
	 *            ?에 해당하는 파라미터
	 * @return List records
	 */

	public List<List<Object>> getRecordsAsList(String sql, Object... parameters) {
		List<List<Object>> result = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ConnectionManager cm = getConnectionManager();
		try {
			pstmt = getPSTMT(cm, sql, parameters);
			rs = pstmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				if (result == null)
					result = new ArrayList<List<Object>>();
				List<Object> columns = new ArrayList<Object>();
				for (int i = 1; i <= columnCount; i++) {
					columns.add(rs.getObject(i));
				}
				result.add(columns);
			}
		} catch (SQLException e) {
			logger.warn(String.format("SQL:%s, Parameters:%s 실행중 %s 에러가 발생했습니다.", sql, parameters, e.getMessage()));
		} finally {
			close(pstmt);
			close(rs);
			cm.closeConnection();
		}
		return result;
	}

	/**
	 * SQL문을 실행합니다.
	 * <p>
	 *
	 * @param sql
	 *            실행할 sql문
	 * @param parameters
	 *            파라미터
	 * @return boolean 실행결과
	 */
	public Boolean execute(String sql, Object... parameters) {
		PreparedStatement pstmt = null;
		ConnectionManager cm = getConnectionManager();
		try {
			pstmt = getPSTMT(cm, sql, parameters);
			pstmt.execute();
			return true;
		} catch (SQLException e) {
			logger.warn(String.format("SQL:%s, Parameters:%s 실행중 %s 에러가 발생했습니다.", sql, parameters, e.getMessage()));
			return false;
		} finally {
			close(pstmt);
			cm.closeConnection();
		}
	}

	protected static void close(ResultSet rs) {
		if (rs == null)
			return;
		try {
			rs.close();
		} catch (SQLException sqle) {
		}
	}

	protected static void close(PreparedStatement pstmt) {
		if (pstmt == null)
			return;
		try {
			pstmt.close();
		} catch (SQLException sqle) {
		}
	}

	/**
	 * DAO의 커넥션을 종료합니다.<br>
	 * 트랜젝션을 사용한경우 작업후 반드시 종료해줘야 합니다.
	 *
	 */
	public void close() {
		cm.close();
	}

	protected static PreparedStatement getPSTMT(ConnectionManager cm, String sql, Object... parameters) {
		Connection conn = cm.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			if (parameters != null)
				for (int j = 0; j < parameters.length; j++) {
					pstmt.setObject(j + 1, parameters[j]);
				}
			logger.debug(pstmt.toString());
		} catch (SQLException e) {
			logger.warn(String.format("SQL:%s, Parameters:%s 실행중 %s 에러가 발생했습니다.", sql, parameters, e.getMessage()));
		}
		return pstmt;
	}

}
