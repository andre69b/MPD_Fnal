package Strategy.Mappers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import DataBaseObject.Association;
import DataBaseObject.EDTable;
import DataBaseObject.ForeignKey;
import DataBaseObject.PrimaryKey;
import MapperBuilder.ColumnInfo;
import MapperBuilder.DataMapper;
import MapperBuilder.DataMapperSQL;
import MapperBuilder.MappingStrategy;
import Strategy.Connections.ConnectionStrategy;

public abstract class AbstractMapping implements MappingStrategy{
	private ColumnInfo primaryKey;
	
	@Override
	public <T> DataMapper<T> build(Class<T> klass, ConnectionStrategy connStr) {
		
		EDTable Table = (EDTable)klass.getAnnotation(EDTable.class);
		if(Table == null)
			throw new UnsupportedOperationException("All ED must have an EDTableAttribute with its TableName!");
		
		primaryKey = null;
		List<ColumnInfo> nameColumns = getColumnInfoAndFillPrimaryKey(klass);
		
		//modifiedColumnsInfoForeignKey();

		Constructor<T> constr = null;
		
		try {
			Constructor<?>[] i = klass.getConstructors();
			Class<?>[] j= i[0].getParameterTypes();
			int c = i[0].getParameterCount();
			constr = (Constructor<T>) Arrays.stream(klass.getConstructors())
					.filter(x -> x.getParameterCount() == nameColumns.size())
					.findFirst()
					.get();
		} catch (NoSuchElementException e) {
			throw new UnsupportedOperationException("All ED must have a Constructor with all parameters!");
		}	
		
		return new DataMapperSQL<T>(Table.TableName(), connStr, klass, nameColumns, primaryKey,constr);
	}
	
	/*private void modifiedColumnsInfoForeignKey() {
		for(Coluim c : listForeignKey){
			
		}
	}*/

	protected abstract <T> Member[] getMembers(Class<T> klass);
	protected abstract ColumnInfo createColumnInfo(Member member);
	protected abstract Annotation[] getMemberAnnotations(Member member);
	
	protected <T> List<ColumnInfo> getColumnInfoAndFillPrimaryKey(Class<T> klass) {
		Member[] members = getMembers(klass);
		List<ColumnInfo> ret = new ArrayList<ColumnInfo>(members.length);
		List<ColumnInfo> ret2 = new LinkedList<ColumnInfo>();
		ColumnInfo ci=null;
		for (Member member : members) {
			Annotation[] annotations = getMemberAnnotations(member);
			if(annotations.length>1)
				throw new RuntimeException("Not supported more than one annotation per member");
			
			ci=createColumnInfo(member);
			if(ci==null)
				continue;
			
			if(annotations.length==0){
				ret.add(ci);
				continue;
			}
			
			if(primaryKey==null && annotations[0] instanceof PrimaryKey){
				ret.add(ci);
				primaryKey = ci;
				continue;
			}
			if(annotations[0] instanceof ForeignKey){
				ret2.add(ci);
				//TODO
			}
		}
		return ret;
	}
}
