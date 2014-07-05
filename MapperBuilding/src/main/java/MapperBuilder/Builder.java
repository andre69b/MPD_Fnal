package MapperBuilder;

import Strategy.Connections.ConnectionStrategy;

public class Builder {

	private MappingStrategy mapStr;
	private ConnectionStrategy connStr;

	public Builder(MappingStrategy mapStr, ConnectionStrategy connStr) {
		this.mapStr = mapStr;
		this.connStr = connStr;
	}

	public <T> DataMapper<T> build(Class<T> klass) {
		return mapStr.build(klass, connStr);
	}
}
