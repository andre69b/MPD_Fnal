package strategyTests.Mappers;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.sql.SQLException;

import org.junit.Test;

import DataBaseObject.EDTable;
import DataBaseObject.PrimaryKey;
import MapperBuilder.Builder;
import MapperBuilder.ColumnInfo;
import MapperBuilder.DataMapper;
import MapperBuilder.DataMapperSQL;
import Strategy.Mappers.FieldsMappingStrategy;

public class FieldsTest  {
	private static final String TableName="TestClasses";
	@EDTable(TableName = TableName)
	private class TestClass {
		
		@PrimaryKey
		public String TestClassID;
		@SuppressWarnings("unused")
		public String what;
		
		@SuppressWarnings("unused")
		public TestClass(String TestClassID, String what) {
			this.TestClassID = TestClassID;
			this.what = what;
		}
	}
	
	@Test
	public void FieldsTestTestClass() throws SQLException{
        
		Builder b = new Builder(new FieldsMappingStrategy(),null); 
		DataMapper<TestClass> TestClassMapper = b.build(TestClass.class); 
		String nameTable = (String) getValue(TestClassMapper, "nameTable");
		ColumnInfo primaryKey = (ColumnInfo) getValue(TestClassMapper, "primaryKey");
		
		assertEquals("TestClassID",primaryKey.getName());   
        assertEquals(TableName,nameTable);        
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
