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
import MapperBuilder.ForeignkeyObject;
import MapperBuilder.MappingStrategy;
import Strategy.Connections.ConnectionStrategy;

public abstract class AbstractMapping implements MappingStrategy{
	private final int LEVEL=1;
	private int currentLevel=0;
	private ColumnInfo primaryKey;
	
	@Override
	public <T> DataMapper<T> build(Class<T> klass, ConnectionStrategy connStr) {
		
		EDTable Table = (EDTable)klass.getAnnotation(EDTable.class);
		if(Table == null)
			throw new UnsupportedOperationException("All ED must have an EDTableAttribute with its TableName!");
		
		primaryKey = null;
		List<ColumnInfo> nameColumns = getColumnInfoAndFillPrimaryKey(klass);
		
		//modifiedColumnsInfoForeignKey();

		Constructor<T> constr = (Constructor<T>)getConstrutor(klass, nameColumns.size());
		
		
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
		List<ForeignkeyObject> ret2 = new LinkedList<ForeignkeyObject>();
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
			Annotation a = annotations[0];
			if(primaryKey==null && a instanceof PrimaryKey){
				ret.add(ci);
				primaryKey = ci;
				continue;
			}
			if(currentLevel<LEVEL && a instanceof ForeignKey){
				ForeignKey v = (ForeignKey)a;
				Class<?> k = v.Type();
				++currentLevel;	
				List<ColumnInfo> list = getColumnInfoAndFillPrimaryKey(k);
				ret2.add(
					new ForeignkeyObject( 
						k,
						((EDTable)k.getAnnotation(EDTable.class)).TableName(),
						list,
						v.Association(),
						v.AttributeName(),
						getConstrutor(k,list.size())
				));
				--currentLevel;
			}
		}
		return ret;
	}
	private  Constructor<?> getConstrutor(Class<?> klass, int numberOfParameters){
		try {
			return  Arrays.stream(klass.getConstructors())
					.filter(x -> x.getParameterCount() == numberOfParameters)
					.findFirst()
					.get();
		} catch (NoSuchElementException e) {
			throw new UnsupportedOperationException("All ED must have a Constructor with all parameters!");
		}	
	}
}
