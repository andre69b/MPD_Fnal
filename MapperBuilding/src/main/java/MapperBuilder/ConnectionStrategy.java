package MapperBuilder;

import java.sql.Connection;

public interface ConnectionStrategy {
	Connection getConnection();
	void autoCommit(boolean autocommit);
	void rollback();
	void commit();
}
