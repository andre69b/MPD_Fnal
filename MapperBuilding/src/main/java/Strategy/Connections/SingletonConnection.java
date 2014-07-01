package Strategy.Connections;

import java.sql.Connection;
import java.sql.SQLException;

import com.microsoft.sqlserver.jdbc.SQLServerException;

public class SingletonConnection extends AbstractConnection {
	public SingletonConnection() throws SQLServerException {
		super();
		connection = ds.getConnection();
	}

	@Override
	public Connection getConnection() {
		try {
			if (connection != null) {
				return connection;
			} else {
				throw new UnsupportedOperationException(
						"Connection already closed.");
			}
		} catch (UnsupportedOperationException ex) {
			throw new RuntimeException(ex);
		}
	}
	@Override
	public void rollback() {
		try {
			if (connection != null) {
				connection.rollback();
			} else {
				throw new UnsupportedOperationException(
						"Connection already closed.");
			}
		} catch (SQLException ex) {
		        throw new RuntimeException(ex);
		}
		
	}

	@Override
	public void commit() {
		try {
			if (connection != null) {
				connection.commit();
			} else {
				throw new UnsupportedOperationException(
						"Connection already closed.");
			}
		} catch (SQLException ex) {
		        throw new RuntimeException(ex);
		}
	}
	@Override
	public void beginTransaction(boolean autocommit) {
		try {
			if (connection != null) {
				connection.setAutoCommit(autocommit);
			} else {
				throw new UnsupportedOperationException(
						"Connection already closed.");
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
}

