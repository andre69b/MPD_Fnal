package MapperBuilder;

import java.util.List;
import java.util.Map;

import DataBaseObject.Association;
import Strategy.Connections.ConnectionStrategy;

public class ForeignkeyObject<T> implements ColumnInfo {

	public Class<T> klass;
	public Map<String, List<ColumnInfo>> mapColumnsInfo;
	public String table;
	public String keyName;
	public Association type;
	public String attributeName;
	private ColumnInfo ci;
	private ConnectionStrategy connStr;

	public ForeignkeyObject(Class<T> klass, String table, String keyName,
			Map<String, List<ColumnInfo>> mapColumnsInfo, Association type,
			String attributeName, ColumnInfo ci, ConnectionStrategy connStr) {
		this.klass = klass;
		this.ci = ci;
		this.table = table;
		this.mapColumnsInfo = mapColumnsInfo;
		this.type = type;
		this.attributeName = attributeName;
		this.connStr = connStr;
		this.keyName = keyName;
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
		if (type.equals(Association.Multiple)) {
			Iterable<T> iter = new DataMapperSQL<T>(table, connStr, klass,
					mapColumnsInfo).getAll().where(keyName + " = ?")
					.bind(valueAttributeName);
			ci.set(instance, iter);
		} else {
			T t = new DataMapperSQL<T>(table, connStr, klass, mapColumnsInfo)
					.getById(valueAttributeName, keyName);
			ci.set(instance, t);
		}
	}
}
