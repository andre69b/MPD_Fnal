package Strategy.Connections;

import java.sql.Connection;

public interface ConnectionStrategy {
	Connection getConnection();
	void beginTransaction(boolean autocommit);
	void rollback();
	void commit();
	void close();
}
