package next.database;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * close 명령이 실행될때 작업을 실행합니다.<br>
 * 트랜젝션을 사용합니다.
 *
 */
public class Transaction implements ConnectionManager {

	private Connection conn;

	@Override
	public void close() {
		if (conn == null)
			return;
		try {
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void closeConnection() {
	}

	@Override
	public Connection getConnection() {
		if(conn==null)
			conn = ConnectionPool.getConnection(false);
		return conn;
	}
}
