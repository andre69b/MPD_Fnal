package DataBaseObject;

public interface SQLIterable<T> extends Iterable<T>, AutoCloseable{
	SQLIterable<T> where(String clause);
	int count();
}