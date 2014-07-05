package Strategy.Mappers;

import java.lang.annotation.Annotation;
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
				//TODO
				e.printStackTrace();
			}
			return ret;
		}
		@Override
		public void set(Object val, Object value) {
			try {
				field.set(val,value);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				int todo;
				//TODO
				e.printStackTrace();
			}
		}
	}

	@Override
	protected <T> Member[] getMembers(Class<T> klass) {
		return klass.getFields();
	}

	@Override
	protected ColumnInfo createColumnInfo(Member member) {
		return new FieldsColumnInfo((Field)member);
	}

	@Override
	protected Annotation[] getMemberAnnotations(Member member) {
		return ((Field)member).getDeclaredAnnotations();
	}
}
