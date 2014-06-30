package Strategy.Mappers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

import DataBaseObject.PrimaryKey;
import MapperBuilder.ColumnInfo;

public class PropertiesMappingStrategy extends AbstractMapping {	
	
	private class PropertiesColumnInfo implements ColumnInfo{
		Method method;
		String name;
		public PropertiesColumnInfo (Method method){
			this.method=method;
			this.name=method.getName().replace("Get", "");
		}
		@Override
		public String getName() {
			return name;
		}

		@Override
		public Object get(Object obj) {
			Object ret = null;
			try {
				ret = method.invoke(obj,(Object) null);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				int todo;
				e.printStackTrace();
			}
			return ret;
		}
	}

	@Override
	protected <T> Member[] getMembers(Class<T> klass) {
		return klass.getMethods();
	}

	@Override
	protected ColumnInfo getColumnInfo(Member member) {
		if(member.getName().contains("Get"))
			return new PropertiesColumnInfo((Method) member);
		else return null;
	}

	@Override
	protected boolean checkPrimaryKeyAnnotation(Member member) {
		PrimaryKey pka = ((Method)member).getAnnotation(PrimaryKey.class);
		return pka!=null;
	}
}
