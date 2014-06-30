package Strategy.Connections;

import java.sql.Connection;

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
}
