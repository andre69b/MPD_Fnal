package Strategy.Mappers;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import DataBaseObject.EDTable;
import DataBaseObject.PrimaryKey;
import MapperBuilder.Builder;
import MapperBuilder.ColumnInfo;
import MapperBuilder.DataMapper;
import MapperBuilder.DataMapperSQL;

public class PropertiesTest  {
	private static final String TableName="TestClasses";
	@EDTable(TableName = TableName)
	private class TestClass {
		
		public String TestClassID;
		public String what;
		
		public TestClass(String TestClassID, String what) {
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
	
	@Test
	public void PropertiesTestTestClass() throws SQLException{
        
		Builder b = new Builder(new PropertiesMappingStrategy(),null); 
		DataMapper<TestClass> TestClassMapper = b.build(TestClass.class); 
		String nameTable = (String) getValue(TestClassMapper, "nameTable");
		ColumnInfo primaryKey = (ColumnInfo) getValue(TestClassMapper, "primaryKey");
		List<ColumnInfo> columnsInfo = (List<ColumnInfo>) getValue(TestClassMapper, "columnsInfo");
		
		assertEquals("TestClassID",primaryKey.getName());   
        assertEquals(TableName,nameTable);        
        assertEquals(2,columnsInfo.size()); 
	}

	private Object getValue(DataMapper<TestClass> TestClassMapper,String fieldname) {
		Object ret=null;
		try {
			Field nameTablefield = DataMapperSQL.class.getDeclaredField(fieldname);
			nameTablefield.setAccessible(true);
			ret = nameTablefield.get(TestClassMapper);
		} catch (IllegalArgumentException | IllegalAccessException
				| NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		return ret;
	}
}
