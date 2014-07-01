package Strategy.Connections;

import java.sql.Connection;
import java.sql.SQLException;

import com.microsoft.sqlserver.jdbc.SQLServerException;

public class SingleCall extends AbstractConnection {
	public SingleCall() throws SQLServerException{
		super();
	}
	@Override
	public Connection getConnection() {
		try {
			close();
			return connection = ds.getConnection();
		} catch (Exception ex) {
	        throw new RuntimeException(ex);
		}
	}
	@Override
	public void rollback() {
		try {
			connection.rollback();
			close();
		} catch (SQLException ex) {
		        throw new RuntimeException(ex);
		}
		
	}

	@Override
	public void commit() {
		try {
			connection.commit();
			close();
		} catch (SQLException ex) {
		        throw new RuntimeException(ex);
		}
	}
}
