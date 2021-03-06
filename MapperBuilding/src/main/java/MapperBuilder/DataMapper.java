package MapperBuilder;

import DataBaseObject.SQLIterableImpl;

public interface DataMapper<T> {

	public SQLIterableImpl<T> getAll();

	public void update(T val);

	public void delete(T val);

	public int insert(T val);

	public T getById(Object key, String attributeName);
}
