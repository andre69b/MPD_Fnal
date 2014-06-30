package Strategy.Mappers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import DataBaseObject.PrimaryKey;
import MapperBuilder.ColumnInfo;

public class PropertiesMappingStrategy extends AbstractMapping {
	
	@Override
	protected <T> List<ColumnInfo> getColumnInfoAndFillPrimaryKey(
			Class<T> klass, ColumnInfo primaryKey) {
		Method[] methods = klass.getMethods();
		List<ColumnInfo> ret = new ArrayList<>(methods.length);
		ColumnInfo ci;
		for (Method method : methods) {
			if(!method.getName().contains("Get"))
				continue;
			ret.add(new PropertiesColumnInfo(method));
			ci=new PropertiesColumnInfo(method);
			ret.add(ci);
			if(primaryKey!=null){
				PrimaryKey pka = method.getAnnotation(PrimaryKey.class);
				if(pka!=null)
					primaryKey=ci;
			}
		}
		return ret;
	}
	
	
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
}
