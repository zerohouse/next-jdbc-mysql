package next.jdbc.mysql;

import java.sql.Connection;
import java.sql.SQLException;

import next.jdbc.mysql.setting.Setting;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class ConnectionPool {

	private static final String COM_MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";

	private static ConnectionPool instance = new ConnectionPool();
	private BoneCP boneCp;
	
	public static Connection getConnection(boolean autocommit) {
		Connection connection = null;
		try {
			connection = instance.boneCp.getConnection();
			if (!autocommit)
				connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public void shutdown() {
		boneCp.shutdown();
	}

	private ConnectionPool() {
		try {
			Class.forName(COM_MYSQL_JDBC_DRIVER);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			BoneCPConfig config = Setting.getDatabase().getConnectionSetting();
			boneCp = new BoneCP(config);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
