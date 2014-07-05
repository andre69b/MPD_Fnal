package Strategy.Mappers;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

import MapperBuilder.ColumnInfo;

public class PropertiesMappingStrategy extends AbstractMapping {
	private static final String GET = "get";

	private class PropertiesColumnInfo implements ColumnInfo {

		Method method;
		String name;

		public PropertiesColumnInfo(Method method) {
			this.method = method;
			String name = method.getName();
			this.name = name.substring(3, name.length());
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public Object get(Object obj) {
			Object ret = null;
			try {
				ret = method.invoke(obj);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				int todo;
				e.printStackTrace();
			}
			return ret;
		}

		@Override
		public void set(Object val, Object value) {
			try {
				method.invoke(val, value);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				int todo;
				e.printStackTrace();
			}
		}
	}

	@Override
	protected <T> Member[] getMembers(Class<T> klass) {
		return klass.getDeclaredMethods();
	}

	@Override
	protected ColumnInfo createColumnInfo(Member member) {
		String get = member.getName().substring(0, 3);
		if (get.equals(GET)) {
			Method toTest = (Method) member;
			if (toTest.getParameterTypes().length == 0)
				return new PropertiesColumnInfo(toTest);

		}
		return null;
	}

	@Override
	protected Annotation[] getMemberAnnotations(Member member) {
		return ((Method) member).getDeclaredAnnotations();
	}
}
