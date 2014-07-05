package MapperBuilder;

import DataBaseObject.SQLIterableImpl;

public interface DataMapper<T> {

	public SQLIterableImpl<T> getAll();

	public void update(T val);

	public void delete(T val);

	public void insert(T val);
}
