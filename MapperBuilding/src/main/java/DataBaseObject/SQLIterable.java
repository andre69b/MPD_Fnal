package DataBaseObject;

public interface SQLIterable<T> extends Iterable<T>, AutoCloseable {
	SQLExtensionMethods<T> where(String clause);

	int count();
}