package MapperBuilder;

import DataBaseObject.Association;

public class ForeignkeyObject {
	
	public String Table;
	public Association Type;
	public String AttributeName;
	
	
	public ForeignkeyObject(String table, Association type, String attributeName){
		this.Table = table;
		this.Type = type;
		this.AttributeName = attributeName;
	}
}
