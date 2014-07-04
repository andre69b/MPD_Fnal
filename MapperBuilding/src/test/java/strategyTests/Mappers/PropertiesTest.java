package strategyTests.Mappers;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import MapperBuilder.Builder;
import MapperBuilder.ColumnInfo;
import MapperBuilder.DataMapper;
import MapperBuilder.DataMapperSQL;
import Strategy.Mappers.PropertiesMappingStrategy;

public class PropertiesTest  {
	
	@Test
	public void PropertiesTestTestClass() throws SQLException{
        
		Builder b = new Builder(new PropertiesMappingStrategy(),null); 
		DataMapper<PropertiesTestClass> TestClassMapper = b.build(PropertiesTestClass.class); 
		String nameTable = (String) getValue(TestClassMapper, "nameTable");
		ColumnInfo primaryKey = (ColumnInfo) getValue(TestClassMapper, "primaryKey");
		List<ColumnInfo> columnsInfo = (List<ColumnInfo>) getValue(TestClassMapper, "columnsInfo");
		assertEquals("TestClassID",primaryKey.getName());   
        assertEquals("TestClasses",nameTable);        
        assertEquals(2,columnsInfo.size()); 
	}

	private Object getValue(DataMapper<PropertiesTestClass> TestClassMapper,String fieldname) {
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
