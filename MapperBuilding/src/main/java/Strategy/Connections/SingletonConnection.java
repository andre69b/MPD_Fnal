package Strategy.Connections;

import java.sql.SQLException;

import Exception.MyRuntimeException;

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
				throw new MyRuntimeException(new UnsupportedOperationException(
						"Connection already closed."));
			}
		} catch (SQLException ex) {
			throw new MyRuntimeException(ex);
		}

	}

	@Override
	public void commit() {
		try {
			if (connection != null) {
				connection.commit();
			} else {
				throw new MyRuntimeException(new UnsupportedOperationException(
						"Connection already closed."));
			}
		} catch (SQLException ex) {
			throw new MyRuntimeException(ex);
		}
	}

	@Override
	public void beginConnection(boolean autocommit) {
		try {
			if (connection != null) {
				connection.setAutoCommit(autocommit);
			} else {
				throw new MyRuntimeException(new UnsupportedOperationException(
						"Connection already closed."));
			}
		} catch (SQLException ex) {
			throw new MyRuntimeException(ex);
		}
	}
}
