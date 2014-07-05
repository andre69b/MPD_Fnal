package MapperBuilder;

import java.util.List;
import java.util.Map;

import DataBaseObject.Association;
import Strategy.Connections.ConnectionStrategy;

public class ForeignkeyObject implements ColumnInfo{

	public Class<Object> klass;
	public List<ColumnInfo> columnInfos;
	public String table;
	public Association type;
	public String attributeName;
	private ColumnInfo ci;
	private ConnectionStrategy connStr;

	public ForeignkeyObject(Class<Object> klass, String table,
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
		Map <String,List<ColumnInfo>> mapColumns = null;
		ColumnInfo primaryKey = null;
		Iterable<Object> iter = new DataMapperSQL<Object>(table, connStr, klass, mapColumns, primaryKey)
		.getAll().where(attributeName+" = ?")
		.bind(valueAttributeName);
		ci.set(instance, iter);
	}
}
