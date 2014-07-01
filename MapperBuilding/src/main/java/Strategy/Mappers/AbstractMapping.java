package Strategy.Mappers;

import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import DataBaseObject.EDTable;
import MapperBuilder.ColumnInfo;
import MapperBuilder.DataMapper;
import MapperBuilder.DataMapperSQL;
import MapperBuilder.MappingStrategy;
import Strategy.Connections.ConnectionStrategy;

public abstract class AbstractMapping implements MappingStrategy{
	ColumnInfo primaryKey;
	@Override
	public <T> DataMapper<T> build(Class<T> klass, ConnectionStrategy connStr) {
		
		EDTable Table = (EDTable)klass.getAnnotation(EDTable.class);
		if(Table == null)
			throw new UnsupportedOperationException("All ED must have an EDTableAttribute with its TableName!");
		
		primaryKey = null;
		List<ColumnInfo> nameColumns = getColumnInfoAndFillPrimaryKey(klass);

		Stream<Constructor<?>> lambda = Arrays.stream(klass.getConstructors())
				.filter(x -> x.getParameterCount() == nameColumns.size());

		if (lambda.count() > 1)
			throw new RuntimeException();

		Constructor<T> constr = (Constructor<T>) lambda.findFirst().get();
		
		
		return new DataMapperSQL<T>(Table.TableName(), connStr, klass, nameColumns, primaryKey);
	}
	
	protected abstract <T> Member[] getMembers(Class<T> klass);
	protected abstract ColumnInfo createColumnInfo(Member member);
	protected abstract boolean checkPrimaryKeyAnnotation(Member member);
	
	protected <T> List<ColumnInfo> getColumnInfoAndFillPrimaryKey(Class<T> klass) {
		Member[] members = getMembers(klass);
		List<ColumnInfo> ret = new ArrayList<>(members.length);
		ColumnInfo ci;
		for (Member member : members) {
			ci = createColumnInfo(member);
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
