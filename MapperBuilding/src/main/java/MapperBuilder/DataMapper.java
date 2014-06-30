package MapperBuilder;

public interface DataMapper<T> {

	public Iterable<T> getAll();
	public void update(T val);
	public void delete(T val);
	public void insert(T val);
}
