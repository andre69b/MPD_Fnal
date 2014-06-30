package MapperBuilder;

import Strategy.Connections.ConnectionStrategy;

public interface MappingStrategy {
	<T> DataMapper<T> build(Class<T> klass, ConnectionStrategy connStr);
}
