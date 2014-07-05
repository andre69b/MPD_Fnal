package MapperBuilder;

import java.sql.PreparedStatement;
import java.util.List;

import DataBaseObject.Association;
import Strategy.Connections.ConnectionStrategy;

public class ForeignkeyObject implements ColumnInfo{

	public Class<?> klass;
	public List<ColumnInfo> columnInfos;
	public String table;
	public Association type;
	public String attributeName;
	private ColumnInfo ci;
	private PreparedStatement cmd;
	private ConnectionStrategy connStr;

	public ForeignkeyObject(Class<?> klass, String table,
			List<ColumnInfo> columnInfos, Association type, String attributeName,ColumnInfo ci, ConnectionStrategy connStr) {
		this.klass = klass;
		this.ci=ci;
		this.table = table;
		this.columnInfos = columnInfos;
		this.type = type;
		this.attributeName = attributeName;
		this.connStr = connStr;
	}

	@Override
	public String getName() {
		return ci.getName();
	}

	@Override
	public Object get(Object instance) {
		return ci.get(instance);
	}

	@Override
	public void set(Object instance, Object valueAttributeName) {
		Iterable<?> iter=null;
		new DataMapperSQL<>(table, conn, klass, mapColumns, primaryKey)
		ci.set(instance, iter);
	}
}
