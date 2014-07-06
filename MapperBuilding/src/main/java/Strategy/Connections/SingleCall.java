package Strategy.Connections;

import java.sql.SQLException;

import Exception.MyRuntimeException;

public class SingleCall extends AbstractConnection {
	public SingleCall() {
		super();
	}

	@Override
	public void rollback() {
		try {
			if (connection != null) {
				connection.rollback();
				close();
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
				close();
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
			close();
			connection = ds.getConnection();
			connection.setAutoCommit(autocommit);
		} catch (SQLException ex) {
			throw new MyRuntimeException(ex);
		}
	}
}
