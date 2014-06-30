package Strategy.Mappers;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

import DataBaseObject.EDTable;
import MapperBuilder.ColumnInfo;
import MapperBuilder.ConnectionStrategy;
import MapperBuilder.DataMapper;
import MapperBuilder.DataMapperSQL;
import MapperBuilder.MappingStrategy;

public abstract class AbstractMapping implements MappingStrategy{
	ColumnInfo primaryKey;
	@Override
	public <T> DataMapper<T> build(Class<T> klass, ConnectionStrategy connStr) {
		
		EDTable Table = (EDTable)klass.getAnnotation(EDTable.class);
		if(Table == null)
			throw new UnsupportedOperationException("All ED must have an EDTableAttribute with its TableName!");
		
		primaryKey = null;
		List<ColumnInfo> nameColumns = getColumnInfoAndFillPrimaryKey(klass);

		return new DataMapperSQL<T>(Table.TableName(), connStr, klass, nameColumns, primaryKey);
	}
	
	protected abstract <T> Member[] getMembers(Class<T> klass);
	protected abstract ColumnInfo getColumnInfo(Member member);
	protected abstract boolean checkPrimaryKeyAnnotation(Member member);
	
	protected <T> List<ColumnInfo> getColumnInfoAndFillPrimaryKey(Class<T> klass) {
		Member[] members = getMembers(klass);
		List<ColumnInfo> ret = new ArrayList<>(members.length);
		ColumnInfo ci;
		for (Member member : members) {
			ci = getColumnInfo(member);
			if(ci==null)
				continue;
			ret.add(ci);
			if(primaryKey==null && checkPrimaryKeyAnnotation(member) ){
				primaryKey=ci;
			}
		}
		return ret;
	}
}
