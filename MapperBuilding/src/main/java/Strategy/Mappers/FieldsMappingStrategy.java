package Strategy.Mappers;

import java.lang.reflect.Field;
import java.lang.reflect.Member;

import DataBaseObject.PrimaryKey;
import MapperBuilder.ColumnInfo;

public class FieldsMappingStrategy extends AbstractMapping {
	
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

	@Override
	protected <T> Member[] getMembers(Class<T> klass) {
		return klass.getFields();
	}

	@Override
	protected ColumnInfo getColumnInfo(Member member) {
		return new FieldsColumnInfo((Field)member);
	}

	@Override
	protected boolean checkPrimaryKeyAnnotation(Member member) {
		PrimaryKey pka = ((Field)member).getAnnotation(PrimaryKey.class);
		return pka!=null;
	}
}
