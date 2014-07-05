package MapperBuilder;

import java.util.List;

import DataBaseObject.Association;

public class ForeignkeyObject {
	
	public Class<?> Klass;
	public List<ColumnInfo> ColumnInfos;
	public String Table;
	public Association Type;
	public String AttributeName;
	
	public ForeignkeyObject(Class<?> klass,String table,List<ColumnInfo> columnInfos, Association type, String attributeName){
		this.Klass = klass;
		this.Table = table;
		this.ColumnInfos = columnInfos;
		this.Type = type;
		this.AttributeName = attributeName;
	}
}
