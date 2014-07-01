package Strategy.Connections;

import java.sql.SQLException;

public class SingletonConnection extends AbstractConnection {
	public SingletonConnection() {
		super();
		try {
			connection = ds.getConnection();
		} catch (SQLException ex) {
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

