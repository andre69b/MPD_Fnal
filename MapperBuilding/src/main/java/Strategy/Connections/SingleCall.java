package Strategy.Connections;

import java.sql.SQLException;

import com.microsoft.sqlserver.jdbc.SQLServerException;

public class SingleCall extends AbstractConnection {
	public SingleCall() throws SQLServerException {
		super();
	}

	@Override
	public void rollback() {
		try {
			if (connection != null) {
				connection.rollback();
				close();
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
				close();
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
			close();
			connection = ds.getConnection();
			connection.setAutoCommit(autocommit);
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
}
