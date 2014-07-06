package Strategy.Mappers;

import MapperBuilder.DataMapper;
import Strategy.Connections.ConnectionStrategy;

public interface MappingStrategy {
	<T> DataMapper<T> build(Class<T> klass, ConnectionStrategy connStr);
}
