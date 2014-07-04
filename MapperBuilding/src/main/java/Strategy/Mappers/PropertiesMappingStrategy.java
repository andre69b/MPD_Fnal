package Strategy.Mappers;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

import DataBaseObject.PrimaryKey;
import MapperBuilder.ColumnInfo;

public class PropertiesMappingStrategy extends AbstractMapping {	
	private static final String GET = "get";
	private class PropertiesColumnInfo implements ColumnInfo{
		
		Method method;
		String name;
		public PropertiesColumnInfo (Method method){
			this.method=method;
			this.name=method.getName().replace(GET, "");
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
		return klass.getDeclaredMethods();
	}

	@Override
	protected ColumnInfo createColumnInfo(Member member) {
		if(member.getName().contains(GET)){
			Method toTest = (Method) member;
			if(toTest.getParameterTypes().length==0)
				return new PropertiesColumnInfo(toTest);
			
		}
		return null;
	}

	@Override
	protected Annotation[] getMemberAnnotations(Member member) {
		return ((Method)member).getDeclaredAnnotations();
	}
}
