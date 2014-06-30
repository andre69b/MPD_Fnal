package Strategy.Connections;

import java.sql.Connection;

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
}

