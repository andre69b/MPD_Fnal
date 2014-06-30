package MapperBuilder;

public interface MappingStrategy {
	<T> DataMapper<T> build(Class<T> klass, ConnectionStrategy connStr);
}
