package MapperBuilder;

import java.lang.reflect.Constructor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import DataBaseObject.SQLIterableImpl;
import Strategy.Connections.ConnectionStrategy;

public class DataMapperSQL<T> implements DataMapper<T>{

	private ConnectionStrategy connStr;
	private String nameTable;
	private Class<T> klass;
	private List<ColumnInfo> columnsInfo;
	private ColumnInfo primaryKey;
	 
	public DataMapperSQL(String nameTable,ConnectionStrategy conn,Class<T> klass,List<ColumnInfo> nameColumns,ColumnInfo primaryKey){
		this.nameTable=nameTable;
		this.connStr=conn;
		this.klass = klass;
		this.columnsInfo = nameColumns;
		this.primaryKey = primaryKey;
	}
	
	
	@Override
	public SQLIterableImpl<T> getAll() {
		SQLIterableImpl<T> retList = new SQLIterableImpl<T>();
		try{
			PreparedStatement cmd = connStr.getConnection().prepareStatement(
	                "SELECT * FROM ?");
	        cmd.setString(1, nameTable);

			Stream<Constructor<?>> lambda = Arrays.stream(klass.getConstructors()).filter(x->x.getParameterCount()>0);
			
			if(lambda.count()>1)
				throw new RuntimeException();
			
			Constructor<T> constr = (Constructor<T>) lambda.findFirst().get();
	        int columnsSize = columnsInfo.size(),auxIndex;
	        
	        ResultSet rs = cmd.executeQuery();
	        
	        while(rs.next())
	        {
	        	Object[] objs = new Object[columnsSize];
	        	auxIndex=0;
	        	while(auxIndex < columnsSize){
	        		objs[auxIndex] = rs.getObject(columnsInfo.get(auxIndex).getName());
	        		auxIndex++;
	        	}
	        	T t = constr.newInstance(objs);
	        	retList.add(t);
	        }
		}catch(Exception e){
			throw new RuntimeException();
		}
		return retList;
	}

	@Override
	public void update(T val) {
		try{
			StringBuilder prepareStamentString = new StringBuilder("UPDATE ? SET ");
			int indexAux = 0;
			while(indexAux < columnsInfo.size()){
				prepareStamentString.append(columnsInfo.get(indexAux).getName()+" = "+columnsInfo.get(indexAux).get(val)+" ");
			}
			prepareStamentString.append("WHERE ?=?");
			PreparedStatement cmd = connStr.getConnection().prepareStatement(
	                prepareStamentString.toString());
	        cmd.setString(1, nameTable);
	        cmd.setString(2, primaryKey.getName());
	        cmd.setObject(3, primaryKey.get(val));
	        
	        cmd.executeQuery();
		}catch(Exception e){
			throw new RuntimeException();
		}
	}

	@Override
	public void delete(T val) {
		try{
			PreparedStatement cmd = connStr.getConnection().prepareStatement(
					"DELETE FROM ? WHERE ?=?");
	        cmd.setString(1, nameTable);
	        cmd.setString(2, primaryKey.getName());
	        cmd.setObject(3, primaryKey.get(val));
	        
	        cmd.executeQuery();
		}catch(Exception e){
			throw new RuntimeException();
		}
	}

	@Override
	public void insert(T val) {
		try{
			StringBuilder prepareStamentString = new StringBuilder("INSERT INTO ? VALUES(");
			int indexAux = 0;
			while(indexAux < columnsInfo.size()){
				prepareStamentString.append(columnsInfo.get(indexAux).get(val));
				if(indexAux!=columnsInfo.size()-1)
					prepareStamentString.append(",");
			}
			prepareStamentString.append(")");
			PreparedStatement cmd = connStr.getConnection().prepareStatement(
	                prepareStamentString.toString());
	        cmd.setString(1, nameTable);
	        
	        cmd.executeQuery();
		}catch(Exception e){
			throw new RuntimeException();
		}
	}

}
