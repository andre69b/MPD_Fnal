package Strategy.Mappers;

import java.util.List;

import DataBaseObject.EDTable;
import MapperBuilder.ColumnInfo;
import MapperBuilder.DataMapper;
import MapperBuilder.DataMapperSQL;
import MapperBuilder.MappingStrategy;
import Strategy.Connections.ConnectionStrategy;

public abstract class AbstractMapping implements MappingStrategy{
	
	@Override
	public <T> DataMapper<T> build(Class<T> klass, ConnectionStrategy connStr) {
		
		EDTable Table = (EDTable)klass.getAnnotation(EDTable.class);
		if(Table == null)
			throw new UnsupportedOperationException("All ED must have an EDTableAttribute with its TableName!");
		
		String tableName = Table.TableName();
		ColumnInfo primaryKey = null;
		List<ColumnInfo> nameColumns = getColumnInfoAndFillPrimaryKey(klass,primaryKey);
		
		return new DataMapperSQL<T>(tableName, connStr, klass, nameColumns, primaryKey);
	}
	protected abstract <T> List<ColumnInfo> getColumnInfoAndFillPrimaryKey(Class<T> klass,ColumnInfo primaryKey);

}
