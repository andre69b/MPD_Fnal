package MapperBuilder;

public interface ColumnInfo {
	public String getName();

	public Object get(Object val);

	public void set(Object val, Object value);
}
