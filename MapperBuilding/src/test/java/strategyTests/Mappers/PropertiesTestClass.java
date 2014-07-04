package strategyTests.Mappers;

import DataBaseObject.EDTable;
import DataBaseObject.PrimaryKey;

@EDTable(TableName = "TestClasses")
public class PropertiesTestClass {
	
	public String TestClassID;
	public String what;
	
	public PropertiesTestClass(String TestClassID, String what) {
		this.TestClassID = TestClassID;
		this.what = what;
	}
	
	@PrimaryKey
	public String getTestClassID(){
		return TestClassID;
	}
	public String getWhat(){
		return what;
	}
	public int getint(int i){
		return i;
	}
	public int togetzero(){
		return 0;
	}
}
