package Strategy.Mappers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import DataBaseObject.PrimaryKey;
import MapperBuilder.ColumnInfo;

public class FieldsMappingStrategy extends AbstractMapping {

	@Override
	protected <T> List<ColumnInfo> getColumnInfoAndFillPrimaryKey(
			Class<T> klass, ColumnInfo primaryKey) {
		Field[] Fields = klass.getFields();
		List<ColumnInfo> ret = new ArrayList<>(Fields.length);
		ColumnInfo ci;
		for (Field field : Fields) {
			ci=new FieldsColumnInfo(field);
			ret.add(ci);
			if(primaryKey!=null){
				PrimaryKey pka = field.getAnnotation(PrimaryKey.class);
				if(pka!=null)
					primaryKey=ci;
			}
		}
		return ret;
	}
	
	private class FieldsColumnInfo implements ColumnInfo{
		Field field;
		public FieldsColumnInfo (Field field){
			this.field=field;
		}
		@Override
		public String getName() {
			return field.getName();
		}

		@Override
		public Object get(Object obj) {
			Object ret = null;
			try {
				ret = field.get(obj);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				int todo;
				e.printStackTrace();
			}
			return ret;
		}
		
	}
}
