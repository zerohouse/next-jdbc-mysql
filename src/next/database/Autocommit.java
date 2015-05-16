package next.database;

import java.sql.Connection;
import java.sql.SQLException;



/**
 * 매 작업마다 다른 커넥션을 사용합니다.<br>
 * 트랜젝션을 사용하지 않습니다.
 *
 */
public class Autocommit implements ConnectionManager {

	private Connection conn;

	@Override
	public void close() {
	}

	@Override
	public void closeConnection() {
		if (conn == null)
			return;
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Connection getConnection() {
		if(conn == null)
			conn = ConnectionPool.getConnection(true);
		return conn;
	}

}
