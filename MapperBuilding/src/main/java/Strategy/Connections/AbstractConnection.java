package Strategy.Connections;

import java.sql.Connection;
import java.sql.SQLException;

import Exception.MyRuntimeException;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public abstract class AbstractConnection implements ConnectionStrategy {
	protected final String USER = "MPDProject";
	protected final String PASSWORD = "123456";
	protected final String SERVERNAME = "localhost";
	protected final int PORTNUMBER = 1433;
	protected final String DATABASENAME = "Northwind";

	protected Connection connection;
	protected SQLServerDataSource ds;

	public AbstractConnection() {
		this.ds = new SQLServerDataSource();
		ds.setUser(USER);
		ds.setPassword(PASSWORD);
		ds.setServerName(SERVERNAME);
		ds.setPortNumber(PORTNUMBER);
		ds.setDatabaseName(DATABASENAME);
	}

	@Override
	public Connection getConnection() {
		if (connection != null) {
			return connection;
		} else {
			throw new MyRuntimeException(new UnsupportedOperationException(
					"Connection already closed."));
		}
	}

	@Override
	public void close() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException ex) {
				throw new MyRuntimeException(ex);
			}
			connection = null;
		}
	}
}
